package com.czxy.manage.service;

import com.czxy.manage.dao.MenuMapper;
import com.czxy.manage.dao.UserMenuMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.MenusEntity;
import com.czxy.manage.model.entity.UserMenuEntity;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserMenuService {
    @Resource
    private MenuMapper menuMapper;

    @Resource
    private UserMenuMapper userMenuMapper;

    public List<UserMenuInfo> get(Integer userId) {
        List<MenusEntity> menusEntities = menuMapper.get();
        List<UserMenuInfo> userMenuInfos = PojoMapper.INSTANCE.toUserMenuInfos(menusEntities);
        List<UserMenuEntity> userMenuEntities = userMenuMapper.getByUserId(userId);
        if (userMenuEntities == null || userMenuEntities.size() == 0) {
            return userMenuInfos;
        }
        userMenuInfos.forEach(n -> {
            n.setPermission(userMenuEntities.contains(n.getId()));
        });
        return userMenuInfos;
    }
}
