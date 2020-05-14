package com.czxy.manage.infrastructure.util.wechat;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Slf4j
@Component
public class WechatUtil {
    @Value("${czxy.wechat.accessTokenUrl}")
    public  String accessTokenUrl;
    @Autowired
    private RestTemplate restTemplate;
    public String getOpenId(String code){
        try {
            String url = String.format(accessTokenUrl,code);
            log.info("url: "+url);
//            ResponseEntity<WechatAccessTokenResponse> forEntity = restTemplate.getForEntity(url, WechatAccessTokenResponse.class);
            ResponseEntity<String> forEntity = restTemplate.getForEntity(url, String.class);
            log.info("微信返回值："+ forEntity.getBody());
            Map<String,String> map = JSON.parseObject(forEntity.getBody(),Map.class);
            if(map.get("errcode")==null&&map.get("openid")!=null){
                return map.get("openid").toString();
            }
            log.info(map.get("errcode").toString());
            return null;
        }catch (Exception ex){
            log.error("获取openid发生错误："+ex);
            return null;
        }

    }
}
