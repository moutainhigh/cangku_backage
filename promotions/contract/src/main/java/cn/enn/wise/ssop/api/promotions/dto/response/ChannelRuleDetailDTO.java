package cn.enn.wise.ssop.api.promotions.dto.response;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 渠道政策详细信息返回参数
 * @author 耿小洋
 */
@Data
@ApiModel("渠道政策详细信息返回参数")
public class ChannelRuleDetailDTO extends TableBase {

    @ApiModelProperty("主键")
    private Long id;

    @ApiModelProperty(value = "渠道Id")
    private Long channelId;

    @ApiModelProperty(value = "商品Id(如果是全部产品id为-1)")
    private Long goodsId;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd")
    @ApiModelProperty("渠道政策可用日期")
    private Timestamp ruleDay;

    @ApiModelProperty("返利单位  1 商品个数")
    private Byte rebateUnit;

    @ApiModelProperty("返利格式  1 百分比 2金额 ")
    private Byte rebateFormat;

    @ApiModelProperty("是否渠道商等级  1 是 2 否 ")
    private Byte isdistribuorLevel;

    @ApiModelProperty(value = "其他服务政策保存Byte数组")
    private String multipleServer;

    @ApiModelProperty(value = "基础政策信息")
    private String baseRule;

    @ApiModelProperty(value = "渠道商等级  1 初级 2 中级 3 高级(多个等级逗号隔开)")
    private String awardDistribuorLevel;

    @ApiModelProperty(value = "奖励政策信息")
    private String awardRule;


}
