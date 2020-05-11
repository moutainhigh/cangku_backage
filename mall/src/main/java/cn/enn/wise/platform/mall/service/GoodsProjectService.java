package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.bo.SysRole;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二销产品所属项目 服务类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsProjectService extends IService<GoodsProject> {

    ResponseEntity<ResPageInfoVO<List<GoodsProject>>> listGoodsProject(ReqPageInfoQry<GoodsProject> goodsProjectReqPageInfoQry);

    /**
     * 根据公司id查询项目list
     * @param companyId
     * @return
     */
    List<GoodsProject> listProjectByCompanyId(long companyId);

    /**
     * 根据公司id查询项目list
     * @param companyId
     * @return
     */
    List<GoodsProject> getProjectByCompanyId(long companyId);



    /**
     * 根据运营人员获取运营项目
     * @param userId
     * @return
     */
    List<GoodsProject> getOperationProject(Long userId,Long companyId);

    /**
     * jiabaiye 根据项目id查询项目详细信息
     * @param projectId
     * @return
     */
    GoodsProject getProjectById(Long projectId);

    /**
     * 根据用户获取项目列表
     * @param id
     * @param sysRoles
     * @return
     */
    List<Map<String, Object>> getProjectListByStaffId(Long id, List<SysRole> sysRoles);


    /**
     * 获取有运营时段的项目
     * @return
     *      项目集合
     */
    List<GoodsProject> getProjectByPeriod();
}
