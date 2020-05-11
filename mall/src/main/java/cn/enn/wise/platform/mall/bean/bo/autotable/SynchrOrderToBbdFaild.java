package cn.enn.wise.platform.mall.bean.bo.autotable;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 将订单同步到百邦达失败表
 */
@Data
@TableName("synchr_order_to_bbd_faild")
@Table(name = "synchr_order_to_bbd_faild")
public class SynchrOrderToBbdFaild {

    @TableId(value = "id", type = IdType.AUTO)
    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键")
    private Long id;

    @Column(name = "c_date",type = MySqlTypeConstant.DATETIME,comment = "同步时间")
    private LocalDateTime cDate;

    @Column(name = "order_code",type = MySqlTypeConstant.VARCHAR,length = 45,comment = "本地订单号")
    private String orderCode;

    @Column(name = "fixed",type = MySqlTypeConstant.INT,length = 10,comment = "是否已经修复",defaultValue = "0")
    private Integer fixed;

}
