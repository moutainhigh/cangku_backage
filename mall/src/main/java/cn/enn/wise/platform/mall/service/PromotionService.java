package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.PromotionBo;
import cn.enn.wise.platform.mall.bean.param.GroupPromotionParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupPromotionVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *
 * 团购活动
 * @author 安辉
 * @since 2019-09-12
 */
public interface PromotionService extends IService<PromotionBo> {

    /**
     * 分页查询
     * @param param
     * @return
     */
    ResponseEntity<ResPageInfoVO<List<GroupPromotionVo>>> listByPage(ReqPageInfoQry<GroupPromotionParam> param);


}
