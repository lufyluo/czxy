package com.czxy.manage.service;

import com.czxy.manage.dao.AddressMapper;
import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.apache.logging.log4j.core.util.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private OrgMapper orgMapper;
    @Autowired
    private OrgService orgService;

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

    public ClassInformationInfo query(Integer id) {
        ClassInformationEntity classInformationEntity = classMapper.query(id);
        ClassInformationInfo classInformationInfo = PojoMapper.INSTANCE.toClassInformationInfo(classInformationEntity);
        return classInformationInfo;
    }

    public PageInfo<ClassStudentInfo> pageStudent(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ClassStudentEntity> classStudentEntities = classMapper.queryAllStudent(Integer.valueOf(pageParam.getParam()));
        for (int i = 0; i < classStudentEntities.size(); i++) {
            ClassStudentEntity classStudentEntity = classStudentEntities.get(i);
            if (classStudentEntity.getStudentType() == 0) {
                classStudentEntity.setStudentTypeName("学员");
            } else if (classStudentEntity.getStudentType() == 1) {
                classStudentEntity.setStudentTypeName("班委干部");
            } else if (classStudentEntity.getStudentType() == 8) {
                classStudentEntity.setStudentTypeName("带班领导");
            }
        }
        PageInfo<ClassStudentInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toClassStudentInfos(classStudentEntities));
        PageInfo<ClassStudentInfo> classStudentInfo = page.toPageInfo();
        return classStudentInfo;
    }

    public Boolean create(ClassCreateInfo classCreateInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(),classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(),classCreateInfo.getRecommendOrgId());

        return true;

    }
}
