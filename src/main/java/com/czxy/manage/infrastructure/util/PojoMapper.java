package com.czxy.manage.infrastructure.util;

import com.czxy.manage.model.entity.*;
import com.czxy.manage.model.entity.classFile.ClassFileEntity;
import com.czxy.manage.model.entity.questionnaire.PaperSubmitEntity;
import com.czxy.manage.model.entity.questionnaire.stem.OptionEntity;
import com.czxy.manage.model.entity.questionnaire.stem.PaperStemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemEntity;
import com.czxy.manage.model.entity.questionnaire.stem.StemOptionEntity;
import com.czxy.manage.model.vo.*;
import com.czxy.manage.model.vo.arrange.ArrangeInfo;
import com.czxy.manage.model.vo.chat.ChatContentCreateInfo;
import com.czxy.manage.model.vo.chat.ChatContentInfo;
import com.czxy.manage.model.vo.classes.*;
import com.czxy.manage.model.vo.classes.files.ClassFileCreateInfo;
import com.czxy.manage.model.vo.customer.CustomerCreateInfo;
import com.czxy.manage.model.vo.customer.CustomerInfo;
import com.czxy.manage.model.vo.customerContacts.ContactLogInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsCreateInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsInfo;
import com.czxy.manage.model.vo.customerContacts.CustomerContactsUpdateInfo;
import com.czxy.manage.model.vo.files.FileInfo;
import com.czxy.manage.model.vo.message.MessageInfo;
import com.czxy.manage.model.vo.plan.PlanCreateInfo;
import com.czxy.manage.model.vo.plan.PlanInfo;
import com.czxy.manage.model.vo.plan.PlanUpdateInfo;
import com.czxy.manage.model.vo.questionnaire.*;
import com.czxy.manage.model.vo.questionnaire.stem.OptionInfo;
import com.czxy.manage.model.vo.questionnaire.stem.PaperStemInfo;
import com.czxy.manage.model.vo.questionnaire.stem.StemInfo;
import com.czxy.manage.model.vo.site.SiteAddInfo;
import com.czxy.manage.model.vo.site.SiteDetailInfo;
import com.czxy.manage.model.vo.site.SiteInfo;
import com.czxy.manage.model.vo.site.TypeInfo;
import com.czxy.manage.model.vo.stock.StockInfo;
import com.czxy.manage.model.vo.student.*;
import com.czxy.manage.model.vo.subject.SubjectByIdInfo;
import com.czxy.manage.model.vo.subject.SubjectDetailDomainInfo;
import com.czxy.manage.model.vo.subject.SubjectImportInfo;
import com.czxy.manage.model.vo.subject.SubjectInfo;
import com.czxy.manage.model.vo.teacher.*;
import com.czxy.manage.model.vo.user.*;
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
    
    UserEntity toUserEntity(UserInfo userInfo);

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
    CourseSubjectDetailInfo toCourseInfo(CourseDetailEntity courseEntities);

    List<CourseSubjectDetailInfo> toCourseInfos(List<CourseDetailEntity> courseEntities);

    ClassInfo toClassInfo(ClassEntity classEntity);

    ClassInformationInfo toClassInformationInfo(ClassInformationEntity classInformationEntity);

    ClassStudentInfo toClassStudentInfo(ClassStudentEntity classStudentEntity);

    List<ClassStudentInfo> toClassStudentInfos(List<ClassStudentEntity> classStudentEntities);

    UserInfo toUserInfo(UserEntity userEntity);

    List<UserInfo> toUserInfos(List<UserEntity> userEntities);

    StudentDetailInfo toStudentDetailInfos(StudentDetailEntity studentDetailEntity);

    List<StudentDetailInfo> toStudentDetailInfos(List<StudentDetailEntity> studentDetailEntities);

    @Mappings({@Mapping(source = "userId", target = "id")})
    UserEntity studentAddToUserEntity(StudentAddInfo studentAddInfo);

    List<UserEntity> studentAddToUserEntities(List<StudentAddInfo> studentAddInfos);

    StudentEntity toStudentEntity(StudentAddInfo studentAddInfo);

    List<StudentEntity> toStudentEntities(List<StudentAddInfo> studentAddInfos);

    UserEntity studentUpdateToUserEntity(StudentUpdateInfo studentUpdateInfo);

    StudentEntity toStudentEntityByStudentUpadate(StudentUpdateInfo studentUpdateInfo);

    UserUpdateEntity studentUpdateToUserUpdateEntity(StudentUpdateInfo studentUpdateInfo);

    @Mappings({@Mapping(source = "classArrangeId",target = "arrangeId")})
    ClassEntity classCreateInfoToClassEntity(ClassCreateInfo classCreateInfo);

    @Mappings({@Mapping(ignore = true, target = "types"), @Mapping(ignore = true, target = "topicId")})
    SiteEntity toSiteEntity(SiteAddInfo siteAddInfo);


    List<SiteEntity> toSiteEntities(List<SiteAddInfo> siteInfos);

    TypeEntity toTypeEntity(TypeInfo types);

    List<TypeEntity> toTypeEntities(List<TypeInfo> types);

    TypeInfo toTypeInfo(TypeEntity typeEntity);

    List<TypeInfo> toTypeInfos(List<TypeEntity> typeEntities);

    OrgInfo toOrgInfo(OrgEntity orgEntity);

    List<OrgInfo> toOrgInfos(List<OrgEntity> orgEntities);


    CompositionInfo toCompositionInfo(CompositionEntity compositionEntity);

    List<CompositionInfo> toCompositionInfos(List<CompositionEntity> compositionEntities);

    TeacherDetailInfo toTeacherDetailInfo(TeacherDetailEntity teacherDetailEntity);

    List<TeacherDetailInfo> toTeacherDetailInfos(List<TeacherDetailEntity> teacherDetailEntities);

    AddressInfo toAddressInfo(AddressEntity addressEntity);

    List<AddressInfo> toAddressInfos(List<AddressEntity> addressEntities);

    UserEntity teacherInfoToUserEntity(TeacherInfo teacherInfo);

    TeacherEntity toTeacherEntity(TeacherInfo teacherInfo);

    TeacherEntity TeacherUpdateToTeacherEntity(TeacherUpdateInfo teacherUpdateInfo);

    UserEntity teacherUpdateToUserEnity(TeacherUpdateInfo teacherUpdateInfo);

    UserUpdateEntity teacherUpdateToUserUpdateEnity(TeacherUpdateInfo teacherUpdateInfo);

    SiteInfo toSiteInfo(SiteEntity siteEntity);
    @Mappings({@Mapping(target = "types",ignore = true),
            @Mapping(target = "pics",ignore = true),
            @Mapping(target = "attachFiles",ignore = true)})
    SiteDetailInfo toSiteDetailInfo(SiteEntity siteEntity);

    List<SiteInfo> toSiteInfo(List<SiteEntity> siteEntities);

    TeacherInformationInfo toTeacherInformationInfo(TeacherInformationEntity teacherInformationEntity);

    UserAccountInfo toUserAccountInfo(UserAccountInfo userAccountInfo);

    List<UserAccountInfo> toUserAccountInfos(List<UserAccountInfo> userAccountInfos);

    com.czxy.manage.model.vo.subject.SubjectDetailInfo toSubjectDetaiInfo(SubjectDetailEntity subjectDetailEntity);

    List<com.czxy.manage.model.vo.subject.SubjectDetailInfo> toSubjectDetaiInfos(List<SubjectDetailEntity> subjectDetailEntities);

    @Mappings({@Mapping(ignore = true, target = "types")})
    SubjectEntity toSubjectEntity(SubjectInfo subjectInfo);

    @Mappings({@Mapping(ignore = true, target = "types")})
    SubjectByIdInfo toSubjectByIdInfo(SubjectEntity subjectEntity);

    List<SubjectByIdInfo> toSubjectByIdInfos(List<SubjectEntity> subjectEntities);

    ClassEntity classUpdateInfoToClassEntity(ClassUpdateInfo classCreateInfo);

    ArrangeInfo toArrangeInfo(ArrangeEntity arrangeEntity);

    List<ArrangeInfo> toArrangeInfos(List<ArrangeEntity> arrangeEntities);

    ArrangeEntity toArrangeEntity(CourseArrangeAddInfo classCourseInfo);

    CourseArrangeEntity toCourseArrangeEntity(CourseArrangeInfo classCourseInfo);

    List<CourseArrangeEntity> toCourseArrangeEntities(List<CourseArrangeInfo> courseInfos);

    CustomerInfo toCustomerInfo(CustomerEntity customerEntity);

    List<CustomerInfo> toCustomerInfos(List<CustomerEntity> customerEntityList);

    GreetInfo toGreetInfo(GreetEntity greetEntity);

    List<GreetInfo> toGreetInfos(List<GreetEntity> greetEntityList);

    SubjectDetailDomainInfo toSubjectDetailDomainInfo(CourseSubjectDetailInfo courseInfos);

    List<SubjectDetailDomainInfo> toSubjectDetailDomainInfos(List<CourseSubjectDetailInfo> courseInfos);

    CustomerEntity toCustomerEntity(CustomerCreateInfo customerInfo);

    PlanInfo toPlanEntitys(PlanEntity planEntity);

    List<PlanInfo> toPlanEntitys(List<PlanEntity> planEntities);

    PlanEntity toPlanEntity(PlanUpdateInfo planUpdateInfo);

    PlanEntity planCreateInfoToPlanEntity(PlanCreateInfo planCreateInfo);


    StockInfo toStockInfo(StockEntity stockEntity);

    List<StockInfo> toStockInfos(List<StockEntity> stockEntityList);

    FileInfo toFileInfo(FileEntity fileEntities);

    List<FileInfo> toFileInfos(List<FileEntity> fileEntities);

    SendEntity toSendEntity(SendInfo sendInfo);

    PaperInfo toPaperInfo(PaperInfo paperInfo);

    List<PaperInfo> toPaperInfos(List<PaperEntity> paperEntityList);

    StemEntity toStemEntity(StemInfo stemCreateInfo);

    OptionEntity toOptionEntities(OptionInfo options);
    List<OptionEntity> toOptionEntities(List<OptionInfo> options);

    StemInfo toStemInfo(StemEntity stemEntity);
    List<StemInfo> toStemInfos(List<StemEntity> stemEntities);

    ArrangeEntity updateInfoToArrangeEntity(CourseArrangeUpdateInfo courseArrangeUpdateInfo);

    PaperStemInfo toPaperStemInfo(PaperStemEntity stemEntities);
    List<PaperStemInfo> toPaperStemInfos(List<PaperStemEntity> stemEntities);

    StemInfo toStemOptionInfo(StemOptionEntity stemEntities);
    List<StemInfo> toStemOptionInfos(List<StemOptionEntity> stemEntities);

    GetAllParam toGetAllParam(PaperPublisInfo paperPublisInfo);

    GetAllParam toGetAllParam(SendInfo sendInfo);

    CustomerContactsInfo toCustomerContactsInfo(CustomerContactsEntity customerEntityList);
    List<CustomerContactsInfo> toCustomerContactsInfos(List<CustomerContactsEntity> customerEntityList);

    CustomerEntity toCustomerEntity(CustomerContactsCreateInfo customerContactsCreateInfo);

    PlanEntity toPlanEntity(CustomerContactsCreateInfo customerContactsCreateInfo);

    CustomerEntity toCustomerEntity(CustomerContactsUpdateInfo customerInfo);

    PlanEntity toPlanEntity(CustomerContactsUpdateInfo customerInfo);

    ContactLogInfo toContactLogInfo(ContactLogEntity entities);
    List<ContactLogInfo> toContactLogInfo(List<ContactLogEntity> entities);


    MessageInfo toMessageInfo(MessageEntity messageEntity);
    List<MessageInfo> toMessageInfos(List<MessageEntity> messageEntities);

    PaperDetailInfo toPaperDetailInfo(PaperDetailEntity paperDetailEntity);

    StemDetailInfo toStemDetailInfo(PaperDetailEntity stemInfo);

    StudentClassNameInfo toStudentClassNameInfo(StudentClassNameEntity studentClassNameEntity);

    PaperSubmitEntity toPaperSubmitEntity(PaperSubmitInfo paperSubmitInfo);

    StemAnalysisDetailInfo toPaperDetailEntity(PaperDetailEntity stemInfo);

    PaperAnalysisDetailInfo toPaperAnalysisDetailInfo(PaperDetailEntity paperDetailEntity);

    OptionAnalysisDetailInfo toOptionAnalysisDetailInfo(PaperDetailEntity paperDetailEntity);

    UserEntity importTeacherInfosToUserEntity(ImportTeacherInfo teacherInfos);
    List<UserEntity> importTeacherInfosToUserEntities(List<ImportTeacherInfo> teacherInfos);

    @Mappings({
            @Mapping(target = "score",ignore = true)
    })
    TeacherEntity importTeacherInfoToTeacherEntity(ImportTeacherInfo teacherInfos);
    List<TeacherEntity> importTeacherInfoToTeacherEntity(List<ImportTeacherInfo> teacherInfos);

    UserPartInfo toUserPartInfo(UserEntity userEntity);

    ClassFileEntity toClassFileEntity(ClassFileCreateInfo classCreateInfo);

    SubjectEntity toImportSubjectEntity(SubjectImportInfo subjectImportInfo);
    List<SubjectEntity> toImportSubjectEntities(List<SubjectImportInfo> subjectImportInfos);

    ChatContentEntity toChatContentEntity(ChatContentCreateInfo chatContentCreateInfo);

    ChatContentInfo toChatContentInfo(ChatContentDetailEntity chatContentDetailEntity);
    List<ChatContentInfo> toChatContentInfos(List<ChatContentDetailEntity> chatContentEntities);

    ContactLogEntity toContactLogEntity(ContactLogInfo contactLogInfo);

    @Mappings({
            @Mapping(source ="assistantId",target = "id"),
            @Mapping(source ="assistantName",target = "name")
    })
    MasterInfo toMasterInfo(ClassOrgEntity value);
    List<MasterInfo> toMasterInfos(List<ClassOrgEntity> value);
}
