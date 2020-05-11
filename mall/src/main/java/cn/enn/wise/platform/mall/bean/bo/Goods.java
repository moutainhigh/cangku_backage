package cn.enn.wise.platform.mall.bean.bo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author caiyt
 * @since 2019-05-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class Goods extends Model<Goods> {
    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 所属项目
     */
    private Long projectId;

    /**
     * 资源类别
     */
    private Long resourceId;

    /**
     * 分类id
     */
    private Long categoryId;

    /**
     * 计价单位
     */
    private String salesUnit;

    private String goodsName;

    /**
     * 是否套餐 1 是 0 否
     */
    private Integer isPackage;

    /**
     * 基础价格
     */
    private BigDecimal basePrice;

    /**
     * 人数上限
     */
    private Integer maxNum;

    /**
     * 使用场地
     */
    private String servicePlace;

    /**
     * 1:热气球 2:船
     */
    private Integer goodsType;

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

    /**
     * 商品状态 1 上架 2 下架
     */
    private Byte goodsStatus;

    /**
     * 商品编号
     */
    private String goodsCode;

    /**
     * 商品使用规则
     */
    private String rules;

    /**
     * 1.2 add 2019-07-25 start
     * 服务线路
     */
    private String serviceRoute;

    /**
     *单次服务时长
     */
    private Integer singleServiceDuration;

    /**
     * 运营时长
     */
    private Integer dayOperationTime;

    /**
     * 单次服务人数
     */
    private Integer maxServiceAmount;

    /**
     *分销价格
     * 1.2 add 2019-07-25 end
     */
    private BigDecimal retailPrice;

    /**
     * 商品是否分时段售卖
     */
    private Integer isByPeriodOperation;

    /**
     * 商品状态 1 套装 2 单品
     */
    private Byte isSuit;

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


    @ApiModelProperty("排序字段")
    private Integer orderby;


    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
