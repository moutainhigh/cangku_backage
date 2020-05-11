package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 *项目信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_project")
public class GoodsProjectTable extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 项目名称
     */
    @Column(name = "name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "项目名称")
    @ApiModelProperty("项目名称")
    private String name;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty("创建人名称")
    private String createUserName;

    /**
     * 修改人名称
     */
    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "修改人名称")
    @ApiModelProperty("修改人名称")
    private String updateUserName;

    /**
     * 项目编号
     */
    @Column(name = "project_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "项目编号")
    @ApiModelProperty("项目编号")
    private String projectCode;

    @Column(name = "company_id",type = MySqlTypeConstant.INT, length = 11,comment = "所属景区Id")
    @ApiModelProperty(value = "所属景区Id")
    private Integer companyId;

    @Column(name = "operation_time",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "运营时间 例如 8:00 -9:00")
    @ApiModelProperty(value = "运营时间 例如 8:00 -9:00")
    private String operationTime;

    @Column(name = "operation_staff",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "运营人员，格式为Id 多个运营人员以逗号分割")
    @ApiModelProperty(value = "运营人员，格式为Id 多个运营人员以逗号分割")
    private String operationStaff;

    @Column(name = "project_status",type = MySqlTypeConstant.INT, length = 11,comment = "项目状态 1 启用  2  禁用")
    @ApiModelProperty(value = "项目状态 1 启用  2  禁用")
    private Integer projectStatus;

    @Column(name = "service_line",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "服务线路 多个线路用逗号隔开")
    @ApiModelProperty(value = "服务线路 多个线路用逗号隔开")
    private String serviceLine;

    @Column(name = "project_start_date",type = MySqlTypeConstant.DATE,comment = "项目开始时间")
    @ApiModelProperty(value = "项目开始时间")
    private Date projectStartDate;

    @Column(name = "max_service_amount",type = MySqlTypeConstant.INT, length = 11,comment = "单次服务总人数")
    @ApiModelProperty(value = "单次服务总人数")
    private Integer maxServiceAmount;

    @Column(name = "service_place_id",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "项目运营地点 多个运营地点用逗号分割")
    @ApiModelProperty(value = "项目运营地点 多个运营地点用逗号分割")
    private String servicePlaceId;

    @Column(name = "device_amount",type = MySqlTypeConstant.INT, length = 11,comment = "设备数量")
    @ApiModelProperty(value = "设备数量")
    private Integer deviceAmount;

    @Column(name = "project_present",type = MySqlTypeConstant.TEXT, comment = "项目介绍")
    @ApiModelProperty(value = "项目介绍")
    private String projectPresent;

    @Column(name = "project_manager",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "项目负责人")
    @ApiModelProperty(value = "项目负责人")
    private String projectManager;

    @Column(name = "project_manager_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "项目负责人id")
    @ApiModelProperty(value = "项目负责人id")
    private Long projectManagerId;

    /**
     * 项目简称
     */
    @Column(name = "abbreviation",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "项目简称")
    @ApiModelProperty("项目简称")
    private String abbreviation;

    @Column(name = "day_operation_time",type = MySqlTypeConstant.INT, length = 11,comment = "单日运营时长，单位: 小时")
    @ApiModelProperty("单日运营时长，单位: 小时")
    private Integer dayOperationTime;


}
