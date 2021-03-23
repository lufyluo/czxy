package com.czxy.manage.dao;

import com.czxy.manage.model.entity.UserMenuEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMenuMapper {
    UserMenuEntity getByUserId(@Param("userId") Integer userId);

    Integer delete(@Param("userId") Integer userId);

    Integer insert(@Param("userId") Integer userId, @Param("menus") String menus);
}
