package com.czxy.manage.service;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.czxy.manage.dao.FileMapper;
import com.czxy.manage.dao.SubjectMapper;
import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.czxy.manage.model.vo.subject.*;
import com.czxy.manage.model.vo.teacher.ImportTeacherInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SubjectService {
    @Resource
    private SubjectMapper subjectMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private TypeMapper typeMapper;
    @Resource
    private TeacherMapper teacherMapper;
    @Autowired
    private TypeService typeService;

    public PageInfo<SubjectDetailInfo> page(SubjectPageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<SubjectDetailEntity> subjectDetailEntities = subjectMapper.query(pageParam.getParam(), pageParam.getTypeId(), pageParam.getCategory());
        List<SubjectDetailInfo> subjectDetailInfos = PojoMapper.INSTANCE.toSubjectDetaiInfos(subjectDetailEntities);
        PageInfo<SubjectDetailInfo> result = page.toPageInfo();
        List<Integer> fileIds = new ArrayList<>();
        List<Integer> typeIds = new ArrayList<>();
        for (int i = 0; i < subjectDetailEntities.size(); i++) {
            SubjectDetailEntity subjectDetailEntity = subjectDetailEntities.get(i);
            if (!StringUtils.isEmpty(subjectDetailEntity.getFiles())) {
                String files = subjectDetailEntity.getFiles();
                Arrays.stream(files.split(",")).forEach(n -> fileIds.add(Integer.parseInt(n)));
            }
            if (!StringUtils.isEmpty(subjectDetailEntity.getTypes())) {
                String types = subjectDetailEntity.getTypes();
                Arrays.stream(types.split(",")).forEach(n -> typeIds.add(Integer.parseInt(n)));
            }
        }
        if (!fileIds.isEmpty()) {
            List<FileEntity> fileEntities = fileMapper.query(fileIds);
            subjectDetailInfos.forEach(n -> {
                if (StringUtil.isNotEmpty(n.getFiles())) {
                    List<String> idsTemp = Arrays.asList(
                            n.getFiles().split(","));
                    n.setFilesName(fileEntities.stream()
                            .filter(item -> idsTemp.contains(item.getId() + ""))
                            .map(FileEntity::getName)
                            .collect(Collectors.joining(",")));
                }
            });
        }
        if (!typeIds.isEmpty()) {
            List<TypeEntity> typeEntities = typeMapper.queryAll(typeIds);
            subjectDetailInfos.forEach(n -> {
                if (StringUtil.isNotEmpty(n.getTypes())) {
                    List<String> idsTemp = Arrays.asList(
                            n.getTypes().split(","));
                    n.setTypeNames(typeEntities.stream()
                            .filter(item -> idsTemp.contains(item.getId() + ""))
                            .map(TypeEntity::getName)
                            .collect(Collectors.joining(",")));
                }
            });
        }
        result.setList(subjectDetailInfos);
        return result;
    }

    @Transactional
    public Boolean add(SubjectInfo subjectInfo) {
        List<TypeInfo> typeInfos = subjectInfo.getTypes();
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(typeInfos);
        typeEntityList.forEach(n -> n.setCategory(0));
        typeService.batchInsertIfObsent(typeEntityList);
        SubjectEntity subjectEntity = PojoMapper.INSTANCE.toSubjectEntity(subjectInfo);
        String result = typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(","));
        subjectEntity.setTypes(result);
        subjectMapper.add(subjectEntity);
        return true;
    }

    public SubjectByIdInfo getById(Integer subjectId) {
        SubjectEntity subjectEntity = subjectMapper.queryById(subjectId);
        SubjectByIdInfo subjectByIdInfo = PojoMapper.INSTANCE.toSubjectByIdInfo(subjectEntity);
        subjectByIdInfo.setTeacherName(teacherMapper.queryName(subjectEntity.getTeacherId()));
        if (!StringUtils.isEmpty(subjectEntity.getTypes())) {
            String types = subjectEntity.getTypes();
            String[] split = types.split(",");
            List<TypeInfo> typeInfoList = new ArrayList<>();
            for (int i = 0; i < split.length; i++) {
                Integer typeInfoId = Integer.parseInt(split[i]);
                TypeInfo typeInfo = new TypeInfo();
                typeInfo.setId(typeInfoId);
                typeInfo.setCategory(0);
                String typeName = typeMapper.query(typeInfoId);
                typeInfo.setName(typeName);
                typeInfoList.add(typeInfo);
            }
            subjectByIdInfo.setTypes(typeInfoList);
        }
        if (!StringUtils.isEmpty(subjectEntity.getFiles())) {
            String files = subjectEntity.getFiles();
            List<Integer> fileIds = new ArrayList<>();
            Arrays.stream(files.split(",")).forEach(n -> fileIds.add(Integer.parseInt(n)));
            List<FileEntity> fileEntities = fileMapper.query(fileIds);
            subjectByIdInfo.setFileInfos(PojoMapper.INSTANCE.toFileInfos(fileEntities));
        }

        return subjectByIdInfo;
    }

    public List<SubjectByIdInfo> getByTeacherId(Integer teacherId) {
        List<SubjectEntity> subjectEntities = subjectMapper.getByTeacherId(teacherId);
        if (subjectEntities == null || subjectEntities.size() == 0) {
            return null;
        }
        List<SubjectByIdInfo> subjectByIdInfo = PojoMapper.INSTANCE.toSubjectByIdInfos(subjectEntities);
        String teacherName = teacherMapper.queryName(teacherId);
        subjectByIdInfo.forEach(n -> n.setTeacherName(teacherName));
        fillTypes(subjectByIdInfo);
        return subjectByIdInfo;
    }

    private void fillTypes(List<SubjectByIdInfo> planInfos) {
        planInfos.forEach(n -> {
            List<Integer> typeIds = Arrays.stream(n.getTypeIds().split(","))
                    .map(item -> Integer.parseInt(item))
                    .distinct().collect(Collectors.toList());
            List<TypeEntity> typeEntities = typeMapper.queryAll(typeIds);
            if (typeEntities != null) {
                n.setTypes(PojoMapper.INSTANCE.toTypeInfos(typeEntities));
            }
        });
    }

    @Transactional
    public Boolean update(SubjectInfo subjectInfo) {
        List<TypeInfo> typeInfos = subjectInfo.getTypes();
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(typeInfos);
        typeService.batchInsertIfObsent(typeEntityList);
        SubjectEntity subjectEntity = PojoMapper.INSTANCE.toSubjectEntity(subjectInfo);
        String result = typeEntityList.stream().map(n -> n.getId().toString()).collect(Collectors.joining(","));
        subjectEntity.setTypes(result);
        subjectMapper.update(subjectEntity);
        return true;
    }

    public Boolean delete(List<Integer> subjectIds) {
        subjectMapper.delete(subjectIds);
        return true;
    }

    public Boolean importSubjects(MultipartFile file) {
        ImportParams params = new ImportParams();
        params.setStartRows(0);
        try {
            List<SubjectImportInfo> subjectImportInfos = ExcelImportUtil.importExcel(file.getInputStream(), SubjectImportInfo.class, params);
            if (subjectImportInfos == null || subjectImportInfos.isEmpty()) {
                return true;
            }
            fillTeacherId(subjectImportInfos);
            List<SubjectEntity> subjectEntities = PojoMapper.INSTANCE.toImportSubjectEntities(subjectImportInfos);
            subjectMapper.batchInsert(subjectEntities);
            return true;
        } catch (ManageException ex) {
            throw ex;
        } catch (Exception e) {
            throw new ManageException(ResponseStatus.FAILURE, "导入课题失败");
        }
    }

    private void fillTeacherId(List<SubjectImportInfo> subjectImportInfos) {
        List<String> collect = subjectImportInfos.stream().map(n -> n.getTeacherName()).distinct().collect(Collectors.toList());
        List<TeacherDetailEntity> teacherEntities = teacherMapper.queryByNames(collect);
        if (teacherEntities == null) {
            throw new ManageException(ResponseStatus.FAILURE, "教师不存在");
        }
        subjectImportInfos.forEach(n -> {
            Optional<TeacherDetailEntity> optionalTeacherDetailEntity = teacherEntities
                    .stream()
                    .filter(item -> item.getName().equals(n.getTeacherName())).findFirst();
            if (optionalTeacherDetailEntity.isPresent()) {
                n.setTeacherId(optionalTeacherDetailEntity.get().getTeacherId());
            } else {
                throw new ManageException(ResponseStatus.FAILURE, n.getTeacherName() + " 教师不存在");
            }
        });
    }
}
