package com.czxy.manage.infrastructure.scheduler;

import com.czxy.manage.dao.GreetMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.wechat.WechatUtil;
import com.czxy.manage.model.entity.GreetEntity;
import com.czxy.manage.model.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
@EnableScheduling
public class BirthDayScheduler {
    @Resource
    private GreetMapper greetMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private WechatUtil wechatUtil;
    private final Integer tagId = 100;

    @Scheduled(cron = "0 11 21 ? * *")
    public void birthDayMessageExecute() {
        List<UserEntity> userEntities = getBirthDayUsers();
        if (userEntities == null || userEntities.size() == 0) {
            return;
        }

        GreetEntity greetEntity = greetMapper.queryFirstBirthDay();
        if (greetEntity == null) {
            log.warn("未设置生日祝福模版！");
            return;
        }
        userEntities.stream().forEach(n -> {
            Boolean notEmptyTagGroups = wechatUtil.hasFans(tagId);
            if (notEmptyTagGroups) {
                log.error("tag 小组未清空！");
                return;
            }
            List<String> openIDs = Arrays.asList(n.getWechatId());
            Boolean aBoolean1 = wechatUtil.addTag(openIDs, tagId);
            if (!aBoolean1) {
                log.error("添加组失败");
                return;
            }
            String birthdayMsg = getBirthdayMsg(n, greetEntity.getGreet());
            wechatUtil.send(tagId, birthdayMsg);
            Boolean clearFlag = wechatUtil.clearTag(openIDs, tagId);
            if (!clearFlag) {
                return;
            }
        });
    }

    private List<UserEntity> getBirthDayUsers() {
        LocalDate now = LocalDate.now();
        String nowDate = now.toString();
        String[] split = nowDate.split("-");
        String birthDay = split[1] + split[2];
        List<UserEntity> userEntities = userMapper.queryByBirthDay(birthDay);
        return userEntities;
    }

    private String getBirthdayMsg(UserEntity userEntity, String msgTemplate) {
        return msgTemplate.replace("{user}", userEntity.getName());
    }
}
