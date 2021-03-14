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
import java.util.stream.Collectors;

@Component
@Slf4j
@EnableScheduling
public class SpecialDayScheduler {
    @Resource
    private GreetMapper greetMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private WechatUtil wechatUtil;
    private final Integer tagId = 102;

    @Scheduled(cron = "20 46 20 ? * *")
    public void batchBirthDayMessageExecute() {
        log.info("新年祝福开始！");
        List<UserEntity> userEntities = getUsers();
        if (userEntities == null || userEntities.size() == 0) {
            return;
        }

        Boolean notEmptyTagGroups = wechatUtil.hasFans(tagId);
        if (notEmptyTagGroups) {
            log.error("tag 小组未清空！");
            return;
        }
        List<String> openIDs = userEntities.stream().map(n -> n.getWechatId()).collect(Collectors.toList());
        Boolean aBoolean1 = wechatUtil.addTag(openIDs, tagId);
        if (!aBoolean1) {
            log.error("添加组失败");
            return;
        }
        String birthdayMsg = "银鼠辞旧岁，金牛送春来！值此除夕，成都村政学院祝您及家人新年快乐，身体健康，合家幸福，牛年大吉！";
//        String birthdayMsg = getBirthdayMsg(null, greetEntity.getGreet());
        Boolean sendFlag = wechatUtil.send(tagId, birthdayMsg, true);
        if (sendFlag) {
            log.info("推送成功！");
        }
        Boolean clearFlag = wechatUtil.clearTag(openIDs, tagId);
        if (!clearFlag) {
            log.error("清理分组失败！");
            return;
        }
        log.info("新年祝福发送成功！");
    }

    private List<UserEntity> getUsers() {
        return userMapper.queryWechatUsers();
    }
}
