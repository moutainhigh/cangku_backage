package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author baijie
 * @date 2019-10-29
 */
@Data
public class TagBean {

    /**
     * tagId : 1
     * tagName : 爆款单品
     */
    @ApiModelProperty("子标签Id")
    private String tagId;

    @ApiModelProperty("子标签名称")
    private String tagName;

    @ApiModelProperty("商品Id")
    private Long goodsId;

    @ApiModelProperty("项目Id")
    private Long projectId;
}
