package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:39
 */
@Mapper
public interface AccountMapper {
    AccountEntity query(@Param("account") String account, @Param("pwd") String pwd);

    Integer updatePwd(@Param("id") int id,@Param("newPwd") String newPwd);

    Integer delete(@Param("id") Integer id);

    Integer insert(AccountEntity accountEntity);

    Integer deleteByUserId(@Param("id") List<Integer> id);

    Integer update(AccountEntity accountEntity);

    Integer exist(@Param("account") String account);

    Integer existUserAccount(@Param("account") String account,@Param("userId") Integer userId);

    Integer updateAccount(AccountEntity userInfo);
}
