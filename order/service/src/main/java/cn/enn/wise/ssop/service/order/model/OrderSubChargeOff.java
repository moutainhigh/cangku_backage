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
 * 子订单核销状态
 */
@Data
@Table(name="order_sub_charge_off")
public class OrderSubChargeOff extends TableBase {



    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @ApiModelProperty("关联order_charge_off表主键")
    @Column(name = "charge_off_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "关联order_charge_off表主键")
    private Long chargeOffId;


    @ApiModelProperty("关联OrderGoods表中order_id字段")
    @Column(name="order_goods_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "关联OrderGoods表中order_id字段")
    private Long orderGoodsId;


    @ApiModelProperty("核销状态，-1：未核销；1：已核销")
    @Column(name = "status",type = MySqlTypeConstant.INT,length = 11,comment = "核销状态，-1：未核销；1：已核销")
    private Integer status;

    @ApiModelProperty("商品单核销时间")
    @Column(name="charge_off_time",type = MySqlTypeConstant.DATETIME,comment = "商品单核销时间")
    private Date chargeOffTime;



}
