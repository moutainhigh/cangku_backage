package cn.enn.wise.ssop.service.promotions.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 抽奖和人员关联信息表（适用于抽奖活动）
 * @author jiaby
 */
@Data
@Table(name = "draw_and_user")
public class DrawAndUser extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "活动Id")
    @Column(name = "activity_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动Id")
    private Long activityId;

    @ApiModelProperty(value = "活动规则Id")
    @Column(name = "activity_rule_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "活动规则Id")
    private Long activityRuleId;

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

    @Column(name = "draw_all_size",type = MySqlTypeConstant.INT,length = 12,comment = "总抽奖次数(次/天/人)")
    @ApiModelProperty("总抽奖次数(次/天/人)")
    private Integer drawAllSize;

    @Column(name = "draw_size",type = MySqlTypeConstant.INT,length = 12,comment = "剩余可用抽奖次数(次/天/人)")
    @ApiModelProperty("剩余可用抽奖次数(次/天/人)")
    private Integer drawSize;

    @Column(name = "draw_used",type = MySqlTypeConstant.INT,length = 12,comment = "当天已经抽奖次数(次/天/人)")
    @ApiModelProperty("当天已经抽奖次数(次/天/人)")
    private Integer drawUsed;

    @Column(
            name = "end_time",
            type = MySqlTypeConstant.TIMESTAMP,
            comment = "结束时间"
    )
    @JsonFormat(
            shape = JsonFormat.Shape.STRING,
            pattern = "yyyy/MM/dd HH:mm:ss"
    )
    @ApiModelProperty("结束时间")
    private Timestamp endTime;

}
