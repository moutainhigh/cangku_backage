package cn.enn.wise.ssop.service.promotions.controller.pc;


import cn.enn.wise.ssop.api.promotions.dto.request.GroupOrderListParam;
import cn.enn.wise.ssop.api.promotions.dto.response.GroupOrderDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.GroupOrderDetailDTO;
import cn.enn.wise.ssop.service.promotions.service.GroupOrderService;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author jiabaiye
 * @Description
 * @Version V1.0
 **/
@RestController
@RequestMapping("pc/grouporder")
@Api(value = "团购订单", tags = {"团购订单管理"})
public class GroupOrderController{

    private static final Logger logger = LoggerFactory.getLogger(GroupOrderController.class);


    @Autowired
    private GroupOrderService groupOrderService;



    @GetMapping("/list")
    @ApiOperation(value = "团购订单列表", notes = "团购订单列表")
    public R<QueryData<GroupOrderDTO>> list(@Validated GroupOrderListParam param) {

        return groupOrderService.listByPage(param);
    }

    @GetMapping("/detail/{id}")
    @ApiOperation(value = "团购订单详情", notes = "团购订单列表")
    public R<GroupOrderDetailDTO> detail(@PathVariable Long id) {

        return new R<>(groupOrderService.getGroupOrderById(id));
    }











}
