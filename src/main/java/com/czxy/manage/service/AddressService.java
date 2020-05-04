package com.czxy.manage.service;

import com.czxy.manage.dao.AddressMapper;
import com.czxy.manage.infrastructure.util.PojoMapper;
import com.czxy.manage.model.entity.AddressEntity;
import com.czxy.manage.model.vo.AddressInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AddressService {
    @Resource
    AddressMapper addressMapper;
    public List<AddressInfo> get(Integer parentId, int category) {
        List<AddressEntity> addressEntities = addressMapper.queryAll(parentId,category);
        return PojoMapper.INSTANCE.toAddressInfos(addressEntities);
    }
}
