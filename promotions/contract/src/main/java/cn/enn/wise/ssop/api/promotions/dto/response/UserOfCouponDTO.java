package cn.enn.wise.ssop.api.promotions.dto.response;

import cn.enn.wise.uncs.base.pojo.response.QueryData;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Data
@ApiModel("用户用券情况")
public class UserOfCouponDTO {

    @ApiModelProperty("列表信息")
    private QueryData<UserOfCouponsListDTO> queryData;

    @ApiModelProperty("统计信息")
    private UserOfCouponsCountDTO userOfCouponsCountDTO;

}
