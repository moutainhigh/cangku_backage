package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 购票历史记录
 * @program: mall
 * @author: zsj
 * @create: 2020-01-07 16:06
 **/
@Data
@Table(name = "order_history")
public class OrderHistory extends Model<OrderHistory> {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "ticket_serial_bbd",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "百邦达票号")
    @ApiModelProperty("百邦达票号")
    private String ticketSerialBbd;

    @Column(name = "create_time",type = MySqlTypeConstant.TIMESTAMP,comment = "创建时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty("创建时间")
    private Timestamp createTime;

    @Column(name = "qr_code",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "二维码")
    @ApiModelProperty("二维码")
    private String qrCode;

    @Column(name = "ticket_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "票主键")
    @ApiModelProperty("票主键")
    private Long ticketId;



















    @TableField(exist = false)
    @ApiModelProperty(value = "次数")
    private String numbers;

}
