/**
 * @Project tourism-common
 * @Title user.java
 * @Package cn.enn.tourism.common.bean
 * @author anhui
 * @date 2019年4月18日 上午11:24:16
 * @version V1.0
 * @Copyright 2019 All rights Reserved, Designed By anhui.
 *
 */
package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 常量类
 * @Description
 * @ClassName  user
 * @author jiabaiye
 * @since JDK 1.8
 */
@Data
@ApiModel("客户端用户信息入参")
public class UserParam {

    private static final long serialVersionUID = -7789368425289461451L;
    @ApiModelProperty("id")
    private Long id;
    @ApiModelProperty("名字")
    private String name;
    @ApiModelProperty("昵称")
    private String nickName;
    @ApiModelProperty("性别")
    private Integer gender;
    @ApiModelProperty("头图")
    private String headImg;
    @ApiModelProperty("身份证号")
    private String idCard;

    /**
     * 一个用户对应多个角色
     */
    @ApiModelProperty("一个用户对应多个角色")
    private List<SysRoleParam> sysRoles;



}
