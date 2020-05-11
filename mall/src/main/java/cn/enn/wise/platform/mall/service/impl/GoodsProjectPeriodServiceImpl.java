package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsProjectPeriod;
import cn.enn.wise.platform.mall.bean.param.ProjectPeriodReqParam;
import cn.enn.wise.platform.mall.bean.vo.ProjectPeriodResVo;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.constants.GoodsConstants;
import cn.enn.wise.platform.mall.mapper.GoodsProjectPeriodMapper;
import cn.enn.wise.platform.mall.service.GoodsExtendService;
import cn.enn.wise.platform.mall.service.GoodsProjectPeriodService;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
@Service
public class GoodsProjectPeriodServiceImpl extends ServiceImpl<GoodsProjectPeriodMapper, GoodsProjectPeriod> implements GoodsProjectPeriodService {
    @Autowired
    private GoodsExtendService goodsExtendService;

    @Override
    public List<GoodsProjectPeriod> listByProjectId(long projectId, Byte status) {
        QueryWrapper<GoodsProjectPeriod> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsProjectPeriod::getProjectId, projectId);
        if (status != null) {
            queryWrapper.lambda().eq(GoodsProjectPeriod::getStatus, status);
        }
        queryWrapper.lambda().ne(GoodsProjectPeriod::getStatus, GoodsConstants.GoodsExtendStatus.DELETED.value());
        queryWrapper.lambda().orderByAsc(GoodsProjectPeriod::getOrderby);
        return this.list(queryWrapper);
    }

    @Override
    public List<ProjectPeriodResVo> listByProjectId(long projectId) {
        List<GoodsProjectPeriod> goodsProjectPeriodList = this.listByProjectId(projectId, null);
        if (goodsProjectPeriodList == null || goodsProjectPeriodList.isEmpty()) {
            return new ArrayList<>();
        }
        // 所有项目时段的ID集合
        List<Long> periodList = goodsProjectPeriodList.stream().map(x -> x.getId()).collect(Collectors.toList());
        // 获取在用的项目时段ID
        Set<Long> periodIdsInUse = goodsExtendService.getPeriodIdsInUse(periodList);
        List<ProjectPeriodResVo> projectPeriodResVos = goodsProjectPeriodList.stream().map(x -> {
            ProjectPeriodResVo projectPeriodResVo = new ProjectPeriodResVo();
            BeanUtils.copyProperties(x, projectPeriodResVo);
            // 如果该时段在用，则不可进行编辑
            if (periodIdsInUse.contains(x.getId())) {
                projectPeriodResVo.setEditable(false);
            } else {
                projectPeriodResVo.setEditable(true);
            }
            return projectPeriodResVo;
        }).collect(Collectors.toList());
        return projectPeriodResVos;
    }

    @Override
    public ResponseEntity updateGoodsProjectPeriod(long projectId, List<ProjectPeriodReqParam> projectPeriodReqParamList, SystemStaffVo staffVo) {
        // 参数校验
        ProjectPeriodReqParam.validateSaveProjectPeriodParam(projectId, projectPeriodReqParamList);
        // 查询出历史值
        List<GoodsProjectPeriod> periodsOriginal = this.listByProjectId(projectId, null);
        // 接受参数中的ID封装为set集合，删除操作时使用
        Set<Long> periodNewSet = projectPeriodReqParamList.stream()
                .map(projectPeriodReqParam -> projectPeriodReqParam.getId()).collect(Collectors.toSet());
        // 当前时间
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        // 删除操作：新传入的参数中不含历史记录的执行删除操作
        if (periodsOriginal != null && !periodsOriginal.isEmpty()) {
            List<GoodsProjectPeriod> periodsDel = periodsOriginal.stream().map(period -> {
                if (!periodNewSet.contains(period.getId())) {
                    period.setStatus(GoodsConstants.GoodsExtendStatus.DELETED.value());
                    period.setProjectId(projectId);
                    period.setUpdateTime(curTime);
                    period.setUpdateUserId(staffVo.getId());
                    period.setUpdateUserName(staffVo.getName());
                    return period;
                }
                return null;
            }).filter(period -> period != null).collect(Collectors.toList());
            if (periodsDel != null && !periodsDel.isEmpty()) {
                this.updateBatchById(periodsDel);
            }
        }
        // 更新操作
        projectPeriodReqParamList.stream().forEach(projectPeriodReqParam -> {
            GoodsProjectPeriod goodsProjectPeriod = new GoodsProjectPeriod();
            BeanUtils.copyProperties(projectPeriodReqParam, goodsProjectPeriod);
            // 不含ID的为新增记录，做save操作
            if (projectPeriodReqParam.getId() == null) {
                goodsProjectPeriod.setCreateTime(curTime);
                goodsProjectPeriod.setCreateUserId(staffVo.getId());
                goodsProjectPeriod.setCreateUserName(staffVo.getName());
                this.save(goodsProjectPeriod);
            } else { // 含ID的为历史记录，做更新操作
                goodsProjectPeriod.setUpdateTime(curTime);
                goodsProjectPeriod.setUpdateUserId(staffVo.getId());
                goodsProjectPeriod.setUpdateUserName(staffVo.getName());
                this.updateById(goodsProjectPeriod);
            }
        });
        return new ResponseEntity();
    }
}
