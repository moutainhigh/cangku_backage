package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.api.cms.dto.request.oldcms.FeedBackVo;
import cn.enn.wise.ssop.api.cms.dto.request.oldmall.FeedBackParam;
import cn.enn.wise.ssop.service.cms.model.FeedBack;
import cn.enn.wise.ssop.service.cms.model.FeedBackExpand;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


@Mapper

public interface FeedBackMapper extends BaseMapper<FeedBack> {

    List<FeedBackVo> findAllFeedBackList(@Param("feedBackBean") FeedBackParam feedBackBean);

    List<FeedBackExpand> findAllFeedBackType(@Param("companyId") String companyId);

    FeedBackVo findFeedBackDetails(@Param("id") Integer id, @Param("companyId") String companyId);
}
