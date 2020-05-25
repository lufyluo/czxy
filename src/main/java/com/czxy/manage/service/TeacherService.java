package com.czxy.manage.service;

import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.teacher.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private OrgService orgService;

    @Autowired
    private UserService userService;
    @Autowired
    private SubjectService subjectService;

    public PageInfo<TeacherDetailInfo> page(TeacherPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<TeacherDetailEntity> teacherDetailEntities = teacherMapper.query(pageParam);
        PageInfo<TeacherDetailInfo> result = page.toPageInfo();
        List<TeacherDetailInfo> teacherDetailInfos = PojoMapper.INSTANCE.toTeacherDetailInfos(teacherDetailEntities);
        if (teacherDetailInfos != null) {
            teacherDetailInfos.forEach(n -> n.setGenderDesc(userService.getGenderDesc(n.getGender())));
        }

        result.setList(teacherDetailInfos);
        return result;
    }

    @Transactional
    public Boolean add(TeacherInfo teacherInfo) {
        orgService.insertIfAbsentOrg(teacherInfo.getOrgName(), teacherInfo.getOrgId());
        UserEntity userEntity = PojoMapper.INSTANCE.teacherInfoToUserEntity(teacherInfo);
        userMapper.insert(userEntity);
        teacherInfo.setUserId(userEntity.getId());
        TeacherEntity teacherEntity = PojoMapper.INSTANCE.toTeacherEntity(teacherInfo);
        teacherMapper.insert(teacherEntity);
        return true;
    }

    public Boolean delete(List<Integer> teacherIds) {
        teacherMapper.delete(teacherIds);
        return true;
    }

    @Transactional
    public Boolean update(TeacherUpdateInfo teacherUpdateInfo) {
        orgService.insertIfAbsentOrg(teacherUpdateInfo.getOrgName(), teacherUpdateInfo.getOrgId());
        TeacherEntity teacherEntity = PojoMapper.INSTANCE.TeacherUpdateToTeacherEntity(teacherUpdateInfo);
        teacherMapper.update(teacherEntity);
        Integer userId = teacherMapper.queryUserId(teacherEntity);
        teacherUpdateInfo.setUserId(userId);
        UserUpdateEntity userUpdateEntity = PojoMapper.INSTANCE.teacherUpdateToUserUpdateEnity(teacherUpdateInfo);
        userMapper.updateByTeacher(userUpdateEntity);
        return true;
    }

    public TeacherInformationInfo query(Integer teacherId) {
        TeacherInformationEntity teacherInformationEntity = teacherMapper.queryAll(teacherId);
        TeacherInformationInfo teacherInformationInfo = PojoMapper.INSTANCE.toTeacherInformationInfo(teacherInformationEntity);
        teacherInformationInfo.setSubjectByIdInfoList(
                subjectService.getByTeacherId(teacherInformationInfo.getTeacherId()));
        return teacherInformationInfo;
    }

    public List<TeacherWechatInfo> get() {
        return teacherMapper.get();
    }
}
