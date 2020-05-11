package cn.enn.wise.ssop.api.promotions.dto.request;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 分销商补充信息表
 * @author 耿小洋
 */
@Data
@ApiModel("分销商补充信息")
public class DistributorAddParam  {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @NotNull
    private Long distributorBaseId;

    @ApiModelProperty(value = "营业执照正面")
    private String businessLicense1;

    @ApiModelProperty(value = "营业执照反面")
    private String businessLicense2;

    @ApiModelProperty(value = "身份证正面")
    private String idCardPage1;

    @ApiModelProperty(value = "身份证背面")
    private String idCardPage2;

    @ApiModelProperty(value = "驾驶证正面")
    private String driverCardPage1;

    @ApiModelProperty(value = "驾驶证反面")
    private String driverCardPage2;

    @ApiModelProperty(value = "导游证正面")
    private String guideCardPage1;

    @ApiModelProperty(value = "导游证反面")
    private String guideCardPage2;

    @ApiModelProperty("提现账户类型 1 微信 2 银行卡")
    @NotNull
    private String withdrawAccountType;

    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，补充信息审核")
    private Byte verifyState;

    @ApiModelProperty("电子合同")
    private String electronicContract;

    @ApiModelProperty("电子合同扫描件")
    private String contractSacnFile;

    @ApiModelProperty(value = "分销商财务账户Id")
    private Long distributorBankId;

    @ApiModelProperty(value = "银行卡照片路径(正面)")
    private String bankCardImg;

    @ApiModelProperty("未通过原因")
    private String reason;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("账户名")
    private String userName;

    @ApiModelProperty("银行卡号")
    private String cardNumber;


    @ApiModelProperty("微信openId")
    private String openId;


    @ApiModelProperty("微信头像")
    private String wechatHead;

    @ApiModelProperty("微信")
    private String wechatNickname;


}
