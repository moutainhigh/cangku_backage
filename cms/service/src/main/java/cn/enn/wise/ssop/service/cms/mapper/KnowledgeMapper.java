
package cn.enn.wise.ssop.service.cms.mapper;


import cn.enn.wise.ssop.api.cms.dto.response.SimpleKnowledgeDTO;
import cn.enn.wise.ssop.service.cms.model.Knowledge;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;



/**
 * @author shiz
 * 知识db操作
 */
@Repository
public interface KnowledgeMapper extends BaseMapper<Knowledge> {

    @Select("select a.*,c.category_name from knowledge a\n" +
            "left join category c on a.category_id=c.id\n" +
            "where a.isdelete=1 order by a.publish_time desc")
    IPage<SimpleKnowledgeDTO> selectListPage(Page page);

    @Update("UPDATE strategy SET view_number = #{viewNumber} WHERE isdelete = 1 AND id = #{id}")
    void updateViewNumber(@Param("id") Object id, @Param("viewNumber") double viewNumber);
}

