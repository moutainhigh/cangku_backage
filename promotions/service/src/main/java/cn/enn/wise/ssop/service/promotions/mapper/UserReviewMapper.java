package cn.enn.wise.ssop.service.promotions.mapper;

import cn.enn.wise.ssop.service.promotions.model.UserReview;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * @author yangshuaiquan
 * 用户回访信息Mapper
 */
@Mapper
@Repository
public interface UserReviewMapper extends BaseMapper<UserReview> {

}
