package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface AddressMapper {
    AddressEntity query(int addressId);

    List<AddressEntity> queryAll(Integer parentId, int category);
}
