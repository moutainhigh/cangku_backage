package cn.enn.wise.platform.mall.bean.bo;

import cn.enn.wise.platform.mall.bean.vo.WithdrawOrderVO;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;
import java.util.List;


/**
 * 提现记录
 *
 * @author gaoguanglin
 * @since version.wzd0911
 */
@Data
@TableName("withdraw_record")
public class WithdrawRecord {

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;


    /**
     * 提现单号
     */
    @ApiModelProperty("提现单号")
    private String withdrawSerial;

    /**
     * 申请时间
     */
    @ApiModelProperty("申请时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date applyDateTime;


    /**
     * 提现金额（使用Plain格式，保留两位小数）
     */
    @ApiModelProperty("提现金额（使用Plain格式，保留两位小数）")
    private String withdraw;

    /**
     * 可提现总金额
     */
    @ApiModelProperty("可提现总金额")
    private String withdrawTotal;

    /**
     * 分销日期，格式如下：2019-05-06/2019-12-08
     */
    @ApiModelProperty("分销日期，格式如下：2019-05-06/2019-12-08")
    private String distributeDateBetween;


    /**
     * 分销商ID
     */
    @ApiModelProperty("分销商ID")
    private Long distributorId;

    /**
     * 分销商姓名
     */
    @ApiModelProperty("分销商姓名")
    private String distributorName;

    /**
     * 分销商手机号
     */
    @ApiModelProperty("分销商手机号")
    private String distributorCellphone;


    /**
     * 分销商账户类型名称
     */
    @ApiModelProperty("分销商账户类型，0:微信；1：银行卡")
    private Byte distributorAccountType;


    /**
     * 分销账户微信标示
     */
    @ApiModelProperty("分销账户微信标示")
    private String distributorAccountSign;

    /**
     * 分销商账户号
     */
    @ApiModelProperty("分销商账户号")
    private String distributorAccountNum;

    /**
     * 开户人姓名
     */
    @ApiModelProperty("开户人姓名")
    private String distributorAccountUser;

    /**
     * 分销商信息
     */
    @ApiModelProperty("分销商信息")
    private String distributorMessage;


    /**
     * 发放状态
     */
    @ApiModelProperty("发放状态,-2:未发放；1：发放中；2：已发放")
    private Byte putOut;

    /**
     * 发放操作人
     */
    @ApiModelProperty("发放操作人")
    private Long putOutUserId;

    /**
     * 发放操作人姓名
     */
    @ApiModelProperty("发放操作人姓名")
    private String putOutUserName;

    /**
     * 发放操作时间
     */
    @ApiModelProperty("发放操作时间")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date putOutDateTime;

    /**
     * 审批状态 0: 未审核；1：已审核 -1 未通过
     */
    @ApiModelProperty("审批状态 -2: 未审核；1：已审核 -1 未通过")
    private Byte permit;

    /**
     * 审批人ID
     */
    @ApiModelProperty("审批人ID")
    private Long permitUserId;

    /**
     * 审批人姓名
     */
    @ApiModelProperty("审批人姓名")
    private String permitUserName;

    /**
     * 审批日期
     */
    @ApiModelProperty("审批日期")
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date permitDateTime;


    /**
     * 订单ID使用半角逗号分隔
     */
    @ApiModelProperty("订单ID使用半角逗号分隔")
    private String ordersId;


    /**
     * 订单分成，顺序与ordersId中的顺序保持对应一致
     */
    @ApiModelProperty("订单分成，顺序与ordersId中的顺序保持对应一致")
    private String ordersProfit;


    /**
     * 是否完成分销订单状态更新
     */
    @ApiModelProperty("是否完成分销订单状态更新")
    private Byte distributeStatusUpdated;


    /**
     * 审批备注
     */
    @ApiModelProperty("审批备注")
    private String permitComment;

    /**
     * 订单详情，根据订单ID查询全部订单数据
     */
    @ApiModelProperty("订单详情，根据订单ID查询全部订单数据")
    @TableField(exist = false)
    private List<WithdrawOrderVO> ordersList;






}
