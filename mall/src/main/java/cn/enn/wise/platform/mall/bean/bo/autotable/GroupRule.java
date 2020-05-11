package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 拼团规则表
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "group_rule")
public class GroupRule {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "group_size",type = MySqlTypeConstant.INT,length = 11,comment = "拼团人数(2-10)")
    @ApiModelProperty(value = "拼团人数(2-10)")
    private Integer groupSize;

    @Column(name = "is_auto_create_group",type = MySqlTypeConstant.TINYINT,length = 4,comment = "是否自动成团,1是 2否")
    @ApiModelProperty(value = "是否自动成团,1是 2否")
    private Byte isAutoCreateGroup;

    @Column(name = "group_type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "1普通团 2 超级团")
    @ApiModelProperty(value = "1普通团 2 超级团")
    private Byte groupType;

    @Column(name = "group_valid_hours",type = MySqlTypeConstant.INT,length = 11,comment = "拼团有效期,单位小时")
    @ApiModelProperty(value = "拼团有效期,单位小时")
    private Integer groupValidHours;

    @Column(name = "group_limit",type = MySqlTypeConstant.INT,length = 11,comment = "每单限购数量")
    @ApiModelProperty(value = "每单限购数量")
    private Integer groupLimit;

    @Column(name = "name",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "规则名称")
    @ApiModelProperty(value = "规则名称")
    private String name;

    @Column(name = "status",type = MySqlTypeConstant.TINYINT,length = 4,comment = "1 可用 2 不可用")
    @ApiModelProperty(value = "1 可用 2 不可用")
    private Byte status;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,comment = "备注")
    @ApiModelProperty(value = "备注")
    private String remark;

    @Column(name = "create_by",type = MySqlTypeConstant.VARCHAR,comment = "创建人")
    @ApiModelProperty(value = "创建人")
    private String createBy;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @Column(name = "type",type = MySqlTypeConstant.TINYINT,length = 4,comment = "规则分类 1拼团规则 2 优惠规则")
    @ApiModelProperty(value = "规则分类 1拼团规则 2 优惠规则")
    private Byte type;

    @Column(name = "auto_create_group_limit",type = MySqlTypeConstant.TINYINT,length = 4,comment = "自动成团人数")
    @ApiModelProperty(value = "自动成团人数")
    private Byte autoCreateGroupLimit;

    @Column(name = "period",type = MySqlTypeConstant.TINYINT,length = 4,comment = "体验时段 1 上午 2 下午 3 全天")
    @ApiModelProperty(value = "体验时段 1 上午 2 下午 3 全天")
    private Byte period;
}
