package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

/**
 * 响应api商品类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel(value = "商品类信息", description = "响应的商品数据封装类")
public class GoodsResVO implements Serializable {
    @ApiModelProperty(value = "主键")
    private Long id;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "所属项目ID")
    private Long projectId;

    @ApiModelProperty(value = "所属项目名")
    private String projectName;

    @ApiModelProperty(value = "资源类别")
    private Long resourceId;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "计价单位")
    private String salesUnit;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "是否套餐 1 是 0 否")
    private Integer isPackage;

    @ApiModelProperty(value = "基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "使用场地")
    private List<Long> servicePlace;

    @ApiModelProperty(value = "使用场地名")
    private String servicePlaceName;

    @ApiModelProperty(value = "1:热气球 2:船")
    private Integer goodsType;

    @ApiModelProperty(value = "创建时间")
    private Timestamp createTime;

    @ApiModelProperty(value = "创建人ID")
    private Long createUserId;

    @ApiModelProperty(value = "创建人名称")
    private String createUserName;

    @ApiModelProperty(value = "更新时间")
    private Timestamp updateTime;

    @ApiModelProperty(value = "更新人ID")
    private Long updateUserId;

    @ApiModelProperty(value = "更新人名称")
    private String updateUserName;

    @ApiModelProperty(value = "商品状态 1 上架 2 下架")
    private Byte goodsStatus;

    @ApiModelProperty(value = "商品编号")
    private String goodsCode;

    @ApiModelProperty(value = "商品使用规则")
    private String rules;

    @ApiModelProperty(value = "商品扩展类数据")
    private List<GoodsExtendResVo> goodsExtendResVoList;

    //1.2 add线路、单次服务时长、分销价格 2019-07-25 start
    @ApiModelProperty(value = "服务线路")
    private Long serviceRoute;

    @ApiModelProperty(value = "服务线路名")
    private String serviceRouteName;

    @ApiModelProperty(value = "单次服务时长")
    private Integer singleServiceDuration;

    @ApiModelProperty(value = "分销价格")
    private BigDecimal retailPrice;
    //1.2 add 2019-07-25 end

    //1.2.1 商品是否分时段 2019-08-07 start
    @ApiModelProperty(value = "商品是否分时段")
    private Integer isByPeriodOperation;

    @ApiModelProperty(value = "运营时长")
    private Integer dayOperationTime;


    @ApiModelProperty(value = "最大服务人数")
    private Integer maxServiceAmount;
    //1.2.1 add 2019-08-07 end

    /**
     * 商品图片
     */
    @ApiModelProperty(value = "商品图片")
    private String img;

    /**
     * 商品描述
     */
    @ApiModelProperty(value = "商品描述")
    private String description;

    /**
     * 商品简介
     */
    @ApiModelProperty(value = "商品简介")
    private String synopsis;

    @ApiModelProperty(value = "商家Id")
    private Long businessId;

    @ApiModelProperty(value = "商品名称")
    private String businessName;

    @ApiModelProperty("商品是否分时段售卖")
    private Integer orderby;

}
