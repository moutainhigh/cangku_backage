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
 * 抽奖活动规则信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_draw_rule")
public class ActivityDrawRule extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @Column(name = "draw_order_condition",type = MySqlTypeConstant.TINYINT,comment = "抽奖订单条件,1单笔订单 2多笔订单")
    @ApiModelProperty(value = "抽奖订单条件,1单笔订单 2多笔订单 对应枚举类型 drawCondition")
    private Byte drawOrderCondition;

    @Column(name = "draw_man_condition",type = MySqlTypeConstant.TINYINT,comment = "抽奖条件,1人头 2金额")
    @ApiModelProperty(value = "抽奖条件,1人头 2金额")
    private Byte drawManCondition;

    @Column(name = "satisfy_price",type = MySqlTypeConstant.INT,length = 12,comment = "满足多少价钱的金额抽奖1次")
    @ApiModelProperty("满足多少价钱的金额抽奖1次")
    private Integer satisfyPrice;

    @Column(name = "isincrease",type = MySqlTypeConstant.TINYINT,comment = "次数是否递增,1是 2否")
    @ApiModelProperty(value = "次数是否递增,1是 2否")
    private Byte isincrease;

    @Column(name = "draw_size",type = MySqlTypeConstant.INT,length = 12,comment = "抽奖次数(次/天/人)")
    @ApiModelProperty("抽奖次数(次/天/人)")
    private Integer drawSize;

    @Column(name = "draw_type",type = MySqlTypeConstant.TINYINT,comment = "抽奖方式 1 九宫格 2 转盘 3 摇一摇")
    @ApiModelProperty("抽奖方式 1 九宫格 2 转盘 3 摇一摇")
    private Byte drawType;

    @Column(name = "open_draw_cycle",type = MySqlTypeConstant.TINYINT,comment = "开奖频率 1 每日一次 2 活动终结一次")
    @ApiModelProperty("开奖频率 1 每日一次 2 活动终结一次")
    private Byte openDrawCycle;

    @Column(name = "cash_time_type",type = MySqlTypeConstant.TINYINT,comment = "兑奖时间类型 1 活动结束后 2中奖后")
    @ApiModelProperty("兑奖时间类型 1 活动结束后 2中奖后")
    private Byte cashTimeType;

    @Column(name = "cash_time",type = MySqlTypeConstant.INT,length = 12,comment = "兑奖时间")
    @ApiModelProperty("兑奖时间")
    private Integer cashTime;

    @Column(name = "cash_code",type = MySqlTypeConstant.VARCHAR,length = 255,comment = "兑奖标识 1 手机号 2微信号 3 身份证号 4地址 可多选")
    @ApiModelProperty("兑奖标识 1 手机号 2微信号 3 身份证号 4地址")
    private String cashCode;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "备注")
    @ApiModelProperty("备注")
    private String remark;

    @ApiModelProperty(value = "奖品类型(json)")
    @Column(name = "cash",type = MySqlTypeConstant.VARCHAR,length = 255,comment = "奖品类型(json)")
    private String cash;

    @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "备注")
    @ApiModelProperty("规则描述")
    private String description;
}
