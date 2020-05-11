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
 * 活动和投放平台关系信息表（适用于所有活动）
 * @author jiaby
 */
@Data
@Table(name = "activity_platform")
public class ActivityPlatform extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

    @ApiModelProperty(value = "投放平台Id")
    @Column(name = "platform_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "投放平台Id")
    private Long platformId;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, length = 4, defaultValue = "1", comment = "状态 1 投放  2 取消投放 ")
    @ApiModelProperty("状态 1 投放  2 取消投放")
    private Byte state;
}
