package cn.enn.wise.ssop.service.promotions.controller.applets;


import cn.enn.wise.ssop.api.promotions.dto.request.DrawOrderParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DrawAndUserDetailDTO;
import cn.enn.wise.ssop.api.promotions.facade.DrawAppletsFacade;
import cn.enn.wise.ssop.service.promotions.service.DrawAndUserService;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/applets/draw")
@Api(value = "小程序抽奖API" , tags = {"小程序抽奖API"})
public class DrawAppletsController implements DrawAppletsFacade {

    private static final Logger logger = LoggerFactory.getLogger(GroupOrderAppletsController.class);

    @Autowired
    private DrawAndUserService drawService;


    @PostMapping("/activity/user/details")
    @ApiOperation(value = "小程序用户查询抽奖次数", notes = "小程序用户查询抽奖次数")
    @Override
    public R<DrawAndUserDetailDTO> selectUserDrawNumber(@RequestBody UserParam user) throws Exception {
        logger.info("获取用户信息-----》[{}]", user);
        return new R(drawService.selectActivityBase(user.getId()));
    }



    @PostMapping("/activity/update/draw/Number")
    @ApiOperation(value = "小程序更新抽奖次数",notes = "小程序更新抽奖次数")
    @Override
    public R<Boolean> getAppletsUpdateDrawNumber(@RequestBody DrawOrderParam drawOrderParam) {
        logger.info("获取抽奖用户的订单信息-----》[{}]", drawOrderParam);
        Boolean selectOrderBoolean = drawService.selectActivityOrderAndUser(drawOrderParam);
        return  new R(selectOrderBoolean);
    }


}
