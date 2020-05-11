package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 活动规则信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_rule")
public class ActivityRule extends TableBase implements Serializable {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动基础信息Id")
    @Column(name = "activity_base_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动基础信息Id")
    private Long activityBaseId;

    @Column(name = "activity_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    @ApiModelProperty("活动类型 1 优惠活动 2 拼团活动 3 抽奖活动")
    private Byte activityType;

    @Column(name = "refund_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "退款类型 1 常规退款 2 不予退款")
    @ApiModelProperty("退款类型 1 常规退款 2 不予退款")
    private Byte refundType;

    @Column(name = "goods_limit",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "产品范围 1 全部产品 2 指定产品")
    @ApiModelProperty("产品范围 1 全部产品 2 指定产品")
    private Byte goodsLimit;

}
