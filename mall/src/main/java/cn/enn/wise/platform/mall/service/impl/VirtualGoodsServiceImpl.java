package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.VirtualGoods;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.param.VirtualGoodsReqParam;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.bean.vo.VirtualGoodsResVO;
import cn.enn.wise.platform.mall.mapper.VirtualGoodsMapper;
import cn.enn.wise.platform.mall.service.VirtualGoodsService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.exception.BusinessException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author: lishuiquan
 * @date 2019-10-29
 * 虚拟商品ServiceImpl
 */
@Service
public class VirtualGoodsServiceImpl extends ServiceImpl<VirtualGoodsMapper, VirtualGoods> implements VirtualGoodsService {

    private static final SimpleDateFormat df = new SimpleDateFormat("yyyy");

    /**
     * 商品编号前缀
     */
    private static final String GOODS_ID_PREFIX = "DS";
    /**
     * 删除状态为2
     */
    private static final byte DELETE_STATUS = 2;

    private static final int GOODS_ID_MAX_LENGTH = 5;

    @Autowired
    private VirtualGoodsMapper virtualGoodsMapper;

    @Override
    public VirtualGoodsResVO getVirtualGoodsById(Long id) {
        if(id==null){
            throw new BusinessException(GeneConstant.PARAM_INVALIDATE, "商品id不能为空");
        }
        VirtualGoodsResVO virtualGoodsResVO = null;
        VirtualGoods virtualGoods = virtualGoodsMapper.selectById(id);
        if(virtualGoods!=null){
            virtualGoodsResVO = new VirtualGoodsResVO();
            BeanUtils.copyProperties(virtualGoods,virtualGoodsResVO);
        }
        return virtualGoodsResVO;
    }

    @Override
    public Long addVirtualGood(VirtualGoodsReqParam virtualGoodsReqParam, SystemStaffVo systemStaffVo) {
        VirtualGoodsReqParam.validateSaveVirtualGoodsReqParam(virtualGoodsReqParam);
        VirtualGoods virtualGoods = new VirtualGoods();
        BeanUtils.copyProperties(virtualGoodsReqParam, virtualGoods);
        // 保存商品基本信息
        virtualGoods.setCreateUserId(systemStaffVo.getId());
        virtualGoods.setCreateUserName(systemStaffVo.getName());
        virtualGoods.setCreateTime(new Timestamp(System.currentTimeMillis()));
        virtualGoodsMapper.insert(virtualGoods);
        virtualGoods.setGoodsId(buildGoodsId(virtualGoods.getId()));
        virtualGoodsMapper.updateById(virtualGoods);
        return virtualGoods.getId();

    }

    @Override
    public Integer modifyVirtualGood(VirtualGoodsReqParam virtualGoodsReqParam,SystemStaffVo systemStaffVo) {
        VirtualGoodsReqParam.validateSaveVirtualGoodsReqParam(virtualGoodsReqParam);
        VirtualGoods virtualGoods = buildUpdateVirtualGoods(virtualGoodsReqParam,systemStaffVo);
        return virtualGoodsMapper.updateById(virtualGoods);
    }

    @Override
    public Integer updateGoodsStatus(VirtualGoodsReqParam virtualGoodsReqParam, SystemStaffVo systemStaffVo) {
        VirtualGoods virtualGoods = buildUpdateVirtualGoods(virtualGoodsReqParam,systemStaffVo);
        return virtualGoodsMapper.updateById(virtualGoods);
    }

    @Override
    public Integer deleteGoods(VirtualGoodsReqParam virtualGoodsReqParam, SystemStaffVo systemStaffVo) {
        VirtualGoods virtualGoods = buildUpdateVirtualGoods(virtualGoodsReqParam,systemStaffVo);
        virtualGoods.setIsDelete(DELETE_STATUS);
        return virtualGoodsMapper.updateById(virtualGoods);
    }

    @Override
    public ResPageInfoVO<List<VirtualGoodsResVO>> getVirtualGoodsList(ReqPageInfoQry<VirtualGoodsReqParam> virtualGoodsPageQry) {
        //构建分页查询条件
        QueryWrapper<VirtualGoods> virtualGoodsWapper = new QueryWrapper<>();
        if (virtualGoodsPageQry != null) {
            VirtualGoods virtualGoods = new VirtualGoods();
            if(virtualGoodsPageQry.getReqObj()!=null){
                BeanUtils.copyProperties(virtualGoodsPageQry.getReqObj(), virtualGoods);
            }
            virtualGoodsWapper = new QueryWrapper<>(virtualGoods);
        }
        Page<VirtualGoods> pageParam = new Page<>(virtualGoodsPageQry.getPageNum(), virtualGoodsPageQry.getPageSize());
        IPage<VirtualGoods> dbPageInfo = this.page(pageParam, virtualGoodsWapper);

        //分页结果输出
        List<VirtualGoodsResVO> virtualGoodsResVOList  = dbPageInfo.getRecords().stream().map(virtualGoods->{
            VirtualGoodsResVO virtualGoodsResVO = new VirtualGoodsResVO();
            BeanUtils.copyProperties(virtualGoods,virtualGoodsResVO);
            return virtualGoodsResVO;
        }).collect(Collectors.toList());

        ResPageInfoVO<List<VirtualGoodsResVO>> resPageInfo = new ResPageInfoVO();
        resPageInfo.setPageNum(dbPageInfo.getCurrent());
        resPageInfo.setPageSize(dbPageInfo.getSize());
        resPageInfo.setTotal(dbPageInfo.getTotal());
        resPageInfo.setRecords(virtualGoodsResVOList);
        return resPageInfo;
    }

    /**
     * 构建商品编号
     * @return
     */
    private String buildGoodsId(Long goodsId){
        int length = String.valueOf(goodsId).length();
        String newGoodsId = GOODS_ID_PREFIX+df.format(new Date());
        for(int i = 0;i<GOODS_ID_MAX_LENGTH-length;i++){
            newGoodsId+="0";
        }
        newGoodsId+=goodsId;
        return newGoodsId;
    }

    /**
     * 验证并构建修改数据
     * @param virtualGoodsReqParam
     * @param systemStaffVo
     * @return VirtualGoods
     */
    private VirtualGoods buildUpdateVirtualGoods(VirtualGoodsReqParam virtualGoodsReqParam, SystemStaffVo systemStaffVo){
        VirtualGoodsReqParam.validateUpdateGoodsReqParam(virtualGoodsReqParam);
        VirtualGoods virtualGoods = new VirtualGoods();
        if(virtualGoodsReqParam!=null){
            BeanUtils.copyProperties(virtualGoodsReqParam, virtualGoods);
        }
        virtualGoods.setUpdateUserId(systemStaffVo.getId());
        virtualGoods.setUpdateUserName(systemStaffVo.getName());
        virtualGoods.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return virtualGoods;
    }
}
