/**
 * Copyright (C), 2018-2019
 * FileName: PageVo
 * Author:   Administrator
 * Date:     2019-04-21 14:41
 * Description:
 */
package cn.enn.wise.ssop.api.cms.dto.request.oldcms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

@ApiModel("分页参数")
@Data
@EqualsAndHashCode
public class PageVo implements Serializable {
    private static final long serialVersionUID = 4397113484381819200L;
    /**
     * 第几页
     */
    @ApiModelProperty("第几页")
    private Integer pageNum;
    /**
     * 每页多少条
     */
    @ApiModelProperty("每页多少条")
    private Integer pageSize;
}