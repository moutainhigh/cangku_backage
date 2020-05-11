package cn.enn.wise.platform.mall.controller.app;

import cn.enn.wise.platform.mall.bean.bo.SysTicketReportBo;
import cn.enn.wise.platform.mall.bean.bo.WeatherSun;
import cn.enn.wise.platform.mall.service.APPVersionService;
import cn.enn.wise.platform.mall.service.MallAdminService;
import cn.enn.wise.platform.mall.util.HttpClientUtil;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/extend")
@Api(value = "拓展接口", tags = { "接收大数据和天气情况数据" })
public class ExtendController {

    @Autowired
    MallAdminService mallAdminService;

    @Autowired
    private APPVersionService appVersionService;

    private static String CURRENT_WIND_URL="http://ssys.laiu8.cn/index.php/work/bigData/nowWindData";

    /**
     * 天气预报同步
     */
    @PostMapping("/weather/add")
    public ResponseEntity add(WeatherSun weatherSun) {

        return mallAdminService.addWeather(weatherSun);
    }


    /**
     * 天气风力同步
     */
    @GetMapping("/weather/update")
    @Scheduled(cron = "1/59 * * * * ?")
    public ResponseEntity update() {
        String windJson = HttpClientUtil.get(CURRENT_WIND_URL);
        JSONObject dataObject = JSON.parseObject(windJson).getJSONObject("data");
        JSONObject wzObject  = dataObject.getJSONObject("wz");
        String windPole = wzObject.getString("windpole");
        windPole = windPole.replace("≈","");
        Double w = new Double(windPole);
        return mallAdminService.updateWind(w.intValue());
    }

    /**
     * 入园同步
     */
    @GetMapping("/park/in")
    public ResponseEntity park(SysTicketReportBo sysTicketReportBo) {
        if(sysTicketReportBo.getScansPercent()==null){
            sysTicketReportBo.setScansPercent(0.0);
        }
        if(sysTicketReportBo.getScansCount()==null){
            sysTicketReportBo.setScansCount(0);
        }
        return mallAdminService.addEnterPark(sysTicketReportBo);
    }



    /**
     * 获取app版本号
     * @param companyId
     * @return
     */
    @RequestMapping(value = "/app/version", method = RequestMethod.POST)
    @ApiOperation(value = "获取app版本信息", notes = "获取app版本信息 1-查询成功 2-companyId不可为空")
    public ResponseEntity getAppVersion(@RequestHeader(value = "companyId") Integer companyId) {
        if(companyId==null){
            return new ResponseEntity(2,"companyId不能为空");
        }
        return new ResponseEntity(1,"companyId："+companyId+" 版本信息查询成功",appVersionService.getAppVersion(companyId));
    }
}
