package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.vo.BusGisVo;
import cn.enn.wise.platform.mall.bean.vo.BusVo;
import cn.enn.wise.platform.mall.bean.vo.PoiVo;
import cn.enn.wise.platform.mall.bean.vo.StationVo;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


/******************************************
 * @author: anhui
 * @createDate: 2019/5/24 11:05
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:二维码API
 ******************************************/
@Api(value = "智行涠洲", tags = { "智行涠洲" })
@RestController
@RequestMapping("/bus")
public class BusController {

    private static String BUS_LIST = "https://m.laiu8.cn/bus/getrecomendstations?coordinate=";
    private static String LINE_ADDRESS = "https://m.laiu8.cn/bus/getlinedetail?stationId={stationId}&lineNo={lineNo}&direction={direction}&stationOrder=0";

    /**
     * 公交列表
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(value = "公交列表", notes = "公交列表")
    public ResponseEntity<List<BusVo>> busList(String coordinate, String direction) {
        String url = BUS_LIST + coordinate;
        List<BusVo> busList = getBusVoList(url, direction);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "成功", busList);
    }

    /**
     * 获取公交信息
     *
     * @return
     */
    private List<BusVo> getBusVoList(String url, String direction) {
        // 公交车列表
        List<BusVo> list = new ArrayList<>();
        String busJson = HttpClientUtil.get(url);
        JSONObject busObject = JSON.parseObject(busJson);
        JSONObject data = busObject.getJSONObject("data");
        JSONObject stationList = data.getJSONObject("stationIdList");
        // 公交车站
        Object[] arrayList = stationList.values().toArray();
        for (Integer i = 0; i < arrayList.length; i++) {
            JSONObject object = (JSONObject) arrayList[i];
            BusVo bus = getBusVo(direction, object);
            list.add(bus);
        }
        // 最近公交站
        for (BusVo busVo : list) {
            for (StationVo stationVo : busVo.getLineInfo()) {
                if (busVo.getIfRecommend() == 1 && busVo.getStationId().equals(stationVo.getSId())) {
                    busVo.setNearestStationName(stationVo.getName());
                }
            }
        }
        return list;
    }

    /**
     * 获得BusVo
     *
     * @param direction
     * @param object
     * @return BusVo
     */
    private BusVo getBusVo(String direction, JSONObject object) {

        String lineNo = object.getString("lineNo");
        String stationId = object.getString("stationId");
        Integer ifRecommend = object.getInteger("ifRecoomend");
        String lineAddress = LINE_ADDRESS;
        lineAddress = lineAddress.replace("{lineNo}", lineNo).replace("{stationId}", stationId).replace("{direction}", direction);

        String lineJson = HttpClientUtil.get(lineAddress);
        JSONObject lineObject = JSON.parseObject(lineJson);
        JSONObject lineData = lineObject.getJSONObject("data");
        JSONObject line = lineData.getJSONObject("line");
        String startSn = line.getString("startSn");
        String endSn = line.getString("endSn");
        String firstTime = line.getString("firstTime");
        String lastTime = line.getString("lastTime");
        String price = line.getString("price");

        BusVo bus = new BusVo();
        bus.setLineNo(lineNo);
        bus.setStationId(stationId);
        bus.setIfRecommend(ifRecommend);
        bus.setStartTime(firstTime);
        bus.setEndTime(lastTime);
        bus.setPrice(price);
        bus.setStartStationName(startSn);
        bus.setEndStationName(endSn);
        bus.setUserTime(-1);

        JSONArray lineArray = line.getJSONArray("lineInfo");
        JSONObject travelTime = line.getJSONObject("travelTime");
        String minuteKey = "minute";
        if (travelTime.containsKey(minuteKey)) {
            bus.setUserTime(travelTime.getInteger("minute"));
        }

        // 站点
        List<StationVo> stationVos = new ArrayList<>();
        for (Integer j = 0; j < lineArray.size(); j++) {
            JSONObject lineObj = (JSONObject) lineArray.get(j);
            addStationVos(stationVos, lineObj);
        }
        // 换乘站
        JSONArray changeStationIds = lineData.getJSONArray("changeStationIds");
        for (Integer k = 0; k < changeStationIds.size(); k++) {
            for (StationVo stationVo : stationVos) {
                if (changeStationIds.get(k).equals(stationVo.getSId())) {
                    stationVo.setIsChangeStation("yes");
                }
            }
        }
        // 当前公交车停靠站
        JSONArray busArray = lineData.getJSONArray("busList");
        for (Integer k = 0; k < busArray.size(); k++) {
            Double distance = Double.valueOf(busArray.get(k).toString());
            for (StationVo stationVo : stationVos) {
                if (distance.intValue() == stationVo.getOrder()) {
                    stationVo.setIsCurrentStation("yes");
                }
            }
        }
        bus.setLineInfo(stationVos);
        return bus;
    }

    /**
     * 添加到集合
     *
     * @param stationVos
     * @param lineObj
     */
    private void addStationVos(List<StationVo> stationVos, JSONObject lineObj) {
        StationVo station = new StationVo();
        station.setLat(lineObj.getString("lat"));
        station.setLng(lineObj.getString("lng"));
        station.setName(lineObj.getString("sn"));
        station.setOrder(lineObj.getInteger("order"));
        station.setSId(lineObj.getString("sId"));
        station.setIsChangeStation("no");
        station.setIsCurrentStation("no");
        stationVos.add(station);
    }

    /**
     * 租车、电三轮信息
     *
     * @return
     */
    @RequestMapping(value = "/gis", method = RequestMethod.GET)
    @ApiOperation(value = "公交列表", notes = "公交列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "Long", name = "type", value = "类型:1厕所 2停车场 3小卖部 4暖水服务 5租车 501商务轿车 502便捷小车 6咨询台", required = true)})
    public ResponseEntity<List<BusGisVo>> busGisList(Long type) {

        List<PoiVo> poiVoList = new ArrayList<>();

        PoiVo poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("国客港码头");
        poiVo.setLng(109.12945399772632);
        poiVo.setLat(21.420672400828636);
        poiVo.setId(1L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("3号厅");
        poiVo.setLng(109.12904796502703);
        poiVo.setLat(21.42064756463046);
        poiVo.setId(1L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("4号楼");
        poiVo.setLng(109.12895895460167);
        poiVo.setLat(21.420380599643234);
        poiVo.setId(1L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("5号楼");
        poiVo.setLng(109.12902995626102);
        poiVo.setLat(21.420062570027085);
        poiVo.setId(1L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("候船厅");
        poiVo.setLng(109.12843689800044);
        poiVo.setLat(21.419183805661795);
        poiVo.setId(1L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("4号楼暖水区");
        poiVo.setLng(109.12915697009831);
        poiVo.setLat(21.420358519764882);
        poiVo.setId(4L);
        poiVoList.add(poiVo);


        // TODO
        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("北海国际客运港3号停车场");
        poiVo.setLng(109.1306266355123);
        poiVo.setLat(21.420591614866996);
        poiVo.setId(2L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("候船厅小卖部");
        poiVo.setLng(109.12853891068247);
        poiVo.setLat(21.419544765880882);
        poiVo.setId(3L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("候船厅小卖部");
        poiVo.setLng(109.12878992760668);
        poiVo.setLat(21.419307664242922);
        poiVo.setId(3L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("候船厅小卖部");
        poiVo.setLng(109.12890893714744);
        poiVo.setLat(21.419313616370353);
        poiVo.setId(3L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("");
        poiVo.setName("售票厅咨询台");
        poiVo.setLng(109.12960900986671);
        poiVo.setLat(21.420652338070617);
        poiVo.setId(6L);
        poiVoList.add(poiVo);


        poiVo = new PoiVo();
        poiVo.setPhone("15288477909");
        poiVo.setName("华夏租车");
        poiVo.setLng(109.10409353869615);
        poiVo.setLat(21.037725391190868);
        poiVo.setId(501L);
        poiVoList.add(poiVo);

        poiVo = new PoiVo();
        poiVo.setPhone("18207791149");
        poiVo.setName("八路租车");
        poiVo.setLng(109.09742853315296);
        poiVo.setLat(21.027575386106566);
        poiVo.setId(502L);
        poiVoList.add(poiVo);


        poiVo = new PoiVo();
        poiVo.setPhone("17376091369");
        poiVo.setName("万家滃租车");
        poiVo.setLng(109.09619997176864);
        poiVo.setLat(21.0534970680122);
        poiVo.setId(502L);
        poiVoList.add(poiVo);


        Long rent = 5L;
        Long rentCar = 501L;
        Long rentElectricTricycle = 502L;
        List<BusGisVo> busGisVoList = new ArrayList<>();

        if (!type.equals(rent)) {
            BusGisVo busGisVo = getBusGisVo(type, poiVoList);
            busGisVoList.add(busGisVo);
        } else {
            BusGisVo busGisVoCar = getBusGisVo(rentCar, poiVoList);
            BusGisVo electricTricycle = getBusGisVo(rentElectricTricycle, poiVoList);
            busGisVoList.add(busGisVoCar);
            busGisVoList.add(electricTricycle);
        }
        return new ResponseEntity(GeneConstant.SUCCESS_CODE, "成功", busGisVoList);
    }

    private BusGisVo getBusGisVo(Long type, List<PoiVo> poiVoList) {
        String name = "";
        Long restroom = 1L;
        Long park = 2L;
        Long supermarket = 3L;
        Long hotWater = 4L;
        Long rent = 5L;
        Long rentCar = 501L;
        Long rentElectricTricycle = 502L;
        Long informationStation = 6L;
        if (type.equals(restroom)) {
            name = "厕所";
        } else if (type.equals(park)) {
            name = "停车场";
        } else if (type.equals(supermarket)) {
            name = "小卖部";
        } else if (type.equals(hotWater)) {
            name = "温水服务";
        } else if (type.equals(rent)) {
            name = "租车";
        } else if (type.equals(rentCar)) {
            name = "商务轿车";
        } else if (type.equals(rentElectricTricycle)) {
            name = "便捷小车";
        } else if (type.equals(informationStation)) {
            name = "咨询台";
        }

        BusGisVo busGisVo = new BusGisVo();
        busGisVo.setType(type);
        busGisVo.setName(name);
        List<PoiVo> poiVos = new ArrayList<>();
        for (PoiVo item : poiVoList) {
            if (item.getId().equals(type)) {
                PoiVo poiVo = new PoiVo();
                poiVo.setId(item.getId());
                poiVo.setLat(item.getLat());
                poiVo.setLng(item.getLng());
                poiVo.setName(item.getName());
                poiVo.setPhone(item.getPhone());
                poiVos.add(poiVo);
            }
        }
        busGisVo.setList(poiVos);
        return busGisVo;
    }
}
