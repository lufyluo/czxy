package com.czxy.manage.dao;

import com.czxy.manage.model.entity.ChatContentDetailEntity;
import com.czxy.manage.model.entity.ChatContentEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ChatroomMapper {
    List<ChatContentDetailEntity> queryNexts(@Param("classId") Integer classId, @Param("lastId") Integer lastId, @Param("step") Integer step);

    Integer insert(ChatContentEntity chatContentEntity);

}
