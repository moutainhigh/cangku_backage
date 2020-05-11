package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.vo.ParkInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SeatInfoVo;

import java.util.List;

/**
 * 来游吧接口服务
 */
public interface LaiYouBaRestApiService {
    List<SeatInfoVo> findSeatInfoByUserInfo(String idCard) throws Exception;

    List<ParkInfoVo> findAllParkingInfo() throws Exception;
}
