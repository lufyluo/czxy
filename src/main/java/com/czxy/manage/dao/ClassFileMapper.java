package com.czxy.manage.dao;

import com.czxy.manage.model.entity.FileEntity;
import com.czxy.manage.model.entity.classFile.ClassFileEntity;
import com.czxy.manage.model.vo.classes.files.ClassFilePageParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ClassFileMapper {
    List<FileEntity> query(ClassFilePageParam<String> pageParam);

    Integer delete(Integer classId, Integer fileId);

    Integer insert(ClassFileEntity classFileEntity);

}
