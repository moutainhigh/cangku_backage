package cn.enn.wise.ssop.service.promotions.service;


import cn.enn.wise.ssop.service.promotions.consts.ChannelEnum;
import cn.enn.wise.ssop.service.promotions.consts.ChannelRuleEnum;
import cn.enn.wise.ssop.service.promotions.mapper.ChannelMapper;
import cn.enn.wise.ssop.service.promotions.model.Channel;
import cn.enn.wise.ssop.service.promotions.util.RedisUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @author jiabaiye
 * 渠道管理
 */
@Service("enumService")
public class EnumService {

    @Autowired
    RedisUtil redisUtil;

    /**
     * 获取界面枚举显示的数据
     * @param pageName
     * @return
     */
    
    public R getEnum(String pageName) {
        if("channel".equals(pageName)){
            return new R<>(ChannelEnum.mapList);
        }else if("channelrule".equals(pageName)){
            return new R<>(ChannelRuleEnum.mapList);
        }
        return new R<>(ChannelEnum.mapList);
    }




}
