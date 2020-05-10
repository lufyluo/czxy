package com.czxy.manage.service;

import com.czxy.manage.dao.AccountMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Autowired
    private AccountService accountService;

    @Transactional
    public Boolean add(UserCreateInfo userInfo) {
        insertIfAbsentOrg(userInfo);
        UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userInfo);
        userMapper.insert(userEntity);
        AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userInfo);
        accountEntity.setUserId(userEntity.getId());
        accountEntity.setPassword(accountService.decodePassword(accountEntity.getPassword(),null));
        accountMapper.insert(accountEntity);
        return true;
    }

    public Boolean delete(List<Integer> id) {
        userMapper.delete(id);
        accountMapper.deleteByUserId(id);
        return true;
    }

    @Transactional
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

    public PageInfo<UserInfo> page(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<UserEntity> userEntities = userMapper.queryMaster(pageParam.getParam());
        PageInfo<UserInfo> userInfos = page.toPageInfo();
        List<UserInfo> userInfoList = PojoMapper.INSTANCE.toUserInfos(userEntities);
        fillGender(userInfoList);
        userInfos.setList(userInfoList);
        return userInfos;
    }

    private void fillGender(List<UserInfo> userInfos) {
        if (userInfos != null) {
            userInfos.forEach(n->{

                switch (n.getGender()){
                    case 0:
                        n.setGenderDesc("男");
                        break;
                    case 1:
                        n.setGenderDesc("女");
                        break;
                    default:
                        n.setGenderDesc("未知");
                        break;
                }
            });
        }
    }

    public PageInfo<UserInfo> pageClassLeader(PageParam<String> pageParam) {
        Page page = PageHelper.startPage(pageParam.getPageIndex(), pageParam.getPageSize());
        List<UserEntity> userEntities = userMapper.queryClassLeader(pageParam.getParam());
        PageInfo<UserInfo> userInfos = page.toPageInfo();
        List<UserInfo> userInfoList = PojoMapper.INSTANCE.toUserInfos(userEntities);
        userInfos.setList(userInfoList);
        return userInfos;
    }
}

