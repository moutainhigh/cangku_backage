package cn.enn.wise.platform.mall.bean.vo;


import cn.enn.wise.platform.mall.bean.bo.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * <p>
 * 员工表
 * </p>
 *
 * @author baijie
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "sysStaff",description = "传输数据对象")
public class SysStaff implements Serializable {

private static final long serialVersionUID=1L;

    /**
     * 主键
     */
    private Long id;

    /**
     * 公司id
     */
    @ApiModelProperty(value = "公司id")
    private Long companyId;

    /**
     * 员工名称
     */
    @ApiModelProperty(value = "员工名称")
    private String name;

    /**
     * 身份证号码
     */
    @ApiModelProperty(value = "身份证件号")
    private String idCard;

    /**
     * 手机号
     */
    @ApiModelProperty(value = "手机号")
    private String phone;

    /**
     * 手持身份证
     */
    @ApiModelProperty(value = "身份证件号")
    private String idCardImg;
    /**
     * 一个用户对应多个角色
     */
    private List<SysRole> sysRoles;

    /**
     * token
     */
    @ApiModelProperty(value = "token")
    private String token;

    /**
     * 登录密码
     */
    @ApiModelProperty(value = "登录密码")
    private String password;

    /**
     * 确认密码
     */
    @ApiModelProperty(value = "确认密码")
    private String confirmPassword;

    /**
     * 身份
     */
    @ApiModelProperty(value = "身份")
    private String idEntity;

    @ApiModelProperty(value = "验证码")
    private String validateCode;

    @ApiModelProperty(value = "审核状态")
    private Integer verify;

    @ApiModelProperty(value = "二维码地址")
    private String qrCodeUrl;


    @ApiModelProperty(value = "1 地点1  2 地点2")
    private Integer servicePlaceId;


    @ApiModelProperty(value = "员工头像")
    private String headImg;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "审核次数,不超过3次")
    private Integer applyTimes;

    @ApiModelProperty(value = "分销商账户Id")
    private String  distributeId;

    @ApiModelProperty(value = "分销账户有效状态1 有效 其他 无效")
    public Integer status;


    public Integer appId;

    @ApiModelProperty("区域Id")
    private Long areaId;

    @ApiModelProperty("管理员Id")
    private Long adminUserId;

    @ApiModelProperty("管理员姓名")
    private String adminUserName;

    @ApiModelProperty("角色信息")
    private String message;

    @ApiModelProperty("性别 1 男 2 女")
    private Byte sex;

    @ApiModelProperty("民族")
    private String nation;

    @ApiModelProperty("导游级别")
    private Byte level;

    @ApiModelProperty("精通语言 1 汉语 2 藏语 3 英文")
    private String language;

    @ApiModelProperty("1 正式职工 2 实习生 ")
    private Byte types;

    @ApiModelProperty("简介")
    private String synopsis;

    @ApiModelProperty(value = "图文详情")
    private String description;

    @ApiModelProperty(value = "业务类型 1.门票券 2.民宿券 3.餐饮券")
    private Integer businessType;
}
