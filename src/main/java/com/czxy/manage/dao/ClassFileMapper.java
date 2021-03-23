package com.czxy.manage.dao;

import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.entity.classFile.ClassFileEntity;
import com.czxy.manage.model.vo.classes.files.ClassFilePageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ClassFileMapper {
    List<FileEntity> query(ClassFilePageParam<String> pageParam);

    Integer delete(@Param("classId") Integer classId,@Param("fileId") Integer fileId);

    Integer insert(ClassFileEntity classFileEntity);

    Integer batchInsert(List<ClassFileEntity> fileEntities);

    Integer deleteAll(@Param("classId") Integer classId);
}
