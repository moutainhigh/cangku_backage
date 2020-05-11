package cn.enn.wise.platform.mall.bean.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * @author bj
 * @Description
 * @Date19-6-5 下午2:18
 * @Version V1.0
 **/
@Data
public class DistributeOrderVo implements Serializable {

    /**
     * 分销商Id
     */
    private String  distributorId;
    /**
     * 订单id
     */
    private String orderId;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;
    /**
     * 商品数量
     */
    private String goodsCount;
    /**
     * 商品单价
     */
    private String goodsPrice;
    /**
     * 订单价格
     */

    private String orderPrice;
    /**
     * 联系人
     */
    private String contacts;

    /**
     * 订单快照
     */
    private String snapshot;

    /**
     * 分销商角色Id
     */
    private String roleId;

}

