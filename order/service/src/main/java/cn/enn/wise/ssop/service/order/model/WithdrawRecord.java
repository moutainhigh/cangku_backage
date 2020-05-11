package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.ssop.api.order.dto.response.WithdrawOrderDTO;
import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.annotation.Unique;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;




/**
 * 提现记录
 *
 * @author gaoguanglin
 * @since JDK1.8 normalize-1.0
 */
@Data
@TableName("withdraw_record")
@Table(name = "withdraw_record")
@ApiModel("提现记录")
public class WithdrawRecord extends TableBase {

    /**
     * 主键
     */
    @ApiModelProperty("主键")
    @Unique
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true,comment = "主键")
    private Long id;


    /**
     * 提现单号
     */
    @ApiModelProperty("提现单号")
    @Column(name = "withdraw_serial",type = MySqlTypeConstant.VARCHAR,comment = "提现单号",length = 90)
    private String withdrawSerial;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "apply_date_time",type = MySqlTypeConstant.DATETIME,comment = "申请时间")
    private Date applyDateTime;


    /**
     * 提现金额（使用Plain格式，保留两位小数）
     */
    @ApiModelProperty("提现金额（使用Plain格式，保留两位小数）")
    @Column(name = "withdraw",type = MySqlTypeConstant.VARCHAR,comment = "提现金额（使用Plain格式，保留两位小数）",length = 60)
    private String withdraw;

    /**
     * 可提现总金额
     */
    @ApiModelProperty("可提现总金额")
    @Column(name = "withdraw_total",type = MySqlTypeConstant.VARCHAR,comment = "可提现总金额",length = 60)
    private String withdrawTotal;

    /**
     * 分销日期，格式如下：2019-05-06/2019-12-08
     */
    @ApiModelProperty("分销日期，格式如下：2019-05-06/2019-12-08")
    @Column(name = "distribute_date_between",type = MySqlTypeConstant.VARCHAR,comment = "分销日期，格式如下：2019-05-06/2019-12-08",length = 60)
    private String distributeDateBetween;


    /**
     * 分销商ID
     */
    @ApiModelProperty("分销商ID")
    @Column(name = "distributor_id",type = MySqlTypeConstant.BIGINT,comment = "分销商ID",length = 20)
    private Long distributorId;

    /**
     * 分销商姓名
     */
    @ApiModelProperty("分销商姓名")
    @Column(name = "distributor_name",type = MySqlTypeConstant.VARCHAR,comment = "分销商姓名",length = 60)
    private String distributorName;

    /**
     * 分销商手机号
     */
    @ApiModelProperty("分销商手机号")
    @Column(name = "distributor_cellphone",type = MySqlTypeConstant.VARCHAR,comment = "分销商手机号",length = 60)
    private String distributorCellphone;


    /**
     * 分销商账户类型名称
     */
    @ApiModelProperty("分销商账户类型，0:微信；1：银行卡")
    @Column(name = "distributor_account_type",type = MySqlTypeConstant.INT,comment = "分销商账户类型，0:微信；1：银行卡",length = 11)
    private int distributorAccountType;


    /**
     * 分销账户微信标示
     */
    @ApiModelProperty("分销账户微信标示")
    @Column(name = "distributor_account_sign",type = MySqlTypeConstant.VARCHAR,comment = "分销账户微信标示",length = 60)
    private String distributorAccountSign;

    /**
     * 分销商账户号
     */
    @ApiModelProperty("分销商账户号")
    @Column(name = "distributor_account_num",type = MySqlTypeConstant.VARCHAR,comment = "分销商账户号",length = 60)
    private String distributorAccountNum;

    /**
     * 开户人姓名
     */
    @ApiModelProperty("开户人姓名")
    @Column(name = "distributor_account_user",type = MySqlTypeConstant.VARCHAR,comment = "开户人姓名",length = 60)
    private String distributorAccountUser;

    /**
     * 分销商信息
     */
    @ApiModelProperty("分销商信息")
    @Column(name = "distributor_message",type = MySqlTypeConstant.VARCHAR,comment = "分销商信息",length = 60)
    private String distributorMessage;


    /**
     * 发放状态
     */
    @ApiModelProperty("发放状态,-2:未发放；1：发放中；2：已发放")
    @Column(name = "put_out",type = MySqlTypeConstant.INT,comment = "发放状态,-2:未发放；1：发放中；2：已发放",length = 11)
    private int putOut;

    /**
     * 发放操作人
     */
    @ApiModelProperty("发放操作人")
    @Column(name = "put_out_user_id",type = MySqlTypeConstant.BIGINT,comment = "发放操作人",length = 11)
    private Long putOutUserId;

    /**
     * 发放操作人姓名
     */
    @ApiModelProperty("发放操作人姓名")
    @Column(name = "put_out_user_name",type = MySqlTypeConstant.VARCHAR,comment = "发放操作人姓名",length = 60)
    private String putOutUserName;

    /**
     * 发放操作时间
     */
    @ApiModelProperty("发放操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "put_out_date_time",type = MySqlTypeConstant.DATETIME,comment = "发放操作时间")
    private Date putOutDateTime;

    /**
     * 审批状态 0: 未审核；1：已审核 -1 未通过
     */
    @ApiModelProperty("审批状态 -2: 未审核；1：已审核 -1 未通过")
    @Column(name = "permit",type = MySqlTypeConstant.INT,comment = "审批状态 -2: 未审核；1：已审核 -1 未通过",length = 11)
    private int permit;

    /**
     * 审批人ID
     */
    @ApiModelProperty("审批人ID")
    @Column(name = "permit_user_id",type = MySqlTypeConstant.BIGINT,comment = "审批人ID",length = 20)
    private Long permitUserId;

    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    @Column(name = "permit_user_name",type = MySqlTypeConstant.VARCHAR,comment = "审批人姓名",length = 60)
    private String permitUserName;

    /**
     * 审批日期
     */
    @ApiModelProperty("审批时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    @Column(name = "permit_date_time",type = MySqlTypeConstant.DATETIME,comment = "审批时间")
    private Date permitDateTime;


    /**
     * 订单ID使用半角逗号分隔
     */
    @ApiModelProperty("订单ID使用半角逗号分隔")
    @Column(name = "orders_id",type = MySqlTypeConstant.VARCHAR,comment = "订单ID使用半角逗号分隔",length = 180)
    private String ordersId;


    /**
     * 订单分成，顺序与ordersId中的顺序保持对应一致
     */
    @ApiModelProperty("订单分成，顺序与ordersId中的顺序保持对应一致")
    @Column(name = "orders_profit",type = MySqlTypeConstant.VARCHAR,comment = "订单分成，顺序与ordersId中的顺序保持对应一致",length = 180)
    private String ordersProfit;


    /**
     * 是否完成分销订单状态更新
     */
    @ApiModelProperty("是否完成分销订单状态更新")
    @Column(name = "distribute_status_updated",type = MySqlTypeConstant.INT,comment = "是否完成分销订单状态更新",length = 11)
    private int distributeStatusUpdated;


    /**
     * 审批备注
     */
    @ApiModelProperty("审批备注")
    @Column(name = "permit_comment",type = MySqlTypeConstant.VARCHAR,comment = "审批备注",length = 180)
    private String permitComment;

    /**
     * 订单详情，根据订单ID查询全部订单数据
     */
    @ApiModelProperty("订单详情，根据订单ID查询全部订单数据")
    @TableField(exist = false)
    private List<WithdrawOrderDTO> ordersList;




}
