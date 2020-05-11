package cn.enn.wise.platform.mall.bean.param;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页页面请求信息
 *
 * @author caiyt
 */
@Data
@ApiModel
public class ReqPageInfoQry<T> implements Serializable {
    @ApiModelProperty(value = "当前页数")
    private Long pageNum;

    @ApiModelProperty(value = "页面数据量")
    private Long pageSize;

    @ApiModelProperty(value = "请求条件实体")
    private T reqObj;

    @Override
    public String toString() {
        return "PageInfoDTO{current=" + pageNum + ", size=" + pageSize + ", records=" + reqObj + '}';
    }
}