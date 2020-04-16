package com.czxy.manage.dao;

import org.apache.ibatis.annotations.Mapper;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:48
 */
@Mapper
public interface TokenMapper {
    Integer delete(String account);

    void insert(String account, String token, Long expire);
}
