
package cn.enn.wise.ssop.service.cms.mapper;


import cn.enn.wise.ssop.service.cms.model.Category;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @author shiz
 * 分类mapper
 */
@Repository
public interface CategoryMapper extends BaseMapper<Category> {

    @Select("select a.count+an.count+k.count+s.count from (select count(*) count from advertise a where a.category_id=#{id}) a,\n" +
            "(select count(*) count from announcement a where a.category_id=#{id}) an,\n" +
            "(select count(*) count from knowledge a where a.category_id=#{id}) k,\n" +
            "(select count(*) count from strategy a where a.category_id=#{id}) s")
    int selectAssociatedCount(@Param("id") Long id);
}

