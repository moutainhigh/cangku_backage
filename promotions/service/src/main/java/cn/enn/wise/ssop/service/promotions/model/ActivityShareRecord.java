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
 * 活动分享记录信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_share_record")
public class ActivityShareRecord extends TableBase {
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动基础信息Id")
    private Long activityBaseId;

    @ApiModelProperty(value = "用户id")
    @Column(name = "user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "用户id")
    private Long userId;

    @Column(name = "nick_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "用户昵称")
    @ApiModelProperty("用户昵称")
    private String nickName;

    @Column(name = "phone",type = MySqlTypeConstant.VARCHAR, length = 20,comment = "电话")
    @ApiModelProperty("电话")
    private String phone;

    @Column(name = "open_id",type = MySqlTypeConstant.VARCHAR, length = 100,comment = "openId")
    @ApiModelProperty("openId")
    private String openId;

    @Column(name = "scan_number_state",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "分享后被扫描次数达到后，发放优惠券状态 1 已发放 2 未发放(未达到发放条件)")
    @ApiModelProperty("分享后被扫描次数达到后，发放优惠券状态 1 已发放 2 未发放(未达到发放条件)")
    private Byte scanNumberState;

    @ApiModelProperty("分享后被扫描次数")
    @Column(name = "scan_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后被扫描次数")
    private Integer scanNumber;

    @ApiModelProperty(value = "分享后被扫描获得优惠券id")
    @Column(name = "scan_get_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分享后被扫描获得优惠券id")
    private Long scanGetCouponId;

    @ApiModelProperty("分享后被扫描可获得优惠券数量")
    @Column(name = "scan_get_coupon_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后被扫描可获得优惠券数量")
    private Integer scanGetCouponNumber;

    @Column(name = "order_number_state",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "分享后成功订单数达到后，发放优惠券状态 1 已发放 2 未发放(未达到发放条件)")
    @ApiModelProperty("分享后成功订单数达到后，发放优惠券状态 1 已发放 2 未发放(未达到发放条件)")
    private Byte orderNumberState;

    @ApiModelProperty("分享后成功订单数")
    @Column(name = "order_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后成功订单数")
    private Integer orderNumber;

    @ApiModelProperty(value = "分享后成功下订单获得优惠券id")
    @Column(name = "order_get_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "分享后成功下订单获得优惠券id")
    private Long orderGetCouponId;

    @ApiModelProperty("分享后成功下订单可获得优惠券数量")
    @Column(name = "order_get_coupon_number",type = MySqlTypeConstant.INT, length = 12,comment = "分享后成功下订单可获得优惠券数量")
    private Integer orderGetCouponNumber;


}
