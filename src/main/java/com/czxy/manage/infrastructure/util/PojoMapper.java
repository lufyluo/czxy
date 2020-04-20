package com.czxy.manage.infrastructure.util;

import com.czxy.manage.model.entity.AccountEntity;
import com.czxy.manage.model.entity.ClassEntity;
import com.czxy.manage.model.entity.ClassOrgEntity;
import com.czxy.manage.model.entity.UserEntity;
import com.czxy.manage.model.vo.classes.ClassInfo;
import com.czxy.manage.model.vo.classes.ClassOrgInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 19-8-15 上午11:35
 */
@Mapper
public interface PojoMapper {
    PojoMapper INSTANCE = Mappers.getMapper(PojoMapper.class);

    UserEntity toUserEntity(UserCreateInfo userInfo);

    AccountEntity toAccountEntity(UserCreateInfo userInfo);

    ClassOrgInfo toClassOrgInfo(ClassOrgEntity entity);
    List<ClassOrgInfo> toClassOrgInfos(List<ClassOrgEntity> classEntities);
}
