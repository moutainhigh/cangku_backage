package cn.enn.wise.platform.mall.bean.vo;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 活动请求参数
 *
 * @author anhui
 * @since 2019/12/23
 */
@ApiModel("航线")
@Data
public class LineTicketVo extends  LineVo {

    @ApiModelProperty("开始时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Timestamp startTime;

    @ApiModelProperty("结束时间")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm")
    private Timestamp endTime;

    @ApiModelProperty("使用时长")
    private String duration;

    @ApiModelProperty("船昵称")
    private String nickName;

    @ApiModelProperty("航线")
    private JSONObject shipLineObj;

    @ApiModelProperty("航线")
    private String shipLineInfo;

    @ApiModelProperty("价格")
    private String price;

    @ApiModelProperty("预订须知")
    private String note;

    @ApiModelProperty("是否可以售卖：1 可以 2 30分钟内即将开船不可以售卖 3 已经开船不可以售卖")
    private String status;



}
