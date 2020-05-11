package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.UserOfCouponRecordsBo;
import cn.enn.wise.platform.mall.bean.param.CouponRecordsParams;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.UserOfCouponRecordsService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 活动请求参数
 * @author anhui
 * @since 2019/9/12
 */
@Service
public class UserOfCouponRecordsImpl extends ServiceImpl<UserOfCouponRecordsMapper, UserOfCouponRecordsBo> implements UserOfCouponRecordsService {


    /**
     * 分页获取消费记录
     *
     * @param param
     * @return
     */
    @Override
    public ResponseEntity<ResPageInfoVO<List<UserOfCouponRecordsBo>>> listByPage(ReqPageInfoQry<CouponRecordsParams> param) {
        CouponRecordsParams params = param.getReqObj();
        QueryWrapper<UserOfCouponRecordsBo> wrapper = new QueryWrapper<>();
        if (params != null) {
            if (StringUtils.isNotEmpty(params.getUserId())) {
                wrapper.eq("user_id", params.getUserId());
            }
            if (StringUtils.isNotEmpty(params.getCode())) {
                wrapper.like("code", params.getCode());
            }
            if (StringUtils.isNotEmpty(params.getCouponType())) {
                wrapper.eq("coupon_type", params.getCouponType());
            }
            if (StringUtils.isNotEmpty(params.getEndTime()) && StringUtils.isNotEmpty(params.getEndTime())) {
                wrapper.lt("create_time", params.getEndTime());
                wrapper.gt("create_time", params.getStartTime());
            }
            if (StringUtils.isNotEmpty(params.getProjectName())) {
                wrapper.eq("project_name", params.getProjectName());
            }
            if (params.getCouponId() != null && params.getCouponId() != 0) {
                wrapper.eq("coupon_id", params.getCouponId());
            }
            if (StringUtils.isNotEmpty(params.getStatus())) {
                if ("1".equals(params.getStatus())) {
                    List<Integer> status = new ArrayList<>();
                    status.add(2);
                    wrapper.in("status", status);
                } else {
                    List<Integer> status = new ArrayList<>();
                    status.add(2);
                    status.add(3);
                    status.add(9);
                    status.add(10);
                    status.add(11);
                    wrapper.in("status", status);
                }
            }
        }

        // mybatis 查询数据
        Page<UserOfCouponRecordsBo> pageInfo = new Page<>(param.getPageNum(), param.getPageSize());
        wrapper.orderByDesc("create_time");
        IPage<UserOfCouponRecordsBo> goodsCouponBoList = this.page(pageInfo, wrapper);

        // 分页
        ResPageInfoVO resPageInfoVO = new ResPageInfoVO();
        if (goodsCouponBoList == null) {
            resPageInfoVO.setPageNum(0L);
            resPageInfoVO.setPageSize(0L);
            resPageInfoVO.setTotal(0L);
            return new ResponseEntity<ResPageInfoVO<List<UserOfCouponRecordsBo>>>(resPageInfoVO);
        }

        // 分页
        resPageInfoVO.setPageNum(goodsCouponBoList.getCurrent());
        resPageInfoVO.setPageSize(goodsCouponBoList.getSize());
        resPageInfoVO.setTotal(goodsCouponBoList.getTotal());


        if (goodsCouponBoList == null || goodsCouponBoList.getRecords().isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<UserOfCouponRecordsBo>>>(resPageInfoVO);
        }
        resPageInfoVO.setRecords(goodsCouponBoList.getRecords());
        return new ResponseEntity<>(resPageInfoVO);

    }
}
