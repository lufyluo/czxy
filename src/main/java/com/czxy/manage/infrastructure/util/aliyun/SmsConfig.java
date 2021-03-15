package com.czxy.manage.infrastructure.util.aliyun;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.czxy.manage.infrastructure.config.AliyunOssConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SmsConfig {
    @Autowired
    private AliyunOssConfig aliyunOssConfig;

    @Bean
    public IAcsClient DefaultAcsClient() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", aliyunOssConfig.getAccessKey(), aliyunOssConfig.getAccessKeySecret());
        IAcsClient client = new DefaultAcsClient(profile);
        return client;
    }
}
