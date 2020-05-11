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
 * 活动和商品关系信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_goods")
public class ActivityGoods extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品Id(如果是全部产品id为-1)")
    private Long goodsId;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品一级规格Id(如果是全部产品id为-1)")
    @Column(name = "goods_extend_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品一级规格Id(如果是全部产品id为-1)")
    private Long goodsExtendId;

    @Column(name = "goods_extend_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品一级规格名称")
    @ApiModelProperty("商品一级规格名称")
    private String goodsExtendName;

    @ApiModelProperty(value = "第三级规格id")
    @Column(name = "goods_project_period_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "第三级规格id")
    private Long goodsProjectPeriodId;

    @Column(name = "title",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "第三层规格名称")
    @ApiModelProperty("第三层规格名称")
    private String title;

    @ApiModelProperty(value = "商产品/资源类型Id")
    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商产品/资源类型Id")
    private Long projectId;

    @Column(name = "project_name",type = MySqlTypeConstant.VARCHAR,length = 50, comment = "产品/资源类型，来自于产品goods中projectName")
    @ApiModelProperty("产品/资源类型，来自于产品")
    private String projectName;

    @Column(name = "sell_price",type = MySqlTypeConstant.INT,length = 12,comment = "销售价格")
    @ApiModelProperty("销售价格")
    private Integer sellPrice;

    @Column(name = "group_price",type = MySqlTypeConstant.INT,length = 12,comment = "拼团价格")
    @ApiModelProperty("拼团价格")
    private Integer groupPrice;

    @Column(name = "refund_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "退款类型 1 常规退款 2 不予退款")
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;
}
