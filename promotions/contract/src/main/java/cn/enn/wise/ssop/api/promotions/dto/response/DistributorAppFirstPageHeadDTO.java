package cn.enn.wise.ssop.api.promotions.dto.response;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/**
 * 分销商App首页上半部分返回参数
 * @author xiaoyang
 */
@Data
@ApiModel("分销商App首页上半部分返回参数")
public class DistributorAppFirstPageHeadDTO {

    @ApiModelProperty("今日分享次数")
    private Integer todayShareCount;

    @ApiModelProperty("今日关注次数")
    private Integer todayFollowCount;
    
    @ApiModelProperty("分销成功次数")
    private Integer todayDistributeSuccessCount;

    @ApiModelProperty("最新3条公告消息")
    private List<String> messageList;

    @ApiModelProperty("分销商业务范围列表")
    private List<BusinessUnit> businessUnitList;


    public static class BusinessUnit {
        @ApiModelProperty("业务范围id")
        public int id;
        @ApiModelProperty("业务范围名称")
        public String name;
    }

    public  BusinessUnit getBusinessUnit(){
        return  new BusinessUnit();
    }
}
