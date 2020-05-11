package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.*;
import cn.enn.wise.platform.mall.bean.param.*;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.mapper.*;
import cn.enn.wise.platform.mall.service.*;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * 船舶服务
 * @author jiabaiye
 * @since 2019/12/25
 */
@Service
public class ShipServiceImpl extends ServiceImpl<ShipMapper, ShipBo> implements ShipService {

    @Autowired
    private ShipMapper shipMapper;

    @Autowired
    private CabinService cabinService;

    @Autowired
    private GoodsExtendMapper goodsExtendMapper;

    @Autowired
    private CabinMapper cabinMapper;

    @Override
    public ResponseEntity<ResPageInfoVO<List<ShipVo>>> listByPage(ReqPageInfoQry<ShipParam> param) {

        ShipParam shipReqQry = param.getReqObj();
        // 封装请求参数
        QueryWrapper<ShipBo> qryWapper = new QueryWrapper<>();
        if (shipReqQry != null) {
            ShipBo shipQry = new ShipBo();
            BeanUtils.copyProperties(shipReqQry, shipQry);
            qryWapper = new QueryWrapper<>(shipQry);
            if (shipQry.getStatus() != null) {
                qryWapper.lambda().eq(ShipBo::getStatus, shipQry.getStatus());
            }
            if (shipQry.getShipType() != null) {
                qryWapper.lambda().eq(ShipBo::getShipType, shipQry.getShipType());
            }
            if (shipQry.getOrganization() != null) {
                qryWapper.lambda().eq(ShipBo::getOrganization, shipQry.getOrganization());
            }
            if (shipQry.getThirdId() != null) {
                qryWapper.lambda().eq(ShipBo::getThirdId, shipQry.getThirdId());
            }
        }
        qryWapper.lambda().orderByAsc(ShipBo::getCode);
        // 分页参数
        Page<ShipBo> pageParam = new Page<>(param.getPageNum(), param.getPageSize());
        // 根据条件查询数据库
        IPage<ShipBo> dbPageInfo = this.page(pageParam, qryWapper);
        ResPageInfoVO resPageInfo = new ResPageInfoVO();
        // 分页信息
        resPageInfo.setPageNum(dbPageInfo.getCurrent());
        resPageInfo.setPageSize(dbPageInfo.getSize());
        resPageInfo.setTotal(dbPageInfo.getTotal());
        List<ShipBo> shipBos = dbPageInfo.getRecords();
        // 如果无数据则直接返回
        if (shipBos == null || shipBos.isEmpty()) {
            return new ResponseEntity<ResPageInfoVO<List<ShipVo>>>(resPageInfo);
        }
        List<ShipVo> shipVoList = new ArrayList<>();
        for(ShipBo shipBo:shipBos){
            ShipVo shipVo = new ShipVo();
            BeanUtils.copyProperties(shipBo,shipVo);
            shipVoList.add(shipVo);
        }
        resPageInfo.setRecords(shipVoList);
        return new ResponseEntity<ResPageInfoVO<List<ShipVo>>>(resPageInfo);
    }



    @Override
    public ResponseEntity<ShipVo> updateImgUrlById(ShipParam param,Long userId) {

        ShipBo shipBo= shipMapper.selectById(param.getId());
        shipBo.setImgUrl(param.getImgUrl());
        shipBo.setUpdateUserId(userId);
        shipBo.setUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        shipMapper.updateById(shipBo);
        ShipVo shipVo = new ShipVo();
        BeanUtils.copyProperties(shipBo,shipVo);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"图片更新成功",shipVo);

    }
    @Override
    public ResponseEntity<ShipVo> getShipById(Long id){
        ShipBo shipBo= shipMapper.selectById(id);
        ShipVo shipVo = new ShipVo();
        BeanUtils.copyProperties(shipBo,shipVo);
        List<CabinVo> cabinVos= cabinService.getCabinList(id);
        shipVo.setCabinVoList(cabinVos);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"获取详情成功",shipVo);
    }

    /**
     * 自动同步船舶、船舱信息
     */
    @Scheduled(cron = "0 */1 *  * * * ")
    public void autoCreateGroupOrder(){
        List<GoodsExtend> goodsExtends = goodsExtendMapper.getShipName();

        for(GoodsExtend goodsExtend:goodsExtends){
            List<GoodsExtend> goodsExtendList = goodsExtendMapper.selectList(new QueryWrapper<GoodsExtend>().eq("nick_name",goodsExtend.getNickName()));
            if(goodsExtendList!=null||goodsExtendList.size()>0){
                ShipBo shipBo = new ShipBo();
                ShipLineInfo shipLineInfo= JSONObject.parseObject(goodsExtendList.get(0).getShipLineInfo(),ShipLineInfo.class);
                shipBo.setName(goodsExtend.getNickName());
                shipBo.setShipType(1);
                shipBo.setOrganization(1);
                shipBo.setStatus(GeneConstant.BYTE_1);
                if(shipLineInfo!=null){
                    shipBo.setThirdId(shipLineInfo.getShipID());
                    shipMapper.insert(shipBo);
                }
            }
        }

        List<GoodsExtend> goodsExtendList1 = goodsExtendMapper.getCabin();

        for(GoodsExtend goodsExtend1:goodsExtendList1){
            ShipBo shipBo1 = shipMapper.selectOne(new QueryWrapper<ShipBo>().eq("name",goodsExtend1.getNickName()));
            if(shipBo1==null){
                continue;
            }
            List<GoodsExtend> goodsExtendList = goodsExtendMapper.selectList(new QueryWrapper<GoodsExtend>().eq("nick_name",goodsExtend1.getNickName()).eq("cabin_name",goodsExtend1.getCabinName()));
            ShipLineInfo shipLineInfo1= JSONObject.parseObject(goodsExtendList.get(0).getShipLineInfo(),ShipLineInfo.class);
            CabinBo cabinBo = new CabinBo();
            cabinBo.setCabinId(shipLineInfo1.getClCabinID().toString());
            cabinBo.setName(goodsExtend1.getCabinName());
            cabinBo.setStatus(GeneConstant.BYTE_1);
            cabinBo.setShipId(shipBo1.getId());
            cabinMapper.insert(cabinBo);
        }
    }
}
