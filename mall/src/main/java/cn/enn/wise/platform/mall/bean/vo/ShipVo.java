package cn.enn.wise.platform.mall.bean.vo;

import cn.enn.wise.platform.mall.bean.bo.autotable.Ship;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * @author jiaby
 */
@Data
@ApiModel("船舶实体类")
public class ShipVo extends Ship {

    @ApiModelProperty("船舱列表信息")
    List<CabinVo> cabinVoList;
}
