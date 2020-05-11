package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Date;
import java.sql.Timestamp;

@Data
@TableName("goods_project_operation")
public class GoodsProjectOperationBo {

    @ApiModelProperty(value = "业务主键")
    @TableId(type = IdType.AUTO)
    private Long id;
    @ApiModelProperty(value = "运营时段")
    private Long periodId;
    @ApiModelProperty(value = "运营日期")
    private Date operationDate;
    @ApiModelProperty(value = "时段标题")
    private String periodTitle;
    @ApiModelProperty(value = "可飞行概率1-100 表示 1%-100%")
    private Integer probability;
    @ApiModelProperty(value = "影响等级")
    private String degreeOfInfluence;
    @ApiModelProperty(value = "状态：1 正常 2 停飞 3 临时征用")
    private Byte status;
    @ApiModelProperty(value = "备注")
    private String remark;
    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;
    @ApiModelProperty(value = "创建人id")
    private Long createUserId;
    @ApiModelProperty(value = "创建人")
    private String createUserName;
    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;
    @ApiModelProperty(value = "更新人id")
    private Long updateUserId;
    @ApiModelProperty(value = "更新人")
    private String updateUserName;
    @ApiModelProperty(value = "运营地点Id")
    private Long servicePlaceId;
}
