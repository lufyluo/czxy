package com.czxy.manage.service;

import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.StudentMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.infrastructure.util.wechat.WechatUtil;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.questionnaire.PaperPublisInfo;
import com.czxy.manage.model.vo.student.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class StudentService {
    @Resource
    private StudentMapper studentMapper;
    @Resource
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Resource
    private ClassMapper classMapper;
    @Autowired
    private OrgService orgService;
    @Resource
    private OrgMapper orgMapper;
    @Autowired
    private WechatUtil wechatUtil;

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
            n.setGenderDesc(userService.getGenderDesc(n.getGender()));
        });
        return toStudentDetailInfos;
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
        return batchInsert(Arrays.asList(studentAddInfo));
    }

    public Boolean importExcel(List<StudentAddInfo> studentAddInfos) {
        fillOrgIds(studentAddInfos);
        return batchInsert(studentAddInfos);
    }

    private void fillOrgIds(List<StudentAddInfo> studentAddInfos) {
        List<String> orgNames = studentAddInfos.stream()
                .filter(n -> !StringUtils.isEmpty(n.getOrgName()))
                .map(StudentAddInfo::getOrgName)
                .collect(Collectors.toList());
        List<OrgEntity> orgs = getOrgEntities(orgNames);

        for (int i = 0; i < studentAddInfos.size(); i++) {
            StudentAddInfo studentAddInfo = studentAddInfos.get(i);
            Optional<OrgEntity> first = orgs.stream()
                    .filter(item -> item.getName().equals(studentAddInfo.getOrgName()))
                    .findFirst();
            if (first.isPresent()) {
                studentAddInfo.setOrgId(first.get().getId());
            }
        }
    }

    private List<OrgEntity> getOrgEntities(List<String> orgNames) {
        List<OrgEntity> orgs = orgMapper.getByNames(orgNames);
        if (orgs == null || orgs.size() == 0) {
            orgMapper.batchInsert(orgNames);
            orgs = orgMapper.getByNames(orgNames);
        } else if (orgs.size() < orgNames.size()) {
            List<String> orgExists = orgs.stream().map(OrgEntity::getName).collect(Collectors.toList());
            orgNames.removeAll(orgExists);
            orgMapper.batchInsert(orgNames);
            orgs = orgMapper.getByNames(orgNames);
        }
        return orgs;
    }

    @Transactional
    public Boolean batchInsert(List<StudentAddInfo> studentAddInfos) {
        if (studentAddInfos == null || studentAddInfos.size() == 0) {
            return true;
        }
        List<UserEntity> userEntities = PojoMapper.INSTANCE.studentAddToUserEntities(studentAddInfos).stream().
                filter(n -> ObjectUtils.nullSafeEquals(n.getId(), null)).collect(Collectors.toList());
        fillUserId(userEntities);

        studentAddInfos.forEach(n -> {
            Optional<UserEntity> user = userEntities.stream()
                    .filter(item ->
                            ObjectUtils.nullSafeEquals(item.getPhone(), (n.getPhone()))).findFirst();
            if (user.isPresent()) {
                n.setUserId(user.get().getId());
            }
        });

        fillClassId(studentAddInfos);
        List<StudentEntity> studentEntities = PojoMapper.INSTANCE.toStudentEntities(studentAddInfos);
        studentMapper.batchInsert(studentEntities);
        studentAddInfos.forEach(n -> {
            Optional<StudentEntity> optionalStudentEntity = studentEntities.stream()
                    .filter(item -> item.getUserId().equals(n.getUserId())).findFirst();
            if (optionalStudentEntity.isPresent()) {
                n.setStudentId(optionalStudentEntity.get().getId());
            }
        });
        return true;
    }

    private void fillUserId(List<UserEntity> userEntities) {
        if (userEntities != null && userEntities.size() != 0) {
            List<UserEntity> existUserEntities = userMapper.queryByPhones(userEntities
                    .stream()
                    .map(UserEntity::getPhone)
                    .filter(n -> !StringUtils.isEmpty(n))
                    .collect(Collectors.toList()));

            existUserEntities
                    .stream()
                    .collect(Collectors.toCollection(()->new TreeSet<>(Comparator.comparing(UserEntity::getPhone))));

            if (existUserEntities == null || existUserEntities.size() == 0) {
                userMapper.batchInsert(userEntities);
            } else if (existUserEntities.size() == userEntities.size()) {
                copyUserId(userEntities, existUserEntities);

            } else {
                List<UserEntity> newUserEntities = userEntities
                        .stream()
                        .filter(n -> !existUserEntities
                                .stream()
                                .anyMatch(m -> ObjectUtils.nullSafeEquals(m.getPhone(), n.getPhone())))
                        .collect(Collectors.toList());
                userMapper.batchInsert(newUserEntities);
                existUserEntities.addAll(newUserEntities);
                copyUserId(userEntities, existUserEntities);
            }
        }
    }

    private void copyUserId(List<UserEntity> target, List<UserEntity> source) {
        target.forEach(n -> {
            Optional<UserEntity> userEntity = source
                    .stream()
                    .filter(m -> ObjectUtils.nullSafeEquals(n.getPhone(), m.getPhone())).findFirst();
            if (userEntity.isPresent()) {
                n.setId(userEntity.get().getId());
            }
        });
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

    public Boolean signByWechat(String phone, String code) {
        Integer userId = userMapper.queryId(phone,null);
        if (userId == null || userId == 0) {
            throw new ManageException(ResponseStatus.FAILURE, "学员不存在");
        }
        StudentEntity studentEntity = studentMapper.queryStudent(userId);
        if (studentEntity.getClassId() == null || studentEntity.getClassId() == 0) {
            throw new ManageException(ResponseStatus.FAILURE, "学生没有班级");
        }
        if (studentEntity.getSignFlag() == 1) {
            throw new ManageException(ResponseStatus.FAILURE, "已签到，请勿重复签到");
        }
        ClassEntity classEntity = classMapper.queryClass(studentEntity.getClassId());
        if(classEntity == null){
            throw new ManageException(ResponseStatus.FAILURE, "尚未加入班级");
        }
        Calendar c2 = Calendar.getInstance();
        c2.setTime(new Date());
        if (c2.before(classEntity.getBeginTime())) {
            throw new ManageException(ResponseStatus.FAILURE, "班级还未开始");
        }
        if (c2.after(classEntity.getEndTime())) {
            throw new ManageException(ResponseStatus.FAILURE, "班级已经结束");
        }
        log.info("==================================》");
        String openId = wechatUtil.getOpenId(code);
        log.info("《==================================");
        if (StringUtils.isEmpty(openId)) {
            throw new ManageException(ResponseStatus.FAILURE, "签到失败，请重试！");
        }
        log.info("学生id: " + userId);
        studentMapper.updateByUserId(userId);
        userMapper.updateWechat(userId, openId);
        return true;
    }

    public Boolean batchUpdateClass(List<StudentAddInfo> studentAddInfos) {
        if (studentAddInfos == null || studentAddInfos.size() == 0) {
            return true;
        }
        studentMapper.updateClass(studentAddInfos);
        return true;
    }

    //根据特殊条件获取所有学员
    public List<StudentDetailEntity> getAllUser(GetAllParam paperPublisInfo) {
        if (paperPublisInfo.getIsToAll()==1) {
            paperPublisInfo = new GetAllParam();
        }
        List<StudentDetailEntity> studentDetailEntities = new ArrayList<>();
        StudentPageParam studentPageParam = new StudentPageParam();
        studentPageParam.setCityId(paperPublisInfo.getCityId());
        studentPageParam.setCountyId(paperPublisInfo.getCountyId());
        studentPageParam.setProvinceId(paperPublisInfo.getProvinceId());
        if (paperPublisInfo.getClassIds() != null && paperPublisInfo.getClassIds().size() > 0) {
            List<StudentDetailEntity> finalStudentDetailEntities = studentDetailEntities;
            paperPublisInfo.getClassIds().forEach(n -> {
                studentPageParam.setClassId(n);
                finalStudentDetailEntities.addAll(studentMapper.query(studentPageParam));
            });
        } else if (paperPublisInfo.getUserIds() != null && paperPublisInfo.getUserIds().size() > 0) {
            return paperPublisInfo.getUserIds().stream().map(n -> {
                StudentDetailEntity studentDetailEntityTemp = new StudentDetailEntity();
                studentDetailEntityTemp.setUserId(n);
                return studentDetailEntityTemp;
            }).collect(Collectors.toList());
        } else {
            studentDetailEntities = studentMapper.query(studentPageParam);
        }
        return distinctStudent(studentDetailEntities);
    }

    private List<StudentDetailEntity> distinctStudent(List<StudentDetailEntity> studentDetailEntities) {
        if (studentDetailEntities != null) {
            studentDetailEntities
                    .stream()
                    .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(StudentDetailEntity::getPhone))));
        }
        return studentDetailEntities;
    }

    public StudentClassNameInfo get(Integer userId) {
        StudentClassNameEntity studentClassNameEntity = userMapper.queryclassName(userId);
        Date date= new Date();
        Date beginTime = studentClassNameEntity.getBeginTime();
        Date endTime = studentClassNameEntity.getEndTime();
        if (date.after(beginTime)&&date.after(endTime)){
            studentClassNameEntity.setClassState("班级正在进行");
        }
        if (date.after(endTime)){
            studentClassNameEntity.setClassState("班级已经结束");
        }
        if (date.before(beginTime)){
            studentClassNameEntity.setClassState("班级还未开始");
        }
        return PojoMapper.INSTANCE.toStudentClassNameInfo(studentClassNameEntity);
    }

    public Boolean authentication(String phone, String name, String code) {
        Integer userId = userMapper.queryId(phone,name);
        if (userId == null || userId == 0) {
            throw new ManageException(ResponseStatus.FAILURE, "学员不存在");
        }
        StudentEntity studentEntity = studentMapper.queryStudent(userId);
        if (studentEntity.getClassId() == null || studentEntity.getClassId() == 0) {
            throw new ManageException(ResponseStatus.FAILURE, "学生没有班级");
        }
        log.info("==================================》");
        String openId = wechatUtil.getOpenId(code);
        log.info("《==================================");
        if (StringUtils.isEmpty(openId)) {
            throw new ManageException(ResponseStatus.FAILURE, "认证失败，请重试！");
        }
        log.info("学生id: " + userId);
        studentMapper.updateByUserId(userId);
        userMapper.updateWechat(userId, openId);
        return true;
    }
}
