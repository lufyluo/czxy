package com.czxy.manage.service.classFile;

import com.czxy.manage.dao.ClassFileMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.entity.classFile.ClassFileEntity;
import com.czxy.manage.model.vo.classes.files.ClassFileCreateInfo;
import com.czxy.manage.model.vo.classes.files.ClassFilePageParam;
import com.czxy.manage.model.vo.files.FileInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassFileService {
    @Resource
    private ClassFileMapper classFileMapper;

    public PageInfo<FileInfo> page(ClassFilePageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<FileEntity> files = classFileMapper.query(pageParam);
        PageInfo pageInfo = page.toPageInfo();
        List<FileInfo> fileInfos = PojoMapper.INSTANCE.toFileInfos(files);
        pageInfo.setList(fileInfos);
        return pageInfo;
    }

    public Boolean delete(Integer classId, Integer fileId) {
        classFileMapper.delete(classId, fileId);
        return true;
    }

    public Boolean create(ClassFileCreateInfo classCreateInfo) {
        ClassFileEntity classFileEntity = PojoMapper.INSTANCE.toClassFileEntity(classCreateInfo);
        classFileMapper.insert(classFileEntity);
        return true;
    }

    public Boolean batchInsert(List<Integer> fileIds, Integer classId) {
        if (fileIds == null || fileIds.size() == 0 || classId == null) {
            return false;
        }
        List<ClassFileEntity> fileEntities =
                fileIds.stream().map(n -> {
                    ClassFileEntity classFileEntity = new ClassFileEntity();
                    classFileEntity.setClassId(classId);
                    classFileEntity.setFileId(n);
                    return classFileEntity;
                }).collect(Collectors.toList());
        classFileMapper.batchInsert(fileEntities);
        return true;
    }

    @Transactional
    public Boolean batchUpdate(Integer classId, List<Integer> fileIds) {
        classFileMapper.deleteAll(classId);
        batchInsert(fileIds, classId);
        return true;
    }
}
