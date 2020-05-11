package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsCouponPromotionBo;
import cn.enn.wise.platform.mall.bean.param.AddGoodsCouponPromotionParam;
import cn.enn.wise.platform.mall.bean.param.PromotionInvalidParam;
import cn.enn.wise.platform.mall.bean.vo.GoodsCouponPromotionVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GoodsCouponPromotionService;
import cn.enn.wise.platform.mall.service.GoodsCouponService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;


/**
 * @author jiabaiye
 * @Description
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/coupon/promotion")
@Api(value = "优惠券活动", tags = {"优惠券活动"})
public class GoodsCouponPromotionController extends BaseController {

    @Autowired
    GoodsCouponPromotionService goodsCouponPromotionService;

    @Autowired
    GoodsCouponService goodsCouponService;

    @PostMapping("/save")
    @ApiOperation(value = "保存", notes = "保存")
    public ResponseEntity<GoodsCouponPromotionVo> save(@RequestBody AddGoodsCouponPromotionParam param) throws ParseException {

        if(param.getId()!=null && param.getId()!=0){
            return goodsCouponPromotionService.updateCouponPromotion(param);
        }
        return goodsCouponPromotionService.addPromotion(param);
    }

    @GetMapping("/detail")
    @ApiOperation(value = "详情", notes = "详情")
    public ResponseEntity<GoodsCouponPromotionVo> detail(String id) throws ParseException {
        return goodsCouponPromotionService.getPromotionDetailById(id);
    }


    //@Scheduled(cron = "0 */1 * * * ?")
    @PostMapping("/open")
    public void cancelOrder() {
        // 自动上架
        QueryWrapper<GoodsCouponPromotionBo> wrapper   = new QueryWrapper<>();
        wrapper.lt("start_time",new Date());
        wrapper.gt("end_time",new Date());
        wrapper.eq("status",1);
        List<GoodsCouponPromotionBo> list = goodsCouponPromotionService.list(wrapper);
        list.forEach(x->{
            x.setStatus(GeneConstant.BYTE_2);
            goodsCouponPromotionService.updateById(x);
        });

        // 自动下架
        wrapper = new QueryWrapper<>();
        wrapper.lt("end_time",new Date());
        wrapper.eq("status",2);
        list = goodsCouponPromotionService.list(wrapper);
        list.forEach(x->{
            x.setStatus(GeneConstant.BYTE_3);
            goodsCouponPromotionService.updateById(x);
        });


        // 自动下架
        QueryWrapper<GoodsCouponBo> goodsCouponBoQueryWrapper   = new QueryWrapper<>();
        goodsCouponBoQueryWrapper.lt("validity_time",new Date());
        goodsCouponBoQueryWrapper.eq("status",1);
        List<GoodsCouponBo> goodsCouponBoList = goodsCouponService.list(goodsCouponBoQueryWrapper);
        goodsCouponBoList.forEach(x->{
            x.setStatus(GeneConstant.BYTE_2);
            goodsCouponService.updateById(x);
        });
    }



    @PostMapping("/invalid")
    @ApiOperation(value = "活动失效", notes = "活动失效")
    public ResponseEntity invalid(@RequestBody PromotionInvalidParam param) {

        return goodsCouponPromotionService.invalid(param);
    }

}
