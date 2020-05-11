package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.UserOfCouponRecordsBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GoodsCouponService;
import cn.enn.wise.platform.mall.service.GoodsProjectService;
import cn.enn.wise.platform.mall.service.UserOfCouponRecordsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * @author jiabaiye
 * @Description
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/coupon/")
@Api(value = "优惠券管理", tags = {"优惠券管理"})
public class GoodsCouponController extends BaseController {

    @Autowired
    GoodsCouponService goodsCouponService;

    @Autowired
    GoodsProjectService goodsProjectService;

    @Autowired
    UserOfCouponRecordsService userOfCouponRecordsService;

    @PostMapping("/list")
    @ApiOperation(value = "优惠券列表", notes = "优惠券列表")
    public ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> list(@RequestBody ReqPageInfoQry<GoodsCouponParam> param) {
        return goodsCouponService.listByPage(param);
    }

    @PostMapping("/use/list")
    @ApiOperation(value = "消费列表", notes = "消费列表")
    public ResponseEntity<ResPageInfoVO<List<UserOfCouponRecordsBo>>> useList(@RequestBody ReqPageInfoQry<CouponRecordsParams> param) {
        return userOfCouponRecordsService.listByPage(param);
    }

    @PostMapping("/lists")
    @ApiOperation(value = "优惠券列表 优惠券活动选择优惠券", notes = "优惠券列表 优惠券活动选择优惠券")
    public ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> lists(@RequestBody ReqPageInfoQry<GoodsCouponParam> param) {
        return goodsCouponService.listByPageForPromotion(param);
    }

    @PostMapping("/project/list")
    @ApiOperation(value = "项目列表 优惠券活动选择优惠券", notes = "项目列表 优惠券活动选择优惠券")
    public ResponseEntity<List<GoodsCouponProjectVo>> getProjectList() {

        List<GoodsCouponProjectVo> result = new ArrayList<>();
        QueryWrapper<GoodsProject>  wrapper = new QueryWrapper<>();
        // 备注 10L 为套装id 优惠券不包含套装项目
        wrapper.ne("id",10L);
        List<GoodsProject> projects = goodsProjectService.list(wrapper);
        projects.forEach(x ->{
            GoodsCouponProjectVo projectVo = new GoodsCouponProjectVo();
            BeanUtils.copyProperties(x,projectVo);
            result.add(projectVo);
        });
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,"成功",result);
    }

    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "保存")
    public ResponseEntity<GoodsCouponVo> save(@RequestBody AddGoodsCouponParam param) throws ParseException {
        if (param.getId() != null && param.getId() != 0) {
            goodsCouponService.updateCoupon(param);
        }
        return goodsCouponService.saveGoodsCoupon(param);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public ResponseEntity<GoodsCouponVo> detail(Long id) {
        return goodsCouponService.getCouponDetail(id);
    }

    @PostMapping("/status")
    @ApiOperation(value = "设置为有效", notes = "详情")
    public ResponseEntity<GoodsCouponVo> up(@RequestBody UpdateGoodsCouponParam param) {

        List<GoodsCouponBo> list = new ArrayList<>();
        for (Long id : param.getId()) {
            Byte status = param.getStatus();
            if (!(status == GeneConstant.BYTE_1 || status == GeneConstant.BYTE_2)) {
                return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR, "状态不可用");
            }
            GoodsCouponBo goodsCouponBo = goodsCouponService.getById(id);
            if (goodsCouponBo == null) {
                return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR, "优惠券不存在");
            }
            goodsCouponBo.setStatus(status);
            list.add(goodsCouponBo);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "设置成功", goodsCouponService.updateBatchById(list));
    }


    @GetMapping("/count/list")
    @ApiModelProperty("优惠券使用情况统计")
    public ResponseEntity<Map<String,Object>> getCouponCount(String startDate,String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice){
        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
            if(startDate.equals(endDate)){
                startDate = startDate + " 00:00:00";
                endDate = endDate + " 23:59:59";
            }
        }

        Map<String, Object> countList = goodsCouponService.getCountList(startDate, endDate, status, kind, couponNo, userId, businessName, couponPrice);

        return ResponseEntity.ok(countList);
    }

    @GetMapping("/count/item")
    @ApiModelProperty("优惠券使用情况统计")
    public ResponseEntity<PageInfo<CouponItemCountVo>> getCouponCountItemList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice){
        if(pageNum == null || pageSize == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"分页参数不正确");
        }

        if(StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)){
            if(startDate.equals(endDate)){
                startDate = startDate + " 00:00:00";
                endDate = endDate + " 23:59:59";
            }
        }


        PageInfo<CouponItemCountVo> countItemList = goodsCouponService.getCountItemList(pageNum,pageSize,startDate, endDate,status,kind,couponNo,userId,businessName,couponPrice);

        return ResponseEntity.ok(countItemList);
    }

    @PostMapping("/update/status")
    public void autoCreateGroupOrder() {
        goodsCouponService.autoCreateGroupOrder();
    }

}
