package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.Goods;
import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GoodsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.omg.CORBA.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Goods API
 *
 * @author caiyt
 * @since 2019-05-22
 */
@RestController
@RequestMapping("/good")
@Api(value = "后台管理商品api", tags = {"后台管理商品api"})
public class GoodsController extends BaseController {
    @Autowired
    private GoodsService goodsService;
    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    /**
     * 根据条件查询商品列表
     *
     * @param goodsPageQry 分页查询条件/数据筛选条件
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询商品列表", notes = "根据条件查询商品列表")
    public ResponseEntity<ResPageInfoVO<List<GoodsResVO>>> listGoods(@RequestBody ReqPageInfoQry<GoodsReqParam> goodsPageQry, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return goodsService.listGoods(goodsPageQry);
    }

    /**
     * 根据条件查询商品列表
     *
     * @param goodsPageQry 分页查询条件/数据筛选条件
     * @return
     */
    @RequestMapping(value = "/list/select", method = RequestMethod.POST)
    @ApiOperation(value = "根据条件查询商品列表", notes = "根据条件查询商品列表")
    public ResponseEntity<ResPageInfoVO<List<GoodsResVO>>> list(@RequestBody ReqPageInfoQry<GoodsReqParam> goodsPageQry, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        goodsPageQry.getReqObj().setFromType(2L);
        return goodsService.listGoods(goodsPageQry);
    }

    /**
     * 根据商品ID查询商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    @RequestMapping(value = "/get/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品ID查询商品详情", notes = "根据商品ID查询商品详情")
    public ResponseEntity<GoodsResVO> getGoodById(@PathVariable("id") long id, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return goodsService.getGoodById(id);
    }

    /**
     * 根据商品ID查询组合套装商品详情
     *
     * @param id 商品ID
     * @return 组合套装商品详情
     */
    @RequestMapping(value = "/getPackage/{id}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品ID查询组合套装商品详情", notes = "根据商品ID查询组合套装商品详情")
    public ResponseEntity<AddGoodsPackageParams> getGoodPackageById(@PathVariable("id") long id, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return goodsService.getGoodPackageById(id);
    }

    /**
     * 更新商品信息
     *
     * @param goodsReqParam 商品数据
     * @return 商品详情
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ApiOperation(value = "更新商品信息", notes = "更新商品信息")
    public ResponseEntity updateGoods(@RequestBody GoodsReqParam goodsReqParam, @RequestHeader(value = "token") String token,@RequestHeader(value = "companyId") Long companyId) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        goodsReqParam.setCompanyId(companyId);
        return goodsService.updateGoods(goodsReqParam, staffVo,token);
    }

    /**
     * 更新商品价格--楠溪江门票更新价格回调
     *
     * @param goodsReqParam 商品数据
     * @return 商品详情
     */
    @RequestMapping(value = "/updateprice", method = RequestMethod.POST)
    @ApiOperation(value = "更新商品价格", notes = "更新商品价格")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", name = "productId", value = "门票id", required = true, dataType = "String"),
            @ApiImplicitParam(paramType = "query", name = "price", value = "门票价格", required = true, dataType = "String"),
    })
    public ResponseEntity updateGoodsPrice(@RequestBody Goods goodstmp) {


        return goodsService.updateGoodsPrice(goodstmp);
    }


    /**
     * 判断商品名称是否已经存在
     *
     * @param goodsName 商品名称
     * @return true 商品已存在 false 商品名称不存在
     */
    @RequestMapping(value = "/exist/{goodsName}", method = RequestMethod.GET)
    @ApiOperation(value = "判断商品名称是否已经存在", notes = "true-已存在 false-不存在")
    public ResponseEntity<Boolean> isGoodsNameExist(@PathVariable("goodsName") String goodsName, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return new ResponseEntity<>(goodsService.isGoodsNameExist(goodsName));
    }

    /**
     * 保存商品信息
     * @param goodsReqParam 商品数据
     * @return 商品详情
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存商品信息", notes = "保存商品信息")
    public ResponseEntity saveGoods(@RequestBody GoodsReqParam goodsReqParam, @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return goodsService.saveGoods(goodsReqParam, staffVo);
    }

    /**
     * 保存商品（套装）信息
     * @param goodsReqParam 商品数据
     * @return 商品详情
     */
    @RequestMapping(value = "/package/save", method = RequestMethod.POST)
    @ApiOperation(value = "保存商品信息", notes = "保存商品信息")
    public ResponseEntity savePackage(@RequestBody AddGoodsPackageParams goodsReqParam, @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        if(goodsReqParam.getGoods().getGoodsId()!=null && goodsReqParam.getGoods().getGoodsId()!=0){
            return goodsService.updatePackage(goodsReqParam, staffVo);
        }
        return goodsService.savePackage(goodsReqParam, staffVo);
    }

    /**
     * 批量上下架商品
     *
     * @param goodsIds 商品ID集合
     * @param status   修改后的状态
     * @return 商品详情
     */
    @RequestMapping(value = "/batch/update/{status}", method = RequestMethod.POST)
    @ApiOperation(value = "批量上下架商品", notes = "1-上架 2-下架")
    public ResponseEntity batchSwitchGoodsStatus(@RequestBody List<Long> goodsIds,
                                                 @PathVariable("status") byte status,
                                                 @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);

        //商品上下架时清除缓存
        remoteServiceUtil.deleteKey(RemoteServiceUtil.TAG_REDIS_KEY);
        remoteServiceUtil.deleteKey(RemoteServiceUtil.PROJECT_REDIS_KEY);

        return goodsService.batchSwitchGoodsStatus(goodsIds, status, staffVo);
    }


    /**
     * 批量删除商品（逻辑删除）
     *
     * @param goodsIds
     * @return
     */
    @RequestMapping(value = "/batch/delete", method = RequestMethod.POST)
    @ApiOperation(value = "批量删除商品", notes = "逻辑删除")
    public ResponseEntity batchDeleteGoods(@RequestBody List<Long> goodsIds,
                                           @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return goodsService.batchDeleteGoods(goodsIds, staffVo);
    }

    /**
     * 设置时段运营
     *
     * @param goodsExtendReqParam 商品时间段数据
     * @return
     */
    @RequestMapping(value = "/extend/update", method = RequestMethod.POST)
    @ApiOperation(value = "设置时段运营", notes = "设置时段运营")
    public ResponseEntity goodsExtendOperation(@RequestBody GoodsExtendReqParam goodsExtendReqParam, @RequestHeader(value = "token") String token) {
        SystemStaffVo staffVo = this.getUserByToken(token);
        return goodsService.goodsExtendOperation(goodsExtendReqParam.getGoodsId(), goodsExtendReqParam.getGoodsExtendReqParamList(), staffVo);
    }


    /**
     * 根据商品ID查询商品的运营时段信息
     *
     * @param goodsId
     * @return
     */
    @RequestMapping(value = "/extend/get/{goodsId}", method = RequestMethod.GET)
    @ApiOperation(value = "根据商品ID查询商品的运营时段信息", notes = "根据商品ID查询商品的运营时段信息")
    public ResponseEntity<List<GoodsExtendOperationResVo>> getGoodsExtendOperationInfo(@PathVariable("goodsId") long goodsId, @RequestHeader(value = "token") String token) {
        this.getUserByToken(token);
        return goodsService.getGoodsExtendOperationInfo(goodsId);
    }

    @GetMapping("/getbyproject/{id}")
    @ApiOperation(value = "根据项目查询所有商品",notes ="根据项目查询所有商品")
    public ResponseEntity<List<Goods>> getGoodsByProject(@PathVariable("id") Long id){

         return  new ResponseEntity<>(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,goodsService.getGoodsByGoodsProject(id));
    }

    @GetMapping("/periodidlist")
    @ApiOperation(value = "根据商品id和运营时间查看所有的时段")
    public ResponseEntity<Map<String,Object>> getGoodsPeriodIdByGoodsIdAndOperationDate(
            String date,
            Long goodsId,
            Integer servicePlaceId){
        if(StringUtils.isEmpty(date) || goodsId==null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数错误");
        }

        Map<String, Object> goodsPeriodIdByGoodsIdAndOperationDate = goodsService.getGoodsPeriodIdByGoodsIdAndOperationDate(goodsId, date, servicePlaceId);


        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS,goodsPeriodIdByGoodsIdAndOperationDate);
    }


    @PostMapping(value = "/project/line/service")
    @ApiOperation(value = "根据项目获取服务路线和服务场地")
    public ResponseEntity<List<GoodsProjectResVo>> getServicePlaceAndLineByIds(@RequestBody Long[] ids){
        List<GoodsProjectResVo> list = new ArrayList<>();
        for(Long projectId :ids) {
            GoodsProjectResVo placeAndLineById = goodsService.getServicePlaceAndLineById(projectId);
            list.add(placeAndLineById);
        }
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,list);
    }

    @ApiOperation("根据项目Id获取商品和服务场地")
    @GetMapping("/project/serviceplace/{projectId}")
    public ResponseEntity getGoodsAndServicePlaceIdByProject(@PathVariable("projectId")Long projectId){

        Map<String, Object> value = goodsService.getGoodsAndServicePlaceIdByProject(projectId);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,value);
    }


    @ApiOperation("根据项目Id获取商品详情")
    @PostMapping("/project/goods/")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "params", name = "params", value = "项目id数字", required = true)
    }
    )
    public ResponseEntity getGoodsByProjectId(@RequestBody Long[] params){
        List<GoodsPackageItemVo> value = goodsService.getGoodsProjectId(params);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,value);

    }

    @ApiOperation("更新排序字段")
    @PostMapping("/update/orderby")
    @ApiImplicitParams({
            @ApiImplicitParam(dataType = "id", name = "id", value = "商品id", required = true),
            @ApiImplicitParam(dataType = "orderBy", name = "orderBy", value = "orderby 排序字段", required = true),
    }
    )
    public ResponseEntity updateGoodsOrderBy(Long id,Integer orderBy,@RequestHeader(value = "token") String token){
        if(id == null || orderBy == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"缺失参数");
        }
        this.getUserByToken(token);
        goodsService.updateGoodsOrderBy(id,orderBy);
        remoteServiceUtil.deleteKey(RemoteServiceUtil.TAG_REDIS_KEY);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);

    }
}
