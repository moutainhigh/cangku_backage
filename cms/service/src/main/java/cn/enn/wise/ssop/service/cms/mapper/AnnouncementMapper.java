
package cn.enn.wise.ssop.service.cms.mapper;


import cn.enn.wise.ssop.api.cms.dto.response.SimpleAnnouncementDTO;
import cn.enn.wise.ssop.service.cms.model.Announcement;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;


/**
 * @author shiz
 * 公告db操作
 */
@Repository
public interface AnnouncementMapper extends BaseMapper<Announcement> {

    @Select("select a.id,content,a.state,a.creator_name,a.publish_time,a.create_time,a.view_number,c.category_name,a.category_id,a.scenic_area_id,a.scenic_area_name " +
            "from announcement a\n" +
            "left join category c on a.category_id=c.id\n" +
            "where a.isdelete=1 order by a.update_time desc")
    IPage<SimpleAnnouncementDTO> selectAnnouncement(Page page);
}

