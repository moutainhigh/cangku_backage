package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 *商品信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods")
public class GoodsTable extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    @Column(name = "project_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "项目id")
    @ApiModelProperty("项目id")
    private Long projectId;

    /**
     * 资源类别
     */
    @Column(name = "resource_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "资源类别")
    @ApiModelProperty("资源类别")
    private Long resourceId;

    /**
     * 分类id
     */
    @Column(name = "category_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "分类id")
    @ApiModelProperty("分类id")
    private Long categoryId;

    /**
     * 计价单位
     */
    @Column(name = "sales_unit",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "计价单位")
    @ApiModelProperty("计价单位")
    private String salesUnit;

    @Column(name = "goods_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品名")
    @ApiModelProperty("商品名")
    private String goodsName;

    /**
     * 是否套餐 1 是 0 否
     */
    @Column(name = "is_package",type = MySqlTypeConstant.INT, length = 4,comment = "是否套餐 1 是 0 否")
    @ApiModelProperty("是否套餐 1 是 0 否")
    private Integer isPackage;

    /**
     * 基础价格
     */
    @Column(name = "base_price",type = MySqlTypeConstant.DECIMAL, length = 10,decimalLength = 2,comment = "基础价格")
    @ApiModelProperty("基础价格")
    private BigDecimal basePrice;

    /**
     * 人数上限
     */
    @Column(name = "max_num",type = MySqlTypeConstant.INT, length = 11,comment = "人数上限")
    @ApiModelProperty("人数上限")
    private Integer maxNum;

    /**
     * 使用场地
     */
    @Column(name = "service_place",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "使用场地")
    @ApiModelProperty("使用场地")
    private String servicePlace;

    /**
     * 1:热气球 2:船
     */
    @Column(name = "goods_type",type = MySqlTypeConstant.INT, length = 11,comment = "1:热气球 2:船")
    @ApiModelProperty("1:热气球 2:船")
    private Integer goodsType;


    /**
     * 创建人名称
     */
    @Column(name = "create_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "创建人名称")
    @ApiModelProperty("创建人名称")
    private String createUserName;

    /**
     * 修改人名称
     */
    @Column(name = "update_user_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "修改人名称")
    @ApiModelProperty("修改人名称")
    private String updateUserName;

    /**
     * 商品状态 1 上架 2 下架
     */
    @Column(name = "goods_status",type = MySqlTypeConstant.INT, length = 4,comment = "商品状态 1 上架 2 下架")
    @ApiModelProperty("商品状态 1 上架 2 下架")
    private Byte goodsStatus;

    /**
     * 商品编号
     */
    @Column(name = "goods_code",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品编号")
    @ApiModelProperty("商品编号")
    private String goodsCode;

    /**
     * 商品使用规则
     */
    @Column(name = "rules",type = MySqlTypeConstant.TEXT,comment = "商品使用规则")
    @ApiModelProperty("商品使用规则")
    private String rules;

    /**
     * 1.2 add 2019-07-25 start
     * 服务线路
     */
    @Column(name = "service_route",type = MySqlTypeConstant.VARCHAR, length = 200,comment = "服务线路")
    @ApiModelProperty("服务线路")
    private String serviceRoute;

    /**
     *单次服务时长
     */
    @Column(name = "single_service_duration",type = MySqlTypeConstant.INT, length = 11,comment = "单次服务时长")
    @ApiModelProperty("单次服务时长")
    private Integer singleServiceDuration;

    /**
     * 运营时长
     */
    @Column(name = "day_operation_time",type = MySqlTypeConstant.INT, length = 11,comment = "单日运营时长")
    @ApiModelProperty("单日运营时长")
    private Integer dayOperationTime;

    /**
     * 单次服务人数
     */
    @Column(name = "max_service_amount",type = MySqlTypeConstant.INT, length = 11,comment = "单次服务人数")
    @ApiModelProperty("单次服务人数")
    private Integer maxServiceAmount;

    /**
     *分销价格
     * 1.2 add 2019-07-25 end
     */
    @Column(name = "retail_price",type = MySqlTypeConstant.DECIMAL, length = 10,decimalLength = 2,comment = "分销价格")
    @ApiModelProperty("项目id")
    private BigDecimal retailPrice;

    /**
     * 商品是否分时段售卖
     */
    @Column(name = "is_by_period_operation",type = MySqlTypeConstant.INT, length = 4,comment = "商品是否分时段售卖")
    @ApiModelProperty("商品是否分时段售卖")
    private Integer isByPeriodOperation;

    /**
     *  1 套装 2 单品
     */
    @Column(name = "is_suit",type = MySqlTypeConstant.INT, length = 4,defaultValue = "2", comment = "1 套装 2 单品")
    @ApiModelProperty("1 套装 2 单品")
    private Byte isSuit;

    @Column(name = "is_suits",type = MySqlTypeConstant.INT, length = 4,defaultValue = "2", comment = "1 套装 2 单品")
    @ApiModelProperty("1 套装 2 单品")
    private Byte isSuits;

    /**
     * 商品图片
     */
    @Column(name = "img",type = MySqlTypeConstant.VARCHAR, length = 1024,comment = "商品图片")
    @ApiModelProperty(value = "商品图片")
    private String img;

    /**
     * 商品描述
     */
    @Column(name = "description",type = MySqlTypeConstant.TEXT,comment = "商品描述")
    @ApiModelProperty(value = "商品描述")
    private String description;

    /**
     * 商品简介
     */
    @Column(name = "synopsis",type = MySqlTypeConstant.VARCHAR, length = 2100,comment = "商品简介")
    @ApiModelProperty(value = "商品简介")
    private String synopsis;

    /**
     * 商家Id
     */
    @Column(name = "business_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商家Id")
    @ApiModelProperty(value = "商家Id")
    private Long businessId;


    /**
     * 商品名称
     */
    @Column(name = "business_name",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商家名称")
    @ApiModelProperty(value = "商家名称")
    private String businessName;

    /**
     * 排序
     */
    @Column(name = "orderby",type = MySqlTypeConstant.INT, length = 4,comment = "排序")
    @ApiModelProperty("商品是否分时段售卖")
    private Integer orderby;

}
