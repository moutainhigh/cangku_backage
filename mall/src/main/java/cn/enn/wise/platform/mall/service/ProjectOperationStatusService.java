package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.vo.ProjectOperationStatusResponseAppVO;


/**
 * 项目运营因素业务接口
 *
 * @author baijie
 * @date 2019-08-26
 */
public interface ProjectOperationStatusService {


    /**
     * 编辑项目运营因素
     * @param projectOperationStatusResponseAppVO
     *         项目运营因素VO
     * @throws Exception
     *          编辑失败异常
     */
    void editOperationStatus(ProjectOperationStatusResponseAppVO projectOperationStatusResponseAppVO)throws Exception;

    /**
     * 根据项目Id获取项目运营因素
     * @param projectId
     *      项目Id
     * @return
     *      项目运营因素Vo
     */
    ProjectOperationStatusResponseAppVO getProjectOperationStatusList(Long projectId);

    /**
     * 根据项目Id获取运营因素响应Vo
     * @param projectId
     *          项目Id
     * @return
     *          结果对象
     */
    ProjectOperationStatusResponseAppVO getProjectOperationStatusResponseAppVOByProjectId(Long projectId,Integer type);

}
