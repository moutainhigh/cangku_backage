package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 拼团明细表
 *
 * @author jiabaiye
 */
@Data
@Builder
@Table(name = "group_order_item")
@AllArgsConstructor
@NoArgsConstructor
public class GroupOrderItem extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "group_order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "拼团表外键")
    @ApiModelProperty("拼团表外键")
    @Index
    private Long groupOrderId;

    @Column(name = "group_order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "拼团编码")
    @ApiModelProperty("拼团编码")
    private String groupOrderCode;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    @Column(name = "join_time",type = MySqlTypeConstant.TIMESTAMP,comment = "加入时间")
    @ApiModelProperty("加入时间")
    private Timestamp joinTime;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "外键 指向订单库订单表中的id")
    @ApiModelProperty("外键 指向订单库订单表中的id")
    private Long orderId;

    @Column(name = "is_header",type = MySqlTypeConstant.TINYINT,length = 4,comment = "是否是团长")
    @ApiModelProperty("是否是团长")
    private Byte isHeader;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "状态（1 已付款 2已退款 ）")
    @ApiModelProperty("状态（1 已付款 2已退款 ）")
    private Byte status;

    @Column(name = "refund_code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "退款单号")
    @ApiModelProperty("退款单号")
    private String refundCode;

    @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品Id")
    @ApiModelProperty("商品Id")
    private Long goodsExtendId;

    @Column(name = "goods_count",type = MySqlTypeConstant.INT,length = 11,comment = "商品数量")
    @ApiModelProperty("商品数量")
    private Integer goodsCount;

    @Column(name = "refund_time",type = MySqlTypeConstant.TIMESTAMP,comment = "退款时间")
    @ApiModelProperty("退款时间")
    private Timestamp refundTime;

    @Column(name = "group_order_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "参团类型 1好友邀请 2游客参团")
    @ApiModelProperty("参团类型 1好友邀请 2游客参团")
    private Byte groupOrderType;

    @Column(name = "contact_name",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "姓名")
    @ApiModelProperty("姓名")
    private String contactName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "电话")
    @ApiModelProperty("电话")
    private String phone;
}
