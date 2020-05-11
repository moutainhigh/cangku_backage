package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 订单取消记录表
 *
 * @author baijie
 * @date 2019-12-10
 */
@Data
@Table(name="order_cancel_record")
public class OrderCancelRecord  extends TableBase {

    /**
     * 主键id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 会员id
     */
    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "会员id")
    private Long memberId;

    /**
     * 订单号
     */
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "订单号")
    private String orderNo;

    /**
     * 父订单id
     */
    @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "父订单id")
    private Long parentOrderId;

    /**
     * 取消时间
     */
    @Column(name = "cancel_time",type = MySqlTypeConstant.DATETIME,length = 0,comment = "取消时间")
    private Date cancelTime;

    /**
     * 取消类型 1 订单支付超时自动取消 2 用户自动取消订单
     */
    @Column(name = "cancel_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "取消类型 1 订单支付超时自动取消 2 用户自动取消订单")
    private Byte cancelType;

    /**
     * 取消原因类型
     */
    @Column(name = "cancel_reason_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "取消原因类型")
    private Byte cancelReasonType;

    /**
     * 取消原因
     */
    @Column(name = "cancel_reason",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "取消原因")
    private String cancelReason;

    /**
     * 订单来源
     */
    @ApiModelProperty("退单平台 1.PC端 2.App端 3.小程序")
    @Column(name = "plat_form",type = MySqlTypeConstant.INT,comment = "订单来源")
    private int platform;//退单平台 1.PC端 2.App端 3.小程序

    /**
     * 订单来源
     */
    @ApiModelProperty("判断退单记录的")
    @Column(name = "judge_record",type = MySqlTypeConstant.VARCHAR,comment = "判断退单记录的")
    private String judgeRecord;//退单平台 1.PC端 2.App端 3.小程序

    /**
     * 订单来源
     */
    @ApiModelProperty("操作人")
    @Column(name = "operation_user",type = MySqlTypeConstant.VARCHAR,comment = "操作人")
    private String operationUser;//退单平台 1.PC端 2.App端 3.小程序

}
