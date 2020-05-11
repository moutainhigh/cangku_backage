package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@ApiModel("行程保存(关联)")
public class CompanyRouteSaveParam implements Serializable {

    @ApiModelProperty("租户行程id")
    private Long compnayRouteId;

    @ApiModelProperty("一组景区routeId")
    private List<Long> routeIds = new ArrayList<>();
}
