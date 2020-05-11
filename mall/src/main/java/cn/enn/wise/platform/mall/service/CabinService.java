package cn.enn.wise.platform.mall.service;


import cn.enn.wise.platform.mall.bean.bo.CabinBo;
import cn.enn.wise.platform.mall.bean.param.CabinParam;
import cn.enn.wise.platform.mall.bean.vo.CabinVo;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * 船舱服务接口
 * @author jiabaiye
 * @since 2019-12-24
 */
public interface CabinService extends IService<CabinBo> {


    /**
     * 保存
     * @param param
     * @return
     */
    ResponseEntity<CabinVo> updateImgUrlById(CabinParam param, Long userId);


    List<CabinVo> getCabinList(Long id);

}
