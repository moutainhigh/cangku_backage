package cn.enn.wise.platform.mall.bean.bo;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 将订单同步到百邦达失败表
 */
@Data
@TableName("synchr_order_to_bbd_faild")
public class SynchrOrderToBbdFaild {

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private LocalDateTime cDate;

    private String orderCode;

    private Integer fixed;

}
