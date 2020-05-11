package cn.enn.wise.ssop.api.order.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
@ApiModel("页面数据封装")
public class PageWrapperDTO<T> {

    @ApiModelProperty("当前页")
    private int pageNum;

    @ApiModelProperty("页面大小")
    private int pageSize;

    @ApiModelProperty("总记录数")
    private int total;

    @ApiModelProperty("数据记录")
    private List<T> data;
}
