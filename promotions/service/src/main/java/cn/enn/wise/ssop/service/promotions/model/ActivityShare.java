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
 * 活动分享信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_share")
public class ActivityShare extends TableBase {
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty("图片分享")
    @Column(name = "img_share",type = MySqlTypeConstant.VARCHAR, length = 1500,comment = "图片分享")
    private String imgShare;

    @ApiModelProperty("链接分享")
    @Column(name = "link_share",type = MySqlTypeConstant.VARCHAR, length = 1500,comment = "链接分享")
    @Deprecated
    private String linkShare;

    @ApiModelProperty("分享次数")
    @Column(name = "share_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享次数")
    @Deprecated
    private Integer shareNumber;

    @ApiModelProperty(value = "分享获得优惠券id")
    @Column(name = "share_get_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分享获得优惠券id")
    @Deprecated
    private Long shareGetCouponId;

    @ApiModelProperty("分享可获得优惠券数量")
    @Column(name = "share_get_coupon_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享可获得优惠券数量")
    @Deprecated
    private Integer shareGetCouponNumber;

    @ApiModelProperty("分享后被扫描次数")
    @Column(name = "scan_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后被扫描次数")
    private Integer scanNumber;

    @ApiModelProperty(value = "分享后被扫描获得优惠券id")
    @Column(name = "scan_get_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分享后被扫描获得优惠券id")
    private Long scanGetCouponId;

    @ApiModelProperty("分享后被扫描可获得优惠券数量")
    @Column(name = "scan_get_coupon_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后被扫描可获得优惠券数量")
    private Integer scanGetCouponNumber;

    @ApiModelProperty("分享后成功订单数")
    @Column(name = "order_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后成功订单数")
    private Integer orderNumber;

    @ApiModelProperty(value = "分享后成功下订单获得优惠券id")
    @Column(name = "order_get_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分享后成功下订单获得优惠券id")
    private Long orderGetCouponId;

    @ApiModelProperty("分享后成功下订单可获得优惠券数量")
    @Column(name = "order_get_coupon_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后成功下订单可获得优惠券数量")
    private Integer orderGetCouponNumber;

    @ApiModelProperty("活动二维码")
    @Column(name = "qr_code",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "活动二维码")
    private String qrCode;

    @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "规则描述")
    @ApiModelProperty("规则描述")
    private String description;
}
