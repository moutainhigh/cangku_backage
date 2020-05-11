package cn.enn.wise.ssop.api.promotions.dto.request;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 获取分销商政策传入参数
 * @author 耿小洋
 */
@Data
@ApiModel("获取分销商政策传入参数")
public class ChannelRuleDetailParam extends TableBase {

    @ApiModelProperty("分销商id")
    @NotNull
    private Long distribuBaseId;

    @ApiModelProperty("渠道政策使用时间")
    @NotNull
    private Date ruleDay;

    @ApiModelProperty(value = "商品Id")
    @NotNull
    private Long goodsId;




}
