package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

/**
 * 套餐订单明细
 * 存储套餐订单单个产品信息
 *
 * @author baijie
 * @date 2020-05-03
 */
@Data
@Table(name = "order_package")
public class OrderPackage extends TableBase {

    /**
     * 主键Id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id ;

    /**
     * 订单Id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 订单号
     */
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "订单号")
    private String orderNo;


    /**
     * 商品ID
     */
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品ID")
    private Long goodsId;

    /**
     * 商品名称
     */
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "商品名称")
    private String goodsName;

    /**
     * 商品分类
     */
    @Column(name = "goods_type",type = MySqlTypeConstant.INT,length = 10,comment = "商品分类")
    private Byte goodsType;

    /**
     * 商品图片
     */
    @Column(name = "img_url",type = MySqlTypeConstant.VARCHAR,length = 500,comment = "商品图片")
    private String imgUrl;

    /**
     * sku信息
     */
    @Column(name = "sku",type = MySqlTypeConstant.TEXT,comment = "sku信息")
    private String sku;

    /**
     * 使用开始日期
     */
    @Column(name = "use_start_date",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "使用开始日期")
    private String useStartDate;

    /**
     * 使用结束日期
     */
    @Column(name = "use_end_date",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "使用结束日期")
    private String useEndDate;


    /**
     *套餐 商品名称
     */
    @Column(name = "package_goods_name",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "商品名称")
    private String packageGoodsName;

    /**
     * 商品skuname
     */
    @Column(name = "goods_sku",type = MySqlTypeConstant.TEXT,comment = "商品skuname")
    private String goodsSku;

    /**
     *商品名称
     */
    @Column(name = "ticket_name",type = MySqlTypeConstant.VARCHAR ,length = 150,comment = "商品名称")
    private String ticketName;

    /**
     *票号
     */
    @Column(name = "ticket_code",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "票号")
    private String ticketCode;

    /**
     *使用状态,状态依据各组套产品自己的流转状态
     */
    @Column(name = "ticket_status",type = MySqlTypeConstant.VARCHAR,length = 20,comment = "使用状态")
    private String ticketStatus;

}
