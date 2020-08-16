package com.czxy.manage.service;

import com.czxy.manage.dao.ChatroomMapper;
import com.czxy.manage.dao.ClassMapper;
import com.czxy.manage.infrastructure.gloable.ManageException;
import com.czxy.manage.infrastructure.response.ResponseStatus;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ChatContentDetailEntity;
import com.czxy.manage.model.entity.ChatContentEntity;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.vo.chat.ChatContentCreateInfo;
import com.czxy.manage.model.vo.chat.ChatContentInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ChatroomService {
    @Resource
    private ClassMapper classMapper;
    @Resource
    private ChatroomMapper chatroomMapper;
    public List<ChatContentInfo> query(Integer classId, Integer lastId, Integer step) {
        ClassEntity classEntity = classMapper.queryClass(classId);
        if(classEntity == null){
            throw new ManageException(ResponseStatus.DATANOTEXIST);
        }
        if(classEntity.getChatOff()==1){
            throw new ManageException(ResponseStatus.CHATROOM_CLOSED);
        }
        List<ChatContentDetailEntity> chatContentEntities = chatroomMapper.queryNexts(classId,lastId,step);

        return PojoMapper.INSTANCE.toChatContentInfos(chatContentEntities);
    }

    public Boolean post(ChatContentCreateInfo chatContentCreateInfo) {
        ChatContentEntity chatContentEntity = PojoMapper.INSTANCE.toChatContentEntity(chatContentCreateInfo);
        chatroomMapper.insert(chatContentEntity);
        return true;
    }
}
