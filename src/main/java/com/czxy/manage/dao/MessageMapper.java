package com.czxy.manage.dao;

import com.czxy.manage.model.entity.MessageEntity;
import com.czxy.manage.model.entity.SendEntity;
import com.czxy.manage.model.vo.message.UserPageParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    Integer batchInsert(List<SendEntity> list);

    List<MessageEntity> query(UserPageParam<String> pageParam);

    MessageEntity queryByUserId(@Param("userId") Integer userId);
}
