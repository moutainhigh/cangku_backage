package cn.enn.wise.platform.mall.controller.pc;


import cn.enn.wise.platform.mall.bean.bo.Route;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.RouteService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 * @author baijie
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/route")
@Api("线路APi")
public class RouteController extends BaseController {

    @Autowired
    private RouteService routeService;

    @GetMapping("/list")
    public ResponseEntity getRouteList(@RequestHeader ("token")String token){
        SystemStaffVo userByToken = this.getUserByToken(token);

        Map<String, Object> map = new HashMap<>();
        map.put("scenic",userByToken.getCompanyId());
        map.put("state",1);
        Collection<Route> routes = routeService.listByMap(map);
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,routes);
    }

    /**
     * 同步路线
     * @return
     */
    @PostMapping("/sync")
    public ResponseEntity syncRoute(){
        routeService.syncRoute();
        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE);
    }

}

