package cn.enn.wise.platform.mall.bean.bo.bak;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author lishuiquan
 * @since 2019-10-29
 */
@Data
//@Table(name = "virtual_goods")
public class VirtualGoodsTable  {
    /**
     * 主键
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 11,isKey = true,isAutoIncrement = true,comment = "主键")
    private Long id;

    /**
     * 商品编号
     */
    @Column(name = "goods_id",type = MySqlTypeConstant.VARCHAR,length =50,comment = "商品编号")
    private String goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length =100,comment = "商品名称")
    private String goodsName;

    /**
     * 简介
     */
    @Column(name = "introduction",type = MySqlTypeConstant.VARCHAR,length =100,comment = "简介")
    private String introduction;

    /**
     * 分类
     */
    @Column(name = "category",type = MySqlTypeConstant.VARCHAR,length =100,comment = "分类")
    private String category;

    /**
     * 价格
     */
    @Column(name = "goods_price",type = MySqlTypeConstant.DECIMAL,length = 10,decimalLength = 2,comment = "价格")
    private BigDecimal goodsPrice;

    /**
     * 供应商
     */
    @Column(name = "supplier",type = MySqlTypeConstant.VARCHAR,length =100,comment = "供应商")
    private String supplier;

    /**
     * 库存
     */
    @Column(name = "inventory",type = MySqlTypeConstant.VARCHAR,length =100,comment = "库存")
    private String inventory;

    /**
     * 缩略图
     */
    @Column(name = "thumbnail",type = MySqlTypeConstant.VARCHAR,length =500,comment = "缩略图")
    private String thumbnail;

    /**
     * 轮播图
     */
    @Column(name = "banner",type = MySqlTypeConstant.VARCHAR,length =4000,comment = "轮播图")
    private String banner;

    /**
     * 淘宝链接
     */
    @Column(name = "taobao_link",type = MySqlTypeConstant.VARCHAR,length =500,comment = "淘宝链接")
    private String taobaoLink;

    /**
     * 已卖数量
     */
    @Column(name = "sell",type = MySqlTypeConstant.INT,length =10,comment = "已卖数量")
    private Integer sell;

    /**
     * 商品详情
     */
    @Column(name = "goods_detail",type = MySqlTypeConstant.TEXT,comment = "商品详情")
    private String goodsDetail;

    /**
     * 商品状态
     */
    @Column(name = "goods_status",type = MySqlTypeConstant.INT, length = 10, defaultValue = "1",comment = "商品状态:1 上架 2 下架")
    private Byte goodsStatus;

    /**
     * 是否删除
     */
    @Column(name = "is_delete",type = MySqlTypeConstant.INT,length = 10, defaultValue = "1",comment = "商品状态:1 正常 2 删除")
    private Byte isDelete;

    /**
     * 创建时间
     */
    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    private Timestamp createTime;

    /**
     * 创建人id
     */
    @Column(name = "create_user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "创建人id")
    private Long createUserId;

    /**
     * 创建人名称
     */
    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "创建人名称")
    private String createUserName;

    /**
     * 更新时间
     */
    @Column(name = "update_time",type = MySqlTypeConstant.TIMESTAMP,comment = "更新时间")
    private Timestamp updateTime;

    /**
     * 更新人id
     */
    @Column(name = "update_user_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "更新人id")
    private Long updateUserId;

    /**
     * 修改人名称
     */
    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR,length =50,comment = "修改人名称")
    private String updateUserName;

}
