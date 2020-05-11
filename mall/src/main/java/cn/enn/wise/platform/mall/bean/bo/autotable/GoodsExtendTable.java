package cn.enn.wise.platform.mall.bean.bo.autotable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 *商品时段信息表
 * @author jiaby
 * @date 2020/02/27
 */
@Data
@Table(name = "goods_extend")
public class GoodsExtendTable extends TableBase {

    @Column(name = "id",type = MySqlTypeConstant.BIGINT, length = 20,isKey = true,isAutoIncrement = true,comment = "主键id")
    @TableId(type = IdType.AUTO)
    @ApiModelProperty("主键")
    private Long id;

    /**
     * 商品id
     */
    @Column(name = "goods_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "商品id")
    @ApiModelProperty("商品id")
    private Long goodsId;

    @Column(name = "period_id",type = MySqlTypeConstant.BIGINT, length = 20,comment = "时段id")
    @ApiModelProperty("时段id")
    private Long periodId;

    /**
     * 商品使用时段
     */
    @Column(name = "timespan",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "商品使用时段")
    @ApiModelProperty("商品使用时段")
    private String timespan;

    /**
     * 排序字段
     */
    @Column(name = "orderby",type = MySqlTypeConstant.INT, length = 4,comment = "排序字段")
    @ApiModelProperty("排序字段")
    private Byte orderby;

    /**
     * 售价
     */
    @Column(name = "sale_price",type = MySqlTypeConstant.DECIMAL, length = 10,decimalLength = 2,comment = "售价")
    @ApiModelProperty("售价")
    private BigDecimal salePrice;

    /**
     * 每时段最大服务人数
     */
    @Column(name = "max_num",type = MySqlTypeConstant.BIGINT, length = 20,comment = "每时段最大服务人数")
    @ApiModelProperty("每时段最大服务人数")
    private Integer maxNum;

    /**
     * 持续时间 单位分钟
     */
    @Column(name = "duration",type = MySqlTypeConstant.INT, length = 6,comment = "持续时间 单位分钟")
    @ApiModelProperty("持续时间 单位分钟")
    private Integer duration;

    /**
     * 状态 1-启用 2-禁用
     */
    @Column(name = "status",type = MySqlTypeConstant.INT, length = 4,comment = " 状态 1-启用 2-禁用")
    @ApiModelProperty(" 状态 1-启用 2-禁用")
    private byte status;


    /**
     * 标签
     */
    @Column(name = "time_label",type = MySqlTypeConstant.VARCHAR, length = 50,comment = "标签")
    @ApiModelProperty("标签")
    private String timeLabel;

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



    //以下是船票字段
    /**
     * 航班日期
     * yyyy-MM-dd
     */
    @Column(name = "line_date",type = MySqlTypeConstant.DATE, comment = "航班日期")
    @ApiModelProperty("航班日期")
    private String lineDate;

    @Column(name = "line_from",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "出发地点")
    @ApiModelProperty("出发地点")
    private String lineFrom;

    @Column(name = "line_to",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "目的地")
    @ApiModelProperty("目的地")
    private String lineTo;

    @Column(name = "cabin_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "船舱名称")
    @ApiModelProperty("船舱名称")
    private String cabinName;

    @Column(name = "ticket_type",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "1 成人票 2 儿童票")
    @ApiModelProperty("1 成人票 2 儿童票")
    private String ticketType;

    @Column(name = "start_time",type = MySqlTypeConstant.TIMESTAMP, comment = "开始时间")
    @ApiModelProperty("开始时间")
    private Date startTime;

    @Column(name = "end_time",type = MySqlTypeConstant.TIMESTAMP,comment = "结束时间")
    @ApiModelProperty("结束时间")
    private Date endTime;

    @Column(name = "nick_name",type = MySqlTypeConstant.VARCHAR, length = 45,comment = "船名称")
    @ApiModelProperty("船名称")
    private String nickName;

    @Column(name = "ship_line_info",type = MySqlTypeConstant.TEXT, comment = "航班信息，json格式")
    @ApiModelProperty("航班信息，json格式")
    private String shipLineInfo;
}
