package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@ApiModel("评论标签")
@NoArgsConstructor
@AllArgsConstructor
public class ProdCommLabel {

    private Long id;

    private String label;
}
