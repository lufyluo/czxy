package com.czxy.manage.dao;

import com.czxy.manage.model.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午9:43
 */
@Mapper
public interface UserMapper {
    UserEntity query(int id);
}
