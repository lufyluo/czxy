package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:39
 */
@Mapper
public interface UserMenuMapper {

    List<UserMenuInfo> get();
}
