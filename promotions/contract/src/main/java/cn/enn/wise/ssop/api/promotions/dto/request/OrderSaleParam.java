package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
//@Table(name="order_sale")
@ApiModel("订单关联活动实体类")
public class OrderSaleParam  {
    /**
     * id 主键
     */
    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 订单id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 订单编号
     */
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "订单号")
    private String orderNo;

    /**
     * 营销策略id
     */
    @Column(name = "sale_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "营销策略id")
    private Long saleId;

    /**
     * 营销策略类型
     */
    @Column(name = "sale_type",type = MySqlTypeConstant.TINYINT,length = 10,comment = "营销策略类型")
    private Byte saleType;

    /**
     * 营销策略名称
     */
    @Column(name = "sale_name",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "营销策略名称")
    private String saleName;

    /**
     * 规则id
     */
    @Column(name = "rule_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "规则id")
    private Long ruleId;

    /**
     * 规则名称
     */
    @Column(name = "rule_name",type = MySqlTypeConstant.VARCHAR,length = 30,comment = "规则名称")
    private String ruleName;

    /**
     * 父订单id
     */
    @Column(name = "parent_order_id",type = MySqlTypeConstant.BIGINT,length = 10,comment = "父订单id")
    private Long parentOrderId;

    /**
     * 附加信息
     */
    @Column(name = "extra_information",type = MySqlTypeConstant.TEXT,length = 0,comment = "附加信息")
    private String extraInformation;



}
