package com.czxy.manage.service;

import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.teacher.*;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import cn.afterturn.easypoi.excel.ExcelImportUtil;

@Service
@Slf4j
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
        Integer orgId = orgService.insertIfAbsentOrg(teacherInfo.getOrgName(), teacherInfo.getOrgId());
        UserEntity userEntity = PojoMapper.INSTANCE.teacherInfoToUserEntity(teacherInfo);
        userEntity.setOrgId(orgId);
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
        Integer orgId = orgService.insertIfAbsentOrg(teacherUpdateInfo.getOrgName(), teacherUpdateInfo.getOrgId());
        TeacherEntity teacherEntity = PojoMapper.INSTANCE.TeacherUpdateToTeacherEntity(teacherUpdateInfo);
        teacherMapper.update(teacherEntity);
        teacherUpdateInfo.setUserId(teacherUpdateInfo.getId());
        UserUpdateEntity userUpdateEntity = PojoMapper.INSTANCE.teacherUpdateToUserUpdateEnity(teacherUpdateInfo);
        userUpdateEntity.setOrgId(orgId);
        userMapper.updateByTeacher(userUpdateEntity);
        return true;
    }

    public TeacherInformationInfo query(Integer teacherId) {
        TeacherInformationEntity teacherInformationEntity = teacherMapper.queryAll(teacherId);
        if (teacherInformationEntity == null) {
            throw new ManageException(ResponseStatus.FAILURE, "错误教师ID");
        }
        TeacherInformationInfo teacherInformationInfo = PojoMapper.INSTANCE.toTeacherInformationInfo(teacherInformationEntity);
        teacherInformationInfo.setSubjectByIdInfoList(
                subjectService.getByTeacherId(teacherInformationInfo.getTeacherId()));
        return teacherInformationInfo;
    }

    public List<TeacherWechatInfo> get() {
        //当前老师资料严重缺省
        //return null;
        return teacherMapper.get();
    }

    @Transactional
    public List<ImportTeacherInfo> batchImport(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setStartRows(0);
        try {
            List<ImportTeacherInfo> teacherInfos = ExcelImportUtil.importExcel(file.getInputStream(), ImportTeacherInfo.class, params);
            if (teacherInfos == null || teacherInfos.size() == 0) {
                throw new ManageException(ResponseStatus.DATANOTEXIST, "无数据！");
            }
            teacherInfos = teacherInfos.stream().filter(n -> !StringUtils.isEmpty(n.getPhone())).collect(Collectors.toList());
            fillOrgIds(teacherInfos);
            fillUserIds(teacherInfos);
            List<TeacherEntity> teacherEntities = PojoMapper.INSTANCE.importTeacherInfoToTeacherEntity(teacherInfos);
            teacherEntities = clearExistTeacher(teacherEntities);
            if (teacherEntities != null && teacherEntities.size() > 0) {
                teacherMapper.batchInsert(teacherEntities);
            }
            return teacherInfos;
        } catch (Exception e) {
            log.error("导入教师失败！");
            throw new ManageException(ResponseStatus.FAILURE, "导入教师失败");
        }
    }

    private List<TeacherEntity> clearExistTeacher(List<TeacherEntity> teacherEntities) {
        List<Integer> userIds = teacherEntities.stream().map(TeacherEntity::getUserId).collect(Collectors.toList());
        if (userIds == null || userIds.size() == 0) {
            return teacherEntities;
        }
        List<TeacherEntity> teacherExistEntities = teacherMapper.queryByUserIds(userIds);
        if (teacherExistEntities != null) {
            teacherEntities = teacherEntities
                    .stream()
                    .filter(
                            n -> !teacherExistEntities
                                    .stream()
                                    .anyMatch(item -> item.getUserId().equals(n.getUserId())))
                    .collect(Collectors.toList());

        }
        return teacherEntities;
    }

    private void fillUserIds(List<ImportTeacherInfo> teacherInfos) {
        List<UserEntity> userEntities = PojoMapper.INSTANCE.importTeacherInfosToUserEntities(teacherInfos).stream().collect(Collectors.toList());
        userService.fillUserId(userEntities);
        teacherInfos.forEach(n -> {
            Optional<UserEntity> user = userEntities.stream()
                    .filter(item ->
                            ObjectUtils.nullSafeEquals(item.getPhone(), (n.getPhone()))).findFirst();
            if (user.isPresent()) {
                n.setUserId(user.get().getId());
            }
        });
    }

    private void fillOrgIds(List<ImportTeacherInfo> importTeacherInfos) {
        List<String> orgNames = importTeacherInfos.stream()
                .filter(n -> !StringUtils.isEmpty(n.getOrgName()))
                .map(ImportTeacherInfo::getOrgName)
                .distinct()
                .collect(Collectors.toList());
        if (orgNames == null || orgNames.size() == 0) {
            return;
        }
        List<OrgEntity> orgs = orgService.batchInsertIfAbsentOrg(orgNames);

        for (int i = 0; i < importTeacherInfos.size(); i++) {
            ImportTeacherInfo importTeacherInfo = importTeacherInfos.get(i);
            Optional<OrgEntity> first = orgs.stream()
                    .filter(item -> item.getName().equals(importTeacherInfo.getOrgName()))
                    .findFirst();
            if (first.isPresent()) {
                importTeacherInfo.setOrgId(first.get().getId());
            }
        }
    }
}
