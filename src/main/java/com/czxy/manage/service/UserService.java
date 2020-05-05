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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

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
    private AccountMapper accountMapper;
    @Autowired
    private UserMenuService userMenuService;
    @Autowired
    OrgService orgService;

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

    public Boolean delete(List<Integer> id) {
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
        Integer orgId = orgService.insertIfAbsentOrg(userCreateInfo.getOrgName(), userCreateInfo.getOrgId());
        userCreateInfo.setOrgId(orgId);
    }

    private void updateUserAccount(UserCreateInfo userCreateInfo) {
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userCreateInfo);
        userMapper.update(userEntity);
        AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userCreateInfo);
        accountMapper.update(accountEntity);
    }

    public UserInfo query(String token) {
        UserEntity userEntity = userMapper.queryByToken(token);
        UserInfo userInfo = PojoMapper.INSTANCE.toUserInfo(userEntity);
        userInfo.setUserMenuInfoList(userMenuService.get(userInfo.getId()));
        return userInfo;
    }
}

