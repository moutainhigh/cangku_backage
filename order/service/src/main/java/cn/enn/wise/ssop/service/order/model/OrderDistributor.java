package cn.enn.wise.ssop.service.order.model;

import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDistributor extends TableBase {

    private Long id;

    private Long orderId;

    private String orderNo;

    private Long distributorId;

    private String distributorName;

    private BigDecimal rebate;

}
