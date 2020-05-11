package cn.enn.wise.platform.mall.bean.bo.autotable;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;


/**
 *项目时段状态变化信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_project_operation_action")
public class GoodsProjectOperationAction extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "from_status",type = MySqlTypeConstant.BIGINT, length = 20,comment = "状态变化之前的状态")
    @ApiModelProperty(value = "状态变化之前的状态")
    private Byte fromStatus;

    @Column(name = "to_status",type = MySqlTypeConstant.INT, length = 4,comment = "状态变化之后的状态")
    @ApiModelProperty(value = "状态变化之后的状态")
    private Byte toStatus;

    @Column(name = "from_probability",type = MySqlTypeConstant.INT, length = 6,comment = "状态变化之前的体验概率")
    @ApiModelProperty(value = "状态变化之前的体验概率")
    private Short fromProbability;

    @Column(name = "to_probability",type = MySqlTypeConstant.INT, length = 6,comment = "状态变化之后的体验概率")
    @ApiModelProperty(value = "状态变化之后的体验概率")
    private Short toProbability;

    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR, length = 255,comment = "变化原因")
    @ApiModelProperty(value = "变化原因")
    private String remark;

    @Column(name = "operation_by",type = MySqlTypeConstant.BIGINT, length = 20,comment = "提交人")
    @ApiModelProperty(value = "提交人")
    private Long operationBy;

    @Column(name = "operation_time",type = MySqlTypeConstant.TIMESTAMP, comment = "操作时间")
    @ApiModelProperty(value = "操作时间")
    private Timestamp operationTime;

    @Column(name = "operation_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "运营单元id")
    @ApiModelProperty(value = "运营单元id")
    private Long operationId;

    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人姓名")
    @ApiModelProperty(value = "创建人姓名")
    private String createUserName;

    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "更新人姓名")
    @ApiModelProperty(value = "更新人姓名")
    private String updateUserName;

    @Column(name = "degree_of_influence",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "影响等级")
    @ApiModelProperty(value = "影响等级")
    private String degreeOfInfluence;

}
