package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * api请求虚拟商品类
 *
 * @author lishuiquan
 * @since 2019-10-29
 */
@Data
@ApiModel
public class VirtualGoodsReqParam implements Serializable {

    /**
     * 商品名称最长30个字符
     */

    private static final int GOODS_NAME_MAX_LENGTH = 30;

    /**
     * 商品简介最长16个字符
     */
    private static final int INTRODUCTION_MAX_LENGTH = 16;
    /**
     * 主键
     */
    @ApiModelProperty(value = "业务主键")
    private Long id;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品编号")
    private String goodsId;

    /**
     * 商品名称
     */
    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    /**
     * 简介
     */
    @ApiModelProperty(value = "商品简介")
    private String introduction;

    /**
     * 分类
     */
    @ApiModelProperty(value = "商品分类")
    private String category;

    /**
     * 价格
     */
    @ApiModelProperty(value = "商品价格")
    private BigDecimal goodsPrice;

    /**
     * 供应商
     */
    @ApiModelProperty(value = "供应商")
    private String supplier;

    /**
     * 库存
     */
    @ApiModelProperty(value = "库存")
    private String inventory;

    /**
     * 缩略图
     */
    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    /**
     * 轮播图
     */
    @ApiModelProperty(value = "轮播图")
    private String banner;

    /**
     * 淘宝链接
     */
    @ApiModelProperty(value = "淘宝链接")
    private String taobaoLink;

    /**
     * 已卖数量
     */
    @ApiModelProperty(value = "已卖数量")
    private Integer sell;

    /**
     * 商品详情
     */
    @ApiModelProperty(value = "商品详情")
    private String goodsDetail;

    /**
     * 商品状态
     */
    @ApiModelProperty(value = "商品状态1:上架，2;下架，默认1")
    private Byte goodsStatus;

    /**
     * 是否删除
     */
    @ApiModelProperty(value = "是否删除1:正常，2:删除,默认1")
    private Byte isDelete;


    /**
     * 校验保存商品的参数
     *
     * @param virtualGoodsReqParam
     */
    public static void validateSaveVirtualGoodsReqParam(VirtualGoodsReqParam virtualGoodsReqParam) {
        if (StringUtils.isEmpty(virtualGoodsReqParam.getGoodsName())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品名称不能为空");
        }else if(virtualGoodsReqParam.getGoodsName().length()>GOODS_NAME_MAX_LENGTH){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品名称不能超过30个字符");
        }
        if (virtualGoodsReqParam.getGoodsPrice() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品价格不能为空");
        }
        if (StringUtils.isEmpty(virtualGoodsReqParam.getThumbnail())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "缩略图还未设置");
        }
        if (StringUtils.isEmpty(virtualGoodsReqParam.getBanner())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "轮播图未设置");
        }
        if (StringUtils.isEmpty(virtualGoodsReqParam.getTaobaoLink())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "淘宝链接还未设置");
        }
        if (StringUtils.isEmpty(virtualGoodsReqParam.getGoodsDetail())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品详情不能为空");
        }
        if(!StringUtils.isEmpty(virtualGoodsReqParam.getIntroduction()) && virtualGoodsReqParam.getIntroduction().length()>INTRODUCTION_MAX_LENGTH){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "简介不能超过30个字符");
        }
    }

    /**
     * 校验修改商品的参数
     *
     * @param goodsReqParam
     */
    public static void validateUpdateGoodsReqParam(VirtualGoodsReqParam goodsReqParam) {
        if (goodsReqParam.getId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品ID不能为空");
        }
    }
}