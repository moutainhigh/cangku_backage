package cn.enn.wise.ssop.api.cms.facade;

import cn.enn.wise.ssop.api.cms.dto.request.CommentPageParam;
import cn.enn.wise.ssop.api.cms.dto.request.ProductCommentParams;
import cn.enn.wise.ssop.api.cms.dto.response.ProductCommentDTO;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Api(value = "评论接口", tags = {"评论接口"})
@FeignClient(value = "cms-service")
public interface UserCommentFacade {
    @ApiOperation(value = "评论列表", notes = "评论列表")
    @GetMapping(value = "/user/comment/list")
    R<QueryData<List<ProductCommentDTO>>> getCommentList(@Validated @RequestBody CommentPageParam commentPageParam);

    @ApiOperation(value = "所有用户评论",notes = "所有用户评论")
    @PostMapping("/user/comment/all/list")
    R<QueryData<List<ProductCommentDTO>>> getCommentAllList(@RequestBody ProductCommentParams prodCommParam);

}
