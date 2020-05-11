package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.ProdComm;
import cn.enn.wise.platform.mall.bean.param.ProdCommParam;
import cn.enn.wise.platform.mall.bean.vo.ProdCommVo;
import cn.enn.wise.platform.mall.bean.vo.ProjectCommentVo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 14:02
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public interface ProdCommMapper extends BaseMapper<ProdComm> {

    /**
     * 获取评价列表
     * @param projectId 项目ID
     * @return 评价列表
     * *
     */
    List<ProjectCommentVo> getCommentList(Long projectId);

    List<ProdCommVo> findCommentList(@Param("prodCommParam")ProdCommParam prodCommParam);
}
