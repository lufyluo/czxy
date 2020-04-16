package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午10:06
 */
@Mapper
public interface UserAccountMapper {
    AccountEntity query(String pwd, String token);
}
