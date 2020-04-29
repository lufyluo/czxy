package com.czxy.manage.dao;

import com.czxy.manage.model.entity.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

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

    Integer updateByStudent(UserUpdateEntity userUpdateEntity);

    Integer batchInsert(List<UserEntity> list);
}
