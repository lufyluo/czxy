package com.czxy.manage.service;

import com.czxy.manage.dao.MessageMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.SendEntity;
import com.czxy.manage.model.vo.SendInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;

    public Boolean send(SendInfo sendInfo) {
        SendEntity sendEntity = PojoMapper.INSTANCE.toSendEntity(sendInfo);
        Set<String> wechatIds = new HashSet<>();
        if (sendEntity.getIsToAll()==1){
            messageMapper.insertAll(sendEntity);

            pushWechat(wechatIds);
            return true;
        }
        if (sendInfo.getClassIds()!=null){
            String classIds = sendInfo.getClassIds().stream().map(n -> n.toString()).collect(Collectors.joining(","));
            sendEntity.setClassIdsString(classIds);

        }
        if (sendInfo.getUserIds()!=null){
            String userIds = sendInfo.getUserIds().stream().map(n -> n.toString()).collect(Collectors.joining(","));
            sendEntity.setUserIdsString(userIds);

        }
        pushWechat(wechatIds);
        messageMapper.insert(sendEntity);

        return true;
    }

    private void pushWechat(Set<String> wechatIds) {

    }

}
