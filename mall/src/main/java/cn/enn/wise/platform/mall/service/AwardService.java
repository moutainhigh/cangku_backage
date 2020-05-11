package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.vo.DrawCouponVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.bean.vo.UserDrawVo;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/31 17:03
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public interface AwardService {

    UserDrawVo userDraw(User user, String openId);

    List<DrawCouponVo> findDrawCouponList(User user, String openId);

    DrawCouponVo judgeCouponUsable(User user,String openId,Integer goodsId,Integer couponId);
}
