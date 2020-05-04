package com.czxy.manage.dao;

import com.czxy.manage.model.entity.UserMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMenuMapper {
    UserMenuEntity getByUserId(Integer userId);

    Integer delete(Integer userId);

    Integer insert(Integer userId, String menus);
}
