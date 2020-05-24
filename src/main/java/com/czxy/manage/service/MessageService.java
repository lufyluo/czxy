package com.czxy.manage.service;

import com.czxy.manage.dao.MessageMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.SendEntity;
import com.czxy.manage.model.entity.StudentDetailEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSendEntity;
import com.czxy.manage.model.vo.SendInfo;
import com.czxy.manage.model.vo.student.GetAllParam;
import com.czxy.manage.model.vo.student.StudentPageParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Resource
    private MessageMapper messageMapper;
    @Autowired
    private StudentService studentService;

    public Boolean send(SendInfo sendInfo) {
        GetAllParam param = PojoMapper.INSTANCE.toGetAllParam(sendInfo);
        List<StudentDetailEntity> studentDetailEntities = studentService.getAllUser(param);
        if (studentDetailEntities != null && studentDetailEntities.size() > 0) {
            pushWechat(studentDetailEntities
                    .stream()
                    .map(n -> n.getWechatId())
                    .filter(n -> !StringUtils.isEmpty(n))
                    .distinct()
                    .collect(Collectors.toList()));

            List<SendEntity> entities = studentDetailEntities
                    .stream()
                    .map(n -> {
                        SendEntity sendEntityTemp = new SendEntity();
                        sendEntityTemp.setMessage(sendInfo.getMessage());
                        sendEntityTemp.setUserId(n.getUserId());
                        sendEntityTemp.setIsToAll(sendInfo.getIsToAll());
                        return sendEntityTemp;
                    }).collect(Collectors.toList());
            if (entities != null && entities.size() > 0) {
                batchInsert(entities);
            }
        }

        return true;
    }

    /**
     * 批次操作，适应多数据
     */
    private void batchInsert(List<SendEntity> list) {
        int batchNum = 400;
        for (int i = 0; i < list.size() / batchNum + 1; i++) {
            int pageSize =
                    batchNum + i * batchNum >= list.size() ? list.size() : batchNum + i * batchNum;
            messageMapper.batchInsert(list.subList(i, pageSize));
            if (i * batchNum >= list.size()) {
                break;
            }
        }
    }

    private void pushWechat(List<String> wechatIds) {

    }

}
