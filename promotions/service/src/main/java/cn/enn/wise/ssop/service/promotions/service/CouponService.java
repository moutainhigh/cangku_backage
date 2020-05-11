package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.*;
import cn.enn.wise.ssop.api.promotions.dto.response.*;
import cn.enn.wise.ssop.service.promotions.consts.CouponEnum;
import cn.enn.wise.ssop.service.promotions.mapper.CouponMapper;
import cn.enn.wise.ssop.service.promotions.model.*;
import cn.enn.wise.uncs.base.pojo.param.QueryPage;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

/**
 * @author wangliheng
 * @date 2020/3/31 4:42 下午
 */
@Service
public class CouponService extends ServiceImpl<CouponMapper, Coupon> {

    @Autowired
    private CouponCashRuleService couponCashRuleService;
    @Autowired
    private CouponFullReduceRuleService couponFullReduceRuleService;
    @Autowired
    private CouponExperienceRuleService couponExperienceRuleService;
    @Autowired
    private CouponGoodsService couponGoodsService;

    /**
     * 添加优惠券
     * @param couponSaveParam
     * @return
     */
    public Boolean saveCoupon(CouponSaveParam couponSaveParam){
        //保存电子券基本信息
        Coupon coupon = new Coupon();
        //todo 判断电子券名称

        BeanUtils.copyProperties(couponSaveParam,coupon,getNullPropertyNames(couponSaveParam));
        //todo 生成券id
        coupon.setCode(getCode(coupon.getCouponType(),""));
        save(coupon);
        //保存电子券明细信息
        if(couponSaveParam.getCouponType().equals(CouponEnum.CASH.getValue())){
            CouponCashRule couponCashRule= new CouponCashRule();
            BeanUtils.copyProperties(couponSaveParam,couponCashRule,getNullPropertyNames(couponSaveParam));
            couponCashRule.setCouponId(coupon.getId());
            //产品专用-保存电子券商品信息
            if(couponSaveParam.getGoodsSpecial() != null){
                if(couponSaveParam.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_YES.getValue())){
                    List<CouponGoodsSaveParam> couponGoodsSaveParams = couponSaveParam.getCouponGoodsSaveParams();
                    if(CollectionUtils.isNotEmpty(couponGoodsSaveParams)){
                        List<CouponGoods> couponGoodsList = Lists.newLinkedList();
                        couponGoodsSaveParams.forEach(couponGoodsSaveParam -> {
                            CouponGoods couponGoods = new CouponGoods();
                            BeanUtils.copyProperties(couponGoodsSaveParam,couponGoods,getNullPropertyNames(couponGoodsSaveParam));
                            couponGoods.setCouponId(coupon.getId());
                            couponGoods.setCouponType(coupon.getCouponType());
                            couponGoodsList.add(couponGoods);
                        });
                        couponGoodsService.saveBatch(couponGoodsList);
                    }
                }
            }
            couponCashRuleService.save(couponCashRule);
        }
        if(couponSaveParam.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
            CouponFullReduceRule couponFullReduceRule = new CouponFullReduceRule();
            BeanUtils.copyProperties(couponSaveParam,couponFullReduceRule,getNullPropertyNames(couponSaveParam));
            couponFullReduceRule.setCouponId(coupon.getId());
            //产品专用-保存电子券商品信息
            if(couponSaveParam.getGoodsSpecial() != null){
                if(couponSaveParam.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_YES.getValue())){
                    List<CouponGoodsSaveParam> couponGoodsSaveParams = couponSaveParam.getCouponGoodsSaveParams();
                    if(CollectionUtils.isNotEmpty(couponGoodsSaveParams)){
                        List<CouponGoods> couponGoodsList = Lists.newLinkedList();
                        couponGoodsSaveParams.forEach(couponGoodsSaveParam -> {
                            CouponGoods couponGoods = new CouponGoods();
                            BeanUtils.copyProperties(couponGoodsSaveParam,couponGoods,getNullPropertyNames(couponGoodsSaveParam));
                            couponGoods.setCouponId(coupon.getId());
                            couponGoods.setCouponType(coupon.getCouponType());
                            couponGoodsList.add(couponGoods);
                        });
                        couponGoodsService.saveBatch(couponGoodsList);
                    }
                }
            }
            couponFullReduceRuleService.save(couponFullReduceRule);
        }
        if(couponSaveParam.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
            CouponExperienceRule couponExperienceRule = new CouponExperienceRule();
            BeanUtils.copyProperties(couponSaveParam,couponExperienceRule,getNullPropertyNames(couponSaveParam));
            couponExperienceRule.setCouponId(coupon.getId());
            //保存电子券商品信息
            List<CouponGoodsSaveParam> couponGoodsSaveParams = couponSaveParam.getCouponGoodsSaveParams();
            if(CollectionUtils.isNotEmpty(couponGoodsSaveParams)){
                List<CouponGoods> couponGoodsList = Lists.newLinkedList();
                couponGoodsSaveParams.forEach(couponGoodsSaveParam -> {
                    CouponGoods couponGoods = new CouponGoods();
                    BeanUtils.copyProperties(couponGoodsSaveParam,couponGoods,getNullPropertyNames(couponGoods));
                    couponGoods.setCouponId(coupon.getId());
                    couponGoods.setCouponType(coupon.getCouponType());
                    couponGoodsList.add(couponGoods);
                });
                couponGoodsService.saveBatch(couponGoodsList);
            }
            couponExperienceRuleService.save(couponExperienceRule);
        }
        return true;
    }

    private String getCode(Byte type,String number){
        StringBuffer sb = new StringBuffer();
        long date =new Date().getTime();
        if(type.equals(CouponEnum.EXPERIENCE.getValue())){
            sb.append("TYQ");
        }
        if(type.equals(CouponEnum.CASH.getValue())){
            sb.append("DJQ");
        }
        if(type.equals(CouponEnum.FULL_REDUCE.getValue())){
            sb.append("MJQ");
        }
        sb.append(date);
        return sb.toString();
    }

    /**
     * 分页列表
     * @param couponListParam
     * @return
     */
    public QueryData<CouponListDTO> getCouponList(CouponListParam couponListParam){
        String code = couponListParam.getCode();
        String name = couponListParam.getName();
        Byte state =couponListParam.getState();
        Byte couponType = couponListParam.getCouponType();
        Timestamp createTimeStart = couponListParam.getCreateTimeStart();
        Timestamp createTimeEnd = couponListParam.getCreateTimeEnd();
        String operationUser = couponListParam.getOperationUser();
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(code)){
            wrapper.like(Coupon::getCode,code);
        }
        if(StringUtils.isNotBlank(name)){
            wrapper.like(Coupon::getName,name);
        }
        if(state!=null){
            wrapper.eq(Coupon::getState,state);
        }
        if(couponType!=null){
            wrapper.eq(Coupon::getCouponType,couponType);
        }
        if(createTimeStart != null && createTimeEnd != null){
            wrapper.ge(Coupon::getCreateTime,createTimeStart);
            wrapper.le(Coupon::getCreateTime,createTimeEnd);
        }
        if(StringUtils.isNotBlank(operationUser)){
            wrapper.like(Coupon::getCreatorName,operationUser).or().like(Coupon::getUpdatorName,operationUser);
        }
        wrapper.orderByDesc(Coupon::getId);
        IPage<Coupon> couponIPage = page(new QueryPage<>(couponListParam),wrapper);
        QueryData<CouponListDTO> queryData = new QueryData<>(couponIPage,this::toCouponListDTO);
        return queryData;
    }

    private CouponListDTO toCouponListDTO(Coupon coupon) {
        CouponListDTO dto = new CouponListDTO();
        BeanUtils.copyProperties(coupon,dto,getNullPropertyNames(coupon));
        return dto;
    }

    /**
     * 电子券详情
     * @param id
     * @return
     */
    public CouponDetailDTO getCouponDetail(Long id){
        CouponDetailDTO couponDetailDTO = new CouponDetailDTO();
        Coupon coupon = getById(id);
        BeanUtils.copyProperties(coupon,couponDetailDTO,getNullPropertyNames(coupon));
        //查询优惠券绑定商品信息
        List<CouponGoods> couponGoodsList = couponGoodsService.list(
                new QueryWrapper<CouponGoods>().eq("coupon_id",id));
        List<CouponGoodsDetailsDTO> couponGoodsDetailsDTOS = Lists.newArrayList();
        couponGoodsList.forEach(couponGoods -> {
            CouponGoodsDetailsDTO couponGoodsDetailsDTO = new CouponGoodsDetailsDTO();
            BeanUtils.copyProperties(couponGoods,couponGoodsDetailsDTO,getNullPropertyNames(couponGoods));
            couponGoodsDetailsDTOS.add(couponGoodsDetailsDTO);
        });
        //体验券
        if(coupon.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
            CouponExperienceRule couponExperienceRule = couponExperienceRuleService.getOne(
                    new QueryWrapper<CouponExperienceRule>().eq("coupon_id",id));
            CouponExperienceRuleDetailDTO couponExperienceRuleDetailDTO = new CouponExperienceRuleDetailDTO();
            BeanUtils.copyProperties(couponExperienceRule,couponExperienceRuleDetailDTO,getNullPropertyNames(couponExperienceRule));
            couponExperienceRuleDetailDTO.setCouponGoodsDetailsDTOS(couponGoodsDetailsDTOS);
            couponDetailDTO.setCouponExperienceRuleDetailDTO(couponExperienceRuleDetailDTO);
        }

        //满减券
        if(coupon.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
            CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(
                    new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",id));
            CouponFullReduceRuleDetailDTO couponFullReduceRuleDetailDTO = new CouponFullReduceRuleDetailDTO();
            BeanUtils.copyProperties(couponFullReduceRule,couponFullReduceRuleDetailDTO,getNullPropertyNames(couponFullReduceRule));
            couponFullReduceRuleDetailDTO.setCouponGoodsDetailsDTOS(couponGoodsDetailsDTOS);
            couponDetailDTO.setCouponFullReduceRuleDetailDTO(couponFullReduceRuleDetailDTO);
        }

        //代金券
        if(coupon.getCouponType().equals(CouponEnum.CASH.getValue())){
            CouponCashRule couponCashRule = couponCashRuleService.getOne(
                    new QueryWrapper<CouponCashRule>().eq("coupon_id",id));
            CouponCashRuleDetailDTO couponCashRuleDetailDTO = new CouponCashRuleDetailDTO();
            BeanUtils.copyProperties(couponCashRule,couponCashRuleDetailDTO,getNullPropertyNames(couponCashRule));
            couponCashRuleDetailDTO.setCouponGoodsDetailsDTOS(couponGoodsDetailsDTOS);
            couponDetailDTO.setCouponCashRuleDetailDTO(couponCashRuleDetailDTO);
        }
        return couponDetailDTO;
    }

    /**
     * 更新优惠券
     * @param couponUpdateParam
     * @return
     */
    public Boolean updateCoupon(CouponUpdateParam couponUpdateParam){
        Coupon coupon = new Coupon();
        BeanUtils.copyProperties(couponUpdateParam,coupon,getNullPropertyNames(couponUpdateParam));
        updateById(coupon);
        if(couponUpdateParam.getCouponType().equals(CouponEnum.CASH.getValue())){
            CouponCashRule couponCashRule = new CouponCashRule();
            BeanUtils.copyProperties(couponUpdateParam,couponCashRule,getNullPropertyNames(couponUpdateParam));
            CouponCashRule couponCashRuleOld = couponCashRuleService.getOne(new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()));
            if(couponCashRuleOld == null){//类型修改
                couponFullReduceRuleService.remove(new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
                couponExperienceRuleService.remove(new QueryWrapper<CouponExperienceRule>().eq("coupon_id",coupon.getId()));
            }else {
                couponCashRule.setId(couponCashRuleOld.getId());
            }
            couponCashRuleService.saveOrUpdate(couponCashRule);
        }
        if(couponUpdateParam.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
            CouponFullReduceRule couponFullReduceRule = new CouponFullReduceRule();
            BeanUtils.copyProperties(couponUpdateParam,couponFullReduceRule,getNullPropertyNames(couponUpdateParam));
            CouponFullReduceRule couponFullReduceRuleOld = couponFullReduceRuleService.getOne(new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
            if(couponFullReduceRuleOld == null){//类型修改
                couponCashRuleService.remove(new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()));
                couponExperienceRuleService.remove(new QueryWrapper<CouponExperienceRule>().eq("coupon_id",coupon.getId()));
            }else {
                couponFullReduceRule.setId(couponFullReduceRuleOld.getId());
            }
            couponFullReduceRuleService.saveOrUpdate(couponFullReduceRule);
        }
        if(couponUpdateParam.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
            CouponExperienceRule couponExperienceRule = new CouponExperienceRule();
            BeanUtils.copyProperties(couponUpdateParam,couponExperienceRule,getNullPropertyNames(couponUpdateParam));
            CouponExperienceRule couponExperienceRuleOld = couponExperienceRuleService.getOne(new QueryWrapper<CouponExperienceRule>().eq("coupon_id",coupon.getId()));
            if(couponExperienceRuleOld == null){//类型修改
                couponCashRuleService.remove(new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()));
                couponFullReduceRuleService.remove(new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
            }else {
                couponExperienceRule.setId(couponExperienceRuleOld.getId());
            }
            couponExperienceRuleService.saveOrUpdate(couponExperienceRule);
        }
        //处理电子券商品信息
        if(couponUpdateParam.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_NO.getValue())
                ||couponUpdateParam.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
            couponGoodsService.remove(new QueryWrapper<CouponGoods>().eq("coupon_id",coupon.getId()));
        }
        if(couponUpdateParam.getGoodsSpecial().equals(CouponEnum.GOODS_SPECIAL_YES.getValue())){
            couponGoodsService.remove(new QueryWrapper<CouponGoods>().eq("coupon_id",coupon.getId()));
            List<CouponGoodsSaveParam> couponGoodsSaveParams = couponUpdateParam.getCouponGoodsSaveParams();
            if(CollectionUtils.isNotEmpty(couponGoodsSaveParams)){
                List<CouponGoods> couponGoodsList = Lists.newLinkedList();
                couponGoodsSaveParams.forEach(couponGoodsSaveParam -> {
                    CouponGoods couponGoods = new CouponGoods();
                    BeanUtils.copyProperties(couponGoodsSaveParam,couponGoods,getNullPropertyNames(couponGoodsSaveParam));
                    couponGoods.setCouponId(coupon.getId());
                    couponGoods.setCouponType(coupon.getCouponType());
                    couponGoodsList.add(couponGoods);
                });
                couponGoodsService.saveBatch(couponGoodsList);
            }
        }
        return true;
    }

    /**
     * 电子券状态修改
     * @param id
     * @return
     */
    public Boolean updateState(Long id){
        Coupon coupon = getById(id);
        if(coupon.getState().equals(CouponEnum.STATE_YES.getValue())){
            coupon.setState(CouponEnum.STATE_NO.getValue());
        }else {
            coupon.setState(CouponEnum.STATE_YES.getValue());
        }
        return updateById(coupon);
    }

    /**
     * 电子券列表-全部
     * @return
     */
    public List<CouponListAllDTO> getAll(){
        List<CouponListAllDTO> couponListAllDTOS = Lists.newArrayList();
        List<Coupon> couponList = list(new QueryWrapper<Coupon>().orderByDesc("id"));
        couponList.forEach(coupon -> {
            CouponListAllDTO couponListAllDTO = new CouponListAllDTO();
            couponListAllDTO.setLabel(coupon.getName());
            couponListAllDTO.setValue(coupon.getId());
            couponListAllDTOS.add(couponListAllDTO);
        });
        return couponListAllDTOS;
    }

    /**
     * 分页列表 -优惠活动
     * @return
     */
    public QueryData<CouponListProDTO> getCouponProList(CouponListProParam couponListProParam){
        String name = couponListProParam.getName();
        Byte couponType = couponListProParam.getCouponType();
        Byte goodsSpecial = couponListProParam.getGoodsSpecial();
        Byte issend = couponListProParam.getIssend();
        Byte state = couponListProParam.getState();
        Timestamp validityStart = couponListProParam.getValidityStart();
        Timestamp validityEnd = couponListProParam.getValidityEnd();
        LambdaQueryWrapper<Coupon> wrapper = new LambdaQueryWrapper<>();
        if(StringUtils.isNotBlank(name)){
            wrapper.like(Coupon::getName,name);
        }
        if(couponType != null){
            wrapper.eq(Coupon::getCouponType,couponType);
        }
        wrapper.orderByDesc(Coupon::getId);
        IPage<Coupon> couponIPage = page(new QueryPage<>(couponListProParam),wrapper);
        QueryData<CouponListProDTO> queryData = new QueryData<>(couponIPage,this::toCouponListProDTO);
        List<CouponListProDTO> couponListProDTOS = queryData.getRecords();
        couponListProDTOS.forEach(couponListProDTO -> {
            if(couponListProDTO.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
                CouponExperienceRule couponExperienceRule = couponExperienceRuleService.getOne(new QueryWrapper<CouponExperienceRule>().eq("coupon_id",couponListProDTO.getId()));
                if(couponExperienceRule != null){
                    couponListProDTO.setIssend(couponExperienceRule.getIssend());
                    couponListProDTO.setValidityType(couponExperienceRule.getValidityType());
                    couponListProDTO.setValidityDay(couponExperienceRule.getValidityDay());
                    couponListProDTO.setStartTime(couponExperienceRule.getStartTime());
                    couponListProDTO.setEndTime(couponExperienceRule.getEndTime());
                }
            }
            if(couponListProDTO.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
                CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",couponListProDTO.getId()));
                if (couponFullReduceRule != null){
                    couponListProDTO.setGoodsSpecial(couponFullReduceRule.getGoodsSpecial());
                    couponListProDTO.setIssend(couponFullReduceRule.getIssend());
                    couponListProDTO.setValidityType(couponFullReduceRule.getValidityType());
                    couponListProDTO.setValidityDay(couponFullReduceRule.getValidityDay());
                    couponListProDTO.setStartTime(couponFullReduceRule.getStartTime());
                    couponListProDTO.setEndTime(couponFullReduceRule.getEndTime());
                }
            }
            if(couponListProDTO.getCouponType().equals(CouponEnum.CASH.getValue())){
                CouponCashRule couponCashRule = couponCashRuleService.getOne(new QueryWrapper<CouponCashRule>().eq("coupon_id",couponListProDTO.getId()));
                if(couponCashRule != null){
                    couponListProDTO.setIsrandom(couponCashRule.getIsrandom());
                    couponListProDTO.setMinPrice(couponCashRule.getMinPrice());
                    couponListProDTO.setMaxPrice(couponCashRule.getMaxPrice());
                    couponListProDTO.setGoodsSpecial(couponCashRule.getGoodsSpecial());
                    couponListProDTO.setIssend(couponCashRule.getIssend());
                    couponListProDTO.setValidityType(couponCashRule.getValidityType());
                    couponListProDTO.setValidityDay(couponCashRule.getValidityDay());
                    couponListProDTO.setStartTime(couponCashRule.getStartTime());
                    couponListProDTO.setEndTime(couponCashRule.getEndTime());
                }
            }
        });
        return queryData;
    }

    private CouponListProDTO toCouponListProDTO(Coupon coupon) {
        CouponListProDTO dto = new CouponListProDTO();
        BeanUtils.copyProperties(coupon,dto,getNullPropertyNames(coupon));
        return dto;
    }

    /**
     * 根据优惠券id获取优惠券信息-订单
     * @param couponOrderParamList
     * @return
     */
    public List<CouponOrderDTO> getCouponOrder(List<CouponOrderParam> couponOrderParamList){
        List<CouponOrderDTO> couponOrderDTOList = Lists.newArrayList();
        couponOrderParamList.forEach( couponOrderParam -> {
            CouponOrderDTO couponOrderDTO = new CouponOrderDTO();
            couponOrderDTO.setUserId(couponOrderParam.getUserId());
            couponOrderDTO.setGoodsExtendId(couponOrderParam.getGoodsExtendId());
            Coupon coupon = getById(couponOrderParam.getCouponId());
            BeanUtils.copyProperties(coupon,couponOrderDTO,getNullPropertyNames(coupon));
            if(coupon.getCouponType().equals(CouponEnum.EXPERIENCE.getValue())){
                CouponExperienceRule couponExperienceRule = couponExperienceRuleService.getOne(new QueryWrapper<CouponExperienceRule>().eq("coupon_id",coupon.getId()));
                couponOrderDTO.setCouponRuleId(couponExperienceRule.getId());
                BeanUtils.copyProperties(couponExperienceRule,couponOrderDTO,getNullPropertyNames(couponExperienceRule));
            }
            if(coupon.getCouponType().equals(CouponEnum.FULL_REDUCE.getValue())){
                CouponFullReduceRule couponFullReduceRule = couponFullReduceRuleService.getOne(new QueryWrapper<CouponFullReduceRule>().eq("coupon_id",coupon.getId()));
                couponOrderDTO.setCouponRuleId(couponFullReduceRule.getId());
                BeanUtils.copyProperties(couponFullReduceRule,couponOrderDTO,getNullPropertyNames(couponFullReduceRule));
            }
            if(coupon.getCouponType().equals(CouponEnum.CASH.getValue())){
                CouponCashRule couponCashRule = couponCashRuleService.getOne(new QueryWrapper<CouponCashRule>().eq("coupon_id",coupon.getId()));
                couponOrderDTO.setCouponRuleId(couponCashRule.getId());
                BeanUtils.copyProperties(couponCashRule,couponOrderDTO,getNullPropertyNames(couponCashRule));
            }
            couponOrderDTO.setCouponId(coupon.getId());
            // TODO: 2020/4/18 判断优惠券是否可用-王立恒 
            couponOrderDTO.setCanUse(new Byte("1"));
            couponOrderDTOList.add(couponOrderDTO);
        });
        return couponOrderDTOList;
    }

}
