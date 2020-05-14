package com.czxy.manage.infrastructure.util.wechat;

import lombok.Data;

@Data
public class WechatAccessTokenResponse {
    /**
     *
     * {
     *   "access_token":"ACCESS_TOKEN",
     *   "expires_in":7200,
     *   "refresh_token":"REFRESH_TOKEN",
     *   "openid":"OPENID",
     *   "scope":"SCOPE"
     * }
     */
    private String access_token;
    private Long expires_in;
    private String refresh_token;
    private String openid;
    private String scope;
}
