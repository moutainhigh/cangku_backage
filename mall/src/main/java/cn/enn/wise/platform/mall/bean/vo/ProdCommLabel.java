package cn.enn.wise.platform.mall.bean.vo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 16:18
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Data
@ApiModel("评论标签")
@NoArgsConstructor
@AllArgsConstructor
public class ProdCommLabel {

    private Long id;

    private String label;
}
