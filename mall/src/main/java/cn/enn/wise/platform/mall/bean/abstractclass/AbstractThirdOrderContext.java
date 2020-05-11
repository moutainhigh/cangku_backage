package cn.enn.wise.platform.mall.bean.abstractclass;

import cn.enn.wise.platform.mall.bean.vo.ThirdOrderContext;
import cn.enn.wise.platform.mall.bean.vo.User;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 三方订单服务抽象类
 *
 * @author baijie
 * @date 2020-02-13
 */
@Data
public class AbstractThirdOrderContext implements ThirdOrderContext {

    @ApiModelProperty("订单类型 7 楠溪江订单")
    private Integer orderType;

    @ApiModelProperty(value = "用户微信昵称")
    private String userWechatName;

    @ApiModelProperty(value = "景点Id")
    private Long scenicId;

    @ApiModelProperty(value = "买票数量")
    private Integer amount;

    @ApiModelProperty(value = "商品拓展Id")
    private Long goodsId;

    @ApiModelProperty(value = "游玩日期 如2020-01-01")
    private String playTime;

    @ApiModelProperty(value = "openId,小程序用户标识openId")
    private String openId;

    @ApiModelProperty(value = "用户信息")
    private User user;

    @ApiModelProperty(value = "ip")
    private String ip;

    @ApiModelProperty(value = "分销商手机号")
    private String driverMobile;

    @ApiModelProperty("小程序服务通知formId")
    private String formId;

    @ApiModelProperty("小程序服务通知路径")
    private String path;
}
