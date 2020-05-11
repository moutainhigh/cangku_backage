package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.param.ShipParam;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.ShipVo;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.ShipService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiaby
 */
@RestController
@RequestMapping("/ship")
@Api(value = "后台管理船舶api", tags = {"后台管理船舶api"})
public class ShipController extends BaseController {

    @Autowired
    private ShipService shipService;

    /**
     *根据条件查询船舶列表
     * @param shipPageQry
     * @param token
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询船舶列表", notes = "根据条件查询船舶列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "shipType", value = "类型 1-客运", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "status", value = "运营状态  1 运营中 2 停运", required = true, dataType = "Byte"),
            @ApiImplicitParam(paramType = "query", name = "organization", value = "所属机构 1 华南新绎游船", required = true, dataType = "Integer"),
            @ApiImplicitParam(paramType = "query", name = "thirdId", value = "第三方主键id", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "token", value = "在header中传验证信息", required = true, dataType = "String"),
    })
    public ResponseEntity<ResPageInfoVO<List<ShipVo>>> listGoods(@RequestBody ReqPageInfoQry<ShipParam> shipPageQry, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return shipService.listByPage(shipPageQry);
    }

    /**
     * 更新船舶照片信息
     * @param param
     * @param token
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新船舶照片信息", notes = "更新船舶照片信息")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "imgUrl", value = "缩略图路径", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "id", value = "主键", required = true, dataType = "Long"),
            @ApiImplicitParam(paramType = "query", name = "token", value = "在header中传验证信息", required = true, dataType = "String"),
    })
    public ResponseEntity updateGoods(@RequestBody ShipParam param, @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return shipService.updateImgUrlById(param,staffVo.getId());
    }
    /**
     * 根据ID查询船舶详情
     *
     * @param id
     * @return 详情
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据ID查询船舶详情", notes = "根据ID查询船舶详情")
    public ResponseEntity<ShipVo> getGoodById(@PathVariable("id") long id, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return shipService.getShipById(id);
    }

}
