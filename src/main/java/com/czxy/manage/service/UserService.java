package com.czxy.manage.service;

import com.czxy.manage.dao.AccountMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.PageParam;
import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.classes.ClassWechatInfo;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.czxy.manage.model.vo.user.UserPartInfo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

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

    public List<String> getWechatIds(List<Integer> userIds) {
        return userMapper.queryByUserIds(userIds);
    }

    @Transactional
    public Boolean add(UserCreateInfo userInfo) {
        List<UserEntity> userEntities = userMapper.queryByPhones(Arrays.asList(userInfo.getPhone()));
        //添加班主任特殊逻辑
        if (userEntities != null && userEntities.size() > 0 && userInfo.getCategory() != null && userInfo.getCategory() == 3) {
            UserEntity userEntity = userEntities.get(0);
            userInfo.setId(userEntity.getId());
            userMapper.updateCategory(userInfo);
            AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userInfo);
            accountEntity.setUserId(userEntity.getId());
            accountEntity.setPassword(accountService.decodePassword(accountEntity.getPassword(), null));
            Integer integer = accountMapper.updateAccount(accountEntity);
            if (integer == 0) {
                accountMapper.insert(accountEntity);
            }
            return true;
        }
        if (userEntities == null || userEntities.size() == 0) {
            insertIfAbsentOrg(userInfo);
            UserEntity userEntity = PojoMapper.INSTANCE.toUserEntity(userInfo);
            Integer insert = userMapper.insert(userEntity);
            userEntities.add(userEntity);
            if (insert != 1) {
                throw new ManageException(ResponseStatus.FAILURE, "添加用户失败，请联系管理员！");
            }
        }

        Integer result = accountMapper.existUserAccount(userInfo.getAccount(), userEntities.get(0).getId());
        if (result >= 1) {
            throw new ManageException(ResponseStatus.DATAEXIST, "该用户已经拥有账号！");
        }

        AccountEntity accountEntity = PojoMapper.INSTANCE.toAccountEntity(userInfo);
        accountEntity.setUserId(userEntities.get(0).getId());
        accountEntity.setPassword(accountService.decodePassword(accountEntity.getPassword(), null));
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
        checkParam(userCreateInfo);
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

    public String getGenderDesc(Integer gender) {
        String genderDesc = "";
        if (gender == null) {
            return "未知";
        }
        switch (gender) {
            case 0:
                genderDesc = "男";
                break;
            case 1:
                genderDesc = "女";
                break;
            default:
                genderDesc = "未知";
                break;
        }
        return genderDesc;
    }

    private void fillGender(List<UserInfo> userInfos) {
        if (userInfos != null) {
            userInfos.forEach(n -> n.setGenderDesc(getGenderDesc(n.getGender())));
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

    public UserInfo queryByWechatId(String openId) {
        UserEntity userEntity = userMapper.queryByWechatId(openId);
        return PojoMapper.INSTANCE.toUserInfo(userEntity);
    }

    public void fillUserId(List<UserEntity> userEntities) {
        if (userEntities != null && userEntities.size() != 0) {
            List<UserEntity> existUserEntities = userMapper.queryByPhones(userEntities
                    .stream()
                    .map(UserEntity::getPhone)
                    .filter(n -> !StringUtils.isEmpty(n))
                    .collect(Collectors.toList()));

            existUserEntities
                    .stream()
                    .collect(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(UserEntity::getPhone))));

            if (existUserEntities == null || existUserEntities.size() == 0) {
                userMapper.batchInsert(userEntities);
            } else if (existUserEntities.size() == userEntities.size()) {
                copyUserId(userEntities, existUserEntities);

            } else {
                List<UserEntity> newUserEntities = userEntities
                        .stream()
                        .filter(n -> !existUserEntities
                                .stream()
                                .anyMatch(m -> ObjectUtils.nullSafeEquals(m.getPhone(), n.getPhone())))
                        .collect(Collectors.toList());
                userMapper.batchInsert(newUserEntities);
                existUserEntities.addAll(newUserEntities);
                copyUserId(userEntities, existUserEntities);
            }
        }
    }

    private void copyUserId(List<UserEntity> target, List<UserEntity> source) {
        target.forEach(n -> {
            Optional<UserEntity> userEntity = source
                    .stream()
                    .filter(m -> ObjectUtils.nullSafeEquals(n.getPhone(), m.getPhone())).findFirst();
            if (userEntity.isPresent()) {
                n.setId(userEntity.get().getId());
            }
        });
    }

    public UserPartInfo queryById(Integer userId) {
        UserEntity userEntity = userMapper.queryByUserId(userId);
        UserPartInfo userInfo = PojoMapper.INSTANCE.toUserPartInfo(userEntity);
        return userInfo;
    }

    public List<FileInfo> get(Integer id) {
        List<ClassEntity> classEntities = userMapper.queryClassId(id);
        if (classEntities == null || classEntities.size() == 0) {
            throw new ManageException(ResponseStatus.FAILURE, "您还不是班级学生，请先加入班级");
        }
        List<Integer> classIds = classEntities.stream().map(n -> n.getId()).collect(Collectors.toList());
        List<FileInfo> fileInfos = userMapper.queryFile(classIds);
        return fileInfos;
    }

    public List<ClassWechatInfo> gerClass(Integer userId) {
        List<ClassWechatInfo> classWechatInfos = userMapper.queryWechatClass(userId);
        return classWechatInfos;
    }

    private void checkParam(UserCreateInfo userCreateInfo) {
        Integer user = userMapper.exist(userCreateInfo.getPhone());
        if (user > 0) {
            throw new ManageException(ResponseStatus.DATAEXIST, "电话已被使用！");
        }

        Integer account = accountMapper.exist(userCreateInfo.getAccount());
        if (account > 0) {
            throw new ManageException(ResponseStatus.DATAEXIST, "账号已被使用！");
        }
    }
}

