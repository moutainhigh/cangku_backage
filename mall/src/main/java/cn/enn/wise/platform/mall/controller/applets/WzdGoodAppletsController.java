package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.param.GoodsReqParam;
import cn.enn.wise.platform.mall.bean.param.TicketInfoQueryParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsApiResVO;
import cn.enn.wise.platform.mall.bean.vo.ShipTicketInfo;
import cn.enn.wise.platform.mall.bean.vo.TicketResVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.WzdGoodsAppletsService;
import cn.enn.wise.platform.mall.util.*;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 涠洲岛小程序商品列表
 *
 * @author baijie
 * @date 2019-07-31
 */

@RestController
@RequestMapping("/good/wzd")
@Api(value = "涠洲岛小程序获取热气球票API")
public class WzdGoodAppletsController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(WzdGoodAppletsController.class);

    @Autowired
    private WzdGoodsAppletsService goodsService;

    @GetMapping("/getbyproject")
    @ApiOperation(value = "小程序根据日期查询商品列表", notes = "小程序根据时间查询商品列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "timeFrame", value = "日期 1 今天,2 明天,3 后天", paramType = "query"),
    })
    public ResponseEntity<Map<String, Object>> getGoodsListByProject(Integer timeFrame,
                                                                     Long projectId,@RequestHeader("companyId")Long companyId,
                                                                     String phone) {

        ParamValidateUtil.validateGoodsList(timeFrame);
        if(projectId == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }

        logger.info("===获取商品列表===");
        GoodsReqParam goodsReqQry = new GoodsReqParam();
        String operationTime;
        operationTime = MallUtil.getDateByType(timeFrame);

        goodsReqQry.setProjectId(projectId);
        goodsReqQry.setCompanyId(companyId);
        goodsReqQry.setOperationDate(operationTime);
        goodsReqQry.setPhone(phone);
        goodsReqQry.setGoodsId(null);
        Map<String, Object> resultMap = goodsService.getGoodsList(goodsReqQry);

        return new ResponseEntity<>(GeneConstant.INT_1, "获取列表成功", resultMap);
    }


    /**
     * 预定票
     *
     * @return
     */
    @PostMapping("/detail")
    @ApiOperation(value = "获取预订票信息", notes = "获取预订票信息")
    public ResponseEntity<TicketResVo> getGoodById(@RequestBody @ApiParam(name = "goodsReqQry") GoodsReqParam goodsReqQry,
                                                    @RequestHeader("companyId") Long companyId
                                                  ) throws Exception {
        logger.info("===开始预定门票====");
        ParamValidateUtil.validateBookingTicketByWzd(goodsReqQry);


        if(goodsReqQry.getProjectId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }
        goodsReqQry.setCompanyId(companyId);

        //设置查询运营时间
        goodsReqQry.setOperationDate(MallUtil.getDateByType(goodsReqQry.getTimeFrame()));

        TicketResVo ticketResVo = goodsService.getGoodInfoById(goodsReqQry);

        return new ResponseEntity<>(GeneConstant.INT_1, "门票信息获取成功", ticketResVo);

    }

    @GetMapping("/info/{id}")
    @ApiOperation("根据商品Id获取商品详情")
    public ResponseEntity<Goods> getGoodsInfoById(@PathVariable("id") Long id){

        return  new ResponseEntity<>(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,goodsService.getGoodsInfoById(id));

    }

    @GetMapping("/experience/type")
    @ApiOperation("获取体验类型")
    public ResponseEntity<List<GoodsApiResVO>> getExperienceType(Long projectId,String phone){

        if(projectId == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }

        List<GoodsApiResVO> experienceType = goodsService.getExperienceType(projectId,phone);
        return new ResponseEntity<>(experienceType);
    }


    @PostMapping("/experience/time")
    @ApiOperation("获取体验时间")
    public ResponseEntity<GoodsApiResVO> getExperienceTime(@RequestBody @ApiParam(name = "goodsReqQry") GoodsReqParam goodsReqQry,
                                            @RequestHeader("companyId") Long companyId){
        //参数校验
        goodsReqQry.setOperationDate(MallUtil.getDateByType(goodsReqQry.getTimeFrame()));
        goodsReqQry.setCompanyId(companyId);
        if(goodsReqQry.getIsByPeriodOperation() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"是否分时段运营不能为空");
        }
        //设置查询运营时间
        GoodsApiResVO goodById = goodsService.getExperienceTime(goodsReqQry);

        return new ResponseEntity(goodById);
    }


    @PostMapping("/group/detail")
    @ApiOperation(value = "获取拼团预订票信息 拼团", notes = "获取拼团预订票信息")
    public ResponseEntity<TicketResVo> getPackageGoodById(@RequestBody @ApiParam(name = "goodsReqQry") GoodsReqParam goodsReqQry,
                                                   @RequestHeader("companyId") Long companyId) throws Exception {
        ParamValidateUtil.validatePackageDetail(goodsReqQry);


        goodsReqQry.setCompanyId(companyId);

        //设置查询运营时间
        goodsReqQry.setOperationDate(MallUtil.getDateByType(goodsReqQry.getTimeFrame()));

        TicketResVo ticketResVo = goodsService.getGroupInfoById(goodsReqQry);

        return new ResponseEntity<>(GeneConstant.INT_1, "套装门票信息获取成功", ticketResVo);

    }

    @PostMapping("/ticket/info")
    @ApiOperation(value = "获取船票预下单信息")
    public ResponseEntity getShipTicketInfo(@RequestBody TicketInfoQueryParam ticketInfoQueryParam){

        ParamValidateUtil.validateQueryParam(ticketInfoQueryParam);

        ShipTicketInfo shipTicketInfo = goodsService.getShipTicketInfo(ticketInfoQueryParam);
        return ResponseEntity.ok(shipTicketInfo);
    }

}
