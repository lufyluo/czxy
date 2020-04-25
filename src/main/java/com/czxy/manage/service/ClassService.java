package com.czxy.manage.service;

import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.classes.ClassInfo;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class ClassService {
    @Resource
    private ClassMapper classMapper;
    @Resource
    private TypeMapper typeMapper;

    public PageInfo<ClassOrgInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ClassOrgEntity> classEntities = classMapper.queryAll(pageParam.getParam());
        PageInfo<ClassOrgInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toClassOrgInfos(classEntities));
        PageInfo<ClassOrgInfo> userAccountPageInfo = page.toPageInfo();
        fetchTopics(userAccountPageInfo.getList());
        return userAccountPageInfo;
    }

    private void fetchTopics(List<ClassOrgInfo> classOrgInfos) {
        List<TypeEntity> types = getTypes(classOrgInfos);
        classOrgInfos.forEach(n -> {
            if (StringUtil.isNotEmpty(n.getTopics())) {
                List<String> idsTemp = Arrays.asList(
                        n.getTopics().split(","));
                n.setTopicNames(types.stream()
                        .filter(item -> idsTemp.contains(item.getId() + ""))
                        .map(TypeEntity::getName)
                        .collect(Collectors.joining(",")));
            }
        });
    }

    private List<TypeEntity> getTypes(List<ClassOrgInfo> classOrgInfos) {
        List<String> partionTopicIds = classOrgInfos.stream().map(ClassOrgInfo::getTopics).collect(Collectors.toList());
        List<Integer> topicIds = new ArrayList<>();
        for (String strs :
                partionTopicIds) {
            if (StringUtil.isNotEmpty(strs)) {
                Arrays.asList(
                        strs.split(",")).stream().flatMapToInt(num -> IntStream.of(Integer.parseInt(num))).forEach(n -> {
                    topicIds.add(n);
                });

            }
        }
        return typeMapper.queryAll(topicIds);
    }

    public Boolean delete(Integer id) {
        classMapper.delete(id);
        return true;
    }

    public List<ClassInfo> query(Integer id) {
        List<ClassInfo> classInfo = classMapper.query(id);
        return classInfo;
    }
}
