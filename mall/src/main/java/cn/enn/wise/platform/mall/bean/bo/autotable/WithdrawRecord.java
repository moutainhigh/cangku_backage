package cn.enn.wise.platform.mall.bean.bo.autotable;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.util.Date;


/**
 * 提现记录
 *
 * @author gaoguanglin
 * @since version.wzd0911
 */
@Data
@TableName("withdraw_record")
@Table(name = "withdraw_record")
public class WithdrawRecord {



    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    private Long id;



    /**
     * 提现单号
     */
    @Column(name = "withdraw_serial",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "提现单号")
    private String withdrawSerial;

    /**
     * 申请时间
     */
    @Column(name = "apply_date_time",type = MySqlTypeConstant.DATETIME,comment = "申请时间")
    private Date applyDateTime;


    /**
     * 提现金额（使用Plain格式，保留两位小数）
     */
    @Column(name = "withdraw",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "提现金额（使用Plain格式，保留两位小数）")
    private String withdraw;


    /**
     * 可提现总金额
     */
    @Column(name = "withdraw_total",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "总共可提现金额（使用Plain格式，保留两位小数）")
    private String withdrawTotal;

    /**
     * 分销日期，格式如下：2019-05-06/2019-12-08
     */
    @Column(name = "distribute_date_between",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "分销日期，格式如下：2019-05-06/2019-12-08")
    private String distributeDateBetween;


    /**
     * 分销商ID
     */
    @Column(name = "distributor_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "分销商ID")
    private Long distributorId;

    /**
     * 分销商姓名
     */
    @Column(name = "distributor_name",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "分销商姓名")
    private String distributorName;

    /**
     * 分销商手机号
     */
    @Column(name = "distributor_cellphone",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "分销商手机号")
    private String distributorCellphone;


    /**
     * 分销商账户类型名称
     */
    @Column(name = "distributor_account_type",type = MySqlTypeConstant.TINYINT,length = 1,comment = "分销商账户类型,0:微信；1：银行卡")
    private Byte distributorAccountType;


    /**
     * 分销账户微信标示
     */
    @Column(name = "distributor_account_sign",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "分销商账户标示，例：1002")
    private String distributorAccountSign;


    /**
     * 分销商账户号
     */
    @Column(name = "distributor_account_num",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "分销商账户号")
    private String distributorAccountNum;


    /**
     * 开户人姓名
     */
    @Column(name = "distributor_account_user",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "开户人姓名")
    private String distributorAccountUser;


    /**
     * 分销商信息
     */
    @Column(name = "distributor_message",type = MySqlTypeConstant.VARCHAR, length = 60,comment = "分销商信息")
    private String distributorMessage;



    /**
     * 发放状态
     */
    @Column(name = "put_out",type = MySqlTypeConstant.TINYINT, length = 1, comment = "发放状态,-2:未发放；1：发放中；2：已发放",defaultValue = "0")
    private Byte putOut;

    /**
     * 发放操作人
     */
    @Column(name = "put_out_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "发放操作人")
    private Long putOutUserId;

    /**
     * 发放操作人姓名
     */
    @Column(name = "put_out_user_name",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "发放操作人姓名")
    private String putOutUserName;

    /**
     * 发放操作时间
     */
    @Column(name = "put_out_date_time",type = MySqlTypeConstant.DATETIME,comment = "发放操作时间")
    private Date putOutDateTime;

    /**
     * 审批状态
     */
    @Column(name = "permit",type = MySqlTypeConstant.TINYINT, length = 1, comment = "审批状态,-2: 未审核；1：已通过,-1：未通过", defaultValue = "0")
    private Byte permit;

    /**
     * 审批人ID
     */
    @Column(name = "permit_user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "审批人ID")
    private Long permitUserId;

    /**
     * 审批人姓名
     */
    @Column(name = "permit_user_name",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "审批人姓名")
    private String permitUserName;

    /**
     * 审批日期
     */
    @Column(name = "permit_date_time",type = MySqlTypeConstant.DATETIME,comment = "审批日期")
    private Date permitDateTime;



    @Column(name = "permit_comment",type = MySqlTypeConstant.VARCHAR,length = 180,comment = "审批备注")
    private String permitComment;


    /**
     * 订单ID使用半角逗号分隔
     */
    @Column(name = "orders_id",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "订单ID使用半角逗号分隔")
    private String ordersId;


    /**
     * 订单分成
     */
    @Column(name = "orders_profit",type = MySqlTypeConstant.VARCHAR,length = 90,comment = "订单分成，使用半角逗号分隔，顺序与orders_id中的orderId保持一致")
    private String ordersProfit;


    /**
     * 是否完成分销单状态更新
     */
    @Column(name = "distribute_status_updated",type = MySqlTypeConstant.TINYINT,length = 1,comment = "是否完成分销单状态更新",defaultValue = "0")
    private Byte distributeStatusUpdated;










}

