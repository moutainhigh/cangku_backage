
package cn.enn.wise.ssop.service.cms.mapper;


import cn.enn.wise.ssop.api.cms.dto.response.ContentListDTO;
import cn.enn.wise.ssop.api.cms.dto.response.SimpleAnnouncementDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

/**
 * @author shiz
 * 合作伙伴db操作
 */
@Repository
public interface ContentMapper {

    IPage<ContentListDTO> selectContentListByTextAndTypeId(Page<?> page, @Param("searchText") String searchText, @Param("typeId")Integer typeId);


}

