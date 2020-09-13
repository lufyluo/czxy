package com.czxy.manage.service;

import com.czxy.manage.dao.ClassExcuteCourseMapper;
import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.ClassMasterMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.service.classFile.ClassFileService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
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
    @Resource
    private StudentMapper studentMapper;
    @Autowired
    private ClassFileService classFileService;

    public PageInfo<ClassOrgInfo> page(ClassPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<ClassOrgEntity> classEntities = classMapper.queryAll(pageParam);
        if (classEntities == null || classEntities.size() == 0) {
            return new PageInfo<>();
        }
        List<Integer> collect = classEntities.stream().map(n -> n.getId()).collect(Collectors.toList());
        List<CountEntity> countEntities = classMapper.queryCount(collect);
        for (ClassOrgEntity classOrgEntity : classEntities) {
            for (CountEntity countEntity : countEntities) {
                if (classOrgEntity.getId() == countEntity.getClassId()) {
                    classOrgEntity.setStudentCount(countEntity.getStudentCount());
                }
            }
        }
        PageInfo<ClassOrgInfo> result = page.toPageInfo();
        result.setList(PojoMapper.INSTANCE.toClassOrgInfos(classEntities));
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
        if (pageParam.getParam() != null && pageParam.getParam() != "") {
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
            } else if (classStudentEntity.getStudentType() == 10) {
                classStudentEntity.setStudentTypeName("对接人");
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
        Integer compositionId = compositionService.insertIfAbsent(classCreateInfo.getCompositionId(), classCreateInfo.getComposition());
        classEntity.setCompositionId(compositionId);
        classMapper.insert(classEntity);
        classFileService.batchInsert(classCreateInfo.getFileIds(), classEntity.getId());
        if (classCreateInfo.getMasterId() != null && classCreateInfo.getMasterId() > 0) {
            classMasterMapper.insertMaster(classCreateInfo.getMasterId(), classEntity.getId(),0);
        }
        if (classCreateInfo.getAssistantId() != null && classCreateInfo.getAssistantId() > 0) {
            classMasterMapper.insertMaster(classCreateInfo.getAssistantId(), classEntity.getId(),1);
        }
        if (classCreateInfo.getStudentAddInfos() != null && classCreateInfo.getStudentAddInfos().size() > 0) {
            classCreateInfo.getStudentAddInfos().forEach(n -> {
                n.setClassId(classEntity.getId());
            });
            studentService.batchInsert(
                    classCreateInfo.getStudentAddInfos()
                            .stream()
                            .filter(n -> n.getClassId() != null || n.getStudentId() == null)
                            .collect(Collectors.toList()));
            studentService.setClassLeader(
                    getLeaderId(classCreateInfo.getLeaderName(), classCreateInfo.getStudentAddInfos()
                    )
                    , classEntity.getId());
        }
        if (classCreateInfo.getClassArrangeId() != null) {
            classCourseMapper.copySnapshot(classCreateInfo.getClassArrangeId());
        }

        return true;
    }

    private Integer getLeaderId(String leaderName, List<StudentAddInfo> studentAddInfos) {
        if (studentAddInfos == null || studentAddInfos.size() == 0 || StringUtils.isEmpty(leaderName)) {
            return null;
        }
        Optional<StudentAddInfo> leader = studentAddInfos
                .stream()
                .filter(n -> ObjectUtils.nullSafeEquals(n.getName(), leaderName)).findFirst();
        if (leader.isPresent()) {
            return leader.get().getUserId();
        }
        throw new ManageException(ResponseStatus.DATANOTEXIST, "该对接人不在该班级学员名单！");
    }

    @Transactional
    public Boolean update(ClassUpdateInfo classCreateInfo) {
        buildData(classCreateInfo);
        ClassEntity classEntity = PojoMapper.INSTANCE.classUpdateInfoToClassEntity(classCreateInfo);
        if (classEntity.getArrangeId() == null) {
            classEntity.setArrangeId(classCreateInfo.getClassArrangeId());
        }
        classMapper.update(classEntity);
        studentService.setClassLeader(
                getLeaderId(classCreateInfo.getLeaderName(), classCreateInfo.getStudentAddInfos()
                )
                , classEntity.getId());
        updateStudents(classCreateInfo);
        classFileService.batchUpdate(classCreateInfo.getId(), classCreateInfo.getFileIds());
        classMasterMapper.delete(classCreateInfo.getMasterId(), classEntity.getId());
        if (classCreateInfo.getMasterId() != null && classCreateInfo.getMasterId() > 0) {
            classMasterMapper.insertMaster(classCreateInfo.getMasterId(), classEntity.getId(),0);
        }
        classMasterMapper.delete(classCreateInfo.getAssistantId(), classEntity.getId());
        if (classCreateInfo.getAssistantName() != null && classCreateInfo.getAssistantId() > 0) {
            classMasterMapper.insertMaster(classCreateInfo.getMasterId(), classEntity.getId(),1);
        }
        return true;
    }

    private void updateStudents(ClassUpdateInfo classCreateInfo) {
        classMapper.clearStudent(classCreateInfo.getId());
        if (classCreateInfo.getStudentAddInfos() != null && classCreateInfo.getStudentAddInfos().size() > 0) {
            classCreateInfo.getStudentAddInfos().forEach(n -> {
                n.setClassId(classCreateInfo.getId());
                n.setClassName(classCreateInfo.getName());
            });
            studentService.batchInsert(
                    classCreateInfo.getStudentAddInfos()
                            .stream()
                            .filter(n -> n.getClassId() != null || n.getStudentId() == null)
                            .collect(Collectors.toList()));
        }
    }

    private void buildData(ClassUpdateInfo classCreateInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(), classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(), classCreateInfo.getRecommendOrgId());
        classCreateInfo.setOrgId(orgId);
        classCreateInfo.setRecommendOrgId(recommendOrgId);
    }

    private void buildData(ClassCreateInfo classCreateInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(classCreateInfo.getOrgName(), classCreateInfo.getOrgId());
        Integer recommendOrgId = orgService.insertIfAbsentOrg(classCreateInfo.getRecommendOrgName(), classCreateInfo.getRecommendOrgId());
        classCreateInfo.setOrgId(orgId);
        classCreateInfo.setRecommendOrgId(recommendOrgId);
    }

    public Boolean updateStudentClass(StudentClassInfo studentClassInfo) {
        studentMapper.updateStudentClass(studentClassInfo.getClassId(), studentClassInfo.getStudentIds());
        return true;
    }

    public List<ClassStudentInfo> getStudents(Integer userId) {
        Integer classId = classMapper.queryRecentByStudentUserId(userId);
        if (classId == null) {
            return null;
        }
        List<ClassStudentEntity> classStudentEntities = classMapper.queryAllStudent(classId);
        return PojoMapper.INSTANCE.toClassStudentInfos(classStudentEntities);
    }
}
