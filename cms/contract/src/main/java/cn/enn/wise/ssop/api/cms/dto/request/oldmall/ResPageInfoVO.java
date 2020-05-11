package cn.enn.wise.ssop.api.cms.dto.request.oldmall;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页页面响应信息
 *
 * @author caiyt
 */
@Data
@ApiModel(value = "分页页面响应信息", description = "分页页面的数据封装类")
public class ResPageInfoVO<T> implements Serializable {
    @ApiModelProperty(value = "当前页数")
    private Long pageNum;

    @ApiModelProperty(value = "页面数据量")
    private Long pageSize;

    @ApiModelProperty(value = "数据总量")
    private Long total;

    @ApiModelProperty(value = "数据内容")
    private T records;

    @Override
    public String toString() {
        return "PageInfoDTO{current=" + pageNum + ", size=" + pageSize + ", total=" + total + ", records=" + records + '}';
    }
}