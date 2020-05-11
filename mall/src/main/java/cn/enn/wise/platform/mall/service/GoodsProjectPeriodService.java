package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GoodsProjectPeriod;
import cn.enn.wise.platform.mall.bean.param.ProjectPeriodReqParam;
import cn.enn.wise.platform.mall.bean.vo.ProjectPeriodResVo;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsProjectPeriodService extends IService<GoodsProjectPeriod> {
    /**
     * 根据项目ID查询该项目下的所有运营时段信息
     *
     * @param projectId
     * @param status
     * @return
     */
    List<GoodsProjectPeriod> listByProjectId(long projectId, Byte status);

    /**
     * 根据项目ID查询该项目下的所有运营时段信息，返回给后台使用
     *
     * @param projectId
     * @return
     */
    List<ProjectPeriodResVo> listByProjectId(long projectId);

    /**
     * 保存商品归属项目的运营时段信息
     *
     * @param projectId
     * @param projectPeriodReqParamList
     * @param staffVo
     * @return
     */
    ResponseEntity updateGoodsProjectPeriod(long projectId, List<ProjectPeriodReqParam> projectPeriodReqParamList, SystemStaffVo staffVo);
}
