package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsProject;
import cn.enn.wise.platform.mall.bean.vo.GoodsProjectVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 二销产品所属项目 Mapper 接口
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
public interface GoodsProjectMapper extends BaseMapper<GoodsProject> {

    List<GoodsProject> listProjectByCompanyId(@Param("companyId") Long companyId,@Param("status") Long status);

    List<GoodsProject> getProjectByCompanyId(@Param("companyId") Long companyId);

    List<GoodsProject> getAllProjectName();

    GoodsProject getProjectById(@Param("id") Long id,@Param("status") Long status);

    /**
     * 根据运营人员获取运营的项目
     * @param userId
     * @return
     */
    List<GoodsProject> getOperationProject(@Param("userId")Long userId,@Param("companyId") Long companyId);

    /**
     * 根据商品id获取项目id
     * @param goodsId
     * @return
     */
    Map<String,Object> selectByGoodsId(@Param("goodsId")Long goodsId);

    /**
     * 根据用户查询项目列表
     * @param id
     * @return
     */
    List<Map<String, Object>> getProjectListByStaffId(@Param("userId")Long id);


    /**
     * 根据项目id查询项目列表
     */
    List<Map<String, Object>> getProjectPlaceListByProjectId(@Param("projectId")Long id);
    /**
     *
     * @param id
     * @param projectId
     * @return
     */
    GoodsProject selectByStaffIdAndProjectId(@Param("staffId")Long id, @Param("projectId")Long projectId);

    /**
     * 获取有运营时段的项目
     * @return
     *      项目集合
     */
    List<GoodsProject> getProjectByPeriod();

    /**
     * 根据用户查询项目列表
     * @param id
     * @return
     */
    List<Map<String, Object>> getProjectListByTicketChanger();

    /**
     * 根据tag查询项目信息
     * @param tagIds 标签集合
     * @return 此标签下的所有项目
     */
    List<GoodsProjectVo> listByTag(List<Long> tagIds);

}
