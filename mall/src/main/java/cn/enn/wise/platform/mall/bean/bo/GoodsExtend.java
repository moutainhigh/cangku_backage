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
import java.util.Date;

/**
 * <p>
 * 热气球时段表
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
public class GoodsExtend extends Model<GoodsExtend> {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 商品id
     */
    private Long goodsId;

    /**
     * 商品使用时段
     */
    private String timespan;

    /**
     * 排序字段
     */
    private Byte orderby;

    /**
     * 售价
     */
    private BigDecimal salePrice;

    /**
     * 每时段最大服务人数
     */
    private Integer maxNum;

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
     * 持续时间 单位分钟
     */
    private Integer duration;

    /**
     * 状态 1-启用 2-禁用
     */
    private byte status;

    /**
     * 标签
     */
    private String timeLabel;

    /**
     * 时段id
     */
    private Long periodId;



    //以下是船票字段
    /**
     * 航班日期
     * yyyy-MM-dd
     */
    private String lineDate;

    private String lineFrom;

    private String lineTo;

    private String cabinName;

    private String ticketType;

    private Date startTime;

    private Date endTime;

    private String nickName;

    private String shipLineInfo;







    public GoodsExtend() {
        super();
    }

    public GoodsExtend(Long goodsId, String timespan, Byte orderby, BigDecimal salePrice, Long periodId) {
        this.goodsId = goodsId;
        this.timespan = timespan;
        this.orderby = orderby;
        this.salePrice = salePrice;
        this.periodId = periodId;
    }

    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
