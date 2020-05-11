package cn.enn.wise.ssop.service.promotions.service;

import cn.enn.wise.ssop.service.promotions.mapper.PlatformMapper;
import cn.enn.wise.ssop.service.promotions.model.Platform;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author 安辉
 * 平台信息
 */
@Service("platformService")
public class PlatformService extends ServiceImpl<PlatformMapper, Platform> {

}
