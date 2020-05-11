package cn.enn.wise.ssop.service.promotions.model;


import cn.enn.wise.uncs.base.pojo.TableBase;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 渠道信息表
 * @author jiaby
 */
@Data
@Table(name = "channel")
public class Channel extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "营销渠道标识id")
    @ApiModelProperty("营销渠道标识id")
    private String code;

    @Column(name = "channel_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "营销渠道名")
    @ApiModelProperty("营销渠道名")
    private String channelName;

    @ApiModelProperty(value = "景区Id")
    @Column(name = "scenic_id",type = MySqlTypeConstant.BIGINT,length = 20,comment = "景区Id")
    private Long scenicId;

    @Column(name = "scenic_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "景区名称")
    @ApiModelProperty("景区名称")
    private String scenicName;

    @Column(name = "state",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "状态 1 启用 2 关闭")
    @ApiModelProperty("状态 1 启用 2 关闭")
    private Byte state;

    @Column(name = "channel_type",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "渠道类型 1 直营 2 分销")
    @ApiModelProperty("渠道类型 1 直营 2 分销")
    private Byte channelType;

//    @ApiModelProperty(value = "营业执照正面")
//    @Column(name = "business_resource",type = MySqlTypeConstant.VARCHAR,length = 255,comment = "营业执照正面")
//    private String businessResource;

    @ApiModelProperty(value = "业务标签")
    @Column(name = "tag",type = MySqlTypeConstant.VARCHAR,length = 255,comment = "业务标签")
    private String tag;

    @ApiModelProperty(value = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3公众号注册")
    @Column(name = "register_resource",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "注册来源 -1 选择注册来源 1 APP注册 2 后台添加 3 公众号注册")
    private String registerResource;

    @ApiModelProperty(value = "app注册免审批 1 入驻资料免审 2 补充资料免审")
    @Column(name = "app_register",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "app注册免审批 1 入驻资料免审 2 补充资料免审")
    private String appRegister;

    @ApiModelProperty(value = "运营方式 1 线上运营 2 线下运营")
    @Column(name = "operation",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "运营方式 1 线上运营 2 线下运营")
    private String operation;

    @ApiModelProperty(value = "数据方式 1 线上对接 2 接口对接")
    @Column(name = "docking",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "数据方式 1 线上对接 2 接口对接")
    private String docking;

    @ApiModelProperty(value = "结算方式 1 底价结算 2 返利结算")
    @Column(name = "settlement",type = MySqlTypeConstant.VARCHAR,length = 50,comment = "结算方式 1 底价结算 2 返利结算")
    private String settlement;

    @Column(name = "sale_goods_type",type = MySqlTypeConstant.TINYINT, defaultValue = "1", comment = "指定销售产品 1 不限 2 指定")
    @ApiModelProperty("指定销售产品 1 不限 2 指定")
    private Byte saleGoodsType;

    @Column(name = "ishave_rule",type = MySqlTypeConstant.TINYINT, defaultValue = "2", comment = "是否有可用政策 1 是 2 否")
    @ApiModelProperty("是否有可用政策 1 是 2 否")
    private Byte ishaveRule;

    @Column(name = "goods_number",type = MySqlTypeConstant.INT,length = 12,comment = "商品个数")
    @ApiModelProperty("商品个数")
    private Integer goodsNumber;
}
