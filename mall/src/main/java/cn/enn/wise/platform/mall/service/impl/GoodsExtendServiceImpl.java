package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.CabinBo;
import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.bo.ShipBo;
import cn.enn.wise.platform.mall.bean.bo.autotable.Cabin;
import cn.enn.wise.platform.mall.bean.vo.*;
import cn.enn.wise.platform.mall.constants.GoodsConstants;
import cn.enn.wise.platform.mall.constants.KnowConstants;
import cn.enn.wise.platform.mall.mapper.CabinMapper;
import cn.enn.wise.platform.mall.mapper.GoodsExtendMapper;
import cn.enn.wise.platform.mall.mapper.ShipMapper;
import cn.enn.wise.platform.mall.service.GoodsExtendService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.MallUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * <p>
 * 热气球时段表 服务实现类
 * </p>
 *
 * @author caiyt
 * @since 2019-05-24
 */
@Service
public class GoodsExtendServiceImpl extends ServiceImpl<GoodsExtendMapper, GoodsExtend> implements GoodsExtendService {



    @Autowired
    private CabinMapper cabinMapper;


    @Autowired
    private ShipMapper shipMapper;


    @Override
    public List<GoodsExtendResVo> getGoodsExtendInfoByGoodsId(long goodsId) {
        // 根据商品ID查询出所有的商品扩展数据
        List<GoodsExtend> goodsExtendList = this.getGoodsExtendByGoodsId(goodsId);
        if (goodsExtendList == null || goodsExtendList.isEmpty()) {
            return new LinkedList<>();
        }
        // 将数据库的数据封装为接口提供的数据
        List<GoodsExtendResVo> goodsExtendResVoList = new LinkedList<>();
        goodsExtendList.stream().forEach(goodsExtend -> {
            GoodsExtendResVo goodsExtendResVo = new GoodsExtendResVo();
            BeanUtils.copyProperties(goodsExtend, goodsExtendResVo);
            goodsExtendResVoList.add(goodsExtendResVo);
        });
        return goodsExtendResVoList;
    }

    @Override
    public List<GoodsExtend> getGoodsExtendByGoodsId(long goodsId) {
        // 根据商品ID查询出所有的商品扩展数据
        QueryWrapper<GoodsExtend> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(GoodsExtend::getGoodsId, goodsId)
                .eq(GoodsExtend::getStatus, GoodsConstants.GoodsExtendStatus.ENABLE.value())
                .orderByAsc(GoodsExtend::getOrderby);
        return this.list(queryWrapper);
    }

    @Override
    public boolean disableByGoodsId(long goodsId, SystemStaffVo staffVo) {
        QueryWrapper<GoodsExtend> goodsExtendQueryWrapper = new QueryWrapper<>();
        goodsExtendQueryWrapper.lambda().eq(GoodsExtend::getGoodsId, goodsId);
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.DISABLE.value());
        goodsExtend.setUpdateUserId(staffVo.getId());
        goodsExtend.setUpdateUserName(staffVo.getName());
        goodsExtend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        return this.update(goodsExtend, goodsExtendQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean saveGoodExtend(GoodsExtend goodsExtend) {
        if(goodsExtend.getTimespan()==null||goodsExtend.getTimespan().equals("")||goodsExtend.getPeriodId()==0){
            return this.save(goodsExtend);
        }
        // 持续时间 单位分钟
        String[] timeSpanArr = goodsExtend.getTimespan().split("-");
        int duration = MallUtil.minutesDiff2TimeStr(timeSpanArr[0].trim(), timeSpanArr[1].trim());
        goodsExtend.setDuration(duration);
        // 按照每半小时服务10人计算
        goodsExtend.setMaxNum(duration * 10 / 30);
        return this.save(goodsExtend);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(long goodsId, long periodId, String timeLabel, BigDecimal salePrice, SystemStaffVo staffVo) {
        QueryWrapper<GoodsExtend> goodsExtendQueryWrapper = new QueryWrapper<>();
        goodsExtendQueryWrapper.lambda().eq(GoodsExtend::getGoodsId, goodsId)
                .eq(GoodsExtend::getStatus, GoodsConstants.GoodsExtendStatus.ENABLE.value())
                .eq(GoodsExtend::getPeriodId, periodId);
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.ENABLE.value());
        goodsExtend.setSalePrice(salePrice);
        goodsExtend.setTimeLabel(timeLabel);
        goodsExtend.setUpdateUserName(staffVo.getName());
        goodsExtend.setUpdateUserId(staffVo.getId());
        goodsExtend.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        this.update(goodsExtend, goodsExtendQueryWrapper);
    }

    @Override
    public Set<Long> getPeriodIdsInUse(List<Long> periodIds) {
        // 查询项目时段在用的商品扩展数据
        QueryWrapper<GoodsExtend> goodsExtendQueryWrapper = new QueryWrapper<>();
        goodsExtendQueryWrapper.lambda()
                .eq(GoodsExtend::getStatus, GoodsConstants.GoodsExtendStatus.ENABLE.value())
                .in(GoodsExtend::getPeriodId, periodIds);
        List<GoodsExtend> goodsExtendList = this.list(goodsExtendQueryWrapper);
        // 将在用的项目时段ID收集起来
        Set<Long> periodIdsInUse = goodsExtendList.stream().map(x -> x.getPeriodId()).collect(Collectors.toSet());
        return periodIdsInUse;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteByGoodsId(List<Long> goodsIds, SystemStaffVo staffVo) {
        QueryWrapper<GoodsExtend> goodsExtendUpdateWrapper = new QueryWrapper<>();
        goodsExtendUpdateWrapper.lambda()
                .eq(GoodsExtend::getStatus, GoodsConstants.GoodsExtendStatus.ENABLE.value())
                .in(GoodsExtend::getGoodsId, goodsIds);
        Timestamp curTime = new Timestamp(System.currentTimeMillis());
        GoodsExtend goodsExtend = new GoodsExtend();
        goodsExtend.setStatus(GoodsConstants.GoodsExtendStatus.DELETED.value());
        goodsExtend.setUpdateUserName(staffVo.getName());
        goodsExtend.setUpdateUserId(staffVo.getId());
        goodsExtend.setUpdateTime(curTime);
        this.update(goodsExtend, goodsExtendUpdateWrapper);
    }

    /**
     * 获取航线
     * @param from
     * @param to
     * @return
     */
    @Override
    public ResponseEntity<LineVo> getLine(String data,String from, String to) {
        List<LineVo> result  = new ArrayList<>();
        LineVo lineVo = new LineVo();
        lineVo.setLineFrom("北海");
        lineVo.setLineTo("涠洲");
        result.add(lineVo);

        lineVo = new LineVo();
        lineVo.setLineFrom("涠洲");
        lineVo.setLineTo("北海");
        result.add(lineVo);

        lineVo = new LineVo();
        lineVo.setLineFrom("北海");
        lineVo.setLineTo("海口");
        result.add(lineVo);

        lineVo = new LineVo();
        lineVo.setLineFrom("海口");
        lineVo.setLineTo("北海");
        result.add(lineVo);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",result);
    }

    /**
     * 获取航线的票
     * @param date
     * @return
     */
    @Override
    public ResponseEntity<LineTicketVo> getTicketByDate(String date,String from,String to,Integer order) {
        List<LineTicketVo> result  = this.baseMapper.getLineTicket(date,from,to,order);
        if(result.isEmpty()){
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"暂时没有该航线");
        }
        result.forEach(x->{
            x.setShipLineObj(JSON.parseObject(x.getShipLineInfo()));
            x.setShipLineInfo("");
            Integer afterTime = x.getShipLineObj().getInteger("afterTime");
            x.setPrice(x.getPrice().replace(".00",""));
            x.setDuration(toHourAndMinute(afterTime));
        });
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,"成功",result);
    }

    /**
     * 转换成x小时x分钟
     * @param afterTime
     * @return
     */
    private String toHourAndMinute(Integer afterTime) {

        int time = afterTime;
        int hours = (int) Math.floor(time / 60);
        int minute = time % 60;

        return hours + "小时" + minute + "分钟";
    }

    /**
     * 获取详情
     *
     * @param date
     * @param from
     * @param to
     * @param startTime
     * @return
     */
    @Override
    public ResponseEntity<LineDetailVo> getTicketDetail(String date, String from, String to, String startTime,String shipName) {
        // 船详情
        LineDetailVo detailVo = new LineDetailVo();

        LineTicketVo lineTicketVo = this.baseMapper.getLineTicketByTime(date,from,to,startTime,shipName);
        if(lineTicketVo==null){
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"航线不存在");
        }
        lineTicketVo.setShipLineObj(JSON.parseObject(lineTicketVo.getShipLineInfo()));
        lineTicketVo.setShipLineInfo("");
        lineTicketVo.setNote(KnowConstants.TICKET_NODE);
        Integer afterTime = lineTicketVo.getShipLineObj().getInteger("afterTime");
        lineTicketVo.setDuration(toHourAndMinute(afterTime));

        // 船舱列表
        List<LineCabinVo> cabinVoList = this.baseMapper.getCabinList(date,from,to,startTime,shipName);
        if(cabinVoList.isEmpty()){
            return new ResponseEntity<>(GeneConstant.BUSINESS_ERROR,"船舱不存在");
        }
        detailVo.setCabin(cabinVoList);
        detailVo.setTicket(lineTicketVo);
        cabinVoList.forEach(x->{
            // TODO 仓图片
            x.setImg("http://travel.enn.cn/group1/M00/01/12/CiaAUl4Gv9-ANA46AAPfwgMrL7M255.png");
            x.setShipLineObj(JSON.parseObject(x.getShipLineInfo()));
            x.setShipLineInfo("");
            String cabinName =x.getCabinName();
            String nickName =x.getNickName();

            QueryWrapper<ShipBo> shipBoQueryWrapper = new QueryWrapper<>();
            shipBoQueryWrapper.eq("name",nickName);
            ShipBo shipBo = shipMapper.selectOne(shipBoQueryWrapper);
            if(shipBo!=null){
                QueryWrapper<CabinBo> wrapper = new QueryWrapper<>();
                wrapper.eq("name",cabinName);
                wrapper.eq("ship_id",shipBo.getId());
                Cabin cabinBo = cabinMapper.selectOne(wrapper);
                if(cabinBo!=null && cabinBo.getImgUrl()!=null){
                    x.setImg(cabinBo.getImgUrl());
                }
            }
            // TODO 查票库存
            x.setStock(x.getShipLineObj().getInteger("freeSeats"));
        });
        return new ResponseEntity<>(GeneConstant.SUCCESS_CODE,"成功",detailVo);
    }
}
