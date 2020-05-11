package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.ProjectOperationStatus;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 项目运营因素Mapper
 *
 * @author baijie
 * @date 2019-08-26
 */
@Mapper
public interface ProjectOperationStatusMapper extends BaseMapper<ProjectOperationStatus> {

    /**
     * 批量添加项目运营因素
     *
     * @param list
     *        项目运营因素实体集合
     * @throws Exception
     *         批量添加失败异常
     */
    void batchInsertOperationStatus(List<ProjectOperationStatus> list) throws Exception;

    /**
     *添加项目运营因素
     * @param projectOperationStatus
     *         项目运营因素实体
     * @throws Exception
     *         添加失败异常
     */
    void addOperationStatus (ProjectOperationStatus projectOperationStatus)throws Exception;

    /**
     *根据Id修改项目运营因素
     * @param projectOperationStatus
     *          项目运营因素实体
     * @throws Exception
     *         修改失败异常
     */
    void updateOperationStatusById(ProjectOperationStatus projectOperationStatus) throws Exception;

    /**
     *根据项目Id获取项目运营因素
     * @param projectId
     *          项目Id
     * @param type
     *         type为null时,查询所有的项目因素
     *         type不为null时,查询有效的项目因素
     * @return
     *          项目运营状态Vo
     */
    List<ProjectOperationStatus> getByProjectId(@Param("projectId") Long projectId,@Param("type") Integer type);

}
