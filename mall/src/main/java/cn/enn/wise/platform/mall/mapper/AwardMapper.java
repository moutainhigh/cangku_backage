package cn.enn.wise.platform.mall.mapper;

import cn.enn.wise.platform.mall.bean.bo.GoodsCouponRuleBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.AwardRule;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2020/3/31 17:14
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
@Mapper
public interface AwardMapper extends BaseMapper<AwardRule> {


    List<Map<String,Object>> judgeCouponUsable(@Param("goodsId") Integer goodsId, @Param("couponId") Integer couponId);


    GoodsCouponRuleBo judgePromotionUsable(@Param("couponId") Integer couponId);
}
