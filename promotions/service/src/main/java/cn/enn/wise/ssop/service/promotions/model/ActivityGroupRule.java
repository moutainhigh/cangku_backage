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
 * 拼团活动规则信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_group_rule")
public class ActivityGroupRule extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @Column(name = "group_valid_hours",type = MySqlTypeConstant.INT,length = 11,comment = "拼团有效期,单位小时")
    @ApiModelProperty(value = "拼团有效期,单位小时")
    private Integer groupValidHours;

    @Column(name = "group_size",type = MySqlTypeConstant.INT,length = 11,comment = "拼团人数(2-10)")
    @ApiModelProperty(value = "拼团人数(2-10)")
    private Integer groupSize;

    @Column(name = "isauto_create_group",type = MySqlTypeConstant.TINYINT,length = 4,comment = "是否自动成团,1是 2否")
    @ApiModelProperty(value = "是否自动成团,1是 2否")
    private Byte isautoCreateGroup;

    @Column(name = "auto_create_group_limit",type = MySqlTypeConstant.TINYINT,length = 4,comment = "自动成团人数")
    @ApiModelProperty(value = "自动成团人数")
    private Byte autoCreateGroupLimit;

    @Column(name = "group_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "1普通团 2 超级团")
    @ApiModelProperty(value = "1普通团 2 超级团")
    private Byte groupType;


    @Column(name = "group_limit",type = MySqlTypeConstant.INT,length = 11,comment = "每单限购数量")
    @ApiModelProperty(value = "每单限购数量")
    private Integer groupLimit;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT,length = 4,comment = "1 可用 2 不可用")
    @ApiModelProperty(value = "1 可用 2 不可用")
    private Byte state;

    @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length = 1500,comment = "备注")
    @ApiModelProperty("备注")
    private String description;

    @Column(name = "period",type = MySqlTypeConstant.TINYINT,length = 4,comment = "体验时段 1 上午 2 下午 3 全天")
    @ApiModelProperty(value = "体验时段 1 上午 2 下午 3 全天")
    private Byte period;

}
