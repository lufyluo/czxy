package com.czxy.manage.service;

import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.ClassStudentInfo;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentPageParam;
import com.czxy.manage.model.vo.student.StudentUpdateInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;

@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private OrgMapper orgMapper;
    @Resource
    private UserMapper userMapper;

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
            n.setSign(n.getSignFlag() == 1);
            n.setDuration(sdf.format(n.getBeginTime())+sdf.format(n.getEndTime()));
            n.setStudentIdentity(getStudentIdentity(n.getType()));
        });
        return null;
    }
    private String getStudentIdentity(Integer type){
        //0-学员；1-班委干部；8-带班领导
        String identity = "-";
        switch (type){
            case 0:
                identity = "学员";
                break;
            case 1:
                identity = "班委干部";
                break;
            case 8:
                identity = "带班领导";
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
        if (studentAddInfo.getOrgId()==null&&!StringUtils.isEmpty(studentAddInfo.getOrgName())){
            OrgEntity orgEntity = new OrgEntity();
            orgEntity.setName(studentAddInfo.getOrgName());
            orgMapper.insertOrg(orgEntity);
            studentAddInfo.setOrgId(orgEntity.getId());
        }
        UserEntity userEntity = PojoMapper.INSTANCE.studentAddtoUserEntity(studentAddInfo);
        userMapper.insert(userEntity);
        studentAddInfo.setUserId(userEntity.getId());
        StudentEntity studentEntity = PojoMapper.INSTANCE.toStudentEntity(studentAddInfo);
        studentMapper.insert(studentEntity);
        return true;
    }

//    public Boolean update(StudentUpdateInfo studentUpdateInfo) {
//        return null;
//    }
}
