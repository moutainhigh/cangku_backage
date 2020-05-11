package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 分销商补充信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_add")
public class DistributorAdd extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "分销商Id")
    @Column(name = "distributor_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商Id")
    private Long distributorBaseId;

    @ApiModelProperty(value = "营业执照正面")
    @Column(name = "business_license1",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "营业执照正面")
    private String businessLicense1;

    @ApiModelProperty(value = "营业执照反面")
    @Column(name = "business_license2",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "营业执照反面")
    private String businessLicense2;

    @ApiModelProperty(value = "身份证正面")
    @Column(name = "id_card_page1",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "身份证正面")
    private String idCardPage1;

    @ApiModelProperty(value = "身份证背面")
    @Column(name = "id_card_page2",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "身份证背面")
    private String idCardPage2;

    @ApiModelProperty(value = "驾驶证正面")
    @Column(name = "driver_card_page1",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "驾驶证正面")
    private String driverCardPage1;

    @ApiModelProperty(value = "驾驶证反面")
    @Column(name = "driver_card_page2",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "驾驶证反面")
    private String driverCardPage2;

    @ApiModelProperty(value = "导游证正面")
    @Column(name = "guide_card_page1",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "导游证正面")
    private String guideCardPage1;

    @ApiModelProperty(value = "导游证反面")
    @Column(name = "guide_card_page2",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "导游证反面")
    private String guideCardPage2;

    @Column(name = "withdraw_account_type",type = MySqlTypeConstant.VARCHAR, length = 50, comment = "提现账户类型 1 微信 2 银行卡")
    @ApiModelProperty("提现账户类型 1 微信 2 银行卡")
    private String withdrawAccountType;

    @ApiModelProperty(value = "分销商财务账户Id")
    @Column(name = "distributor_bank_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分销商财务账户Id")
    private Long distributorBankId;

    @ApiModelProperty(value = "银行卡照片路径(正面)")
    @Column(name = "bank_card_img",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "银行卡照片路径(正面)")
    private String bankCardImg;

    @Column(name = "wechat_nickname",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "微信")
    @ApiModelProperty("微信")
    private String wechatNickname;


    @Column(name = "open_id",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "微信openid")
    @ApiModelProperty("微信openid")
    private String openId;


    @Column(name = "wechat_head",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "微信头像")
    @ApiModelProperty("微信头像")
    private String wechatHead;

    @Column(name = "verify_state",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "审核状态：1-待审核 2-审核通过 3-审核未通过，补充信息审核")
    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，补充信息审核")
    private Byte verifyState;

    @Column(name = "electronic_contract",type = MySqlTypeConstant.TEXT, length = 1000,comment = "电子合同")
    @ApiModelProperty("电子合同")
    private String electronicContract;

    @Column(name = "contract_sacn_file",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "电子合同扫描件")
    @ApiModelProperty("电子合同扫描件")
    private String contractSacnFile;

    @Column(name = "reason",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "未通过原因")
    @ApiModelProperty("未通过原因")
    private String reason;
}
