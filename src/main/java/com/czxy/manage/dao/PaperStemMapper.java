package com.czxy.manage.dao;

import com.czxy.manage.model.vo.PaperAddInfo;
import com.czxy.manage.model.vo.questionnaire.PaperCreateInfo;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PaperStemMapper {
    Integer insert(PaperAddInfo paperAddInfo);

    Integer delete(Integer id);
}
