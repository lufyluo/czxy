package com.czxy.manage.service;

import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional
    public Boolean add(UserCreateInfo userInfo) {
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userInfo);
        userMapper.insert(userEntity);
        AccountEntity accountEntity =PojoMapper.INSTANCE.toAccountEntity(userInfo);
        accountEntity.setUserId(userEntity.getId());
        userMapper.insertAccount(accountEntity);
        return null;
    }
}

