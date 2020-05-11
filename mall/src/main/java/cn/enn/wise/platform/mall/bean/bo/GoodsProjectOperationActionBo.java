package cn.enn.wise.platform.mall.bean.bo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

@Data
@TableName("goods_project_operation_action")
public class GoodsProjectOperationActionBo {

    @ApiModelProperty(value = "业务主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "状态变化之前的状态")
    private Byte fromStatus;

    @ApiModelProperty(value = "状态变化之后的状态")
    private Byte toStatus;

    @ApiModelProperty(value = "状态变化之前的体验概率")
    private Short fromProbability;

    @ApiModelProperty(value = "状态变化之前的体验概率")
    private Short toProbability;

    @ApiModelProperty(value = "变化原因")
    private String remark;

    @ApiModelProperty(value = "提交人")
    private Long operationBy;

    @ApiModelProperty(value = "操作时间")
    private Timestamp operationTime;

    @ApiModelProperty(value = "运营单元id")
    private Long operationId;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @ApiModelProperty(value = "创建人id")
    private Long createUserId;

    @ApiModelProperty(value = "创建人姓名")
    private String createUserName;

    @ApiModelProperty(value = "更新人Id")
    private Long updateUserId;

    @ApiModelProperty(value = "更新人姓名")
    private String updateUserName;

    @ApiModelProperty(value = "影响等级")
    private String degreeOfInfluence;

}
