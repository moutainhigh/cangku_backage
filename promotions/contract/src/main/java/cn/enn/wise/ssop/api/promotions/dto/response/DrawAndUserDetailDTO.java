package cn.enn.wise.ssop.api.promotions.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author 康康
 * 奖品类型参数
 */
@Data
@ApiModel("抽奖次数和用户信息返回参数")
public class DrawAndUserDetailDTO {



    /**
     * 活动名称
     */
    @ApiModelProperty("活动名称")
    private String activityName;
    /**
     * 活动id
     */
    @ApiModelProperty("活动id")
    private Long activityId;

    /**
     * 用户id
     */
    @ApiModelProperty("用户id")
    private Long userId;

    /**
     * 总的抽奖次数
     */
    @ApiModelProperty("总抽奖次数")
    private Integer drawAllSize;

    /**
     * （次/天/人）剩余可用抽奖次数
     */
    @ApiModelProperty("剩余可用抽奖次数")
    private Integer drawSize;

    /**
     * （次/天/人）当天已抽奖次数
     */
    @ApiModelProperty("已经抽奖次数")
    private Integer drawUsed;


    /**
     * 每人每天活动规则可以抽奖的次数
     */
    @ApiModelProperty("人/天抽奖次数")
    private Integer ruleDrawSize;

    /**
     * 当天剩余可以抽奖的次数      ruleDrawSize-drawUsed
     */
    @ApiModelProperty("当天剩余抽奖次数")
    private Integer dayDrawSize;


    /**
     * 剩余多少时间结束
     */
    @ApiModelProperty("结束时间（有效时间）")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy/MM/dd HH:mm:ss")
    private Timestamp endTime;

    //没抽奖 所以 有三次
    //抽奖了 但是剩余 三次


}
