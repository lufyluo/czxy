package com.czxy.manage.infrastructure.config;

import com.czxy.manage.infrastructure.util.Base64Util;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.oss")
@Setter
public class AliyunOssConfig {
    private String endPoint;
    private String accessKey;
    private String accessKeySecret;
    private String bucket;

    public String getEndPoint() {
        return decode(endPoint);
    }

    public String getAccessKey() {
        return decode(accessKey);
    }

    public String getAccessKeySecret() {
        return decode(accessKeySecret);
    }

    public String getBucket() {
        return decode(bucket);
    }
    private String decode(String content){
        return Base64Util.decodeData(Base64Util.decodeData(content));
    }
}
