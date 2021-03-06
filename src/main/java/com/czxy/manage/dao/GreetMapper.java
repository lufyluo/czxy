package com.czxy.manage.dao;

import com.czxy.manage.model.entity.GreetEntity;
import com.czxy.manage.model.vo.GreetInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GreetMapper {

    List<GreetEntity> page(@Param("param") String param, @Param("type") String type);

    Integer add(GreetInfo greetInfo);

    Integer update(GreetInfo greetInfo);

    Integer delete(@Param("ids") List<Integer> ids);

    GreetEntity queryFirstBirthDay();
}
