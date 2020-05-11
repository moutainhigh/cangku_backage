package cn.enn.wise.platform.mall.bean.vo;

import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/23
 */
@Data
@ApiModel("船舱")
public class LineCabinVo {

    @ApiModelProperty("图片")
    private String img;

    @ApiModelProperty("仓")
    private String cabinName;

    @ApiModelProperty("价格")
    private BigDecimal price;

    @ApiModelProperty("库存")
    private Integer stock;

    @ApiModelProperty("航线")
    private JSONObject shipLineObj;

    @ApiModelProperty("航线")
    private String shipLineInfo;

    @ApiModelProperty("船名称")
    private String nickName;




}
