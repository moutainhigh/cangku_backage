package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * 拼团明细表BO
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "group_order_item")
@AllArgsConstructor
@NoArgsConstructor
public class GroupOrderItem {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    private Long id;

    @Column(name = "group_order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "拼团表外键")
    @Index
    private Long groupOrderId;

    @Column(name = "group_order_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "拼团编码")
    private String groupOrderCode;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "用户id")
    private Long userId;

    @Column(name = "join_time",type = MySqlTypeConstant.TIMESTAMP,comment = "加入时间")
    private Timestamp joinTime;

    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "外键 指向 mall库orders表中的id")
    private Long orderId;

    @Column(name = "is_header",type = MySqlTypeConstant.TINYINT,length = 4,comment = "是否是团长")
    private Byte isHeader;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "状态（1 已付款 2已退款 ）")
    private Byte status;

    @Column(name = "refund_code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "退款单号")
    private String refundCode;

    @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "时段Id")
    private Long goodsExtendId;

    @Column(name = "goods_count",type = MySqlTypeConstant.INT,length = 11,comment = "商品数量")
    private Integer goodsCount;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "更新时间")
    private Timestamp updateTime;

    @Column(name = "refund_time",type = MySqlTypeConstant.TIMESTAMP,comment = "退款时间")
    private Timestamp refundTime;

    @Column(name = "group_order_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "参团类型 1好友邀请 2游客参团")
    private Byte groupOrderType;
}
