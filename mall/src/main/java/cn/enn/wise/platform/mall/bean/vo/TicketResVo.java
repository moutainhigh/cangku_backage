package cn.enn.wise.platform.mall.bean.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author bj
 * @Description  票预定信息
 * @Date19-5-26 下午1:49
 * @Version V1.0
 **/
@Data
@ApiModel("预订票信息返回实体")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketResVo {

    /**
     * 商品Id
     */
    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "3 门票")
    private Integer goodsType;

    /**
     * 默认数量
     */
    @ApiModelProperty(value = "默认数量")
    private Integer amount;
    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 入园时间
     */
    @ApiModelProperty(value = "入园时间")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date enterTime;

    /**
     * 体验时间
     */
    @ApiModelProperty(value = "体验时间")
    private String experienceTime;

    /**
     * sku对应的价格
     */
    @ApiModelProperty(value = "sku对应的价格")
    private String siglePrice;

    /**
     *  时间节点1 今天,2 明天,3 后天
     */
    @ApiModelProperty(value = " 时间节点1 今天,2 明天,3 后天")
    private Integer timeFrame;
    /**
     * 时段Id
     */
    @ApiModelProperty(value = "时段Id")
    private Integer periodId;

    @ApiModelProperty(value = "多人票中实际优惠金额")
    private String realTips;

    @ApiModelProperty(value = "分销价格")
    private BigDecimal retailPrice;

    @ApiModelProperty(value = "项目Id")
    private Long  projectId;

    @ApiModelProperty(value = "是否是分销商品 1 是 2 不是 ,通过小程序扫分销二维码进入,判断该分销商的身份是否可以分销该商品")
    private Integer isDistributeGoods  ;

    @ApiModelProperty("拼团价")
    private BigDecimal groupPrice;

    @ApiModelProperty("每单限购数量")
    private Integer groupLimit;

    @ApiModelProperty("项目图片")
    private String headImg;

    @ApiModelProperty("项目名称")
    private String projectName;

}
