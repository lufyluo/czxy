package com.czxy.manage.infrastructure.util.wechat;

public class WechatConfig {
    private static final String msgTag = "msgTag";
    public static final int msgTagId = 102;
    private static final String paperTag = "paperTag";
    public static final int paperTagId = 101;

    public static synchronized String getMsgTag() {
        return msgTag;
    }

    public static synchronized String getPaperTag() {
        return paperTag;
    }
}
