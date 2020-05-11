package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author lishuiquan
 * @since 2019-10-29
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class VirtualGoods extends Model<VirtualGoods> {
    /**
     * 主键
     */
    @TableId(value = "id",type = IdType.AUTO)
    private Long id;

    /**
     * 商品编号
     */
    private String goodsId;
    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 简介
     */
    private String introduction;

    /**
     * 分类
     */
    private String category;

    /**
     * 价格
     */
    private BigDecimal goodsPrice;

    /**
     * 供应商
     */
    private String supplier;

    /**
     * 库存
     */
    private String inventory;

    /**
     * 缩略图
     */
    private String thumbnail;

    /**
     * 轮播图
     */
    private String banner;

    /**
     * 淘宝链接
     */
    private String taobaoLink;

    /**
     * 已卖数量
     */
    private Integer sell;

    /**
     * 商品详情
     */
    private String goodsDetail;

    /**
     * 商品状态
     */
    private Byte goodsStatus;

    /**
     * 是否删除
     */
    private Byte isDelete;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 创建人id
     */
    private Long createUserId;

    /**
     * 创建人名称
     */
    private String createUserName;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 更新人id
     */
    private Long updateUserId;

    /**
     * 修改人名称
     */
    private String updateUserName;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
