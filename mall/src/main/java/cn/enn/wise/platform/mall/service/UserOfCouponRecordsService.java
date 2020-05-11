package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.UserOfCouponRecordsBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 优惠券
 * @author 安辉
 * @since 2019-09-12
 */
public interface UserOfCouponRecordsService extends IService<UserOfCouponRecordsBo> {


    /**
     * 分页获取消费记录
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<UserOfCouponRecordsBo>>> listByPage(ReqPageInfoQry<CouponRecordsParams> param);
}
