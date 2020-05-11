package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/4/3 6:08 下午
 */
@Data
@ApiModel("优惠券列表")
public class CouponListAllDTO {

    private String label;

    private Long value;
}
