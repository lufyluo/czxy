package com.czxy.manage.service;

import com.czxy.manage.dao.UserMenuMapper;
import com.czxy.manage.infrastructure.response.BaseResponse;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserMenuService {
    @Resource
    private UserMenuMapper userMenuMapper;

    public List<UserMenuInfo> get() {
        List<UserMenuInfo> userMenuInfo = userMenuMapper.get();
        return userMenuInfo;
    }
}
