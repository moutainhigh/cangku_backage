package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 套餐商品VO
 *
 * @author anhui
 * @date 2019-11-20
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ApiModel("套装商品信息VO")
public class PackageGoodAdminVO extends  PackageGoodVO {

    @ApiModelProperty("项目名称")
    private String projectName;

}
