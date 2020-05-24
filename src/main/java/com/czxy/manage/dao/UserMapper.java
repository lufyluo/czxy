package com.czxy.manage.dao;

import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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


    Integer delete(@Param("id") List<Integer> id);

    Integer update(UserEntity userEntity);

    UserEntity queryByToken(String token);

    Integer updateByStudent(UserUpdateEntity userUpdateEntity);

    Integer batchInsert(List<UserEntity> list);

    Integer updateByTeacher(UserUpdateEntity userUpdateEntity);

    List<UserEntity> queryMaster(String param);

    List<UserEntity> queryClassLeader(String param);

    Integer queryId(String phone);

    Integer updateWechat(Integer userId,String openId);

    Integer updateByCustomer(CustomerInfo customerInfo);

    List<UserEntity> queryByPhones(List<String> phones);
}
