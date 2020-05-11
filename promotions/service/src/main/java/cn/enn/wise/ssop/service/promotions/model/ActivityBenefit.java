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
 * 活动收益预估信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_benefit")
public class ActivityBenefit extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动基础信息Id")
    private Long activityBaseId;

    @Column(name = "cost",type = MySqlTypeConstant.INT,length = 12,comment = "预计成本")
    @ApiModelProperty("预计成本")
    private Integer cost;

    @Column(name = "order_number",type = MySqlTypeConstant.INT,length = 12,comment = "收益订单数量")
    @ApiModelProperty("收益订单数量")
    private Integer orderNumber;

    @Column(name = "gross_profit",type = MySqlTypeConstant.INT,length = 12,comment = "毛利收益")
    @ApiModelProperty("毛利收益")
    private Integer grossProfit;

    @Column(name = "customer_proportion",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "客群占比")
    @ApiModelProperty("客群占比")
    private String customerProportion;

    @Column(name = "goods_sort",type = MySqlTypeConstant.VARCHAR,length = 1000,comment = "产品占比排序")
    @ApiModelProperty("产品占比排序")
    private String goodsSort;

    @Column(name = "channel_sort",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "分销渠道 l排序视图")
    @ApiModelProperty("分销渠道排序视图")
    private String channelSort;

    @Column(name = "activity_benefit_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "活动收益预估类型：1 没有基线 2 有基线")
    @ApiModelProperty("活动收益预估类型：1 没有基线 2 有基线")
    private Byte activityBenefitType;
}
