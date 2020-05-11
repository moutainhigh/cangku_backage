package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * 酒店实体
 */
@Data
@Table(name = "order_ticket")
public class OrderTicket extends TableBase {

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
     * 父订单Id
     */
    @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "父订单Id")
    private Long parentOrderId;

    /**
     * 订单类型
     */
    @Column(name = "order_type",type = MySqlTypeConstant.TINYINT,length = 11,comment = "订单类型")
    private Byte orderType;

    /**
     * 商品编码
     */
    @Column(name = "goods_code",type = MySqlTypeConstant.VARCHAR,length = 40,comment = "商品编码")
    private String goodsCode;

    /**
     * 商品名称
     */
    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品名称")
    private String goodsName;

    /**
     * 商品分类id
     */
    @Column(name = "goods_type_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品分类id")
    private Long goodsTypeId;

    /**
     * 商品id
     */
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品id")
    private Long goodsId;

    /**
     * 商品规格id
     */
    @Column(name = "goods_extent_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商品SKU id")
    private Long goodsExtentId;

    /**
     * 商品规格名称
     */
    @Column(name = "goods_extent_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "商品SKU名称")
    private String goodsExtentName;

    /**
     * 游客姓名
     */
    @Column(name = "tourist_name",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "游客姓名")
    private String touristName;

    /**
     *联系人电话
     */
    @Column(name = "tourist_phone",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "联系人电话")
    private String touristPhone;

    /**
     * 联系人证件号
     */
    @Column(name = "tourist_card",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "联系人证件号")
    private String touristCard;

    /**
     * 票号
     */
    @Column(name = "ticket_number",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "票号")
    private String ticketNumber;

    /**
     * 票状态
     */
    @Column(name = "ticket_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "票状态")
    private Byte ticketType;

    /**
     * 锁定状态
     */
    @Column(name = "lock_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "锁定状态")
    private Byte lockType;

    /**
     * 总计需核销次数
     */
    @Column(name = "sum_verify_number",type = MySqlTypeConstant.TINYINT,length = 30,comment = "总计需核销次数")
    private Integer sumVerifyNumber;

    /**
     * 已核销次数
     */
    @Column(name = "already_verify_number",type = MySqlTypeConstant.TINYINT,length = 30,comment = "已核销次数")
    private Integer alreadyVerifyNumber;


    /**
     * 首次核销时间
     */
    @Column(name = "first_verify_date",type = MySqlTypeConstant.TIMESTAMP,length = 11,comment = "首次核销时间")
    private Date firstVerifyDate;


    /**
     * 最后核销时间
     */
    @Column(name = "last_verify_date",type = MySqlTypeConstant.TIMESTAMP,length = 11,comment = "最后核销时间")
    private Date lastVerifyDate;

    /**
     * 商家id
     */
    @Column(name = "merchant_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "商家id")
    private Long merchantId;

    /**
     * 景区id
     */
    @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区id")
    private Long scenicId;

    /**
     * 三方出票id
     */
    @Column(name = "tripartite_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "三方出票id")
    private Long tripartiteId;

    /**
     * 第三方票号
     */
    @Column(name = "ticket_no",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "第三方票号")
    private String ticketNo;

    /**
     * 第三方票务系统标识
     */
    @Column(name = "ticket_system_code",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "第三方票务系统标识")
    private String ticketSystemCode;

    /**
     * 第三方订单编号
     */
    @Column(name = "three_order_no",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "第三方订单编号")
    private String threeOrderNo;

}
