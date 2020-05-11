package cn.enn.wise.platform.mall.controller.applets;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.HotelGoodsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ParamValidateUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 商品api
 *
 * @author baijie
 * @date 2019-09-20
 */
@RestController
@RequestMapping("/hotel/good")
public class HotelGoodsController extends BaseController {

    @Autowired
    private HotelGoodsService goodsService;

    @PostMapping("/save")
    public ResponseEntity saveGoods(@RequestBody HotelGoods goods) throws Exception {

        ParamValidateUtil.validateHotelGoods(goods);

        goodsService.saveGoods(goods);

        return ResponseEntity.success();

    }

    @GetMapping("/get")
    public ResponseEntity getGoodsById(String id) {

        if(StringUtils.isEmpty(id)){

            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"id不能为空");
        }

        HotelGoods byId = goodsService.getById(id);

        return ResponseEntity.success(byId);
    }

    @GetMapping("/delete/{id}")
    public ResponseEntity deleteGoods(@PathVariable("id") String id) throws Exception {

        goodsService.deleteById(id);

        return ResponseEntity.success();
    }


    @PostMapping("/update")
    public ResponseEntity updateById(@RequestBody HotelGoods goods) throws Exception {
        ParamValidateUtil.validateHotelGoods(goods);

        if(StringUtils.isEmpty(goods.getId())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品Id不能为空");
        }
        goodsService.updateGoods(goods);

        return ResponseEntity.success();
    }

    @GetMapping("/list")
    public ResponseEntity goodsList() {

        List<HotelGoods> hotelGoods = goodsService.goodsList();

        return ResponseEntity.success(hotelGoods);
    }


}
