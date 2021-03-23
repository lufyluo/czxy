package com.czxy.manage.dao;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PaperSubmitMapper {
    Integer countByPaperId(@Param("paperId") Integer paperId);
}
