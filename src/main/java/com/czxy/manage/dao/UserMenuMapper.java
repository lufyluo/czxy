package com.czxy.manage.dao;

import com.czxy.manage.model.entity.UserMenuEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMenuMapper {
    List<UserMenuEntity> getByUserId(Integer userId);
}
