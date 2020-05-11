package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单评价表
 *
 * @author lishuiquan
 * @date 2020-04-02
 */
@Data
@Table(name="order_evaluate")
public class OrderEvaluate extends TableBase {

    /**
     * 主键id
     */
    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    private Long id;

    /**
     * 订单Id
     */
    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    private Long orderId;

    /**
     * 订单号
     */
    @Column(name = "order_no",type = MySqlTypeConstant.VARCHAR,length = 32,comment = "订单号")
    @ApiModelProperty(value = "订单号")
    private String orderNo;

    /**
     * 会员id
     */
    @Column(name = "member_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "会员id")
    private Long memberId;

    /**
     * 分数
     */
    @Column(name = "score",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "分数")
    private Byte score;

    /**
     * 评价描述
     */
    @Column(name = "description",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "评价描述")
    private String description;

    /**
     * 备注
     */
    @Column(name = "remark",type = MySqlTypeConstant.TEXT,comment = "备注")
    private String remark;

    /**
     * 标签
     */
    @Column(name = "label",type = MySqlTypeConstant.VARCHAR,length = 200,comment = "标签")
    private String label;

    /**
     * 评价时间
     */
    @Column(name = "evaluate_time",type = MySqlTypeConstant.DATETIME,length = 0,comment = "评价时间")
    private Date evaluateTime;
}
