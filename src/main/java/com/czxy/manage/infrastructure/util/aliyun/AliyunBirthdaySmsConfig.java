package com.czxy.manage.infrastructure.util.aliyun;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "aliyun.sms.birthday")
@Data
public class AliyunBirthdaySmsConfig {
    private String template;
    private String sign;

}
