package cn.enn.wise.platform.mall.bean.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class Page<T> {

    @ApiModelProperty("当前页")
    private int pageNum;

    @ApiModelProperty("页面大小")
    private int pageSize;

    @ApiModelProperty("总记录数")
    private int total;

    @ApiModelProperty("数据记录")
    private List<T> data;

}
