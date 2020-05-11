package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.param.CabinParam;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.CabinService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiaby
 */
@RestController
@RequestMapping("/cabin")
@Api(value = "后台管理船舱api", tags = {"后台管理船舱api"})
public class CabinController extends BaseController {

    @Autowired
    private CabinService cabinService;

    /**
     * 更新船舱照片信息
     * @param param
     * @param token
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新船舱照片信息", notes = "更新船舱照片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "imgUrl", value = "缩略图路径", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "船舱主键id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "token", value = "在header中传验证信息", required = true, dataType = "String"),
    })
    public ResponseEntity updateGoods(@RequestBody CabinParam param, @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return cabinService.updateImgUrlById(param,staffVo.getId());
    }
}
