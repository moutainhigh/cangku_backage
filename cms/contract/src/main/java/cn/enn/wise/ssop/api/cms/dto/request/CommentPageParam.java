package cn.enn.wise.ssop.api.cms.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.web.bind.annotation.PathVariable;

import javax.validation.constraints.NotNull;

@Data
public class CommentPageParam {


    @ApiModelProperty(value = "项目id")
    @NotNull
    private Long projectId;

    @ApiModelProperty(value = "页面条数")
    private Integer pageNo;
    @ApiModelProperty(value = "页面大小")
    private Integer pageSize;
}
