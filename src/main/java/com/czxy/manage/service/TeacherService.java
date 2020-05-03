package com.czxy.manage.service;

import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.TeacherEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.teacher.TeacherInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.nio.channels.Pipe;

@Service
public class TeacherService {
    @Resource
    private TeacherMapper teacherMapper;
    @Autowired
    private OrgService orgService;
    @Resource
    private UserMapper userMapper;
    @Transactional
    public Boolean add(TeacherInfo teacherInfo) {
        orgService.insertIfAbsentOrg(teacherInfo.getOrgName(),teacherInfo.getOrgId());
        UserEntity userEntity =PojoMapper.INSTANCE.teacherInfoToUserEntiy(teacherInfo);
        userMapper.insert(userEntity);
        teacherInfo.setUserId(userEntity.getId());
        TeacherEntity teacherEntity = PojoMapper.INSTANCE.toTeacherEntity(teacherInfo);
        teacherMapper.insert(teacherEntity);
        return true;
    }
}
