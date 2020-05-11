package cn.enn.wise.platform.mall.controller;

import cn.enn.wise.platform.mall.bean.vo.ParkInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SeatInfoVo;
import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.service.WiseFlightService;
import cn.enn.wise.platform.mall.util.OpenIdAuthRequired;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 智慧航班接口
 * @author shizhai
 */
@RestController
@RequestMapping("/flight")
@Api(value = "智慧航班接口", tags = "智慧航班接口")
public class WiseFlightController extends BaseController{

    @Autowired
    HttpServletRequest request;
    @Autowired
    WiseFlightService wiseFlightService;

    @GetMapping("/seat/find")
    @ApiOperation(value = "座位查询")
    @OpenIdAuthRequired
    public ResponseEntity<List<SeatInfoVo>> getSeatByUserInfo(){
        User user =(User)request.getAttribute("currentUser");
        List<SeatInfoVo> seatInfoVoList = wiseFlightService.getSeatByUser(user);
        if(seatInfoVoList==null) return ResponseEntity.error();
        return ResponseEntity.ok(seatInfoVoList);
    }




    @PostMapping("/ship/requisition/bindUserMessage")
    @ApiOperation(value = "绑定用户信息并查询座位")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "idCard", value = "身份证号",required = true,paramType = "query"),
            @ApiImplicitParam(name = "name", value = "姓名",required = true,paramType = "query"),
            @ApiImplicitParam(name = "phone", value = "手机号",required = true,paramType = "query")
    })
    @OpenIdAuthRequired
    public ResponseEntity bindUserMessage(String idCard,String name,String phone){
        User user =(User)request.getAttribute("currentUser");
        boolean isOk = wiseFlightService.bindUserMessage(user,idCard,name,phone);
        return isOk?ResponseEntity.ok():ResponseEntity.error();
    }


    @PostMapping("/ship/requisition/add")
    @ApiOperation(value = "增加升舱需求单")
    @OpenIdAuthRequired
    public ResponseEntity<Boolean> requisitionAdd(String content,String name){
        User user =(User)request.getAttribute("currentUser");
        Boolean isOk = null;
        try {
            isOk = wiseFlightService.requisitionAdd(user,content,name);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.ok(isOk);
    }

    @GetMapping("/park/list")
    @ApiOperation(value = "停车场信息列表")
    public ResponseEntity<List<ParkInfoVo>> getParkingList(){
        List<ParkInfoVo> parkingList = wiseFlightService.getParkingList();
        if(parkingList==null) return ResponseEntity.error();
        if(parkingList.size()>0) {
            ParkInfoVo parkInfoVo = parkingList.get(0);
            parkInfoVo.setLonlat("21.420591614866996,109.1306266355123");
            parkInfoVo.setParkAddress("广西北海市国际客运港");
            parkInfoVo.setParkInfo("北海国际客运港3号停车场");
        }
        return ResponseEntity.ok(parkingList);
    }
}
