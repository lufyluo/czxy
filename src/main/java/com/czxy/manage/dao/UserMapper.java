package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.OrgEntity;
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

    Integer insert(UserEntity userEntity);


    Integer delete(int id);

    Integer update(UserEntity userEntity);

    UserEntity queryByToken(String token);
}
