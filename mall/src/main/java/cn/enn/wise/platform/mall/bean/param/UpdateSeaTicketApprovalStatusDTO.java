package cn.enn.wise.platform.mall.bean.param;

import lombok.Data;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/15 10:38
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
public class UpdateSeaTicketApprovalStatusDTO {

    private List<String> ticketIds;

    private Integer approvalStatus;
}
