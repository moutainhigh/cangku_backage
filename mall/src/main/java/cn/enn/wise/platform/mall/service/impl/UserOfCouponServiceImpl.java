package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsCouponPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.UserOfCouponBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.mapper.GoodsCouponMapper;
import cn.enn.wise.platform.mall.mapper.GoodsCouponPromotionMapper;
import cn.enn.wise.platform.mall.mapper.UserOfCouponMapper;
import cn.enn.wise.platform.mall.service.UserOfCouponService;
import cn.enn.wise.platform.mall.util.DateUtil;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class UserOfCouponServiceImpl extends ServiceImpl<UserOfCouponMapper, UserOfCouponBo> implements UserOfCouponService {

    @Autowired
    private UserOfCouponMapper userOfCouponMapper;

    @Autowired
    private GoodsCouponMapper goodsCouponMapper;

    @Autowired
    private GoodsCouponPromotionMapper goodsCouponPromotionMapper;

    @Override
    public ResponseEntity saveUserOfCoupon(UserOfCouponParam param,Long userId) {
        Integer tmp = 2;
        //获取活动，验证活动是否有效
        GoodsCouponPromotionBo goodsCouponPromotionBo = goodsCouponPromotionMapper.selectOne(new QueryWrapper<GoodsCouponPromotionBo>().eq("id",param.getPromotionId()));
        if(goodsCouponPromotionBo==null){
            return new ResponseEntity(GeneConstant.INT_3,"活动失效");
        }else{
            if("2".equals(goodsCouponPromotionBo.getStatus())){
                return new ResponseEntity(GeneConstant.INT_3,"活动失效");
            }
        }
        //查询优惠券规则，判断是否已经达到领取的上线
        GoodsCouponBo goodsCoupon = goodsCouponMapper.selectById(param.getGoodsCouponId());
        if(goodsCoupon==null){
            return new ResponseEntity(GeneConstant.INT_4,"优惠券失效");
        }else{
            UserOfCouponBo userOfCouponBo = userOfCouponMapper.selectOne(new QueryWrapper<UserOfCouponBo>().eq("user_id",userId).eq("goods_coupon_id",param.getGoodsCouponId()).eq("promotion_id",param.getPromotionId()).eq("status",1).eq("coupon_resource",1));
            if(param.getPhone()==null||"".equals(param.getPhone())){
                if(goodsCouponPromotionBo.getPromotionCrowdType()==GeneConstant.BYTE_2&&goodsCouponPromotionBo.getPromotionCrowd().equals("2")){
                    return new ResponseEntity(GeneConstant.INT_7,"不是分销商绑定的用户，无法领取！");
                }
                if(userOfCouponBo!=null){
                    return new ResponseEntity(GeneConstant.INT_6,"已有此类型优惠券，请使用后再领取！");
                }
            }else{
                if(goodsCouponPromotionBo.getPromotionCrowdType()!=GeneConstant.BYTE_2||!goodsCouponPromotionBo.getPromotionCrowd().equals("2")){
                    param.setPhone(null);
                }
                if(userOfCouponBo!=null&&param.getPhone().equals(userOfCouponBo.getPhone())){
                    return new ResponseEntity(GeneConstant.INT_6,"已有此类型优惠券，请使用后再领取！");
                }
            }

            //获取是否限量
//            if(goodsCoupon.getGetType()==GeneConstant.BYTE_2){
//                List<UserOfCouponBo> userOfCouponBos = userOfCouponMapper.selectList(new QueryWrapper<UserOfCouponBo>().eq("user_id",userId).eq("goods_coupon_id",param.getGoodsCouponId()).eq("promotion_id",param.getPromotionId()));
//                int limit = goodsCoupon.getGetLimit();
//                if(limit <=userOfCouponBos.size()){
//                    return new ResponseEntity(GeneConstant.INT_5,"领取次数已达到，您已领取过优惠券");
//                }
//            }
            //发放是否限量 转增的优惠券领取不计算总量
            if(!tmp.equals(param.getCouponResource())){
                if(goodsCoupon.getInitType()==GeneConstant.BYTE_2){
                    if(goodsCoupon.getGetedSize()>=goodsCoupon.getInitSize()){
                        return new ResponseEntity(GeneConstant.INT_5,"已经达到优惠券发放最大量");
                    }
                }
            }

        }
        UserOfCouponBo userOfCouponBo = new UserOfCouponBo();
        userOfCouponBo.setCouponResource(param.getCouponResource());
        userOfCouponBo.setUserId(userId);
        userOfCouponBo.setGoodsCouponId(param.getGoodsCouponId());
        userOfCouponBo.setStatus(GeneConstant.BYTE_1);
        userOfCouponBo.setPromotionId(param.getPromotionId());

        if(tmp.equals(goodsCoupon.getValidityType())){
            userOfCouponBo.setValidityTime(DateUtil.getNowTimeAddSomeDay(goodsCoupon.getValidityDay()));
        }else{
            userOfCouponBo.setValidityTime(goodsCoupon.getValidityTime());
        }
        userOfCouponBo.setCreateUserId(param.getUserId());
        userOfCouponBo.setCreateTime(new Timestamp(System.currentTimeMillis()));
        userOfCouponBo.setUpdateUserId(param.getUserId());
        userOfCouponBo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        userOfCouponBo.setOpenId(param.getOpenId());
        userOfCouponBo.setPhone(param.getPhone()==null?"":param.getPhone());
        //转增的优惠券需要修改状态为被领取
        if(tmp.equals(param.getCouponResource())){
            UserOfCouponBo userOfCouponBo1 = userOfCouponMapper.selectById(param.getId());
            if(userOfCouponBo1==null){
                return new ResponseEntity(GeneConstant.INT_7,"已被他人领取");
            }
            userOfCouponBo1.setStatus(GeneConstant.BYTE_5);
            userOfCouponMapper.updateById(userOfCouponBo1);
        }else{
            //更新优惠券领取数量
            if(goodsCoupon.getGetedSize()==null){
                goodsCoupon.setGetedSize(1);
            }else{
                int getedSize = goodsCoupon.getGetedSize()+1;
                goodsCoupon.setGetedSize(getedSize);
            }
            goodsCouponMapper.updateById(goodsCoupon);
        }
        userOfCouponMapper.insert(userOfCouponBo);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"领取成功");
    }

    @Override
    public ResponseEntity updateUserOfCoupon(UserOfCouponParam param) {
        UserOfCouponBo userOfCouponBo = userOfCouponMapper.selectById(param.getId());
        if(userOfCouponBo==null){
            return new ResponseEntity(GeneConstant.BUSINESS_ERROR,"此优惠券不存在");
        }else{
            if(GeneConstant.BYTE_2==(userOfCouponBo.getStatus())){
                return new ResponseEntity(GeneConstant.INT_2,"此优惠券已使用");
            }else if(GeneConstant.BYTE_3==(userOfCouponBo.getStatus())){
                return new ResponseEntity(GeneConstant.INT_3,"此优惠券已过期");
            }else if(GeneConstant.BYTE_5==(userOfCouponBo.getStatus())){
                return new ResponseEntity(GeneConstant.INT_5,"此优惠券已被他人领取");
            }
            userOfCouponBo.setStatus(GeneConstant.BYTE_4);
            userOfCouponBo.setUpdateTime(DateUtil.getNowDayTime());
            userOfCouponMapper.updateById(userOfCouponBo);
            return new ResponseEntity(GeneConstant.SUCCESS_CODE,"",userOfCouponBo);
        }

    }

    @Override
    public Integer countCoupon(Long userId,String phone){
        int a = userOfCouponMapper.selectCouponSizeByUserId(userId);
        if(phone!=null&&!"".equals(phone)){
            a = a+userOfCouponMapper.selectCouponSizeByUserIdAndPhone(userId,phone);
        }
        return a;
    }
}
