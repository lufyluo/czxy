package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
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
    AccountEntity query(String account, String pwd);

    Integer updatePwd(int id, String newPwd);

    Integer delete(Integer id);

    Integer insert(AccountEntity accountEntity);

    Integer deleteByUserId(@Param("id") List<Integer> id);

    Integer update(AccountEntity accountEntity);

    Integer exist(String account);
}
