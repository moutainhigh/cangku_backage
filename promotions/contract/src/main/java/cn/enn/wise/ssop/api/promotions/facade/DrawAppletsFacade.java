package cn.enn.wise.ssop.api.promotions.facade;

import cn.enn.wise.ssop.api.promotions.dto.request.DrawOrderParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserParam;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public interface DrawAppletsFacade {



    @PostMapping("/applets/draw/activity/user/details")
    @ApiOperation(value = "小程序用户查询次数", notes = "小程序用户查询次数")
    R selectUserDrawNumber(@RequestBody UserParam user)  throws Exception ;


    @PostMapping("/applets/draw/activity/update/draw/Number")
    @ApiOperation(value = "小程序订单满足更新抽奖次数",notes = "小程序订单满足更新抽奖次数")
    R getAppletsUpdateDrawNumber(@RequestBody DrawOrderParam drawOrderParam);





}
