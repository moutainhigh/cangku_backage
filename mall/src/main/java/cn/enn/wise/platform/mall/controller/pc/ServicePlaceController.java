package cn.enn.wise.platform.mall.controller.pc;


import cn.enn.wise.platform.mall.bean.bo.ServicePlace;
import cn.enn.wise.platform.mall.bean.vo.SystemStaffVo;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.ServicePlaceService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author baijie
 * @since 2019-07-25
 */
@RestController
@RequestMapping("/serviceplace")
@Api("服务场地Api")
public class ServicePlaceController extends BaseController {
    @Autowired
    private ServicePlaceService servicePlaceService;

    @GetMapping("/get")
    @ApiOperation("获取服务地点")
    public ResponseEntity getServicePlaceList(@RequestHeader("token") String token){

        SystemStaffVo userByToken = this.getUserByToken(token);

        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("status",1);
        columnMap.put("company_id",userByToken.getCompanyId());
        Collection<ServicePlace> servicePlaces = servicePlaceService.listByMap(columnMap);

        return new ResponseEntity(GeneConstant.SUCCESS_CODE,GeneConstant.SUCCESS_CHINESE,servicePlaces);
    }

}

