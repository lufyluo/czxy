package com.czxy.manage.service;

import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-15 下午10:01
 */
@Service
public class UserService {
    @Resource
    private UserMapper userMapper;
    public Boolean add(UserCreateInfo userInfo) {
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userInfo);
        userMapper.insert(userEntity);
        return null;
    }
}

