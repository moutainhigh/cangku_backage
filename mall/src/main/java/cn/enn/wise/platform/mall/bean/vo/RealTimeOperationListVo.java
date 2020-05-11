package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RealTimeOperationListVo {

    @ApiModelProperty(value = "屯白：废弃")
    private List<GoodsOperationPeriodVo> tunBai;
    @ApiModelProperty(value = "缩松：废弃")
    private List<GoodsOperationPeriodVo> suoSong;

    @ApiModelProperty(value = "数据列表")
    private List<RealTimeOperationListItem> dataList;

    @ApiModelProperty(value = "运营状态值类型 1数值 2 文字类型")
    private Integer operationStatusType;

    @ApiModelProperty(value = "文字类型标签")
    private String label;
}
