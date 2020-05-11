package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.bo.HotelGoods;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.bean.vo.HotelOrderVo;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * @author bj
 * @Description 参数校验类
 * @Date19-5-26 下午2:21
 * @Version V1.0
 **/
public class ParamValidateUtil {

    private static final Logger logger = LoggerFactory.getLogger(ParamValidateUtil.class);

    /**
     * 校验goodsList参数
     *
     * @param timeFrame
     * @return
     */

    public static void validateGoodsList(Integer timeFrame) {
        logger.info("===开始校验参数===goodsList");
        if (timeFrame == null) {
            logger.info("===timeFrame不能为null===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"timeFrame不能为null");
        }
        logger.info("===参数校验成功 ===");
    }

    /**
     * 校验门票预定参数
     *
     * @param goodsReqQry
     * @return
     */
    public static void validateBookingTicket(GoodsReqParam goodsReqQry) {
        logger.info("===开始校验参数===bookingTicket");
        if (goodsReqQry.getGoodsId() == null) {
            logger.info("===goodsId不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"goodsId不能为空");
        }
        if (goodsReqQry.getPeriodId() == null) {
            logger.info("===periodId不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"periodId不能为空");
        }
        if (goodsReqQry.getTimeFrame() == null) {
            logger.info("===timeFrame不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"timeFrame不能为空");
        }
        logger.info("===参数校验成功 ===");
    }


    /**
     * 校验门票预定参数
     *
     * @param goodsReqQry
     * @return
     */
    public static void validateBookingTicketByWzd(GoodsReqParam goodsReqQry) {
        logger.info("===开始校验参数===bookingTicket");
        if (goodsReqQry.getGoodsId() == null) {
            logger.info("===goodsId不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"goodsId不能为空");
        }

        if(goodsReqQry.getProjectId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }

        Integer isByPeriodOperation = goodsReqQry.getIsByPeriodOperation();
        if ( isByPeriodOperation == null) {
            logger.info("===是否分时段不能为空===");
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"isByPeriodOperation不能为空");
            isByPeriodOperation = 1;
            goodsReqQry.setIsByPeriodOperation(1);
        }

        if(isByPeriodOperation == 1){

            if (goodsReqQry.getTimeFrame() == null) {
                logger.info("===timeFrame不能为空===");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"timeFrame不能为空");
            }
            if (goodsReqQry.getPeriodId() == null) {
                logger.info("===periodId不能为空===");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"periodId不能为空");
            }
        }



        logger.info("===参数校验成功 ===");
    }

    /**
     * 校验门票套装预定参数
     *
     * @param goodsReqQry
     * @return
     */
    public static void validatePackageDetail(GoodsReqParam goodsReqQry) {
        if (goodsReqQry.getGoodsId() == null) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"goodsId不能为空");
        }

        if(goodsReqQry.getProjectId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"projectId不能为空");
        }

        logger.info("===validatePackageDetail 参数校验成功 ===");
    }
    /**
     * 校验下单支付参数
     */

    public static void validatePay(PayParam payParam,
                                                     User user,
                                                     String openId) {
        logger.info("===开始校验参数===bookingTicket");
        if (openId == null || user == null || user.getId() == null
                || user.getId() == 0) {
            logger.error(
                    "tiket pay error openId = " + openId + "; user = " + user);
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"===用户身份已过期===");
        }

        if (payParam.getPhone() == null || !AccountValidatorUtil.isMobile(payParam.getPhone())) {
            logger.info("===手机号不正确===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===手机号不正确===");
        }
//        if (StringUtils.isEmpty(payParam.getIdNumber()) || !AccountValidatorUtil.isIDCard(payParam.getIdNumber())) {
//            logger.info("===身份证号不正确===");
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===身份证号不正确===");
//        }
        if (StringUtils.isEmpty(payParam.getName())) {
            logger.info("===购票联系人不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===购票联系人不能为空===");
        }
        if (payParam.getScenicId() == null) {
            logger.info("===景点Id不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===景点Id不能为空===");
        }
        if (payParam.getAmount() == null || payParam.getAmount() == 0) {
            logger.info("===购票数量不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===购票数量不能为空===");
        }

        if (payParam.getGoodsId() == null) {
            logger.info("===商品Id不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===商品Id不能为空===");
        }
        if (StringUtils.isEmpty(payParam.getPayType()) ) {
            logger.info("===支付方式不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===支付方式不能为空===");
        }
        if (payParam.getTotalPrice() == null) {
            logger.info("===商品总价不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===商品总价不能为空===");
        }

        Integer isByPeriodOperation = payParam.getIsByPeriodOperation();
        if(isByPeriodOperation == null){

            isByPeriodOperation = 1;
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"是否分时段售卖不能为空");
            payParam.setIsByPeriodOperation(1);
        }

        if( isByPeriodOperation == 1){

            if (payParam.getPeriodId() == null) {
                logger.info("===时段Id不能为空===");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===时段Id不能为空===");
            }
        }
        if (payParam.getTimeFrame() == null) {
            logger.info("===日期不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===日期不能为空===");
        }
        logger.info("===参数校验成功 ===");
    }

    /**
     * 校验支付原有订单参数
     * @param orderCode
     * @param user
     * @param openId
     * @param scenicId
     * @return
     */
    public static void validatePayOldOrder(String orderCode,
                                                     User user,
                                                     String openId,
                                                     Long scenicId) {

        logger.info("===开始校验参数===PayOldOrder");
        if (openId == null || user == null || user.getId() == null
                || user.getId() == 0) {
            logger.error(
                    "tiket pay error openId = " + openId + "; user = " + user);
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"用户身份已过期");
        }
        if (scenicId == null) {
            logger.info("===景点Id不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"景点Id不能为空");
        }
        if (orderCode == null) {
            logger.info("===订单号不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单号不能为空");
        }
        logger.info("===参数校验成功 ===");
    }

    /**
     * 校验获取订单详情参数
     * @param user
     * @param orderCode
     * @return
     */
    public static void validateGetOrderByIdAndUserId(User user,String orderCode){
        logger.info("===开始校验参数===GetOrderByIdAndUserId");
        if(user==null || user.getId() == null){
            logger.info("===查找不到用户信息===");
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"用户身份已过期");
        }
        if(StringUtils.isEmpty(orderCode)){
            logger.info("===订单号不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单号不能为空");
        }
    }
    /**
     *校验获取订单列表参数
     */
    public static void  validateListOrderByUserId(User user,Long scenicId,Integer state){
        logger.info("===开始校验参数===ListOrderByUserId");

        if(user==null || user.getId() == null){
            logger.info("===查找不到用户信息===");
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"查找不到用户信息");
        }
        if(scenicId == null){
            logger.info("===scenicId不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"scenicId不能为空");
        }
        if(state != null){
            logger.info("state值不为null,state="+state);
            if(state != 2){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"state值不正确");
            }
        }
        logger.info("===参数校验成功 ===");
    }

    /**
     * 订单完成参数校验
     * @param orderCode
     * @return
     */
    public static void validateComplateOrder(String orderCode){
        logger.info("===开始校验参数===validateComplateOrder");
        if (StringUtils.isEmpty(orderCode)) {
            logger.info("===orderCode不能为null===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"orderCode不能为null");
        }
        logger.info("===参数校验成功 ===");

    }

    /**
     * 校验取消订单参数
     * @param user
     * @param orderCode
     */
    public static void validateRefundOrder(User user,String orderCode){
        logger.info("===开始校验参数===validateRefundOrder");

        if(user==null || user.getId() == null){
            logger.info("===查找不到用户信息===");
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"查找不到用户信息");
        }
        if (StringUtils.isEmpty(orderCode)) {
            logger.info("===orderCode不能为null===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"orderCode不能为null");
        }
        logger.info("===参数校验成功 ===");
    }

    /**
     * 验证生成离线订单
     * @param orderReqVo
     */
    public static  void validateSaveOfflineOrder(OrderReqVo orderReqVo){

        logger.info("===开始校验参数===validateSaveOfflineOrder");

        if(orderReqVo == null){
            logger.info("===请求不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"请求数据不能为空");
        }
        if(orderReqVo.getOrderPrice() == null){
            logger.info("===订单总价不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单总价不能为空");
        }
        if(orderReqVo.getAmount() == null || orderReqVo.getAmount() <=0){
            logger.info("===商品数量不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品数量不能为空");
        }
        if(orderReqVo.getGoodsId() == null){
            logger.info("===商品id不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品id不能为空");
        }

        if(orderReqVo.getEnterTime() == null){
            logger.info("===入园时间不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"入园时间不能为空");
        }

        if(orderReqVo.getActualPay() == null){
            logger.info("===实收金额不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"实收金额不能为空");
        }
        if(orderReqVo.getName() == null){
            logger.info("===订单客人姓名不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单客人姓名不能为空");
        }
        if(orderReqVo.getPhone() == null || !AccountValidatorUtil.isMobile(orderReqVo.getPhone())){
            logger.info("===订单客人手机号输入有误===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单客人手机号输入有误");
        }
        if(orderReqVo.getIsDistributeOrder() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"是否是分销订单不能为空!");
        }
        if(orderReqVo.getIsDistributeOrder() == 1){

            if(orderReqVo.getDistributorPhone() == null || !AccountValidatorUtil.isMobile(orderReqVo.getDistributorPhone())){
                logger.info("===分销商手机号输入有误===");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"分销商手机号输入有误");
            }
        }
        if(orderReqVo.getOfflineStatus() == null){
            logger.info("===订单状态不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单状态不能为空");
        }
        if(orderReqVo.getIsByPeriodOperation() == 1){

            if(orderReqVo.getGoodsExtendId() == null){
                logger.info("===商品时段id不能为空===");
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品时段id不能为空");
            }

        }


    }

    public static void validateScanCodeData(ScanCodeDataReqVo scanCodeDataReqVo) {
        if(scanCodeDataReqVo == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不能为空");
        }

        if(scanCodeDataReqVo.getCompanyId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"companyId不能为空");
        }
        if(scanCodeDataReqVo.getScanType() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"scanType不能为空");
        }
        if(scanCodeDataReqVo.getScanDateStart() == null){
            scanCodeDataReqVo.setScanDateStart(DateUtil.parse("2019-05-26"));
        }
        if(scanCodeDataReqVo.getScanDateEnd() == null){

            scanCodeDataReqVo.setScanDateEnd(new Date());
        }
    }


    public static void validateEditProjectOperationStatus(ProjectOperationStatusResponseAppVO vo){

        if(vo == null){

            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不能为空!");
        }

        //清除前端无效数据
        List<ProjectOperationStatusDescVo> descVos = vo.getDescVos();
        if(CollectionUtils.isNotEmpty(descVos)){

            Iterator<ProjectOperationStatusDescVo> iterator = descVos.iterator();
            while (iterator.hasNext()){

                ProjectOperationStatusDescVo descVo = iterator.next();
                if(descVo.getId() == null && descVo.getOperationStatus() == null){
                    iterator.remove();
                }
            }
        }

    }


    public static void ValidateSaveOrderVo(HotelOrderVo hotelOrderVo){
        if(hotelOrderVo == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不为空");
        }

        if (hotelOrderVo.getPhone() == null || !AccountValidatorUtil.isMobile(hotelOrderVo.getPhone())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===手机号不正确===");
        }

        if(StringUtils.isEmpty(hotelOrderVo.getName())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品状态不为空");

        }

        if(hotelOrderVo.getScenicId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"景点Id不为空");

        }
        if(hotelOrderVo.getAmount() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"购买数量不为空");

        }

        if(StringUtils.isEmpty(hotelOrderVo.getGoodsId())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品Id不为空");

        }

        if(StringUtils.isEmpty(hotelOrderVo.getIncomeDate())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"入住日期不为空");

        }

        if(StringUtils.isEmpty(hotelOrderVo.getDepartureDate())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"离店时间不为空");

        }

        if(hotelOrderVo.getDayStayed() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"入住天数不能为空");
        }
    }

    public static void validateHotelGoods(HotelGoods goods) {
        if(goods == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不为空");
        }

        if(goods.getStatus() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品状态不为空");

        }

        if(goods.getDescribe() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"描述不为空");

        }

        if(goods.getHotelName() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"酒店名称不为空");

        }

        if(goods.getHouseName() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"房间名称不为空");

        }

        if(goods.getPresent() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"商品简介不为空");

        }

        if(goods.getPrice() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"价格不为空");

        }

        if(goods.getRule() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"使用规则不为空");

        }
    }

    public static void validateHomeListByTagParam(GoodsProjectParam goodsProjectParam){

        if(goodsProjectParam.getTagId() == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"tagId不能为空");
        }
    }

    public static void validateQueryParam(TicketInfoQueryParam ticketInfoQueryParam) {

        if(StringUtils.isEmpty(ticketInfoQueryParam.getCabinName())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"船舱名称不能为空");
        }

        if(StringUtils.isEmpty(ticketInfoQueryParam.getLineDate())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"查询日期不能为空");
        }

        if(StringUtils.isEmpty(ticketInfoQueryParam.getLineFrom())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"出发点不能为空");
        }

        if(StringUtils.isEmpty(ticketInfoQueryParam.getLineTo())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"目的点不能为空");
        }
        if(StringUtils.isEmpty(ticketInfoQueryParam.getTimespan())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"开船时间不能为空");
        }

        if(StringUtils.isEmpty(ticketInfoQueryParam.getShipName())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"船名称不能为空");
        }
    }

    public static void validateSaveTicketParam(PayParam payParam, User user, String openId) {
        logger.info("===开始校验参数===bookingTicket");
        if (openId == null || user == null || user.getId() == null
                || user.getId() == 0) {
            logger.error(
                    "tiket pay error openId = " + openId + "; user = " + user);
            throw new BusinessException(GeneConstant.LOGIN_TIMEOUT,"===用户身份已过期===");
        }

//        if (payParam.getPhone() == null || !AccountValidatorUtil.isMobile(payParam.getPhone())) {
//            logger.info("===手机号不正确===");
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===手机号不正确===");
//        }
//        if (StringUtils.isEmpty(payParam.getName())) {
//            logger.info("===购票联系人不能为空===");
//            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===购票联系人不能为空===");
//        }

        if (payParam.getGoodsId() == null) {
            logger.info("===商品Id不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===商品Id不能为空===");
        }
        if (StringUtils.isEmpty(payParam.getPayType()) ) {
            logger.info("===支付方式不能为空===");
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"===支付方式不能为空===");
        }

        if (StringUtils.isEmpty(payParam.getMessageCode())) {
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"验证码不能为空");
        }
        if(CollectionUtils.isEmpty(payParam.getContacts())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"乘客信息不能为空");
        }

        logger.info("===参数校验成功 ===");
    }

    public static void checkBbdOfflineOrderParams(BbdOrderParams bbdOrderParams) {

        if(bbdOrderParams == null){
            throw new  BusinessException(GeneConstant.PARAM_INVALIDATE,"参数列表不能为空");
        }
        if(StringUtils.isEmpty(bbdOrderParams.getProductId())){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"产品id不能为空");
        }
        List<BbdOrderParams.OrderItem> orderItems = bbdOrderParams.getOrderItems();
        if(CollectionUtils.isEmpty(orderItems)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"orderItems不能为空");
        }
        for (BbdOrderParams.OrderItem orderItem : orderItems) {

            if(orderItem.getTicketType() == null){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"票的类型不能为空");
            }
            if(orderItem.getOrderPrice() == null){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单金额不能为空");
            }
            if(StringUtils.isEmpty(orderItem.getThirdOrderNo())){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"订单号不能为空");
            }

            if(StringUtils.isEmpty(orderItem.getTicketQrCodeBbd())){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"佰邦达票二维码不能为空");
            }

            if(StringUtils.isEmpty(orderItem.getOrderSerialBbd())){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"佰邦达订单号不能为空");
            }

            if(StringUtils.isEmpty(orderItem.getTicketSerialBbd())){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"佰邦达票号不能为空");
            }

        }


        if(StringUtils.isNotEmpty(bbdOrderParams.getBusinessNumber())){
            if(bbdOrderParams.getIsScenicService() == null){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"是否是景区接送服务不能为空");
            }
        }

    }

    /**
     * 检查订单改签参数
     * @param changeParam
     */
    public static void checkBbdOrderChange(BbdOrderChangeParam changeParam) {

        if(changeParam == null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"参数不能为空");
        }

        List<BbdOrderChangeParam.ChangeData> changeData = changeParam.getChangeData();
        if(CollectionUtils.isEmpty(changeData)){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"changeData不能为空");
        }
        changeData.stream().forEach( data -> {
            String changeOrderSerialBbd = data.getChangeOrderSerialBbd();
            if(StringUtils.isEmpty(changeOrderSerialBbd)){
                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"changeOrderSerialBbd不能为空");
            }

            if(StringUtils.isEmpty(data.getChangeTicketSerialBbd())){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"changeTicketSerialBbd不能为空");
            }

            if(StringUtils.isEmpty(data.getTicketSerialBbdId())){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"ticketSerialBbdId不能为空");
            }

            if(StringUtils.isEmpty(data.getTicketQrCodeBbd())){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"ticketQrCodeBbd不能为空");
            }

            if(StringUtils.isEmpty(data.getTicketSerialBbd())){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"ticketSerialBbd不能为空");
            }

            if(data.getTicketStateBbd() == null){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"ticketStateBbd不能为空");
            }
            if(data.getIsTicketPrinted() == null){

                throw new BusinessException(GeneConstant.PARAM_INVALIDATE,"isTicketPrinted不能为空");
            }
        });


    }
}
