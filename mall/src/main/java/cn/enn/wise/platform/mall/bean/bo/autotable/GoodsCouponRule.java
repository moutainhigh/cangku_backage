package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/9
 */
@Data
@Table(name = "goods_coupon_rule")
public class GoodsCouponRule extends  TableBase{
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    private Long id;

    @Column(name = "promotion_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "优惠券活动Id")
    @ApiModelProperty("活动Id")
    private Long promotionId;

    @Column(name = "goods_coupon_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "优惠券id")
    @ApiModelProperty("优惠券Id")
    private Long goodsCouponId;

    @Column(name = "use_rule",type = MySqlTypeConstant.INT, length = 10,comment = "使用条件 1 订单总价 2 商品单价")
    @ApiModelProperty("使用条件 1 订单总价 2 商品单价")
    private Integer useRule;

    @Column(name = "min_use",type = MySqlTypeConstant.INT, length = 10,comment = "达到限额可用（满足多少钱可用）")
    @ApiModelProperty("达到限额可用（满足多少钱可用）")
    private Integer minUse;

    @Column(name = "is_send",type = MySqlTypeConstant.INT, length = 1,comment = "是否可以转增 1可以 2 不可以")
    @ApiModelProperty("是否可以转增 1可以 2 不可以")
    private Integer isSend;

    @Column(name = "is_share",type = MySqlTypeConstant.INT, length = 1,comment = "是否优惠共享 1可以 2 不可以")
    @ApiModelProperty("是否优惠共享 1可以 2 不可以")
    private Integer isShare;

    @Column(name = "overlay_use_coupon_id",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "叠加使用优惠券id，逗号分隔")
    @ApiModelProperty("叠加使用优惠券id")
    private String overlayUseCouponId;

    @Column(name = "is_overlay",type = MySqlTypeConstant.INT, length = 1,comment = "是否可以叠加 1可以 2 不可以")
    @ApiModelProperty("是否可以叠加 1可以 2 不可以")
    private Integer isOverlay;

    @Column(name = "tag",type = MySqlTypeConstant.VARCHAR, length = 1000,comment = "规则描述")
    @ApiModelProperty("规则描述")
    private String tag;

    @Column(name = "is_project_use",type = MySqlTypeConstant.INT, length = 1,comment = "活动中是否项目专用 1是 2 不是")
    @ApiModelProperty("活动中是否项目专用 1是 2 不是")
    private Integer isProjectUse;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "项目id")
    @ApiModelProperty("项目id")
    private Long projectId;

    @Column(name = "goods_id",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "项目专用下 可使用的商品ids，多个商品逗号分隔")
    @ApiModelProperty("项目专用下 可使用的商品ids")
    private String goodsId;

    @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "规则描述")
    @ApiModelProperty("规则描述")
    private String description;

    @Column(name = "promotion_get_type",type = MySqlTypeConstant.TINYINT, length = 1,comment = "限制类型 1 不限制 2 限制")
    @ApiModelProperty("限制类型 1 不限制 2 限制")
    private Byte promotionGetType;

    @Column(name = "promotion_get_limit",type = MySqlTypeConstant.INT, length = 10,comment = "领取限制数量")
    @ApiModelProperty("领取限制数量")
    private Integer promotionGetLimit;
}
