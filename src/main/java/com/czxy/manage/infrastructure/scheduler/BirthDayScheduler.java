package com.czxy.manage.infrastructure.scheduler;

import com.alibaba.fastjson.JSON;
import com.czxy.manage.dao.GreetMapper;
import com.czxy.manage.dao.UserMapper;
import com.czxy.manage.infrastructure.util.aliyun.AliyunBirthdaySmsConfig;
import com.czxy.manage.infrastructure.util.aliyun.SmsUtil;
import com.czxy.manage.infrastructure.util.wechat.WechatUtil;
import com.czxy.manage.model.entity.GreetEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.SendInfo;
import com.czxy.manage.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    @Autowired
    private MessageService messageService;
    private final Integer tagId = 102;

    @Autowired
    private SmsUtil smsUtil;
    @Autowired
    private AliyunBirthdaySmsConfig config;

    //@Scheduled(cron = "0 30 12 ? * *")
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

    //@Scheduled(cron = "0 30 14 ? * *")
    public void batchBirthDayMessageExecute() {
        log.info("生日祝福开始！");
//        List<UserEntity> userEntities = getBirthDayUsers();
//        if (userEntities == null || userEntities.size() == 0) {
//            return;
//        }
//
//        GreetEntity greetEntity = greetMapper.queryFirstBirthDay();
//        if (greetEntity == null) {
//            log.warn("未设置生日祝福模版！");
//            return;
//        }
//        Boolean notEmptyTagGroups = wechatUtil.hasFans(tagId);
//        if (notEmptyTagGroups) {
//            log.error("tag 小组未清空！");
//            return;
//        }
//        List<String> openIDs = userEntities.stream().map(n->n.getWechatId()).collect(Collectors.toList());
//        Boolean aBoolean1 = wechatUtil.addTag(openIDs, tagId);
//        if (!aBoolean1) {
//            log.error("添加组失败");
//            return;
//        }
//        String birthdayMsg = getBirthdayMsg(null, greetEntity.getGreet());
//        Boolean sendFlag = wechatUtil.send(tagId, birthdayMsg);
//        if(sendFlag){
//            log.info("推送成功！");
//        }
//        Boolean clearFlag = wechatUtil.clearTag(openIDs, tagId);
//        if (!clearFlag) {
//            log.error("清理分组失败！");
//            return;
//        }
        log.info("生日祝福发送成功！");
    }


    @Scheduled(cron = "0 30 09 ? * *")
    public void batchBirthDaySMSMessageExecute() {
        log.info("生日祝福开始！");
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
            String birthdayMsg = getBirthdayMsg(n, greetEntity.getGreet());
            SendInfo sendInfo = new SendInfo();
            sendInfo.setMessage(birthdayMsg);
            sendInfo.setUserIds(Arrays.asList(n.getId()));
            sendInfo.setIsToAll(0);
            messageService.send(sendInfo);
            if (StringUtils.isEmpty(n.getPhone())) {
                return;
            }

            Map param = new HashMap();
            param.put("user", n.getName());
            String templateParam = JSON.toJSONString(param);
            smsUtil.sendTemplate(config.getTemplate(), config.getSign(), n.getPhone(), templateParam);
        });
        log.info("生日祝福发送成功！");
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
        if (userEntity == null) {
            return msgTemplate.replace("{user}", "");
        }
        return msgTemplate.replace("{user}", userEntity.getName());
    }
}
