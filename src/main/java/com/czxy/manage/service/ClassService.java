package com.czxy.manage.service;

import com.czxy.manage.dao.ClassExcuteCourseMapper;
import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.ClassMasterMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.ClassInformationEntity;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.ClassStudentEntity;
import com.czxy.manage.model.vo.classes.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassService {
    @Resource
    private ClassMapper classMapper;
    @Autowired
    private OrgService orgService;
    @Autowired
    private StudentService studentService;
    @Resource
    private ClassMasterMapper classMasterMapper;
    @Resource
    private ClassExcuteCourseMapper classCourseMapper;
    @Autowired
    private CompositionService compositionService;

    public PageInfo<ClassOrgInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ClassOrgEntity> classEntities = classMapper.queryAll(pageParam.getParam());
        PageInfo<ClassOrgInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toClassOrgInfos(classEntities));
        //fetchTopics(userAccountPageInfo.getList());
        return result;
    }

    public Boolean delete(List<Integer> id) {
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
        Integer classId = null;
        if (pageParam.getParam()!=null&&pageParam.getParam()!=""){
            classId = Integer.valueOf(pageParam.getParam());
        }
            List<ClassStudentEntity> classStudentEntities = classMapper.queryAllStudent(classId);
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
        Integer compositionId = compositionService.insertIfAbsent(classCreateInfo.getCompositionId(),classCreateInfo.getComposition());
        classEntity.setCompositionId(compositionId);
        classMapper.insert(classEntity);
        if(classCreateInfo.getMasterId()!=null&&classCreateInfo.getMasterId()>0){
            classMasterMapper.insertMaster(classCreateInfo.getMasterId(), classEntity.getId());
        }
        if (classCreateInfo.getStudentAddInfos() != null && classCreateInfo.getStudentAddInfos().size() > 0) {
            classCreateInfo.getStudentAddInfos().forEach(n -> {
                n.setOrgId(classCreateInfo.getOrgId());
                n.setClassId(classEntity.getId());
            });
            studentService.batchInsert(
                    classCreateInfo.getStudentAddInfos()
                            .stream()
                            .filter(n->n.getStudentId()!=null)
                            .collect(Collectors.toList()));
            studentService.batchUpdateClass(
                    classCreateInfo.getStudentAddInfos()
                            .stream()
                            .filter(n->n.getStudentId()==null)
                            .collect(Collectors.toList()));
        }
        if (classCreateInfo.getClassArrangeId() != null) {
            classCourseMapper.copySnapshot(classCreateInfo.getClassArrangeId());
        }

        studentService.setClassLeader(classCreateInfo.getLeaderId(),classEntity.getId());
        return true;
    }

    @Transactional
    public Boolean update(ClassUpdateInfo classCreateInfo) {
        buildData(classCreateInfo);
        ClassEntity classEntity = PojoMapper.INSTANCE.classUpdateInfoToClassEntity(classCreateInfo);
        classMapper.update(classEntity);
        studentService.setClassLeader(classCreateInfo.getLeaderId(),classEntity.getId());
        return true;
    }

    private void buildData(ClassUpdateInfo classCreateInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(), classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(), classCreateInfo.getRecommendOrgId());
        classCreateInfo.setOrgId(orgId);
        classCreateInfo.setRecommendOrgId(recommendOrgId);
    }

    private void buildData(ClassCreateInfo classCreateInfo){
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(), classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(), classCreateInfo.getRecommendOrgId());
        classCreateInfo.setOrgId(orgId);
        classCreateInfo.setRecommendOrgId(recommendOrgId);
    }
}
