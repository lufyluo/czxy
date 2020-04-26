package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface AddressMapper {
    AddressEntity query(int addressId);
}
