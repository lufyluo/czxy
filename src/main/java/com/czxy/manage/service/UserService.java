package com.czxy.manage.service;

import com.czxy.manage.dao.AccountMapper;
import com.czxy.manage.dao.OrgMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.OrgEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.classes.ClassInformationInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
    @Resource
    private OrgMapper orgMapper;
    @Resource
    private AccountMapper accountMapper;

    @Transactional
    public Boolean add(UserCreateInfo userInfo) {
        insertIfAbsentOrg(userInfo);
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userInfo);
        userMapper.insert(userEntity);
        AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userInfo);
        accountEntity.setUserId(userEntity.getId());
        accountMapper.insert(accountEntity);
        return true;
    }

    public Boolean delete(Integer id) {
        userMapper.delete(id);
        accountMapper.deleteByUserId(id);
        return true;
    }

    public Boolean update(UserCreateInfo userCreateInfo) {
        insertIfAbsentOrg(userCreateInfo);
        updateUserAccount(userCreateInfo);
        return true;
    }

    private void insertIfAbsentOrg(UserCreateInfo userCreateInfo) {
        if (userCreateInfo.getOrgId() == null && !StringUtils.isEmpty(userCreateInfo.getOrgName())) {
            OrgEntity orgEntity = new OrgEntity();
            orgEntity.setName(userCreateInfo.getOrgName());
            orgMapper.insertOrg(orgEntity);
            userCreateInfo.setOrgId(orgEntity.getId());

        }
    }

    private void updateUserAccount(UserCreateInfo userCreateInfo) {
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userCreateInfo);
        userMapper.update(userEntity);
        AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userCreateInfo);
        accountMapper.update(accountEntity);
    }

    public UserInfo query(String token) {
        return null;
    }
}

