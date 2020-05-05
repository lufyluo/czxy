package com.czxy.manage.dao;

import com.czxy.manage.model.entity.FileEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FileMapper {
    List<FileEntity> query(@Param("fileIds") List<Integer> fileIds);
}
