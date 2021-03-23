package com.czxy.manage.dao;

import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.classes.ClassWechatInfo;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import com.czxy.manage.model.vo.files.FileInfo;
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
    UserEntity query(@Param("id") int id);

    Integer insert(UserEntity userEntity);


    Integer delete(@Param("id") List<Integer> id);

    Integer update(UserEntity userEntity);

    UserEntity queryByToken(@Param("token") String token);

    Integer updateByStudent(UserUpdateEntity userUpdateEntity);

    Integer batchInsert(List<UserEntity> list);

    Integer updateByTeacher(UserUpdateEntity userUpdateEntity);

    List<UserEntity> queryMaster(@Param("param") String param);

    List<UserEntity> queryClassLeader(@Param("param") String param);

    Integer queryId(@Param("phone") String phone, @Param("name") String name);

    Integer updateWechat(@Param("userId") Integer userId, @Param("openId") String openId);

    Integer updateByCustomer(CustomerInfo customerInfo);

    List<UserEntity> queryByPhones(List<String> phones);

    UserEntity queryByWechatId(@Param("openId") String openId);

    StudentClassNameEntity queryclassName(@Param("userId") Integer userId);

    UserEntity queryByUserId(@Param("userId") Integer userId);

    List<ClassEntity> queryClassId(@Param("id") Integer id);

    List<FileInfo> queryFile(@Param("classIds") List<Integer> classIds);

    List<ClassWechatInfo> queryWechatClass(@Param("userId") Integer userId);

    List<String> queryByUserIds(List<Integer> userIds);

    Integer exist(@Param("phone") String phone);

    List<UserEntity> queryByBirthDay(@Param("birthDay") String birthDay);

    List<UserEntity> queryWechatUsers();
}
