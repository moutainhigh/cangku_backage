package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.UserReviewDTO;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


/**
 * @author yangshuaiquan
 */
@FeignClient("promotions-service")
public interface ActivityFacade {

    @ApiOperation(value = "回访信息的展示", notes = "回访信息的展示")
    @GetMapping(value = "/activity/userreview/list")
    R<List<UserReviewDTO>> listUserReview(@Validated UserReviewParam userReviewParam);

    @ApiOperation(value = "回访信息的添加", notes = "回访信息的添加")
    @PostMapping(value = "/activity/userreview/save")
    R<Boolean> saveUserReview(@RequestBody UserReviewParam userReviewParam);


}
