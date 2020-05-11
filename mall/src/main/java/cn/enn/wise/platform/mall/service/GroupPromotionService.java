package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GroupPromotionBo;
import cn.enn.wise.platform.mall.bean.bo.group.GroupPromotionGoodsBo;
import cn.enn.wise.platform.mall.bean.param.GroupPromotionParam;
import cn.enn.wise.platform.mall.bean.param.PromotionInvalidParam;
import cn.enn.wise.platform.mall.bean.param.PromotionParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.text.ParseException;
import java.util.List;

/**
 *
 * 团购活动
 * @author 安辉
 * @since 2019-09-12
 */
public interface GroupPromotionService extends IService<GroupPromotionBo> {

    /**
     * 分页查询
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>> listByPage(ReqPageInfoQry<GroupPromotionParam> param);

    /**
     * 获取活动详情
     * @return
     */
    ResponseEntity<GroupPromotionDetailVo> getPromotionById(Integer id) throws Exception;

    /**
     * 添加活动
     * @param param
     * @return
     */
    ResponseEntity addPromotion(GroupPromotionParam param) throws ParseException;

    /**
     * 批量失效
     * @param param
     * @return
     */
    ResponseEntity<GroupPromotionVo> invalid(PromotionInvalidParam param);

    /**
     * 筛选活动
     * @param param
     * @return
     */
    ResponseEntity<List<GroupPromotionVo>> listByFilter(PromotionParam param);

    /**
     * 获取团购商品列表
     * @return
     */
    List<GroupPromotionGoodsBo> listActivePromotionGoodsList();
}
