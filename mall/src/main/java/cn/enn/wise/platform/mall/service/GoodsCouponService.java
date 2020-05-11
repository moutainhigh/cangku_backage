package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.GoodsCouponBo;
import cn.enn.wise.platform.mall.bean.param.AddGoodsCouponParam;
import cn.enn.wise.platform.mall.bean.param.CouponParam;
import cn.enn.wise.platform.mall.bean.param.GoodsCouponParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.CouponItemCountVo;
import cn.enn.wise.platform.mall.bean.vo.GoodsCouponVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;
import com.github.pagehelper.PageInfo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * 优惠券
 * @author 安辉
 * @since 2019-09-12
 */
public interface GoodsCouponService extends IService<GoodsCouponBo> {

    /**
     * 优惠券列表
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> listByPage(ReqPageInfoQry<GoodsCouponParam> param);

    /**
     * 保存
     * @param param
     * @return
     */
    ResponseEntity<GoodsCouponVo> saveGoodsCoupon(AddGoodsCouponParam param) throws ParseException;


    /**
     * 查询新人优惠券
     * @param companyId
     * @param userId
     * @return
     */
    ResponseEntity<List<CouponParam>> getNewPeopleCouponList(Long companyId,Long userId);


    /**
     * 查询用户拥有的优惠券，根据状态查询
     * @param companyId
     * @param userId
     * @param status
     * @return
     */
    ResponseEntity<List<CouponParam>> getCouponList(Long companyId, Long userId,String status);

    /**
     * 查询用户拥有的优惠券，根据状态查询
     * @param companyId
     * @param userId
     * @param status
     * @return
     */
    ResponseEntity<List<CouponParam>> getCouponListV2_1(Long companyId, Long userId,String status,String phone);

    /**
     * 查询优惠券详情
     * @param id
     * @return
     */
    ResponseEntity getCouponInfo(Long id);

    /**
     * 优惠券详情
     * @param id
     * @return
     */
    ResponseEntity<GoodsCouponVo> getCouponDetail(Long id);

    /**
     * 更新优惠券
     * @param param
     */
    ResponseEntity updateCoupon(AddGoodsCouponParam param);


    /**
     * 选择优惠券
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GoodsCouponVo>>> listByPageForPromotion(ReqPageInfoQry<GoodsCouponParam> param);


    /**
     * 预下单查询优惠券列表
     * @param companyId
     * @param userId
     * @param projectId
     * @param goodsId 商品ID
     * @param status  1 未领取的优惠券 2 已领取并且可以使用的优惠券 3 已领取但不适用于当前项目的优惠券
     * @return
     */
    List<CouponParam> getCoupon(Long companyId,Long userId,Long projectId,Integer status,Long goodsId,String phone);

    ResponseEntity isNewPeople(Long userId);

    /**
     * 获取优惠券统计结果
     *
     * @param startDate
     * @param endDate
     * @param status 状态
     * @param kind  类别
     * @param couponNo  券号
     * @param userId  游客id
     * @param businessName
     * @param couponPrice
     * @return
     */
    Map<String,Object> getCountList(String startDate, String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice);

    /**
     * 获取优惠券统计结果明细
     * @param startDate
     * @param endDate
     * @param status 状态
     * @param kind  类别
     * @param couponNo  券号
     * @param userId  游客id
     * @param businessName
     * @param couponPrice
     * @return
     */
    PageInfo<CouponItemCountVo> getCountItemList(Integer pageNum, Integer pageSize, String startDate, String endDate, String status, String kind, String couponNo, String userId, String businessName, Integer couponPrice);


    void autoCreateGroupOrder();

}
