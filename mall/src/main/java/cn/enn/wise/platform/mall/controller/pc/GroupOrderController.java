package cn.enn.wise.platform.mall.controller.pc;

import cn.enn.wise.platform.mall.bean.param.GroupOrderParam;
import cn.enn.wise.platform.mall.bean.param.ReqPageInfoQry;
import cn.enn.wise.platform.mall.bean.vo.GroupOrderDetailVo;
import cn.enn.wise.platform.mall.bean.vo.GroupOrderVo;
import cn.enn.wise.platform.mall.bean.vo.ResPageInfoVO;
import cn.enn.wise.platform.mall.controller.BaseController;
import cn.enn.wise.platform.mall.service.GroupOrderService;
import cn.enn.wise.platform.mall.util.GeneConstant;
import cn.enn.wise.platform.mall.util.ResponseEntity;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author jiabaiye
 * @Description
 * @Date19-9-12
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/grouporder")
@Api(value = "团购订单", tags = {"团购订单管理"})
public class GroupOrderController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(GroupOrderController.class);
//    @Autowired
//    private GoodsService goodsService;

    @Autowired
    private GroupOrderService groupOrderService;



    @PostMapping("/list")
    @ApiOperation(value = "团购订单列表", notes = "团购订单列表")
    public ResponseEntity<ResPageInfoVO<List<GroupOrderVo>>> list(@RequestBody ReqPageInfoQry<GroupOrderParam> param) {

        if(param.getReqObj()!=null && param.getReqObj().getStatus()!=null && param.getReqObj().getStatus().size()==0){
            param.getReqObj().setStatus(null);
        }

        ResPageInfoVO<List<GroupOrderVo>> list=groupOrderService.listByPage(param);
        return new ResponseEntity(GeneConstant.INT_1, "团购列表", list);
    }

    @PostMapping("/detail")
    @ApiOperation(value = "团购订单详情", notes = "团购订单列表")
    public ResponseEntity<GroupOrderDetailVo> detail(Long id) {

        GroupOrderDetailVo order=groupOrderService.getGroupOrderById(id);
        return new ResponseEntity(GeneConstant.INT_1, "团购列表", order);
    }











}
