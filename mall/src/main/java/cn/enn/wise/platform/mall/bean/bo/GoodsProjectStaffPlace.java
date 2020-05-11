package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("goods_project_staff_place")
public class GoodsProjectStaffPlace {


    @ApiModelProperty(value = "业务主键")
    @TableId(type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "员工Id")
    private Long staffId;

    @ApiModelProperty(value = "项目Id")
    private Long projectId;

    @ApiModelProperty(value = "地点Id")
    private Long placeId;


}
