package cn.enn.wise.platform.mall.bean.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

/**
 * 酒店商品实体
 *
 * @author baijie
 * @date 2019-09-20
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("酒店售卖商品实体")
public class HotelGoods {

    @Id
    @ApiModelProperty("主键")
    private String id;
    /**
     * 酒店名称
     */
    @ApiModelProperty("酒店名称")
    private String hotelName;

    /**
     * 房间名称
     */
    @ApiModelProperty("房间名称")
    private String houseName;

    /**
     * 价格
     *
     */
    @ApiModelProperty("价格")
    private BigDecimal price;

    /**
     * 商品简介
     */
    @ApiModelProperty("商品简介")
    private String present;

    /**
     * 描述
     */
    @ApiModelProperty("描述")
    private String describe;

    /**
     * 使用规则
     */
    @ApiModelProperty("使用规则")
    private String rule;

    /**
     * 1上架 2 下架 3 删除
     */
    @ApiModelProperty("1上架 2 下架 3 删除")
    private Integer status;

    @ApiModelProperty("联系电话")
    private String phone;
}
