package cn.enn.wise.ssop.api.promotions.dto.response;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商补充信息
 * @author 耿小洋
 */
@Data
@ApiModel("分销商补充信息返回参数")
public class DistributorAddDTO {

    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
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
    private String withdrawAccountType;

    @ApiModelProperty("银行名称")
    private String bankName;

    @ApiModelProperty("银行代码")
    private String bankCode;

    @ApiModelProperty("账户名")
    private String userName;

    @ApiModelProperty("银行卡号")
    private String cardNumber;

    @ApiModelProperty("状态 1 正常 2 停用")
    private Byte state;

    @ApiModelProperty("详细地址")
    private String bankAddress;


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

    @ApiModelProperty("名称")
    private String openId;

    @ApiModelProperty("微信头像")
    private String wechatHead;

    @ApiModelProperty("微信昵称")
    private String wechatNickname;


}
