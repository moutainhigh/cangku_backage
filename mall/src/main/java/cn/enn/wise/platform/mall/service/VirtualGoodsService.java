package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.param.VirtualGoodsReqParam;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.bean.vo.VirtualGoodsResVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author: lishuiquan
 * @date 2019-10-29
 * 虚拟商品Service
 */
public interface VirtualGoodsService {

    /**
     * 获取虚拟商品详细信息
     * @param id
     * @return VirtualGoods
     */
    VirtualGoodsResVO getVirtualGoodsById(@Param("id") Long id);

    /**
     * 添加虚拟商品
     * @param virtualGoodsReqParam
     * @param systemStaffVo
     * @return Long
     */
    Long addVirtualGood(VirtualGoodsReqParam virtualGoodsReqParam, SystemStaffVo systemStaffVo);

    /**
     * 修改虚拟商品
     * @param virtualGoodsReqParam
     * @return Integer
     */
    Integer modifyVirtualGood(VirtualGoodsReqParam virtualGoodsReqParam,SystemStaffVo systemStaffVo);

    /**
     * 修改商品上下架状态
     * @param virtualGoodsReqParam
     * @return Integer
     */
    Integer updateGoodsStatus(VirtualGoodsReqParam virtualGoodsReqParam,SystemStaffVo systemStaffVo);

    /**
     * 删除商品
     * @param virtualGoodsReqParam
     * @return Integer
     */
    Integer deleteGoods(VirtualGoodsReqParam virtualGoodsReqParam,SystemStaffVo systemStaffVo);

    /**
     * 查询虚拟商品列表
     * @param virtualGoodsPageQry
     * @return ResPageInfoVO<List<VirtualGoods>>
     */
    ResPageInfoVO<List<VirtualGoodsResVO>> getVirtualGoodsList(ReqPageInfoQry<VirtualGoodsReqParam> virtualGoodsPageQry);
}
