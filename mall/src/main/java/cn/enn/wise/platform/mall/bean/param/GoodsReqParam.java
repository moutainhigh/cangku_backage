package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * api请求商品类
 *
 * @author caiyt
 * @since 2019-05-23
 */
@Data
@ApiModel
public class GoodsReqParam implements Serializable {
    @ApiModelProperty(value = "所属项目ID")
    private Long projectId;

    @ApiModelProperty(value = "所属项目名称")
    private String projectName;

    @ApiModelProperty(value = "分类ID")
    private Long categoryId;

    @ApiModelProperty(value = "资源类型ID")
    private Long resourceId;

    @ApiModelProperty(value = "运营时间")
    private String operationDate;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "时段Id")
    private Integer periodId;

    @ApiModelProperty(value = "商品Id")
    private Long goodsId;

    @ApiModelProperty(value = "商品状态 1 上架 2 下架")
    private Byte goodsStatus;

    @ApiModelProperty(value = "时间节点1 今天,2 明天,3 后天")
    private Integer timeFrame;

    @ApiModelProperty(value = "是否套餐 1 是 0 否")
    private Integer isPackage;

    @ApiModelProperty(value = "基础价格")
    private BigDecimal basePrice;

    @ApiModelProperty(value = "优惠价格")
    private BigDecimal realTips;

    @ApiModelProperty(value = "人数上限")
    private Integer maxNum;

    @ApiModelProperty(value = "计价单位")
    private String salesUnit;

    @ApiModelProperty(value = "使用场地")
    private String[] servicePlace;

    @ApiModelProperty(value = "使用场地id")
    private List<Long> servicePlaceId;

    @ApiModelProperty(value = "使用场地名")
    private String servicePlaceName;

    @ApiModelProperty(value = "商品使用规则")
    private String rules;

    //1.2 add线路、单次服务时长、分销价格 2019-07-25 start
    @ApiModelProperty(value = "服务线路")
    private Long serviceRoute;

    @ApiModelProperty(value = "服务线路名")
    private String serviceRouteName;

    @ApiModelProperty(value = "单次服务时长")
    private Integer singleServiceDuration;


    @ApiModelProperty(value = "运营时长")
    private Integer dayOperationTime;


    @ApiModelProperty(value = "最大服务人数")
    private Integer maxServiceAmount;

    @ApiModelProperty(value = "分销价格")
    private BigDecimal retailPrice;
    //1.2 add 2019-07-25 end

    //1.2.1 商品是否分时段 2019-08-07 start
    @ApiModelProperty(value = "商品是否分时段")
    private Integer isByPeriodOperation;
    //1.2.1 add 2019-08-07 end

    /**
     * 分销商手机号
     */
    @ApiModelProperty(value = "分销商手机号")
    private String phone;

    /**
     * 公司ID
     */
    @ApiModelProperty(value = "公司ID")
    private Long companyId;

    @ApiModelProperty(value = "拼团活动Id")
    private Long promotionId;

    /**
     * 分销商Id
     */
    @ApiModelProperty(value = "分销商Id")
    private Long distributorId;


    @ApiModelProperty(value = "来源")
    private Long fromType;

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

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商家名称")
    private String businessName;

    /**
     * 校验保存商品的参数
     *
     * @param goodsReqParam
     */
    public static void validateSaveGoodsReqParam(GoodsReqParam goodsReqParam) {
        if (goodsReqParam.getProjectId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "所属项目还未选择");
        }
        if (goodsReqParam.getResourceId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "资源类型还未选择");
        }
        if (StringUtils.isEmpty(goodsReqParam.getGoodsName())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品名称还未设置");
        }
        if (goodsReqParam.getIsPackage() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "是否套餐还未选择");
        }
        if (goodsReqParam.getBasePrice() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "基础价格还未设置");
        }
        if (goodsReqParam.getCategoryId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品分类还未选择");
        }
        if (goodsReqParam.getMaxNum() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品使用人数上限还未设置");
        }
        if (StringUtils.isEmpty(goodsReqParam.getSalesUnit())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "计价单位还未选择");
        }
        if (StringUtils.isEmpty(goodsReqParam.getServicePlace())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "服务场地还未选择");
        }
        //1.2 add start
        if (goodsReqParam.getRetailPrice() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "分销价格还未设置");
        }
        if (goodsReqParam.getSingleServiceDuration()<=0||goodsReqParam.getSingleServiceDuration()>=240) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "服务时间大于0小于240分钟");
        }
    }

    /**
     * 校验修改商品的参数
     *
     * @param goodsReqParam
     */
    public static void validateUpdateGoodsReqParam(GoodsReqParam goodsReqParam) {
        if (goodsReqParam.getGoodsId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品ID不能为空");
        }
//        if (goodsReqParam.getProjectId() == null) {
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "所属项目ID不能为空");
//        }
        // validateSaveGoodsReqParam(goodsReqParam);
    }
}
