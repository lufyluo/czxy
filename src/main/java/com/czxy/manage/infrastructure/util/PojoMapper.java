package com.czxy.manage.infrastructure.util;

import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.vo.CompositionInfo;
import com.czxy.manage.model.vo.OrgInfo;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.czxy.manage.model.vo.student.StudentAddInfo;
import com.czxy.manage.model.vo.student.StudentDetailInfo;
import com.czxy.manage.model.vo.student.StudentUpdateInfo;
import com.czxy.manage.model.vo.user.UserCreateInfo;
import com.czxy.manage.model.vo.user.UserInfo;
import com.czxy.manage.model.vo.user.UserMenuInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
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

    OrgEntity toOrgEntity(UserCreateInfo userCreateInfo);

    UserMenuInfo toUserMenuInfo(MenusEntity menusEntity);

    List<UserMenuInfo> toUserMenuInfos(List<MenusEntity> menusEntities);

    ClassFeeDetailInfo toClassFeeDetailInfo(ClassFeeEntity classFeeEntity);

    List<ClassFeeDetailInfo> toClassFeeDetailInfos(List<ClassFeeEntity> classFeeEntities);

    ClassArrangeInfo toClassArrangeInfo(ArrangeEntity arrangeEntity);

    @Mappings({
            @Mapping(ignore = true, target = "beginTime"),
            @Mapping(ignore = true, target = "endTime")
    })
    CourseDetailInfo toCourseInfo(CourseDetailEntity courseEntities);

    List<CourseDetailInfo> toCourseInfos(List<CourseDetailEntity> courseEntities);

    ClassInfo toClassInfo(ClassEntity classEntity);

    ClassInformationInfo toClassInformationInfo(ClassInformationEntity classInformationEntity);

    ClassStudentInfo toClassStudentInfo(ClassStudentEntity classStudentEntity);

    List<ClassStudentInfo> toClassStudentInfos(List<ClassStudentEntity> classStudentEntities);

    UserInfo toUserInfo(UserEntity userEntity);

    StudentDetailInfo toStudentDetailInfos(StudentDetailEntity studentDetailEntity);

    List<StudentDetailInfo> toStudentDetailInfos(List<StudentDetailEntity> studentDetailEntities);

    UserEntity studentAddToUserEntity(StudentAddInfo studentAddInfo);

    List<UserEntity> studentAddToUserEntities(List<StudentAddInfo> studentAddInfos);

    StudentEntity toStudentEntity(StudentAddInfo studentAddInfo);

    List<StudentEntity> toStudentEntities(List<StudentAddInfo> studentAddInfos);

    UserEntity studentUpdateToUserEntity(StudentUpdateInfo studentUpdateInfo);

    StudentEntity toStudentEntityByStudentUpadate(StudentUpdateInfo studentUpdateInfo);

    UserUpdateEntity studentUpdateToUserUpdateEntity(StudentUpdateInfo studentUpdateInfo);

    ClassEntity classCreateInfoToClassEntity(ClassCreateInfo classCreateInfo);

    @Mappings({@Mapping(ignore = true, target = "types"), @Mapping(ignore = true, target = "topics")})
    SiteEntity toSiteEntity(SiteAddInfo siteAddInfo);

    TypeEntity siteAddInfoToType(TypeInfo types);

    List<TypeEntity> siteAddInfoToTypes(List<TypeInfo> types);

    TypeInfo toTypeInfo(TypeEntity typeEntity);
    List<TypeInfo> toTypeInfos(List<TypeEntity> typeEntities);

    OrgInfo toOrgInfo(OrgEntity orgEntity);
    List<OrgInfo> toOrgInfos(List<OrgEntity> orgEntities);

    TypeEntity toTypeEntity(TypeInfo topicInfo);
    List<TypeEntity> toTypeEntities(List<TypeInfo> topicInfos);

    CompositionInfo toCompositionInfo(CompositionEntity compositionEntity);
    List<CompositionInfo> toCompositionInfos(List<CompositionEntity> compositionEntities);
}
