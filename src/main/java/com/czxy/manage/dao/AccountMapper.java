package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;

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
}
