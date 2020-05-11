package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.ShipBo;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.ShipVo;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 船舶服务接口
 * @author jiabaiye
 * @since 2019-12-24
 */
public interface ShipService extends IService<ShipBo> {

    /**
     * 优惠券列表
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<ShipVo>>> listByPage(ReqPageInfoQry<ShipParam> param);

    /**
     * 保存
     * @param param
     * @return
     */
    ResponseEntity<ShipVo> updateImgUrlById(ShipParam param,Long userId);

    /**
     * 根据id获取船舶详情信息
     * @param id
     * @return
     */
    ResponseEntity<ShipVo> getShipById(Long id);


}
