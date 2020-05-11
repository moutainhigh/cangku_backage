package cn.enn.wise.platform.mall.bean.param;

import lombok.Builder;
import lombok.Data;

import java.util.Date;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/1/13 18:49
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@Builder
public class DistributeBindUserParam {

    private Integer id;

    private String distributePhone;

    private String userId;

    private Date bindTime;
}
