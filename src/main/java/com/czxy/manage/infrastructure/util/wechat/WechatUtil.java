package com.czxy.manage.infrastructure.util.wechat;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@ConditionalOnBean(RestTemplate.class)
@Component
public class WechatUtil {
    @Value("${czxy.wechat.accessTokenUrl}")
    public  String accessTokenUrl;
    @Autowired
    private RestTemplate restTemplate;
    public WechatAccessTokenResponse getOpenId(String code){
        try {
            String url = String.format(accessTokenUrl,code);
            ResponseEntity<WechatAccessTokenResponse> forEntity = restTemplate.getForEntity(url, WechatAccessTokenResponse.class);
            return forEntity.getBody();
        }catch (Exception ex){
            log.error("获取openid发生错误："+ex);
            return null;
        }

    }
}
