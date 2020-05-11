package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.bo.autotable.PromotionAndGoods;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.GoodsCouponPromotionService;
import cn.enn.wise.platform.mall.service.GoodsCouponRuleService;
import cn.enn.wise.platform.mall.service.GoodsCouponService;
import cn.enn.wise.platform.mall.service.UserOfCouponRecordsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.RemoteServiceUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class GoodsCouponServiceImpl extends ServiceImpl<GoodsCouponMapper, GoodsCouponBo> implements GoodsCouponService {


    @Autowired
    private OrderAppletsMapper orderAppletsMapper;

    @Autowired
    private GoodsCouponPromotionMapper goodsCouponPromotionMapper;

    @Autowired
    private UserOfCouponMapper userOfCouponMapper;

    @Autowired
    private GoodsCouponPromotionService goodsCouponPromotionService;

    @Resource
    private PromotionAndGoodsMapper promotionAndGoodsMapper;


    @Autowired
    private GoodsCouponRuleService goodsCouponRuleService;

    @Autowired
    private UserOfCouponRecordsService userOfCouponRecordsService;

    @Autowired
    private RemoteServiceUtil remoteServiceUtil;

    @Resource
    private GoodsCouponMapper goodsCouponMapper;

    @Override
    public ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> listByPage(ReqPageInfoQry<GoodsCouponParam> param) {
        GoodsCouponParam params  = param.getReqObj();
        QueryWrapper<GoodsCouponBo> wrapper = new QueryWrapper<>();

        if(params!=null){
            if(StringUtils.isNotEmpty(params.getName())){
                wrapper.like("name",params.getName());
            }
            if(params.getCouponType()!=null && params.getCouponType()!=0){
                wrapper.eq("coupon_type",params.getCouponType());
            }
            if(params.getTag() != null && params.getTag() !=0){
                wrapper.eq("tag",params.getTag());
            }
            if(params.getStatus() != null && params.getStatus() !=0){
                wrapper.eq("status",params.getStatus());
            }
            if(params.getValidityTime()!=null && 2 == params.getValidityTime().length){
//                wrapper
//                        .gt("start_time",)
//
//                        .lt("validity_time",);
                wrapper.apply(" start_time > {0}  and validity_time < {1}",params.getValidityTime()[0] , params.getValidityTime()[1]);
            }
            if("2".equals(params.getType())){
                wrapper.eq("status",1);
                wrapper.apply(" ( ( init_size > ifnull(geted_size,0) or init_type = 1 ) and status = 1 )");
            }
        }

        // mybatis 查询数据
        Page<GoodsCouponBo> pageInfo = new Page<>(param.getPageNum(),param.getPageSize());
        wrapper.orderByDesc("id");
        IPage<GoodsCouponBo> goodsCouponBoList = this.page(pageInfo,wrapper);

        // 分页
        ResPageInfoVO resPageInfoVO = new ResPageInfoVO();
        if(goodsCouponBoList == null ){
            resPageInfoVO.setPageNum(0L);
            resPageInfoVO.setPageSize(0L);
            resPageInfoVO.setTotal(0L);
            return new ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>>(resPageInfoVO);
        }
        // 分页
        resPageInfoVO.setPageNum(goodsCouponBoList.getCurrent());
        resPageInfoVO.setPageSize(goodsCouponBoList.getSize());
        resPageInfoVO.setTotal(goodsCouponBoList.getTotal());
        List<GoodsCouponVo> list =getGoodsCouponVoList(goodsCouponBoList.getRecords());

        if(list == null || list.isEmpty()){
            return new ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>>(resPageInfoVO);
        }
        resPageInfoVO.setRecords(list);
        return new ResponseEntity<>(resPageInfoVO);
    }

    @Override
    public ResponseEntity<GoodsCouponVo> saveGoodsCoupon(AddGoodsCouponParam param) throws ParseException {
        GoodsCouponBo couponBO = new GoodsCouponBo();
        BeanUtils.copyProperties(param, couponBO);
        if (param.getValidityType() == 1) {
            String startTime = param.getValidityTime()[0];
            String endTime = param.getValidityTime()[1];
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            couponBO.setStartTime(new Timestamp(sdf.parse(startTime).getTime()));
            couponBO.setValidityTime(new Timestamp(sdf.parse(endTime).getTime()));
        }

        if (param.getInitType() == 1) {
            couponBO.setInitSize(0);
        }
        if (param.getGetType() == 1) {
            couponBO.setGetLimit(0);
        }

        couponBO.setValidityDay(param.getValidityDay());
        couponBO.setCreateUserId(-1L);
        couponBO.setCreateTime(new Timestamp(System.currentTimeMillis()));
        couponBO.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        couponBO.setUpdateUserId(-1L);
        couponBO.setOrgName(JSON.toJSONString(param.getOrgList()));
        couponBO.setKind(param.getKind());

        if (!this.saveOrUpdate(couponBO)) {
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR, "失败", null);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "成功", couponBO);
    }

    private List<GoodsCouponVo> getGoodsCouponVoList(List<GoodsCouponBo> records) {

        List<GoodsCouponVo> list = new ArrayList<>();
        for(GoodsCouponBo item:records){
            GoodsCouponVo target = getGoodsCouponVo(item);
            list.add(target);
        }
        return list;
    }

    private GoodsCouponVo getGoodsCouponVo(GoodsCouponBo item) {
        GoodsCouponVo target  =new GoodsCouponVo();
        BeanUtils.copyProperties(item,target);
        target.setOrgList(JSON.parseArray(item.getOrgName(), OrgParam.class));

        if(target.getInitType() == 1 ){
            target.setInitSize(0);
        }
        if(target.getGetType() == 1){
            target.setGetLimit(0);
        }

        Integer count = getRecordsCount(item);

        target.setUsedCount(count);
        target.setGetedSize(item.getGetedSize());
        target.setIsUsed(checkIfUsed(item));
        target.setRemainderSize(target.getInitSize()-target.getGetedSize());
        GoodsCouponPromotionServiceImpl.getValidityTimes(target);
        target.setUseRule("1");
        target.setIsSend("2");
        target.setIsShare("2");
        target.setPromotionGetLimit(0);
        target.setPromotionGetType("2");
        target.setIsProjectUse("2");
        target.setProjectId(0L);

        return target;
    }

    /**
     * 获取消费记录数量
     * @param item
     * @return
     */
    private Integer getRecordsCount(GoodsCouponBo item) {
        QueryWrapper<UserOfCouponRecordsBo> wrapper = new QueryWrapper<>();
        wrapper.eq("status",11).eq("coupon_id",item.getId());
        wrapper.or();
        wrapper.eq("status",3).eq("coupon_id",item.getId());
        wrapper.or();
        wrapper.eq("status",2).eq("coupon_id",item.getId());
        wrapper.or();
        wrapper.eq("status",9).eq("coupon_id",item.getId());
        wrapper.or();
        wrapper.eq("status",10).eq("coupon_id",item.getId());
        return userOfCouponRecordsService.count(wrapper);
    }

    /**
     * 是否使用中
     * @param item
     */
    private Boolean checkIfUsed(GoodsCouponBo item) {
        //todo 需要查询使用情况 被活动引用视为使用
        QueryWrapper<GoodsCouponRuleBo> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_coupon_id", item.getId());
        return 0 != goodsCouponRuleService.count(wrapper);
    }

    @Override
    public ResponseEntity<List<CouponParam>> getNewPeopleCouponList(Long companyId,Long userId){
        if(userId==null){
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",goodsCouponPromotionMapper.getNewPeopleCoupon(companyId));
        }else{
            //新用户是没有下过订单的，老用户是下过订单的
            List<Order> order =orderAppletsMapper.getOrderByUserIdAndState(userId);
            //新人获取未领取的新人优惠券
            if(order==null||order.size()==0){
                return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",goodsCouponPromotionMapper.getNewPeopleCouponByUserId(companyId, userId));
            }
            return new ResponseEntity(GeneConstant.INT_2,"成功",null);
        }
    }

    @Override
    public ResponseEntity<GoodsCouponVo> getCouponDetail(Long id) {
        GoodsCouponBo couponBo = this.getById(id);
        if(couponBo==null){
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"优惠券不存在");
        }
        return new ResponseEntity<>(getGoodsCouponVo(couponBo));
    }

    @Override
    public ResponseEntity updateCoupon(AddGoodsCouponParam param) {
        GoodsCouponBo goodsCouponBo = this.getById(param.getId());
        if(goodsCouponBo==null){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"优惠券不存在");
        }
        BeanUtils.copyProperties(param,goodsCouponBo);
        if(!this.updateById(goodsCouponBo)){
            return  new ResponseEntity(GeneConstant.BUSINESS_ERROR,"更新失败");
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"更新成功");
    }

    @Override
    public ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> listByPageForPromotion(ReqPageInfoQry<GoodsCouponParam> param) {
        return listByPage(param);
    }

    @Override
    public ResponseEntity<List<CouponParam>> getCouponList(Long companyId, Long userId, String status){
        List<CouponParam> list = new ArrayList<>();

        //未领取优惠券列表
        if ("0".equals(status)) {
            //新用户是没有下过订单的，老用户是下过订单的
            List<Order> order =orderAppletsMapper.getOrderByUserIdAndState(userId);
            //新人获取未领取的新人优惠券
            if(order==null||order.size()==0){
                list= goodsCouponPromotionMapper.getNewPeopleCouponByUserId(companyId, userId);
            }
            List<CouponParam> couponParamList = goodsCouponPromotionMapper.selectCouponNotGeted(companyId, userId);
            list.addAll(couponParamList);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",list);
        }else if("1".equals(status)){
            List<CouponParam>  paramList = goodsCouponPromotionMapper.getCouponByStatus1(companyId, userId);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",paramList);
        }else{
            List<CouponParam>  paramList = goodsCouponPromotionMapper.getCouponByStatus(companyId, userId,status);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",paramList);
        }

    }
    @Override
    public ResponseEntity<List<CouponParam>> getCouponListV2_1(Long companyId, Long userId, String status,String phone){
        List<CouponParam> list = new ArrayList<>();
        //未领取优惠券列表
        if ("0".equals(status)) {
            //新用户是没有下过订单的，老用户是下过订单的
            List<Order> order =orderAppletsMapper.getOrderByUserIdAndState(userId);
            //新人获取未领取的新人优惠券
            if(order==null||order.size()==0){
                list= goodsCouponPromotionMapper.getNewPeopleCouponByUserId(companyId, userId);
            }
            ResponseEntity responseEntity =remoteServiceUtil.getCheckUserResult(phone,companyId);
            if(responseEntity.getResult()==1){
                List<CouponParam> couponParamList = goodsCouponPromotionMapper.getDistributorCouponByUserId(companyId, userId, phone);
                list.addAll(couponParamList);
            }
            List<CouponParam> couponParamList = goodsCouponPromotionMapper.selectCouponNotGeted(companyId, userId);
            list.addAll(couponParamList);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",list);
        }else if("1".equals(status)){
            List<CouponParam>  paramList = goodsCouponPromotionMapper.getCouponByStatus1(companyId, userId);
            if(phone!=null&&!"".equals(phone)){
                List<CouponParam> listPhone = goodsCouponPromotionMapper.getCouponByStatusAndPhone(companyId, userId,status,phone);
                paramList.addAll(listPhone);
            }
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",paramList);
        }else{
            List<CouponParam>  paramList = goodsCouponPromotionMapper.getCouponByStatus(companyId, userId,status);
            if(phone!=null&&!"".equals(phone)){
                List<CouponParam> listPhone = goodsCouponPromotionMapper.getCouponByStatusAndPhone(companyId, userId,status,phone);
                paramList.addAll(listPhone);
            }
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",paramList);
        }

    }

    @Override
    public ResponseEntity getCouponInfo(Long id){

        List<CouponParam>  paramList = goodsCouponPromotionMapper.getCouponInfo(id);

        if(paramList==null||paramList.size()==0){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"详情获取失败");
        }
        CouponParam couponParam = paramList.get(0);
        //判断活动是否过期
        if(GeneConstant.BYTE_2!=couponParam.getPromotionStatus()){
            couponParam.setStatus(GeneConstant.BYTE_3);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",couponParam);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",couponParam);

    }
    @Override
    public List<CouponParam> getCoupon(Long companyId,Long userId,Long projectId,Integer status,Long goodsId,String phone) {
        List<CouponParam> couponList = new ArrayList<>();
        if(status == 1){
            //未领取的优惠券
            ResponseEntity<List<CouponParam>> listResponseEntity ;

            if(StringUtils.isNotEmpty(phone)){
            listResponseEntity = getCouponListV2_1(companyId, userId, "0",phone);
            }else {
                listResponseEntity   = getCouponList(companyId, userId, "0");

            }

            List<CouponParam> couponListValue = listResponseEntity.getValue();
            if(CollectionUtils.isNotEmpty(couponListValue)){

                List<CouponParam> filterList = couponListValue.stream().filter(x -> projectId.equals(x.getProjectId())).collect(Collectors.toList());

                couponList.addAll(filterList);
                getCouponByGoodsId(goodsId,couponList,couponListValue);
            }

            if(CollectionUtils.isNotEmpty(couponList)){
                if(StringUtils.isNotEmpty(phone)){
                    List<CouponParam> collect = couponList.stream().filter(x -> GeneConstant.STRING_2.equals(x.getPromotionCrowd()) && GeneConstant.BYTE_2.equals(x.getPromotionCrowdType())).collect(Collectors.toList());
                    return collect;

                }
            }


            return couponList;
        }

        //已领取的优惠券,适用于该项目的优惠券
        List<CouponParam>  paramList;
        if(StringUtils.isNotEmpty(phone)){

            paramList = goodsCouponPromotionMapper.getCouponByStatusAndPhone(companyId, userId,"1",phone);
        }else {
            paramList = goodsCouponPromotionMapper.getCouponByStatus(companyId, userId,"1");
        }

            if(CollectionUtils.isNotEmpty(paramList)){
                List<CouponParam> collect = paramList.stream().filter(
                        //当前项目适用
                        y -> projectId.equals(y.getProjectId())).collect(Collectors.toList());
                couponList.addAll(collect);
                getCouponByGoodsId(goodsId, couponList, paramList);
                couponList =  distinctByKey(couponList);
            }


            if(CollectionUtils.isNotEmpty(couponList)){
                if(StringUtils.isNotEmpty(phone)){
                    couponList = couponList.stream().filter(x -> phone.equals(x.getPhone())).collect(Collectors.toList());

                }
            }

        //下单时候除了针对 该项目通用的优惠券 和 新手专享的通用优惠券 外，其余都为不可用优惠券。。。
        if(CollectionUtils.isNotEmpty(paramList)){

            if(status == 3){
            //过滤掉可用的优惠券，剩下的都是不可用的优惠券
                Iterator<CouponParam> iterator = paramList.iterator();
                while(iterator.hasNext()){
                    CouponParam next = iterator.next();
                    couponList.stream().forEach( x-> {
                        if(next!= null && next.getUserOfCouponId().equals(x.getUserOfCouponId())){
                            iterator.remove();
                        }
                    });
                }


                return paramList;
            }

        }

        return couponList;
    }

    /**
     * 根据领取记录Id去重
     * @param couponList
     */
    private List<CouponParam>  distinctByKey(List<CouponParam> couponList) {
       return couponList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(o -> o.getUserOfCouponId()))), ArrayList::new));
    }

    private void getCouponByGoodsId(Long goodsId, List<CouponParam> couponList, List<CouponParam> paramList) {
        //如果不是项目专用的优惠券，就判断该优惠券是否适用于该商品
        List<CouponParam> isNotProjectUse = paramList.stream().filter(y -> y.getIsProjectUse() == 2).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(isNotProjectUse)) {
           List<Long> idsList =  new ArrayList<>();

            //查询活动适用的商品列表
            isNotProjectUse.stream().forEach(x -> idsList.add(x.getPromotionId()));
            if(idsList.size() > 0){
                List<PromotionAndGoods> promotionListByPromotionId = promotionAndGoodsMapper.getPromotionListByPromotionId(idsList);
                if(CollectionUtils.isNotEmpty(promotionListByPromotionId)){
                    //匹配不是项目专属，但是适用于该商品的
                    promotionListByPromotionId.stream().forEach( x -> {

                        isNotProjectUse.stream().forEach( y -> {
                            //相同活动
                            if(x.getPromotionId().equals(y.getPromotionId())){
                                //商品相同
                                if(goodsId.equals(x.getGoodsId())){
                                    couponList.add(y);
                                }

                            }
                        });
                    });
                }
            }
        }
    }

    @Override
    public ResponseEntity isNewPeople(Long userId){
        //新用户是没有下过订单的，老用户是下过订单的
        List<Order> order =orderAppletsMapper.getOrderByUserIdAndState(userId);
        //新人获取未领取的新人优惠券
        if(order==null||order.size()==0){
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"是新人");
        }else{
            return new ResponseEntity(GeneConstant.INT_2,"不是新人");
        }
    }

    /**
     *
     * 优惠券过期修改状态定时任务
     * 每一分钟轮询一次
     *
     */
    //@Scheduled(cron = "0 */1 *  * * * ")
    @Override
    public void autoCreateGroupOrder(){

        //查询活动过期 更新优惠券过期
        userOfCouponMapper.updateExpiredCouponByPromotion();
        //更新领取的优惠券已经过期的数据
        userOfCouponMapper.updateExpiredCoupon();
        //转让12小时后的未领取的优惠券，被退回
        userOfCouponMapper.updateSendCoupon();
    }


    @Override
    public Map<String, Object> getCountList(String startDate, String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice) {

        List<String> couponNameList = new ArrayList<>();
        couponNameList.add("门票券");
        couponNameList.add("民宿券");
        couponNameList.add("餐饮券");

        Map<String, Object> map = new HashMap<>();

        List<CouponCountVo> couponCountList = goodsCouponMapper.getCouponCountList(startDate, endDate, status, kind, couponNo, userId,couponPrice,businessName);

        if(CollectionUtils.isEmpty(couponCountList)){
            if(couponCountList == null){
                couponCountList = new ArrayList<>();
            }
            for (String name : couponNameList) {

                CouponCountVo build = CouponCountVo.builder()
                        .couponType(name)
                        .expired(0L)
                        .toBeUsed(0L)
                        .total(0L)
                        .used(0L)
                        .build();


                couponCountList.add(build);
            }
        }else {
            //没有的填充为 0
            List<String> exitNameList = new ArrayList<>();
            if(couponNameList.size() != couponCountList.size()){
                //找出少了哪一个数据
                for (CouponCountVo couponCountVo : couponCountList) {
                    if(couponNameList.contains(couponCountVo.getCouponType())){
                        exitNameList.add(couponCountVo.getCouponType());
                    }
            }
                couponNameList.removeAll(exitNameList);

                for (String couName : couponNameList) {

                    CouponCountVo build = CouponCountVo.builder()
                            .couponType(couName)
                            .expired(0L)
                            .toBeUsed(0L)
                            .total(0L)
                            .used(0L)
                            .usageRate("0.00%")
                            .build();

                    couponCountList.add(build);
                }

                }


        }


        List<String> couponPriceNameList = new ArrayList<>();
        couponPriceNameList.add("门票券");
        couponPriceNameList.add("民宿券");
        couponPriceNameList.add("餐饮券");


        List<CouponPriceVo> couponPriceList = goodsCouponMapper.getCouponPriceList(startDate, endDate, status, kind, couponNo, userId,couponPrice,businessName);

        BigDecimal price = new BigDecimal("0");
        if(CollectionUtils.isEmpty(couponPriceList)){

            if(couponPriceList == null){
                couponPriceList = new ArrayList<>();
            }

            for (String name : couponPriceNameList) {

                CouponPriceVo buildPrice = CouponPriceVo.builder()
                        .couponType(name)
                        .expired(price)
                        .toBeUsed(price)
                        .total(price)
                        .used(price)
                        .build();


                couponPriceList.add(buildPrice);
            }
        }else {

            if (couponPriceNameList.size() != couponPriceList.size()) {
                List<String> exitNameList = new ArrayList<>();

                //找出少了哪一个数据
                for (CouponPriceVo couponCountVo : couponPriceList) {
                    if (couponPriceNameList.contains(couponCountVo.getCouponType())) {
                        exitNameList.add(couponCountVo.getCouponType());
                    }
                }
                couponPriceNameList.removeAll(exitNameList);
//
                for (String couName : couponPriceNameList) {

                    CouponPriceVo build = CouponPriceVo.builder()
                            .couponType(couName)
                            .expired(price)
                            .toBeUsed(price)
                            .total(price)
                            .used(price)
                            .build();

                    couponPriceList.add(build);
                }

                }

            }

        Long tobeUsed = 0L;
        Long used = 0L;
        Long expired = 0L;
        Long total = 0L;
            //分别计算总的价格
        for (CouponCountVo couponCountVo : couponCountList) {
         used += couponCountVo.getUsed();
         tobeUsed += couponCountVo.getToBeUsed();
         expired += couponCountVo.getExpired();
         total+= couponCountVo.getTotal();
         if(couponCountVo.getTotal() == 0){
             couponCountVo.setUsageRate("0.00%");
         }else {
             couponCountVo.setUsageRate(new BigDecimal(couponCountVo.getUsed() / couponCountVo.getTotal().doubleValue()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString()+"%");

         }

        }



        CouponCountVo countTotal = CouponCountVo.builder()
                .used(used)
                .total(total)
                .toBeUsed(tobeUsed)
                .expired(expired)
                .couponType("合计")
                .build();
        if(total == 0){
            countTotal.setUsageRate("0.00%");
        }else {
            countTotal.setUsageRate(new BigDecimal(used / total.doubleValue()).multiply(new BigDecimal("100")).setScale(2,BigDecimal.ROUND_HALF_DOWN).toString()+"%");
        }
        couponCountList.add(countTotal);

        BigDecimal tobeUsedPrice = new BigDecimal("0");
        BigDecimal usedPrice = new BigDecimal("0");
        BigDecimal expiredPrice = new BigDecimal("0");
        BigDecimal totalPrice = new BigDecimal("0");

        for (CouponPriceVo couponPriceVo : couponPriceList) {
            tobeUsedPrice = tobeUsedPrice.add(couponPriceVo.getToBeUsed());
            usedPrice = usedPrice.add(couponPriceVo.getUsed());
            expiredPrice = expiredPrice.add(couponPriceVo.getExpired());
            totalPrice = totalPrice.add(couponPriceVo.getTotal());
        }
        CouponPriceVo totalPriceVO = CouponPriceVo.builder()
                .couponType("总计")
                .expired(expiredPrice)
                .toBeUsed(tobeUsedPrice)
                .used(usedPrice)
                .total(totalPrice)
                .build();

        couponPriceList.add(totalPriceVO);


        map.put("countList",couponCountList);
        map.put("priceList",couponPriceList);


        return map;
    }


    @Override
    public PageInfo<CouponItemCountVo> getCountItemList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice) {

        PageHelper.startPage(pageNum,pageSize);

        List<CouponItemCountVo> couponCountItemList = goodsCouponMapper.getCouponCountItemList(startDate, endDate,status,kind,couponNo,userId,businessName,couponPrice);
        if(CollectionUtils.isNotEmpty(couponCountItemList)){
            couponCountItemList.stream().forEach( x-> {
                if("已过期".equals(x.getCouponStatus())){
                    x.setSettlementAmount(null);
                    x.setSettlementDiscount(null);
                }
            });
        }
        PageInfo<CouponItemCountVo> pageInfo = new PageInfo<>(couponCountItemList);

        return pageInfo;
    }
}
