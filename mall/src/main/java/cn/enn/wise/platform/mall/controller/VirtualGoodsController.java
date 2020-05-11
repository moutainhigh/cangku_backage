package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.param.VirtualGoodsReqParam;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.bean.vo.VirtualGoodsResVO;
import cn.enn.wise.platform.mall.service.VirtualGoodsService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * VirtualGoods API
 *
 * @author lishuiquan
 * @since 2019-10-29
 */
@RestController
@RequestMapping("/virtual")
@Api(value = "后台管理虚拟商品api", tags = {"后台管理虚拟商品api"})
public class VirtualGoodsController  extends BaseController  {

    @Autowired
    private VirtualGoodsService virtualGoodsService;

    @RequestMapping(value = "/getGoods", method = RequestMethod.GET)
    @ApiOperation(value = "根据虚拟商品ID查询商品详情", notes = "根据虚拟商品ID查询商品详情")
    public ResponseEntity<VirtualGoodsResVO> getVirtualGoodsById(@RequestParam Long id){
        VirtualGoodsResVO virtualGoodsResVO = virtualGoodsService.getVirtualGoodsById(id);
        return new ResponseEntity(virtualGoodsResVO);
    }

    @PostMapping(value = "/addGoods")
    @ApiOperation(value = "添加虚拟商品", notes = "添加虚拟商品")
    public ResponseEntity<Integer> addVirtualGoods(@RequestBody VirtualGoodsReqParam virtualGoodsReqParam, @RequestHeader(value = "token") String token){
        SystemStaffVo staffVo = this.getUserByToken(token);
        Long id = virtualGoodsService.addVirtualGood(virtualGoodsReqParam,staffVo);
        return new ResponseEntity(id);
    }

    @PostMapping("/modifyGoods")
    @ApiOperation(value = "修改虚拟商品", notes = "修改虚拟商品")
    public ResponseEntity<Integer> modifyVirtualGoods(@RequestBody VirtualGoodsReqParam virtualGoodsReqParam, @RequestHeader(value = "token") String token){
        SystemStaffVo staffVo = this.getUserByToken(token);
        Integer modifyCount = virtualGoodsService.modifyVirtualGood(virtualGoodsReqParam,staffVo);
        return new ResponseEntity<>(modifyCount);
    }

    @PostMapping("/updateGoodsStatus")
    @ApiOperation(value = "虚拟商品上下架", notes = "虚拟商品上下架")
    public ResponseEntity<Integer> updateGoodsStatus(@RequestBody VirtualGoodsReqParam virtualGoodsReqParam, @RequestHeader(value = "token") String token){
        SystemStaffVo staffVo = this.getUserByToken(token);
        Integer modifyCount = virtualGoodsService.updateGoodsStatus(virtualGoodsReqParam,staffVo);
        return new ResponseEntity<>(modifyCount);
    }

    @PostMapping("/deleteGoods")
    @ApiOperation(value = "虚拟商品删除", notes = "虚拟商品删除")
    public ResponseEntity<Integer> deleteGoods(@RequestBody VirtualGoodsReqParam virtualGoodsReqParam, @RequestHeader(value = "token") String token){
        SystemStaffVo staffVo = this.getUserByToken(token);
        Integer deleteCount = virtualGoodsService.deleteGoods(virtualGoodsReqParam,staffVo);
        return new ResponseEntity<>(deleteCount);
    }

    @RequestMapping(value = "/listGoods",method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询虚拟商品列表", notes = "根据条件查询虚拟商品列表")
    public ResponseEntity<List> listVirtualGoods(@RequestBody ReqPageInfoQry<VirtualGoodsReqParam> virtualGoodsPageQry){
        ResPageInfoVO<List<VirtualGoodsResVO>> virtualGoodsList = virtualGoodsService.getVirtualGoodsList(virtualGoodsPageQry);
        return new ResponseEntity(virtualGoodsList);
    }
}
