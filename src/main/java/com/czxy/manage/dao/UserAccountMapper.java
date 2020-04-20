package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.vo.user.UserAccountInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午10:06
 */
@Mapper
public interface UserAccountMapper {
    AccountEntity query(String pwd, String token);

    List<UserAccountInfo> queryAll(String param);
}
