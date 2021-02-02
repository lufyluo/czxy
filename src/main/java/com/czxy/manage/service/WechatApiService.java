package com.czxy.manage.service;

import com.czxy.manage.infrastructure.util.wechat.WechatUtil;
import com.czxy.manage.model.vo.wechat.MediaBody;
import com.czxy.manage.model.vo.wechat.TagInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class WechatApiService {
    @Autowired
    private WechatUtil wechatUtil;
    public Integer createTag(String tag) {
        return wechatUtil.createTag(tag);
    }

    public List<TagInfo> get(String tag) {
        return null;
    }

    public Boolean preview(String openId,String msg) {
        return wechatUtil.preview(openId,msg);
    }

    public Boolean bind(Integer tagId, String wechatId) {
        return wechatUtil.addTag(Arrays.asList(wechatId),tagId);
    }

    public Boolean clear(Integer tagId, String wechatId) {
        return wechatUtil.clearTag(Arrays.asList(wechatId),tagId);
    }
}
