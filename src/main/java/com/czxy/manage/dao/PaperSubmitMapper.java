package com.czxy.manage.dao;

import lombok.Data;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperSubmitMapper {
    Integer countByPaperId(Integer paperId);
}
