package cn.enn.wise.ssop.service.cms.mapper;

import cn.enn.wise.ssop.api.cms.dto.request.CommentPageParam;
import cn.enn.wise.ssop.api.cms.dto.request.ProductCommentParams;
import cn.enn.wise.ssop.api.cms.dto.response.ProductCommentDTO;
import cn.enn.wise.ssop.api.promotions.dto.request.DistributorBaseListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorListDTO;
import cn.enn.wise.ssop.service.cms.model.Announcement;
import cn.enn.wise.ssop.service.cms.model.ProductComment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductCommentMapper extends BaseMapper<ProductComment> {
    List<ProductCommentDTO> getProductCommentPageInfo(CommentPageParam param);
    int getProductCommentCount(CommentPageParam param);

    List<ProductCommentDTO> getProductCommentAll(ProductCommentParams prodCommParam);


}
