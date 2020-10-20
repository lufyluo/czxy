package com.czxy.manage.model.vo.user;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.czxy.manage.model.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Author lufy
 * @Description ...
 * @Date 20-4-16 下午9:10
 */
@Data
public class UserInfo extends BaseEntity {
    @ApiModelProperty("用户id,新加则不传")
    private Integer id;
    private String name;
    private String idCard;
    private String phone;
    @ApiModelProperty("0-男,1-女,2-未知")
    private Integer gender;
    @ApiModelProperty("男,女,未知")
    private String genderDesc;
    @ApiModelProperty("职位")
    private String position;
    private Integer age;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date birthday;
    @ApiModelProperty("用户类型-0：普通用户，1：学员，2：讲师，3：班主任，4：公职人员，5：其它")
    private Integer category;
    private String wechatId;
    @ApiModelProperty("工作单位id")
    private Integer orgId;
    @ApiModelProperty("民族")
    private String nation;
    @ApiModelProperty("籍贯")
    private String nativePlace;
    @ApiModelProperty("学历")
    private String education;
    @ApiModelProperty("政治面貌")
    private String politics;
    @ApiModelProperty("头像")
    private String headImg;
    @ApiModelProperty("账号")
    private String account;
    @ApiModelProperty("用户菜单权限")
    private List<UserMenuInfo> userMenuInfoList;
}
