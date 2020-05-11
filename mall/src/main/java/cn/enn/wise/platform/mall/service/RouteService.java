package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.Route;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author baijie
 * @since 2019-07-25
 */
public interface RouteService extends IService<Route> {

    /**
     * 同步路线
     */
   void syncRoute();

}
