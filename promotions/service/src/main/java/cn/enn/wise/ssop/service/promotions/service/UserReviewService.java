package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.UserReviewDTO;
import cn.enn.wise.ssop.service.promotions.mapper.UserReviewMapper;
import cn.enn.wise.ssop.service.promotions.model.UserReview;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;


/**
 * @author yangshuaiquan
 * 用户信息回访
 */
@Service("userReviewService")
public class UserReviewService extends ServiceImpl<UserReviewMapper, UserReview> {
    @Autowired
    private UserReviewMapper userReviewMapper;

    public Boolean saveReview(UserReviewParam userReviewParam) {
        //用户信息回访插入
        UserReview userReview = new UserReview();
        BeanUtils.copyProperties(userReviewParam,userReview,getNullPropertyNames(userReviewParam));
        userReviewMapper.insert(userReview);
        return true;
    }
    public  List<UserReviewDTO> listUserReview(UserReviewParam userReviewParam){
        QueryWrapper<UserReview> userReviewQueryWrapper = new QueryWrapper<>();
        userReviewQueryWrapper.eq("user_id",userReviewParam.getUserId())
                .eq("activity_base_id",userReviewParam.getActivityBaseId())
                .orderByDesc("create_time");
        List<UserReview> userReviews = userReviewMapper.selectList(userReviewQueryWrapper);
        List<UserReviewDTO> userReviewDTOS = new ArrayList<>();
        userReviews.forEach(c->{
            UserReviewDTO userReviewDTO = new UserReviewDTO();
            BeanUtils.copyProperties(c,userReviewDTO,getNullPropertyNames(c));
            userReviewDTOS.add(userReviewDTO);
        });
        return userReviewDTOS;
    }
}
