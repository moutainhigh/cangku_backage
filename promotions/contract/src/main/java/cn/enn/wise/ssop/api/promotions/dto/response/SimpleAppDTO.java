package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@ApiModel(description = "客户端简单对象")
@Data
public class SimpleAppDTO {

    @ApiModelProperty("主键")
    private Long id;



    @ApiModelProperty("创建时间")
    private Date createTime;


    @ApiModelProperty("修改时间")
    private Date updateTime;


    @ApiModelProperty("禁启用状态 1启用 2禁用")
    private int state;


    @ApiModelProperty("是否删除 1正常 2已删除")
    private int isDelete;

}
