package com.czxy.manage.service;

import com.czxy.manage.dao.ContactLogMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.ContactLogEntity;
import com.czxy.manage.model.vo.customerContacts.ContactLogInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ContactLogService {
    @Resource
    private ContactLogMapper contactLogMapper;

    public Boolean add(ContactLogInfo contactLogInfo) {
        contactLogMapper.insert(contactLogInfo);
        return true;
    }

    public List<ContactLogInfo> get(Integer planId) {
        List<ContactLogEntity> entities= contactLogMapper.query(planId);
        return PojoMapper.INSTANCE.toContactLogInfo(entities);
    }
}
