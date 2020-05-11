package cn.enn.wise.ssop.service.order.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.util.Date;
import java.util.List;


/**
 * 订单核销
 */
@Data
@Table(name="order_charge_off")
public class OrderChargeOff extends TableBase {



    @Column(name = "id",type = MySqlTypeConstant.BIGINT,length = 20,isAutoIncrement =true,isKey = true)
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;


    @Column(name = "order_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "订单id")
    @ApiModelProperty(value = "订单id,关联Orders表中order_id字段")
    private Long orderId;


    @ApiModelProperty("二维码标示")
    @Column(name = "qr_tag",type = MySqlTypeConstant.VARCHAR,length = 60,comment = "二维码标示")
    private String qrTag;


    @ApiModelProperty("二维码Base64编码值")
    @Column(name = "qr_img_base64",type = MySqlTypeConstant.TEXT,comment = "二维码Base64编码")
    private String qrImgBase64;


    @ApiModelProperty("整单核销状态，-1：未核销；0：部分核销；1：已核销")
    @Column(name="status",type = MySqlTypeConstant.INT,length = 11,comment = "整单核销状态，-1：未核销；0：部分核销；1：已核销")
    private Integer status;


    @ApiModelProperty("整单核销时间")
    @Column(name = "charge_off_time",type = MySqlTypeConstant.DATETIME,comment = "整单完成核销时间")
    private Date chargeOffTime;


    /**
     * 子订单核销状态集合，不在表中存储
     */
    @ApiModelProperty("子订单核销状态")
    @TableField(exist = false)
    private List<OrderSubChargeOff> subOrderChargeOffList;



}
