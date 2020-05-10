package com.czxy.manage.service;

import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.student.StudentUpdateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private ClassMapper classMapper;
    @Autowired
    private OrgService orgService;


    public PageInfo<StudentDetailInfo> page(StudentPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<StudentDetailEntity> studentDetailEntities = studentMapper.query(pageParam);
        PageInfo<StudentDetailInfo> result = page.toPageInfo();
        List<StudentDetailInfo> list = fillOtherProperty(PojoMapper.INSTANCE.toStudentDetailInfos(studentDetailEntities));
        result.setList(list);

        return result;
    }

    private List<StudentDetailInfo> fillOtherProperty(List<StudentDetailInfo> toStudentDetailInfos) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        toStudentDetailInfos.forEach(n -> {
            n.setSign(ObjectUtils.nullSafeEquals(n.getSignFlag(), 1));
            if (n.getBeginTime() != null && n.getEndTime() != null) {
                n.setPeriod(sdf.format(n.getBeginTime()) + sdf.format(n.getEndTime()));
            }

            n.setStudentIdentity(getStudentIdentity(n.getType()));
            n.setGenderDesc(getGenderDesc(n.getGender()));
        });
        return toStudentDetailInfos;
    }

    private String getGenderDesc(Integer gender) {
        String genderDesc = "";
        switch (gender) {
            case 0:
                genderDesc = "男";
                break;
            case 1:
                genderDesc = "女";
                break;
            default:
                genderDesc = "未知";
                break;
        }
        return genderDesc;
    }

    private String getStudentIdentity(Integer type) {
        //0（默认）-学员；1-班委干部；8-带班领导
        String identity = "-";
        switch (type) {
            case 1:
                identity = "班委干部";
                break;
            case 8:
                identity = "带班领导";
                break;
            default:
                identity = "学员";
                break;
        }
        return identity;
    }

    public Boolean sign(List<Integer> studentIds) {
        Boolean sign = studentMapper.sign(studentIds);
        return sign;
    }

    public Boolean delete(List<Integer> studentIds) {
        Boolean delete = studentMapper.delete(studentIds);
        return delete;
    }

    @Transactional
    public Boolean add(StudentAddInfo studentAddInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(studentAddInfo.getOrgName(), studentAddInfo.getOrgId());
        studentAddInfo.setOrgId(orgId);
        UserEntity userEntity = PojoMapper.INSTANCE.studentAddToUserEntity(studentAddInfo);
        userMapper.insert(userEntity);
        studentAddInfo.setUserId(userEntity.getId());
        StudentEntity studentEntity = PojoMapper.INSTANCE.toStudentEntity(studentAddInfo);
        studentMapper.insert(studentEntity);
        return true;
    }

    @Transactional
    public Boolean batchInsert(List<StudentAddInfo> studentAddInfos) {
        List<UserEntity> userEntity = PojoMapper.INSTANCE.studentAddToUserEntities(studentAddInfos);
        userMapper.batchInsert(userEntity);
        studentAddInfos.forEach(n -> {
            Optional<UserEntity> user = userEntity.stream().filter(item -> item.getIdCard().equals(n.getIdCard())).findFirst();
            if (user.isPresent()) {
                n.setUserId(user.get().getId());
            }
        });
        fillClassId(studentAddInfos);
        List<StudentEntity> studentEntities = PojoMapper.INSTANCE.toStudentEntities(studentAddInfos);
        studentMapper.batchInsert(studentEntities);
        return true;
    }

    private void fillClassId(List<StudentAddInfo> studentAddInfos) {
        if (studentAddInfos.get(0).getClassId() == null && !StringUtils.isEmpty(studentAddInfos.get(0).getClassName())) {
            if (studentAddInfos.stream().map(StudentAddInfo::getClassName).distinct().count() > 1) {
                throw new ManageException(ResponseStatus.FAILURE, "一个批量导入不能导入多个班级学生");
            }
            Integer classId = classMapper.queryByName(studentAddInfos.get(0).getClassName());
            studentAddInfos.forEach(n -> n.setClassId(classId));
        }
    }

    @Transactional
    public Boolean update(StudentUpdateInfo studentUpdateInfo) {
        Integer orgId = orgService.insertIfAbsentOrg(studentUpdateInfo.getOrgName(), studentUpdateInfo.getOrgId());
        studentUpdateInfo.setOrgId(orgId);
        StudentEntity studentEntity = PojoMapper.INSTANCE.toStudentEntityByStudentUpadate(studentUpdateInfo);
        studentMapper.update(studentEntity);
        StudentUpdateEntity studentUpdateEntity = studentMapper.queryByStudentId(studentEntity);
        UserUpdateEntity userUpdateEntity = PojoMapper.INSTANCE.studentUpdateToUserUpdateEntity(studentUpdateInfo);
        userUpdateEntity.setUserId(studentUpdateEntity.getUserId());
        userMapper.updateByStudent(userUpdateEntity);
        return true;
    }

    public void setClassLeader(Integer userId, Integer classId) {
        studentMapper.clearLeader(classId);
        if (userId == null || userId == 0) {
            return;
        }
        studentMapper.setLeader(userId, classId);
    }

    public Boolean signByWechat(String phone, String openId) {
        Integer userId = userMapper.queryId(phone);
        if (userId == null) {
            throw new ManageException(ResponseStatus.FAILURE, "签到失败");
        }
        if (studentMapper.queryByUserId(userId)) {
            studentMapper.updateByUserId(userId);
            userMapper.updateWechat(userId, openId);
        } else {
            throw new ManageException(ResponseStatus.FAILURE, "签到失败");
        }
        return true;
    }
}
