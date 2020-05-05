package com.czxy.manage.service;

import com.czxy.manage.dao.FileMapper;
import com.czxy.manage.dao.SubjectMapper;
import com.czxy.manage.dao.TeacherMapper;
import com.czxy.manage.dao.TypeMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.entity.SubjectDetailEntity;
import com.czxy.manage.model.entity.SubjectEntity;
import com.czxy.manage.model.entity.TypeEntity;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.czxy.manage.model.vo.subject.SubjectByIdInfo;
import com.czxy.manage.model.vo.subject.SubjectDetailInfo;
import com.czxy.manage.model.vo.subject.SubjectInfo;
import com.czxy.manage.model.vo.subject.SubjectPageParam;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
        List<FileEntity> fileEntities = fileMapper.query(fileIds);
        List<TypeEntity> typeEntities = typeMapper.queryAll(typeIds);
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
        subjectDetailInfos.forEach(n -> {
            if (StringUtil.isNotEmpty(n.getTypes())) {
                List<String> idsTemp = Arrays.asList(
                        n.getTypes().split(","));
                n.setTypeName(typeEntities.stream()
                        .filter(item -> idsTemp.contains(item.getId() + ""))
                        .map(TypeEntity::getName)
                        .collect(Collectors.joining(",")));
            }
        });
        result.setList(subjectDetailInfos);
        return result;
    }

    @Transactional
    public Boolean add(SubjectInfo subjectInfo) {
        List<TypeInfo> typeInfos = subjectInfo.getTypes();
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(typeInfos);
        typeService.batchInsertIfObsent(typeEntityList);
        SubjectEntity subjectEntity = PojoMapper.INSTANCE.toSubjectEntity(subjectInfo);
        String result = typeEntityList.stream().map(n->n.getId().toString()).collect(Collectors.joining(","));
        subjectEntity.setTypes(result);
        subjectMapper.add(subjectEntity);
        return true;
    }

    public SubjectByIdInfo getById(Integer subjectId) {
        SubjectEntity subjectEntity = subjectMapper.queryById(subjectId);
        SubjectByIdInfo subjectByIdInfo = PojoMapper.INSTANCE.toSubjectByIdInfo(subjectEntity);
        subjectByIdInfo.setTeacherName(teacherMapper.queryName(subjectEntity.getTeacherId()));
        String types = subjectEntity.getTypes();
        String[] split = types.split(",");
        List<TypeInfo> typeInfoList = new ArrayList<>();
            for (int i = 0; i <split.length ; i++) {
                Integer typeInfoId = Integer.parseInt(split[i]);
                TypeInfo typeInfo = new TypeInfo();
                typeInfo.setId(typeInfoId);
                typeInfo.setCategory(0);
                String typeName = typeMapper.query(typeInfoId);
                typeInfo.setName(typeName);
                typeInfoList.add(typeInfo);
            }
        subjectByIdInfo.setTypes(typeInfoList);
        return subjectByIdInfo;
    }
    @Transactional
    public Boolean update(SubjectInfo subjectInfo) {
        List<TypeInfo> typeInfos = subjectInfo.getTypes();
        List<TypeEntity> typeEntityList = PojoMapper.INSTANCE.toTypeEntities(typeInfos);
        typeService.batchInsertIfObsent(typeEntityList);
        SubjectEntity subjectEntity = PojoMapper.INSTANCE.toSubjectEntity(subjectInfo);
        String result = typeEntityList.stream().map(n->n.getId().toString()).collect(Collectors.joining(","));
        subjectEntity.setTypes(result);
        subjectMapper.update(subjectEntity);
        return true;
    }
}
