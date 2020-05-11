package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.mapper.AppMapper;
import cn.enn.wise.platform.mall.service.APPVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jiaby
 * @since 2019-08-08
 */
@Service
public class AppVersionServiceImpl implements APPVersionService {

    @Autowired
    private AppMapper appMapper;
    @Override
    public String getAppVersion(Integer companyId) {

        String s = appMapper.getAppVersionByCompanyId(companyId);
        return s;
    }
}
