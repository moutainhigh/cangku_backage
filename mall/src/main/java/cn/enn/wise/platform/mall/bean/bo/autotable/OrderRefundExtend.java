package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/12/16 10:00
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Table(name = "order_refund_extend")
public class OrderRefundExtend {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @ApiModelProperty(value = "id")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @Column(name = "order_refund_id",type = MySqlTypeConstant.INT,length = 11,comment = "退款ID")
    private Integer orderRefundId;

    @Column(name = "order_item_id",type = MySqlTypeConstant.INT,length = 11,comment = "订单项Id")
    private Integer orderItemId;

    @Column(name = "project_id",type = MySqlTypeConstant.INT,length = 11,comment = "项目id")
    private Integer projectId;

    @Column(name = "project_name",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "项目名称")
    private String projectName;

    @Column(name = "goods_id",type = MySqlTypeConstant.INT,length = 11,comment = "商品ID")
    private Integer goodsId;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "商品名称")
    private String goodsName;

    @Column(name = "refund_num",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "退款流水号")
    private String refundNum;

    @Column(name = "refund_amount",type = MySqlTypeConstant.INT,length = 11,comment = "退款数量")
    private Integer refundAmount;

    @Column(name = "refund_price",type = MySqlTypeConstant.DECIMAL,length = 12,decimalLength = 2,comment = "退款数量")
    private BigDecimal refundPrice;

}
