package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * app端分销商修改补充信息参数
 * @author 耿小洋
 */
@Data
@ApiModel("app端分销商修改补充信息参数")
public class DistributorAddUpdateAppParam {

    @ApiModelProperty(value = "身份证号码")
    private String idCardNumber;

    @ApiModelProperty(value = "身份证照片1")
    private String idCardPage1;

    @ApiModelProperty(value = "身份证照片2")
    private String idCardPage2;

    @ApiModelProperty(value = "银行名称")
    private String bankName;

    @ApiModelProperty(value = "银行账户名")
    private String bankAccountName;//不一致userName

    @ApiModelProperty(value = "银行账户号")
    private String bankCardNumber;//不一致cardNumber

    @ApiModelProperty(value = "银行卡照片")
    private String bankCardPage; //bankCardImg

    @ApiModelProperty(value = "银行code码")
    private String bankCode; //待定

    @ApiModelProperty(value = "司机照片1")
    private String driverCardPage1;

    @ApiModelProperty(value = "司机照片2")
    private String driverCardPage2;

    @ApiModelProperty(value = "导游照片1")
    private String guideCardPage1;

    @ApiModelProperty(value = "导游照片2")
    private String guideCardPage2;

    @ApiModelProperty(value = "微信昵称")
    private String wechatNickname;

    @ApiModelProperty(value = "微信头像")
    private String headImg;//不一致wechatHead

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "验证码")
    private String messageCode;

    @ApiModelProperty(value = "微信openId")
    private String openId;

    @ApiModelProperty(value = "用户id")
    private String userId;


}
