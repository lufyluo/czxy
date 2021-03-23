package com.czxy.manage.dao;

import com.czxy.manage.model.entity.AddressEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AddressMapper {
    AddressEntity query(@Param("addressId") int addressId);

    List<AddressEntity> queryAll(@Param("parentId") Integer parentId, @Param("category") int category);
}
