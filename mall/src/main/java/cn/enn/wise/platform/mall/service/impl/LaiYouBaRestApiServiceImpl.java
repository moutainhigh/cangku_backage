package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.vo.ParkInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SeatInfoVo;
import cn.enn.wise.platform.mall.service.LaiYouBaRestApiService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Service("laiYouBaRestApiService")
@Slf4j
public class LaiYouBaRestApiServiceImpl implements LaiYouBaRestApiService {

    RestTemplate restTemplate = new RestTemplate();
    private String domain="https://m.laiu8.cn";

    /**
     * 获取客户航班座位信息
     * @param idCard
     * @return
     */
    @Override
    public List<SeatInfoVo> findSeatInfoByUserInfo(String idCard) throws Exception {
        String url = domain+"/vip/ajaxTicketNo?id_card={id_card}";
        Map params = new HashMap() {{
            put("id_card", idCard);
        }};
        ResponseEntity<ResEntity1> responseEntity = restTemplate.getForEntity(url, ResEntity1.class,params);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if(statusCode!=HttpStatus.OK){
            log.error("座位获取失败,参数:idCard={}",idCard);
            throw new Exception("座位获取失败");
        }
        List<SeatInfoVo> seatInfoVos = new ArrayList<>();
        ResEntity1 body = responseEntity.getBody();
        List<Map> listData = (List<Map>) body.getData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(listData!=null)
        for (Map seatInfo : listData) {
            String idCardStr = String.valueOf(seatInfo.get("id_card"));
            String name = String.valueOf(seatInfo.get("name"));
            String lineName = String.valueOf(seatInfo.get("line_name"));
            String qrCode = String.valueOf(seatInfo.get("qr_code"));
            String shipDateStr = String.valueOf(seatInfo.get("ship_date"));
            String shipName = String.valueOf(seatInfo.get("ship_name"));
            String ticketNo = String.valueOf(seatInfo.get("ticket_no"));

            Date shipDate = dateFormat.parse(shipDateStr);
            SeatInfoVo seatInfoVo = new SeatInfoVo(idCard,lineName,name,qrCode,shipDate,shipName,ticketNo);
            seatInfoVo.setShipDateStr(shipDateStr);
            seatInfoVos.add(seatInfoVo);
        }
        // 按时间排序
        seatInfoVos = seatInfoVos.stream().sorted((o1, o2) -> o1.getShipDate().after(o2.getShipDate())?1:-1).collect(Collectors.toList());
        return seatInfoVos;
    }


    /**
     * 获取全部停车场信息
     * @return
     * @throws Exception
     */
    @Override
    public List<ParkInfoVo> findAllParkingInfo() throws Exception {
        String url = domain+"/intelligentapi/parkingdata";
        Map params = new HashMap();
        ResponseEntity<ResEntity1> responseEntity = restTemplate.getForEntity(url, ResEntity1.class,params);
        HttpStatus statusCode = responseEntity.getStatusCode();
        if(statusCode!=HttpStatus.OK){
            log.error("获取全部停车场信息");
            throw new Exception("获取全部停车场信息");
        }
        List<ParkInfoVo> parkInfoVos = new ArrayList<>();
        ResEntity1 body = responseEntity.getBody();
        List<Map> listData = (List<Map>) body.getData();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for (Map seatInfo : listData) {
            String feeDescribe = String.valueOf(seatInfo.get("fee_describe"));
            Integer parkId = (Integer)seatInfo.get("park_id");
            Integer total = (Integer)seatInfo.get("total");
            Integer usable = (Integer)seatInfo.get("usable");
            Integer used = (Integer)seatInfo.get("used");
            String parkInfo = String.valueOf(seatInfo.get("park_info"));
            String updateTimeStr = String.valueOf(seatInfo.get("update_time"));
            String shipName = String.valueOf(seatInfo.get("ship_name"));
            String ticketNo = String.valueOf(seatInfo.get("ticket_no"));

            Date updateTime = dateFormat.parse(updateTimeStr);
            ParkInfoVo parkInfoVo = new ParkInfoVo(parkId, feeDescribe, parkInfo, updateTime, total, usable, used);
            parkInfoVos.add(parkInfoVo);
        }
        return parkInfoVos;
    }






}

/**
 * 接口响应体类型1
 */
@Data
class ResEntity1{
    private int status;
    private String msg;
    private Object data;
}
/**
 * 接口响应体类型1
 */
@Data
class ResEntity2{
    private int code;
    private Object data;
}
