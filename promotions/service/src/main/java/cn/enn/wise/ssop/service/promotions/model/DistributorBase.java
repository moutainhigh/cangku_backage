package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 分销商基础信息表
 * @author jiaby
 */
@Data
@Table(name = "distributor_base")
public class DistributorBase extends TableBase {


    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "distributor_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "分销商名称")
    @ApiModelProperty("分销商名称")
    private String distributorName;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "分销商编码")
    @ApiModelProperty("分销商编码")
    private String code;

    @ApiModelProperty(value = "景区Id")
    @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区Id")
    private Long scenicId;

    @Column(name = "scenic_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "景区名称")
    @ApiModelProperty("景区名称")
    private String scenicName;

    @ApiModelProperty(value = "城市Id")
    @Column(name = "city_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "城市Id")
    private Long cityId;

    @Column(name = "city_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "城市名称")
    @ApiModelProperty("城市名称")
    private String cityName;

    @ApiModelProperty(value = "区Id")
    @Column(name = "area_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "区Id")
    private Long areaId;

    @Column(name = "area_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "区名称")
    @ApiModelProperty("区名称")
    private String areaName;

    @ApiModelProperty(value = "渠道Id")
    @Column(name = "channel_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "渠道Id")
    private Long channelId;

    @Column(name = "channel_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "渠道名称")
    @ApiModelProperty("渠道名称")
    private String channelName;

    @Column(name = "distributor_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "分销商类型 1 集团企业 2 个人企业 3 个人")
    @ApiModelProperty("分销商类型 1 集团企业 2 个人企业 3 个人")
    private Byte distributorType;

    @ApiModelProperty(value = "归属分销商Id")
    @Column(name = "parent_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "归属分销商Id")
    private Long parentId;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "状态 1 有效 2 无效")
    @ApiModelProperty("状态 1 有效 2 无效")
    private Byte state;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @Column(name = "verify_state",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    @ApiModelProperty("审核状态：1-待审核 2-审核通过 3-审核未通过，分销身份审核")
    private Byte verifyState;

    @Column(name = "verify_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "审核类型：1-草稿 2-保存成功的可用数据")
    @ApiModelProperty("审核类型：1-草稿 2-保存成功的可用数据")
    private Byte verifyType;

    @Column(name = "into_time", type = "timestamp",comment = "入驻时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd")
    @ApiModelProperty("入驻时间")
    private Timestamp intoTime;

    @Column(name = "reason",type = MySqlTypeConstant.VARCHAR, length = 1500,comment = "未通过原因")
    @ApiModelProperty("未通过原因")
    private String reason;

    @Column(name = "register_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "注册状态：1-合作商家 2-运营管理人员")
    @ApiModelProperty("注册状态：1-合作商家 2-运营管理人员")
    private Byte registerType;

    @Column(name = "edit_step",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "分销商保存轨迹：1 基础信息 2联系人信息 3财务信息 4业务信息 5补充信息")
    @ApiModelProperty("分销商保存轨迹：1 基础信息 2联系人信息 3财务信息 4业务信息 5补充信息")
    private Byte editStep;

    @ApiModelProperty(value = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3公众号注册")
    @Column(name = "register_resource",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3 公众号注册")
    private String registerResource;

//
//    /**
//     * 申请日期
//     */
//    private Date applyDate;
//

//
//    /**
//     * 级别
//     */
//    private Byte level;
//
//    /**
//     * 身份身份角色
//     */
//    private String userRole;
//
//    /**
//     * 备注
//     */
//    private String remark;
//
//    /**
//     * 注册来源：1 大峡谷运营App
//     */
//    private Byte source;
//
//    /**
//     * 附件地址
//     */
//    private String attachmentUrl;
//
//    /**
//     * 创建时间
//     */
//    private Timestamp createTime;
//
//    /**
//     * 创建人id
//     */
//    private Long createUserId;
//
//    /**
//     * 创建人名称
//     */
//    private String createUserName;
//
//    /**
//     * 更新时间
//     */
//    private Timestamp updateTime;
//
//    /**
//     * 更新人id
//     */
//    private Long updateUserId;
//
//    /**
//     * 修改人名称
//     */
//    private String updateUserName;
//
//    /**
//     * 过期日期
//     */
//    private Timestamp expiredTime;
//
//    /**
//     * 已提交申请次数
//     */
//    private Integer applyTimes;
//
//    /**
//     * 审核状态：提现使用，外部导游使用
//     */
//    @ApiModelProperty(value = "账号状态：1通过 2 不通过 3是待审核")
//    private Integer auditStatus;
//
//    @ApiModelProperty(value = "审核原因")
//    private String reason;
//
//    public Distributor() {
//        super();
//    }
//
//    @Override
//    protected Serializable pkVal() {
//        return this.id;
//    }
//
//    /**
//     * 微信openId：企业发送零钱到钱包使用
//     */
//    @ApiModelProperty(value = "是否可以提现，null或者空字符串 不能提现")
//    private String openId;
//
//
//    @ApiModelProperty("区域Id")
//    private Long areaId;
//
//    @ApiModelProperty("管理员Id")
//    private Long adminUserId;
//
//    @ApiModelProperty("角色信息")
//    private String message;
//
//    @ApiModelProperty("管理员姓名")
//    private String adminUserName;
//
//    //add 补充个人资料 jiabaiye
//
//    @ApiModelProperty(value = "身份证正面")
//    private String idCardPage1;
//
//    @ApiModelProperty(value = "身份证背面")
//    private String idCardPage2;
//
//    @ApiModelProperty(value = "银行名")
//    private String bankName;
//
//    @ApiModelProperty(value = "银行账户名")
//    private String bankAccountName;
//
//    @ApiModelProperty(value = "银行卡号")
//    private String bankCardNumber;
//
//    @ApiModelProperty(value = "银行卡正面照片")
//    private String bankCardPage;
//
//    @ApiModelProperty(value = "驾驶证正面")
//    private String driverCardPage1;
//
//    @ApiModelProperty(value = "驾驶证反面")
//    private String driverCardPage2;
//
//    @ApiModelProperty(value = "导游证正面")
//    private String guideCardPage1;
//
//    @ApiModelProperty(value = "导游证反面")
//    private String guideCardPage2;
//
//    @ApiModelProperty(value = "微信头像")
//    private String headImg;
//
//    @ApiModelProperty(value = "银行编码")
//    private String bankCode;
//
//    @ApiModelProperty(value = "分销商业务编号")
//    private String businessNumber;
}
