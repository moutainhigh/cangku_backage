package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 港口信息
 *
 * @author shizhai
 * @since 2019/12/27
 */
@Data
@ApiModel("港口信息")
@NoArgsConstructor
@AllArgsConstructor
public class PortInfo {

    @ApiModelProperty("港口名称")
    private String name;

    @ApiModelProperty("地址")
    private String address;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("经纬度")
    private String latlon;

}
