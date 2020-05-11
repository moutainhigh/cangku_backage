package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.vo.ParkInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SeatInfoVo;
import cn.enn.wise.platform.mall.bean.vo.User;

import java.util.List;

public interface WiseFlightService {
    List<SeatInfoVo> getSeatByUser(User user);

    List<ParkInfoVo> getParkingList();

    Boolean requisitionAdd(User user, String content, String name) throws Exception;

    boolean bindUserMessage(User user, String idCard, String name, String phone);
}
