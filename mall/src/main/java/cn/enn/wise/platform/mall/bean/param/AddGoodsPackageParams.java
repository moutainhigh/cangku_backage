package cn.enn.wise.platform.mall.bean.param;

import cn.enn.wise.platform.mall.bean.vo.GoodsProjectResVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/11/19
 */
@Data
@ApiModel("套装添加")
public class AddGoodsPackageParams {
    @ApiModelProperty("商品详情")
    private GoodsReqParam goods;

    @ApiModelProperty("套装明细")
    private List<GoodsPackageItemsParams> items;

    @ApiModelProperty("商品项目路线和运营地点")
    private List<GoodsProjectParams> projects;

    @ApiModelProperty("商品项目路线和运营地点全部")
    private List<GoodsProjectResVo> goodsProjectResVos;

}
