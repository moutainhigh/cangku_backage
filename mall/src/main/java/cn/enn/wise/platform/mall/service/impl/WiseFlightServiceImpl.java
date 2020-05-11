package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.vo.ParkInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SeatInfoVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.service.LaiYouBaRestApiService;
import cn.enn.wise.platform.mall.service.WiseFlightService;
import cn.enn.wise.platform.mall.util.MessageSender;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;

@Slf4j
@Service("wiseFlightService")
public class WiseFlightServiceImpl implements WiseFlightService {

    @Autowired
    LaiYouBaRestApiService laiYouBaRestApiService;
    @Autowired
    MessageSender messageSender;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    @Value("${url.hostprefix}${url.memberBasePath}")
    String MemberContextPath;
    @Autowired
    HttpServletRequest request;

    @Value("${spring.profiles.active}")
    String profilesActive;

    RestTemplate restTemplate = new RestTemplate();

    /**
     * 获取最近航班信息
     * @param user
     * @return
     */
    @Override
    public List<SeatInfoVo> getSeatByUser(User user) {
        String idCard = user.getIdCard();
//        String idCardOld="340122195803086915";
//        String name="李晓苏";
        String phone = user.getPhone();
        Date now = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(now);
        calendar.add(Calendar.HOUR_OF_DAY,-2);
        now = calendar.getTime();

        try {
            List<SeatInfoVo> seatInfoVoList = laiYouBaRestApiService.findSeatInfoByUserInfo(idCard);
            Date finalNow = now;
            //筛选 当天的并查询时间前2小时后全部的订单内容
            seatInfoVoList = seatInfoVoList.stream().filter(seatInfoVo -> {
                return seatInfoVo.getShipDate().after(finalNow)||seatInfoVo.getShipDate().equals(finalNow);
            }).collect(Collectors.toList());
            for (SeatInfoVo seatInfoVo : seatInfoVoList) {
                seatInfoVo.setPhone(phone);
            }
            return seatInfoVoList;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<ParkInfoVo> getParkingList() {
        try {
            return laiYouBaRestApiService.findAllParkingInfo();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    private HashMap<String,String> managerInfoMap=null;

    @Override
    public Boolean requisitionAdd(User user, String content, String name) throws Exception {
        if(profilesActive.trim().equals("prod")){
            managerInfoMap = new HashMap<String,String>(){{
                put("北游28","庞海英,13977905191");
                put("北游18","杨晓华,17777977500");
                put("北游12","王莉莉,18169798266");
                put("北游16","韦春雪,13768096886");
                put("北游15","孙海云,13557798020");
                put("北部湾2号","赵山祥,13507799826");
                put("北部湾3号","李彬,13977921934");
            }};
        }else{
            managerInfoMap = new HashMap<String,String>(){{
                put("北游28","张超,18611369632");
                put("北游18","张超,18611369632");
                put("北游12","张超,18611369632");
                put("北游16","张超,18611369632");
                put("北游15","张超,18611369632");
                put("北部湾2号","张超,18611369632");
                put("北部湾3号","张超,18611369632");
            }};
        }

        if(Strings.isEmpty(content)) return false;
        String phone = user.getPhone();
        List<SeatInfoVo> seatInfoVoList = getSeatByUser(user);
        if(seatInfoVoList.size()==0){
            return false;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        SeatInfoVo seatInfoVo = seatInfoVoList.get(0);
        Date shipDate = seatInfoVo.getShipDate();
        String shipDateStr = dateFormat.format(shipDate);//航班时间
        String lineName = seatInfoVo.getLineName();
        String shipName = seatInfoVo.getShipName();
        String ticketNo = seatInfoVo.getTicketNo();



        //获取乘务长信息
        String manageInfo=null;
        Set set = managerInfoMap.keySet();
        for (Object mapKey : set) {
            String key = (String) mapKey;
            if(shipName.contains(key)){
                manageInfo = managerInfoMap.get(key);
                break;
            }
        }
        if(manageInfo==null) return false;
        String managerName = manageInfo.split(",")[0];
        String managerPhone = manageInfo.split(",")[1];

        //发送短信
        HashMap<String, String> map = new HashMap<>();
        map.put("managerName", managerName);
        map.put("flightTime", shipDateStr);
        map.put("flightLine", lineName);
        map.put("seatInfo", shipName+" "+ticketNo);
        map.put("guestName", name);
        map.put("guestPhone", phone);
        map.put("requisitionText", content);

        map.put("companyId", String.valueOf(11));
        map.put("phone", managerPhone);
        map.put("type", String.valueOf(7));
        try{
            messageSender.sendSmsV2(map);
            redisTemplate.opsForValue().set("requisitionOrder_"+phone+"_"+shipDateStr, JSON.toJSONString(map));
            return true;
        }catch (Exception e){
            log.error("短信发送失败，参数：[{}]",map);
            throw new Exception("短信发送失败");
        }
    }

    @Override
    public boolean bindUserMessage(User user, String idCard, String name, String phone) {
        String companyId = request.getHeader("companyId");
        String appId = request.getHeader("appId");
        String openId = request.getHeader("openId");

        //绑定身份证
        String url1 = MemberContextPath+"/user/update/id/card";
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.add("idCard",idCard);
        HttpHeaders header = new HttpHeaders();
        header.add("companyId",companyId);
        header.add("appId",appId);
        header.add("openId",openId);
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, header);
        ResponseEntity<JSONObject> forEntity = restTemplate.postForEntity(url1, httpEntity, JSONObject.class);
        if(forEntity.getStatusCode()!= HttpStatus.OK&&forEntity.getBody()==null){
            return false;
        }
        JSONObject body = forEntity.getBody();
        int result = body.getIntValue("result");
        if(result!=1) return false;



        //绑定手机号
        String url3 = MemberContextPath+"/user/update/phone";
        MultiValueMap<String, String> map3 = new LinkedMultiValueMap<>();
        map3.add("phone",phone);
        // 需求需要传参为form-data格式
        header.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, String>> httpEntity3 = new HttpEntity<>(map3, header);
        ResponseEntity<JSONObject> forEntity3 = restTemplate.postForEntity(url3, httpEntity3, JSONObject.class);
        if(forEntity3.getStatusCode()!= HttpStatus.OK&&forEntity3.getBody()==null){
            return false;
        }
        JSONObject body3 = forEntity3.getBody();
        int result3 = body3.getIntValue("result");
        if(result3!=1) return false;
        return true;
    }
}
