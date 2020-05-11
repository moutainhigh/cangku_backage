package cn.enn.wise.ssop.api.promotions.dto.request;

import cn.enn.wise.uncs.base.pojo.TableBase;
import cn.enn.wise.uncs.base.pojo.param.QueryParam;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.sql.Timestamp;

@Data
@EqualsAndHashCode(callSuper = true)
public class ChannelParam extends QueryParam {

    @ApiModelProperty("id, 不传为新增数据")
    private Long id;

    @ApiModelProperty("营销渠道名")
    @NotNull
    private String channelName;



    @ApiModelProperty("状态 1 启用 2 关闭")
    @NotNull
    private Byte state;

    @ApiModelProperty("渠道类型 1 直营 2 分销")
    @NotNull
    private Byte channelType;

//    @ApiModelProperty(value = "营业执照正面")
//    private String businessResource;

    @ApiModelProperty(value = "业务标签")
    private String tag;

    @ApiModelProperty(value = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3公众号注册")
    private String registerResource;

    @ApiModelProperty(value = "app注册免审批 1 入驻资料免审 2 补充资料免审")
    private String appRegister;

    @ApiModelProperty(value = "运营方式 1 线上运营 2 线下运营")
    private String operation;

    @ApiModelProperty(value = "数据方式 1 线上对接 2 接口对接")
    private String docking;

    @ApiModelProperty(value = "结算方式 1 底价结算 2 返利结算")
    private String settlement;

    @ApiModelProperty("指定销售产品 1 不限 2 指定")
    private Byte saleGoodsType;

    @ApiModelProperty("是否有可用政策 1 是 2 否")
    private Byte ishaveRule;

    @ApiModelProperty("逻辑删除 1 未删除 2 已删除")
    private Byte isdelete;

    @ApiModelProperty("乐观锁")
    private Byte version;

     @ApiModelProperty("租户id")
    private Long companyId;

     @ApiModelProperty("创建人id")
    private Long createUserId;

     @ApiModelProperty("更改人id")
    private Long updateUserId;

     @ApiModelProperty("创建时间")
    private Timestamp createTime;

     @ApiModelProperty("修改时间")
    private Timestamp updateTime;


}