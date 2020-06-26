package com.czxy.manage.infrastructure.util.wechat;

public class WechatConfig {
    private static final String msgTag = "msgTag";
    private static final int msgTagId = 100;
    private static final String paperTag = "paperTag";
    private static final int paperTagId = 101;

    public static synchronized String getMsgTag() {
        return msgTag;
    }

    public static synchronized String getPaperTag() {
        return paperTag;
    }
}
