package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ChatContentDetailEntity;
import com.czxy.manage.model.entity.ChatContentEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ChatroomMapper {
    List<ChatContentDetailEntity> queryNexts(Integer classId, Integer lastId, Integer step);

    Integer insert(ChatContentEntity chatContentEntity);

}
