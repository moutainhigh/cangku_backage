package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.ProjectOperationStatus;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.ProjectOperationStatusMapper;
import cn.enn.wise.platform.mall.service.ProjectOperationStatusService;
import cn.enn.wise.platform.mall.util.GeneUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目运营因素业务接口实现
 *
 * @author baijie
 * @date 2019-08-26
 */
@Service
public class ProjectOperationStatusServiceImpl implements ProjectOperationStatusService {

    @Autowired
    private ProjectOperationStatusMapper projectOperationStatusMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void editOperationStatus(ProjectOperationStatusResponseAppVO statusVo)throws Exception {


        List<ProjectOperationStatusDescVo> descVos = statusVo.getDescVos();

        for (ProjectOperationStatusDescVo descVo : descVos) {

            ProjectOperationStatus projectOperationStatus = ProjectOperationStatus.builder()
                    .id(descVo.getId())
                    .projectId(statusVo.getProjectId())
                    .operationStatus(descVo.getOperationStatus())
                    .operationStatusType(statusVo.getOperationStatusType())
                    .status(statusVo.getStatus())
                    .descValue(descVo.getDescValue())
                    .reason(GeneUtil.listToString(descVo.getReasons()))
                    .degreeOfInfluence(GeneUtil.listToString(descVo.getDegreeOfInfluences()))
                    .label(statusVo.getLabel())
                    .factorType(statusVo.getFactorType())
                    .isShowApplet(statusVo.getIsShowApplet())
                    .build();

            //添加
            if(descVo.getId() == null){
                //运营状态为空不存储
                if(projectOperationStatus.getOperationStatus() != null){

                    projectOperationStatusMapper.addOperationStatus(projectOperationStatus);
                }
            }else {
            //修改
                if(projectOperationStatus.getOperationStatus() == null){
                    projectOperationStatusMapper.deleteById(projectOperationStatus.getId());
                }else {

                    projectOperationStatusMapper.updateOperationStatusById(projectOperationStatus);
                }

            }

        }

    }

    @Override
    public ProjectOperationStatusResponseAppVO getProjectOperationStatusList(Long projectId) {

        return getProjectOperationStatusResponseAppVOByProjectId(projectId,null);
    }

    @Override
    public ProjectOperationStatusResponseAppVO getProjectOperationStatusResponseAppVOByProjectId(Long projectId,Integer type) {

        //查询项目因素
        List<ProjectOperationStatus> byProjectId = projectOperationStatusMapper.getByProjectId(projectId, type);

        ProjectOperationStatusResponseAppVO projectOperationStatusResponseAppVO = new ProjectOperationStatusResponseAppVO();
        List<ProjectOperationStatusDescVo> descVos = new ArrayList<>();
        //组装数据
        for (ProjectOperationStatus projectOperationStatus : byProjectId) {
            //组装公共数据
            projectOperationStatusResponseAppVO.setFactorType(projectOperationStatus.getFactorType());
            projectOperationStatusResponseAppVO.setLabel(projectOperationStatus.getLabel());
            projectOperationStatusResponseAppVO.setProjectId(projectOperationStatus.getProjectId());
            projectOperationStatusResponseAppVO.setOperationStatusType(projectOperationStatus.getOperationStatusType());
            projectOperationStatusResponseAppVO.setStatus(projectOperationStatus.getStatus());
            projectOperationStatusResponseAppVO.setIsShowApplet(projectOperationStatus.getIsShowApplet());


            //组装单独数据
            ProjectOperationStatusDescVo operationStatusDescVo = new ProjectOperationStatusDescVo();
            operationStatusDescVo.setDescValue(projectOperationStatus.getDescValue());
            operationStatusDescVo.setId(projectOperationStatus.getId());
            operationStatusDescVo.setOperationStatus(projectOperationStatus.getOperationStatus());
            operationStatusDescVo.setReasons(GeneUtil.stringToList(projectOperationStatus.getReason()));
            operationStatusDescVo.setDegreeOfInfluences(GeneUtil.stringToList(projectOperationStatus.getDegreeOfInfluence()));
            descVos.add(operationStatusDescVo);

        }

        projectOperationStatusResponseAppVO.setDescVos(descVos);

        return projectOperationStatusResponseAppVO;
    }
}
