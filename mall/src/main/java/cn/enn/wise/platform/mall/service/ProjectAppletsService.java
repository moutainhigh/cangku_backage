package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.param.GoodsProjectParam;
import cn.enn.wise.platform.mall.bean.vo.ProjectVo;


/**
 * 小程序项目展示相关业务
 *
 * @author baijie
 * @date 2019-11-04
 */
public interface ProjectAppletsService {

    /**
     * 根据项目Id获取项目详情
     * @param goodsProjectParam 参数列表
     * @return 项目详情Vo
     */
    ProjectVo getProjectInfoById(GoodsProjectParam goodsProjectParam);
}
