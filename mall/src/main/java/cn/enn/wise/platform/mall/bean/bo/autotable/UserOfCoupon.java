package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 用户领取的优惠券记录信息
 *
 * @author jiabaiye
 * @since 2019/12/13
 */
@Data
@Table(name = "user_of_coupon")
public class UserOfCoupon extends  TableBase{

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    @Column(name = "goods_coupon_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "优惠券id")
    @ApiModelProperty("优惠券id")
    private Long goodsCouponId;

    @Column(name = "promotion_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "活动id")
    @ApiModelProperty("活动id")
    private Long promotionId;

    @Column(name = "coupon_resource",type = MySqlTypeConstant.INT, length = 10,comment = "优惠券来源 1-自己领取 2-朋友赠送")
    @ApiModelProperty("优惠券来源 1-自己领取 2-朋友赠送")
    private Integer couponResource;

    @Column(name = "validity_time",type = MySqlTypeConstant.TIMESTAMP,comment = "有效期")
    @ApiModelProperty("有效期")
    private Timestamp validityTime;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "活动状态( 1领取未使用 2已使用 3已过期 4装让中 5已转让)")
    @ApiModelProperty("活动状态( 1领取未使用 2已使用 3已过期 4装让中 5已转让)")
    private Byte status;

    @Column(name = "open_id",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "openId")
    @ApiModelProperty("用户openId")
    @Index
    private String openId;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 30,comment = "分销商电话号")
    @ApiModelProperty("分销商电话号")
    private String phone;

    @Column(name = "order_resource",type = MySqlTypeConstant.INT, length = 10,comment = "优惠券 1-线上 2-线下")
    @ApiModelProperty("优惠券 1-线上 2-线下")
    private Integer orderResource;

    @Column(name = "coupon_price",type = MySqlTypeConstant.DECIMAL,length =10,decimalLength = 2,comment = "优惠金额")
    @ApiModelProperty(value = "优惠金额")
    private Double couponPrice;

    /**
     * 商家Id
     */
    @Column(name = "business_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商家Id")
    @ApiModelProperty(value = "商家Id")
    private Long businessId;

    /**
     * 商品名称
     */
    @Column(name = "business_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商家名称")
    @ApiModelProperty(value = "商家名称")
    private String businessName;

    @Column(name = "enter_time",type = MySqlTypeConstant.TIMESTAMP,comment = "有效期")
    @ApiModelProperty("有效期")
    private Timestamp enterTime;

    @Override
    public String toString() {
        return "UserOfCoupon{" +
                "id=" + id +
                ", userId=" + userId +
                ", goodsCouponId=" + goodsCouponId +
                ", promotionId=" + promotionId +
                ", couponResource=" + couponResource +
                ", validityTime=" + validityTime +
                ", status=" + status +
                ", openId='" + openId + '\'' +
                ", phone='" + phone + '\'' +
                ", orderResource=" + orderResource +
                ", couponPrice=" + couponPrice +
                ", businessId=" + businessId +
                ", businessName='" + businessName + '\'' +
                ", enterTime=" + enterTime +
                '}';
    }
}
