package com.czxy.manage.service;

import com.czxy.manage.dao.MenuMapper;
import com.czxy.manage.dao.UserMenuMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.infrastructure.util.maplain.MaplainUtil;
import com.czxy.manage.model.entity.MenusEntity;
import com.czxy.manage.model.entity.UserMenuEntity;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import com.czxy.manage.model.vo.user.UserMenuSaveInfo;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.Arrays;
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
        UserMenuEntity userMenuEntities = userMenuMapper.getByUserId(userId);
        if (userMenuEntities == null) {
            userMenuInfos.forEach(n -> {
                n.setPermission(false);
            });
            return MaplainUtil.toHierarchy(userMenuInfos);
        }
        List<String> codes = Arrays.asList(userMenuEntities.getMenuCodes().split(","));
        userMenuInfos.forEach(n -> {
            n.setPermission(codes.contains(n.getCode()));
        });
        return MaplainUtil.toHierarchy(userMenuInfos);
    }

    @Transactional
    public Boolean save(UserMenuSaveInfo userMenuSaveInfo) {
        userMenuMapper.delete(userMenuSaveInfo.getUserId());
        userMenuMapper.insert(userMenuSaveInfo.getUserId(),
                StringUtils.collectionToDelimitedString(userMenuSaveInfo.getCodes(), ","));
        return true;
    }
}
