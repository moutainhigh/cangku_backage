package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;


/**
 * 酒店实体
 */
@Data
@Table(name = "order_hotel")
public class OrderHotel  extends TableBase {

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
     * 房型
     */
    @Column(name = "home_type",type = MySqlTypeConstant.VARCHAR,length = 11,comment = "房间号")
    private String homeType;

    /**
     * 房间号
     */
    @Column(name = "home_no",type = MySqlTypeConstant.VARCHAR,length = 11,comment = "房间号")
    private String homeNo;

    /**
     * 入住时间
     */
    @Column(name = "come_date",type = MySqlTypeConstant.TIMESTAMP,length = 11,comment = "入住时间")
    private String comeDate;

    /**
     * 离店时间
     */
    @Column(name = "leave_date",type = MySqlTypeConstant.TIMESTAMP,length = 11,comment = "离店时间")
    private String leaveDate;

    /**
     * 备注
     */
    @Column(name = "remark",type = MySqlTypeConstant.VARCHAR,length = 100,comment = "备注")
    private String remark;

}
