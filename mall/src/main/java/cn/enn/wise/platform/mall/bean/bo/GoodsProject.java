package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * <p>
 * 二销产品所属项目
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsProject extends Model<GoodsProject> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目简称
     */
    private String abbreviation;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp createTime;

    /**
     * 创建人id
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 更新时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private Timestamp updateTime;

    /**
     * 更新人id
     */
    private Long updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;

    /**
     * 项目编号
     */
    private String projectCode;

    @ApiModelProperty(value = "所属景区Id")
    private Integer companyId;

    @ApiModelProperty(value = "运营时间 例如 8:00 -9:00")
    private String operationTime;

    @ApiModelProperty(value = "运营人员，格式为Id 多个运营人员以逗号分割")
    private String operationStaff;

    @ApiModelProperty(value = "项目状态 1 启用  2  禁用")
    private Integer projectStatus;

    @ApiModelProperty(value = "服务线路 多个线路用逗号隔开")
    private String serviceLine;

    @ApiModelProperty(value = "项目开始时间")
    private Date projectStartDate;

    @ApiModelProperty(value = "单次服务总人数")
    private Integer maxServiceAmount;

    @ApiModelProperty(value = "项目运营地点 多个运营地点用逗号分割")
    private String servicePlaceId;

    @ApiModelProperty(value = "设备数量")
    private Integer deviceAmount;

    @ApiModelProperty(value = "项目介绍")
    private String projectPresent;

    @ApiModelProperty(value = "项目负责人")
    private String projectManager;

    @ApiModelProperty(value = "项目负责人id")
    private Long projectManagerId;

    @ApiModelProperty("单日运营时长，单位: 小时")
    private Integer dayOperationTime;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
