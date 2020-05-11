package cn.enn.wise.ssop.service.order.model;

import lombok.Data;
import java.util.List;

/**
 * @author anhui257@163.com
 */
@Data
public class OrderDataBean {
    private int peopleNumBer;
    private List<OrderBean> list;
}
