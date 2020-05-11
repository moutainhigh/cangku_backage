package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
/**
 *时段信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_project_period")
public class GoodsProjectPeriod extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;


    /**
     * 项目id
     */
    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "项目id")
    @ApiModelProperty(value = "项目id")
    private Long projectId;

    /**
     * 时段名称
     */
    @Column(name = "title",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "时段名称")
    @ApiModelProperty(value = "时段名称")
    private String title;

    /**
     * 开始时间
     */
    @Column(name = "start_time",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "开始时间")
    @ApiModelProperty(value = "开始时间")
    private String startTime;

    /**
     * 结束时间
     */
    @Column(name = "end_time",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "结束时间")
    @ApiModelProperty(value = "结束时间")
    private String endTime;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty(value = "创建人名称")
    private String createUserName;


    /**
     * 修改人名称
     */
    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "修改人名称")
    @ApiModelProperty(value = "修改人名称")
    private String updateUserName;

    /**
     * 排序字段
     */
    @Column(name = "orderby",type = MySqlTypeConstant.INT, length = 4,comment = "排序字段")
    @ApiModelProperty(value = "排序字段")
    private Byte orderby;

    /**
     * 状态 1-启用 2-禁用
     */
    @Column(name = "status",type = MySqlTypeConstant.INT, length = 4,comment = "1 可用 2 不可用")
    @ApiModelProperty(value = "1 可用 2 不可用")
    private byte status;


}
