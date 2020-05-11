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
 * 优惠活动规则信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_discount_rule")
public class ActivityDiscountRule extends TableBase implements Serializable {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @Column(name = "algorithms",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "优惠算法 1 早定优惠 2 特价直减 3 满减优惠")
    @ApiModelProperty("优惠算法 1 早定优惠 2 特价直减 3 满减优惠")
    private Byte algorithms;

    @Column(name = "discount_rule",type = MySqlTypeConstant.TEXT,comment = "优惠规则")
    @ApiModelProperty("优惠规则")
    private String discountRule;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "活动规则描述")
    @ApiModelProperty("活动规则描述")
    private String remark;



}
