package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:48
 */
@Mapper
public interface TokenMapper {
    void flushTime(@Param("token") String token);

    Integer delete(@Param("account") String account);

    void insert(@Param("userId") Integer userId, @Param("account") String account, @Param("token") String token, @Param("expire") Long expire);

    Boolean tokenQuery(@Param("token") String token);
}
