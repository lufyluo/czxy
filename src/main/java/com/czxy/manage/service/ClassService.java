package com.czxy.manage.service;

import com.czxy.manage.dao.*;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    private TypeService typeService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private StudentService studentService;
    @Resource
    private ClassMasterMapper classMasterMapper;
    @Resource
    private ClassExcuteCourseMapper classCourseMapper;

    public PageInfo<ClassOrgInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ClassOrgEntity> classEntities = classMapper.queryAll(pageParam.getParam());
        PageInfo<ClassOrgInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toClassOrgInfos(classEntities));
        //fetchTopics(userAccountPageInfo.getList());
        return result;
    }

    private void fetchTopics(List<ClassOrgInfo> classOrgInfos) {
        List<TypeInfo> types = getTypes(classOrgInfos);
        classOrgInfos.forEach(n -> {
            if (StringUtil.isNotEmpty(n.getTopics())) {
                List<String> idsTemp = Arrays.asList(
                        n.getTopics().split(","));
                n.setTopicInfos(types.stream()
                        .filter(item -> idsTemp.contains(item.getId() + ""))
                        .collect(Collectors.toList()));
            }
        });
    }

    private List<TypeInfo> getTypes(List<ClassOrgInfo> classOrgInfos) {
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
        List<TypeEntity> typeEntities = typeMapper.queryAll(topicIds);

        return PojoMapper.INSTANCE.toTypeInfos(typeEntities);
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
        return result;
    }

    @Transactional
    public Boolean create(ClassCreateInfo classCreateInfo) {
        buildData(classCreateInfo);
        ClassEntity classEntity = PojoMapper.INSTANCE.classCreateInfoToClassEntity(classCreateInfo);
        classMapper.insert(classEntity);
        classMasterMapper.insertMaster(classCreateInfo.getMasterId(), classEntity.getId());
        if (classCreateInfo.getStudentAddInfos() != null && classCreateInfo.getStudentAddInfos().size() > 0) {
            classCreateInfo.getStudentAddInfos().forEach(n -> {
                n.setOrgId(classCreateInfo.getOrgId());
            });
            studentService.batchInsert(classCreateInfo.getStudentAddInfos());
        }
        if (classCreateInfo.getClassArrangeId() != null) {
            classCourseMapper.copySnapshot(classCreateInfo.getClassArrangeId());
        }
        studentService.setClassLeader(classCreateInfo.getLeaderId(),classEntity.getId());
        return true;
    }

    public Boolean update(ClassCreateInfo classCreateInfo) {
        buildData(classCreateInfo);
        ClassEntity classEntity = PojoMapper.INSTANCE.classCreateInfoToClassEntity(classCreateInfo);
        classMapper.update(classEntity);
        studentService.setClassLeader(classCreateInfo.getLeaderId(),classEntity.getId());
        return true;
    }

    private void buildData(ClassCreateInfo classCreateInfo){
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(), classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(), classCreateInfo.getRecommendOrgId());
        classCreateInfo.setOrgId(orgId);
        classCreateInfo.setRecommendOrgId(recommendOrgId);
        saveTopics(classCreateInfo);
    }
    private void saveTopics(ClassCreateInfo classCreateInfo){
        if(classCreateInfo.getTopicInfos() ==null || classCreateInfo.getTopicInfos().size() == 0){
            classCreateInfo.setTopics(null);
            return;
        }
        List<TypeEntity> topics = PojoMapper.INSTANCE.toTypeEntities(classCreateInfo.getTopicInfos());
        typeService.batchInsertIfObsent(topics);
        classCreateInfo.setTopics(topics.stream().map(n->n.getId().toString()).collect(Collectors.joining(",")));
    }
}
