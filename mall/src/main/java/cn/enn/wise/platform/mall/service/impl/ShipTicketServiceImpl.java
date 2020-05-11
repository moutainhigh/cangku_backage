package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.GoodsExtend;
import cn.enn.wise.platform.mall.bean.vo.ShipLineInfo;
import cn.enn.wise.platform.mall.mapper.GoodsExtendMapper;
import cn.enn.wise.platform.mall.service.ShipTicketService;
import cn.enn.wise.platform.mall.util.thirdparty.LalyoubaShipHttpApiUtil;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShiftData;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShiftInfo;
import cn.enn.wise.platform.mall.util.thirdparty.laiyoubovo.ShipBaseVo;
import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;

/**
 * 船票服务
 */
@Service("shipTicketService")
@Slf4j
public class ShipTicketServiceImpl implements ShipTicketService {

    @Autowired
    GoodsExtendMapper goodsExtendMapper;
    @Value("${spring.profiles.active}")
    String active;
//    String active="prod";

//    @Scheduled(fixedRate = 1000*60*5)
    public void updateShipLineInfo(){
        updateShipDataToGoods();
    }

    @Override
    public void updateShipDataToGoods(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hhmmss = new SimpleDateFormat("HH:mm");

        List<String> lineList = Arrays.asList("16,17", "17,16", "16,18","18,16");

        for (String line : lineList) {  //多个航线
            String iStart = line.split(",")[0];
            String iArrive = line.split(",")[1];

            Calendar now = Calendar.getInstance();
            for (int i = 0; i < 30; i++) {      //30天预售期
                ArrayList<Long> updatedIds = new ArrayList<>();
                Date time = now.getTime();
                String dDate = dateFormat.format(time);
                ShipBaseVo<ShiftData> baseVo = LalyoubaShipHttpApiUtil.getShift(active, iStart, iArrive, dDate);
                if(baseVo==null||baseVo.getStatus()!=1||baseVo.getData()==null) continue;
                ShiftData shiftData = baseVo.getData();
                if(shiftData==null) continue;
                List<ShiftInfo> flight = shiftData.getFlight();
                for (ShiftInfo shiftInfo : flight) {

                    String startPortName = shiftInfo.getStartPortName();
                    String arrivePortName = shiftInfo.getArrivePortName();
                    String cabinName = shiftInfo.getCabinName();
                    String shipName = shiftInfo.getShipName();
                    Float ticketFullPrice = shiftInfo.getTicketFullPrice();
                    String clTime = shiftInfo.getClTime();

                    String lineID = shiftInfo.getLineID();
                    String shipID = shiftInfo.getShipID();
                    String clCabinID = shiftInfo.getClCabinID();
                    Long startPortID = shiftInfo.getStartPortID();
                    Long arrivePortID = shiftInfo.getArrivePortID();

                    Long freeSeats = shiftInfo.getFreeSeats();

                    String fromInfo = getPortNameByCityName(startPortName);
                    String arriveInfo = getPortNameByCityName(arrivePortName);




                    int afterTime = getAfterTimeByShipName(startPortName + "-" + arrivePortName, shipName);
                    String arriveTime="";
                    Date startTimeD=null;
                    Date arriveTimeD=null;
                    try {
                        startTimeD = hhmmss.parse(clTime);
                        startTimeD.setYear(time.getYear());
                        startTimeD.setMonth(time.getMonth());
                        startTimeD.setDate(time.getDate());
                        Calendar arriveTimeC = Calendar.getInstance();
                        arriveTimeC.setTime(startTimeD);
                        arriveTimeC.add(Calendar.MINUTE,afterTime);
                        arriveTimeD = arriveTimeC.getTime();
                        arriveTime = hhmmss.format(arriveTimeD);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    BigDecimal salePrice = shiftInfo.getDiscountType().trim().equals("0")?new BigDecimal(ticketFullPrice):new BigDecimal(Float.valueOf(shiftInfo.getDiscountPrice()));
                    //船票附加信息
                    ShipLineInfo shipLineInfo = new ShipLineInfo(lineID, shipID, clCabinID, startPortID, arrivePortID,clTime,
                            arriveTime,afterTime,fromInfo,arriveInfo,freeSeats.intValue(),salePrice);
                    String shipLineInfoStr = JSON.toJSONString(shipLineInfo);

                    //更新的数据
                    GoodsExtend updateBean = new GoodsExtend();
                    updateBean.setShipLineInfo(shipLineInfoStr);
                    updateBean.setSalePrice(salePrice);
                    updateBean.setStatus(Byte.valueOf("1"));
                    updateBean.setEndTime(arriveTimeD);



                    LambdaQueryWrapper<GoodsExtend> wrapper = new LambdaQueryWrapper<>();
                    wrapper.eq(GoodsExtend::getLineFrom,startPortName)
                            .eq(GoodsExtend::getLineTo,arrivePortName)
                            .eq(GoodsExtend::getLineDate,dDate)
                            .eq(GoodsExtend::getCabinName,cabinName)
                            .eq(GoodsExtend::getNickName,shipName)
                            .eq(GoodsExtend::getStartTime,startTimeD);

                    List<Long> ids = goodsExtendMapper.selectIdsByWrapper(wrapper); //待更新ids

                    int update = goodsExtendMapper.update(updateBean, wrapper);     //更新价格和剩余座位

                    if(update==0){


                        GoodsExtend adultTicket = new GoodsExtend(1000L, clTime, Byte.valueOf("0"), BigDecimal.ZERO, null);
                        GoodsExtend childTicket = new GoodsExtend(1000L, clTime, Byte.valueOf("0"), BigDecimal.ZERO, null);


                        adultTicket.setLineFrom(startPortName);
                        adultTicket.setLineTo(arrivePortName);
                        adultTicket.setLineDate(dDate);
                        adultTicket.setCabinName(cabinName);
                        adultTicket.setTicketType("101");
                        adultTicket.setNickName(shipName);
                        adultTicket.setShipLineInfo(shipLineInfoStr);
                        adultTicket.setSalePrice(salePrice);
                        adultTicket.setStartTime(startTimeD);
                        adultTicket.setEndTime(arriveTimeD);
                        adultTicket.setStatus(Byte.valueOf("1"));  //增加默认上架

                        childTicket.setLineFrom(startPortName);
                        childTicket.setLineTo(arrivePortName);
                        childTicket.setLineDate(dDate);
                        childTicket.setCabinName(cabinName);
                        childTicket.setTicketType("203");
                        childTicket.setNickName(shipName);
                        childTicket.setShipLineInfo(shipLineInfoStr);
                        childTicket.setSalePrice(salePrice);
                        childTicket.setStartTime(startTimeD);
                        childTicket.setEndTime(arriveTimeD);
                        adultTicket.setStatus(Byte.valueOf("1"));   //增加默认上架

                        goodsExtendMapper.insert(adultTicket);
                        goodsExtendMapper.insert(childTicket);

                        updatedIds.add(adultTicket.getId());
                        updatedIds.add(childTicket.getId());
                    }else{
                        updatedIds.addAll(ids);
                    }
                }
                if(updatedIds.size()==0) updatedIds.add(-1L);

                //更新未查到的航班数据为下架  status=2
                String iStartName = getPortNameByPortId(iStart);
                String iArriveName = getPortNameByPortId(iArrive);
                LambdaQueryWrapper<GoodsExtend> wrapper = new LambdaQueryWrapper<>();
                wrapper.eq(GoodsExtend::getLineFrom,iStartName)
                        .eq(GoodsExtend::getLineTo,iArriveName)
                        .eq(GoodsExtend::getLineDate,dDate)
                        .notIn(GoodsExtend::getId,updatedIds);
                GoodsExtend goodsExtend = new GoodsExtend();
                goodsExtend.setStatus(Byte.valueOf("2"));

                goodsExtendMapper.update(goodsExtend,wrapper);

                now.add(Calendar.DATE,1);


            }



        }


    }

    private String getPortNameByPortId(String id) {
        if("16".equals(id)){
            return "北海";
        }else if("17".equals(id)){
            return "涠洲";
        }else if("18".equals(id)){
            return "海口";
        }
        return null;
    }


    /**
     * 根据航线和船名 计算经过时间
     * @param lineName 航线  例: 涠洲岛-北海
     * @param shipName 船名
     * @return 经过时间， 单位分钟
     */
    public int getAfterTimeByShipName(String lineName,String shipName){
        if(lineName.contains("涠洲")){
            String smallShipNames = ",北游18,北游28,北部湾2号,北部湾3号,";
            String fastShipNames = ",北游12,北游16,";
            String slowNames = ",北部湾2号*,北部湾3号*,";
            if(smallShipNames.contains(","+shipName+",")){
                return 90;
            }else if(fastShipNames.contains(","+shipName+",")){
                return 80;
            }else if(slowNames.contains(","+shipName+",")){
                return 150;
            }
            else{
                return 80;
            }
        }else if(lineName.contains("海口")){
            if (lineName.contains("北海-海口")){
                return 60*12;
            }
            return 60*11;
        }else{
            return 0;
        }
    }

    private String getPortNameByCityName(String cityName){
        cityName = cityName.trim();
        if("涠洲".equals(cityName)){
            return "西角码头";
        }else if("北海".equals(cityName)){
            return "国际客运港码头";
        }else if("海口".equals(cityName)){
            return "秀英港码头";
        }else{
            return "";
        }
    }


}
