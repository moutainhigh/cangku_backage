package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.CabinBo;
import cn.enn.wise.platform.mall.bean.param.CabinParam;
import cn.enn.wise.platform.mall.bean.vo.CabinVo;
import cn.enn.wise.platform.mall.mapper.CabinMapper;
import cn.enn.wise.platform.mall.service.CabinService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 船舱服务
 * @author jiabaiye
 * @since 2019/12/25
 */
@Service
public class CabinServiceImpl extends ServiceImpl<CabinMapper, CabinBo> implements CabinService {

    @Autowired
    private CabinMapper cabinMapper;




    @Override
    public ResponseEntity<CabinVo> updateImgUrlById(CabinParam param, Long userId) {

        CabinBo cabinBo= cabinMapper.selectById(param.getId());
        cabinBo.setImgUrl(param.getImgUrl());
        cabinBo.setUpdateUserId(userId);
        cabinBo.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        cabinMapper.updateById(cabinBo);
        CabinVo cabinVo = new CabinVo();
        BeanUtils.copyProperties(cabinBo,cabinVo);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"图片更新成功",cabinVo);
    }

    @Override
    public List<CabinVo> getCabinList(Long id) {
        QueryWrapper wrapper = new QueryWrapper();
        wrapper.eq("ship_id",id);
        List<CabinBo> cabinBos = cabinMapper.selectList(wrapper);
        List<CabinVo> cabinVos = new ArrayList<>();
        for(CabinBo cabinBo:cabinBos){
            CabinVo cabinVo = new CabinVo();
            BeanUtils.copyProperties(cabinBo,cabinVo);
            cabinVos.add(cabinVo);
        }
        return  cabinVos;
    }
}
