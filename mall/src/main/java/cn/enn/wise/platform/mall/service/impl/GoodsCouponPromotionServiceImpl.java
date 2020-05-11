package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.AddGoodsCouponPromotionParam;
import cn.enn.wise.platform.mall.bean.param.PromotionInvalidParam;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.GoodsCouponPromotionMapper;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class GoodsCouponPromotionServiceImpl extends ServiceImpl<GoodsCouponPromotionMapper, GoodsCouponPromotionBo> implements GoodsCouponPromotionService {

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private GoodsService goodsService;

    @Autowired
    private PromotionAndGoodsService promotionAndGoodsService;

    @Autowired
    private GoodsCouponRuleService goodsCouponRuleService;

    @Autowired
    private GoodsCouponService goodsCouponService;



    @Autowired
    private GoodsExtendService goodsExtendService;

    @Autowired
    private GoodsProjectService goodsProjectService;



    @Override
    public ResponseEntity<GoodsCouponPromotionVo> updateCouponPromotion(AddGoodsCouponPromotionParam param) throws ParseException {
        GoodsCouponPromotionBo goodsCouponPromotionBo =  this.getById(param.getId());

        BeanUtils.copyProperties(param,goodsCouponPromotionBo);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsCouponPromotionBo.setStartTime(new Timestamp(sdf.parse(param.getStartTime()).getTime()));
        goodsCouponPromotionBo.setEndTime(new Timestamp(sdf.parse(param.getEndTime()).getTime()));

        if(this.updateById(goodsCouponPromotionBo)){
            // 删除商品
            QueryWrapper<PromotionAndGoodsBo> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("promotion_id",param.getId());
            promotionAndGoodsService.remove(queryWrapper);

            QueryWrapper<GoodsCouponRuleBo> goodsCouponRuleBoQueryWrapper = new QueryWrapper<>();
            goodsCouponRuleBoQueryWrapper.eq("promotion_id",param.getId());
            goodsCouponRuleService.remove(goodsCouponRuleBoQueryWrapper);
            // 删除规则

            // 添加商品 和 规则
            addGoodsAndRule(param, goodsCouponPromotionBo);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",goodsCouponPromotionBo);
        }
        else{
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"失败");
        }
    }

    @Override
    public ResponseEntity<GoodsCouponPromotionBo> saveGoodsCouponPromotion(AddGoodsCouponPromotionParam param) {
        GoodsCouponPromotionBo goodsCouponPromotionBo = new GoodsCouponPromotionBo();
        BeanUtils.copyProperties(param, goodsCouponPromotionBo);
        if (!this.save(goodsCouponPromotionBo)) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR, "保存失败");
        }
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", goodsCouponPromotionBo);
    }

    @Override
    public ResponseEntity<GoodsCouponPromotionVo> getCouponPromotionDetail(Long id) {
        GoodsCouponPromotionBo goodsCouponPromotionBo = this.getById(id);
        if (goodsCouponPromotionBo == null) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "活动不存在");
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "活动详情", goodsCouponPromotionBo);
    }

    @Override
    public ResponseEntity<GoodsCouponPromotionVo> addPromotion(AddGoodsCouponPromotionParam param) throws ParseException {
        // 验证不重复添加
        QueryWrapper<GoodsCouponPromotionBo> goodsCouponPromotionBoQueryWrapper = new QueryWrapper<>();
        goodsCouponPromotionBoQueryWrapper.eq("name",param.getName());
        List<GoodsCouponPromotionBo> goodsCouponPromotionBoList = this.list(goodsCouponPromotionBoQueryWrapper);
        if(goodsCouponPromotionBoList.size()>0){
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"活动已存在！");
        }
        // 添加活动
        GoodsCouponPromotionBo goodsCouponPromotionBo = getGoodsCouponPromotionBo(param);
        if (this.save(goodsCouponPromotionBo)) {
            addGoodsAndRule(param, goodsCouponPromotionBo);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE, "成功", goodsCouponPromotionBo);
        }
        return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "失败");
    }

    private void addGoodsAndRule(AddGoodsCouponPromotionParam param, GoodsCouponPromotionBo goodsCouponPromotionBo) {
        // 添加商品
        List<PromotionAndGoodsBo> list = new ArrayList<>();
        param.getGoodsList().forEach(x -> {
            PromotionAndGoodsBo promotionAndGoodsBo = new PromotionAndGoodsBo();
            promotionAndGoodsBo.setGoodsId(x.longValue());
            promotionAndGoodsBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
            promotionAndGoodsBo.setCreateUserId(-1L);
            promotionAndGoodsBo.setPromotionId(goodsCouponPromotionBo.getId());
            promotionAndGoodsBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            promotionAndGoodsBo.setUpdateUserId(-1L);
            list.add(promotionAndGoodsBo);
        });
        promotionAndGoodsService.saveBatch(list);

        // 添加规则
        List<GoodsCouponRuleBo> goodsCouponRuleBoList  = new ArrayList<>();
        param.getCouponList().forEach(x->{
            GoodsCouponRuleBo goodsCouponRuleBo = new GoodsCouponRuleBo();
            BeanUtils.copyProperties(x,goodsCouponRuleBo);
            goodsCouponRuleBo.setPromotionId(goodsCouponPromotionBo.getId());
            goodsCouponRuleBo.setGoodsCouponId(x.getCouponId());

            goodsCouponRuleBo.setIsProjectUse(Integer.parseInt(x.getIsProjectUse()));
            goodsCouponRuleBo.setIsOverlay(1);
            goodsCouponRuleBo.setIsSend(Integer.parseInt(x.getIsSend()));
            goodsCouponRuleBo.setIsShare(1);
            goodsCouponRuleBo.setMinUse(x.getMinUse());
            goodsCouponRuleBo.setProjectId(x.getProjectId());
            goodsCouponRuleBo.setPromotionGetLimit(x.getPromotionGetLimit());
            goodsCouponRuleBo.setPromotionGetType(Byte.parseByte(x.getPromotionGetType()));
            goodsCouponRuleBo.setIsProjectUse(Integer.parseInt(x.getIsProjectUse()));
            goodsCouponRuleBo.setUseRule(Integer.parseInt(x.getUseRule()));
            goodsCouponRuleBo.setDescription(x.getDescription());
            goodsCouponRuleBo.setTag(x.getDescription());


            goodsCouponRuleBo.setCreateTime(new Timestamp(System.currentTimeMillis()) );
            goodsCouponRuleBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
            goodsCouponRuleBo.setCreateUserId(-1L);
            goodsCouponRuleBo.setUpdateUserId(-1L);
            goodsCouponRuleBoList.add(goodsCouponRuleBo);
        });
        goodsCouponRuleService.saveBatch(goodsCouponRuleBoList);
    }

    @Override
    public ResponseEntity<GoodsCouponPromotionVo> getPromotionDetailById(String id) {
        GoodsCouponPromotionBo goodsCouponPromotionBo = this.getById(Long.valueOf(id));
        if (goodsCouponPromotionBo == null) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR, "活动不存在");
        }
        GoodsCouponPromotionVo goodsCouponPromotionVo = new GoodsCouponPromotionVo();
        BeanUtils.copyProperties(goodsCouponPromotionBo, goodsCouponPromotionVo);


        QueryWrapper<PromotionAndGoodsBo> wrapper = new QueryWrapper<>();
        wrapper.eq("promotion_id", id);
        List<PromotionAndGoodsBo> goodsList = promotionAndGoodsService.list(wrapper);
        List<PromotionAndGoodsVo> goodsVoList = new ArrayList<>();
        goodsList.forEach(x -> {
            PromotionAndGoodsVo goodsVo = new PromotionAndGoodsVo();
            BeanUtils.copyProperties(x, goodsVo);
            Goods good = goodsService.getById(x.getGoodsId());
            GoodsProject project = goodsProjectService.getById(good.getProjectId());
            goodsVo.setBasePrice(good.getBasePrice());
            goodsVo.setGoodsName(good.getGoodsName());
            goodsVo.setProjectName(project.getName());
            goodsVo.setRetailPrice(good.getRetailPrice());
            goodsVo.setGoodsStatus(good.getGoodsStatus().toString());
            goodsVo.setGoodsCode(good.getGoodsCode());
            goodsVoList.add(goodsVo);
        });

        QueryWrapper<GoodsCouponRuleBo> ruleBoQueryWrapper = new QueryWrapper<>();
        ruleBoQueryWrapper.eq("promotion_id", id);
        List<GoodsCouponRuleBo> goodsCouponRuleBoList = goodsCouponRuleService.list(ruleBoQueryWrapper);
        List<GoodsCouponRuleVo> goodsCouponRuleVoList = new ArrayList<>();
        goodsCouponRuleBoList.forEach(x -> {
            GoodsCouponRuleVo goodsCouponRuleVo = new GoodsCouponRuleVo();
            BeanUtils.copyProperties(x, goodsCouponRuleVo);
            goodsCouponRuleVo.setIsProjectUse(x.getIsProjectUse().toString());
            goodsCouponRuleVo.setIsSend(x.getIsSend().toString());
            goodsCouponRuleVo.setIsShare(x.getIsShare().toString());
            goodsCouponRuleVo.setIsOverlay(x.getIsOverlay().toString());
            goodsCouponRuleVo.setUseRule(x.getUseRule().toString());
            goodsCouponRuleVo.setPromotionGetType(x.getPromotionGetType().toString());
            goodsCouponRuleVo.setDescription(x.getTag());
            goodsCouponRuleVoList.add(goodsCouponRuleVo);
        });


        List<GoodsCouponVo> goodsCouponVoList = new ArrayList<>();
        goodsCouponRuleBoList.forEach(x -> {
            GoodsCouponBo goodsCouponBo = goodsCouponService.getById(x.getGoodsCouponId());
            GoodsCouponVo goodsCouponVo = new GoodsCouponVo();
            BeanUtils.copyProperties(x, goodsCouponVo);
            BeanUtils.copyProperties(goodsCouponBo, goodsCouponVo);
            goodsCouponVo.setIsProjectUse(x.getIsProjectUse().toString());
            goodsCouponVo.setIsSend(x.getIsSend().toString());
            goodsCouponVo.setIsShare(x.getIsShare().toString());
            goodsCouponVo.setIsOverlay(x.getIsOverlay().toString());
            goodsCouponVo.setUseRule(x.getUseRule().toString());
            goodsCouponVo.setPromotionGetType(x.getPromotionGetType().toString());
            goodsCouponVo.setDescription(x.getDescription());
            if(goodsCouponBo.getInitType()==GeneConstant.BYTE_2){
                goodsCouponVo.setRemainderSize(goodsCouponBo.getInitSize()-goodsCouponBo.getGetedSize());
            }
            getValidityTimes(goodsCouponVo);

            goodsCouponVoList.add(goodsCouponVo);
        });


        goodsCouponPromotionVo.setCouponList(goodsCouponVoList);
        goodsCouponPromotionVo.setGoodsList(goodsVoList);
        goodsCouponPromotionVo.setRuleList(goodsCouponRuleVoList);

        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE, "成功", goodsCouponPromotionVo);
    }

    static void getValidityTimes(GoodsCouponVo goodsCouponVo) {
        if (goodsCouponVo.getValidityType() == 1L) {
            if (goodsCouponVo.getStartTime() != null && goodsCouponVo.getValidityTime() != null) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                goodsCouponVo.setValidityTimes(sdf.format(goodsCouponVo.getStartTime()) + "-" + sdf.format(goodsCouponVo.getValidityTime()));
            }
        } else {
            goodsCouponVo.setValidityTimes("领取" + goodsCouponVo.getValidityDay() + "日内有效");
        }
    }

    /**
     * 失效
     *
     * @param param
     * @return
     */
    @Override
    public ResponseEntity invalid(PromotionInvalidParam param) {
        try {
            List<GoodsCouponPromotionBo> list= this.baseMapper.selectBatchIds(param.getIds());
            for(GoodsCouponPromotionBo item:list){
                item.setStatus(new Byte("4"));
                item.setReason(param.getReason());
                this.updateById(item);
            }
            return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,"更新成功");
        } catch (NumberFormatException e) {
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"更新失败");
        }
    }

    /**
     * 构造业务类
     * @param param
     * @return
     * @throws ParseException
     */
    private GoodsCouponPromotionBo getGoodsCouponPromotionBo(AddGoodsCouponPromotionParam param) throws ParseException {
        GoodsCouponPromotionBo goodsCouponPromotionBo = new GoodsCouponPromotionBo();

        if(param.getId() == null || param.getId() == 0 ){
            goodsCouponPromotionBo.setCode(getPromotionCode());
            goodsCouponPromotionBo.setName(param.getName());
        }

        goodsCouponPromotionBo.setCompanyId(Long.parseLong(companyId));
        goodsCouponPromotionBo.setCompanyName(param.getScenicSpots());
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        goodsCouponPromotionBo.setStartTime(new Timestamp(sdf.parse(param.getStartTime()).getTime()));
        goodsCouponPromotionBo.setEndTime(new Timestamp(sdf.parse(param.getEndTime()).getTime()));
        goodsCouponPromotionBo.setManager(param.getManager());
        goodsCouponPromotionBo.setCost(param.getCost());
        goodsCouponPromotionBo.setOrgName(JSON.toJSONString(param.getOrgName()));
        // 1 通用 2 指定人群
        if (param.getPromotionCrowd() == 1) {
            goodsCouponPromotionBo.setPromotionCrowdType(GeneConstant.BYTE_1);
            goodsCouponPromotionBo.setPromotionCrowd("0");
        } else {
            goodsCouponPromotionBo.setPromotionCrowdType(GeneConstant.BYTE_2);
            goodsCouponPromotionBo.setPromotionCrowd(param.getPromotionCrowdList()[0].toString());
        }
        goodsCouponPromotionBo.setRemark(param.getRemark());
        goodsCouponPromotionBo.setStatus(new Byte(param.getStatus()[0]));
        goodsCouponPromotionBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        goodsCouponPromotionBo.setCreateUserId(-1L);
        goodsCouponPromotionBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        goodsCouponPromotionBo.setUpdateUserId(-1L);
        goodsCouponPromotionBo.setScenicSpots(param.getScenicSpots());
        goodsCouponPromotionBo.setPromotionRejectType(param.getPromotionReject());
        return goodsCouponPromotionBo;
    }

    /**
     * 创建编号
     *
     * @return
     */
    private String getPromotionCode() {
        Integer total = this.count();
        total = total+1;
        String code = String.format("%04d", total);
        return "P"+code;
    }

    private List<GoodsCouponPromotionVo> getGoodsCouponPromotionVoList(List<GoodsCouponPromotionBo> records) {
        List<GoodsCouponPromotionVo> list = new ArrayList<>();
        records.forEach(x -> {
            GoodsCouponPromotionVo goodsCouponPromotionVo = new GoodsCouponPromotionVo();
            BeanUtils.copyProperties(x, goodsCouponPromotionVo);
            list.add(goodsCouponPromotionVo);
        });
        return list;
    }
}
