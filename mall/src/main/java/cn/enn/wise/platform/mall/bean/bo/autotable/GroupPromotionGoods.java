package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Index;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 拼团活动商品明细表
 *
 * @author baijie
 * @date 2019-09-11
 */
@Data
@Table(name = "group_promotion_goods")
public class GroupPromotionGoods {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Column(name = "group_promotion_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "外键 指向 拼团活动主键")
    @ApiModelProperty("外键指向 拼团活动主键")
    @Index
    private Long groupPromotionId;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "项目id")
    @ApiModelProperty("项目id")
    private Long projectId;

    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品id")
    @ApiModelProperty("商品id")
    private Long goodsId;

    @Column(name = "goods_price",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "原价")
    @ApiModelProperty("原价")
    private BigDecimal goodsPrice;

    @Column(name = "group_price",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "拼团价")
    @ApiModelProperty("拼团价")
    private BigDecimal groupPrice;

    @Column(name = "goods_num",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品编号")
    @ApiModelProperty("商品编号")
    private String goodsNum;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品名称")
    @ApiModelProperty("商品名称")
    private String goodsName;

    @Column(name = "retail_price",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "分销价格")
    @ApiModelProperty("分销价格")
    private BigDecimal retailPrice;

    @Column(name = "goods_cost",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "参团商品成本")
    @ApiModelProperty("参团商品成本")
    private BigDecimal goodsCost;
}
