package cn.enn.wise.ssop.service.cms.controller;



import cn.enn.wise.ssop.api.cms.dto.request.CommentPageParam;
import cn.enn.wise.ssop.api.cms.dto.request.ProdCommLabel;
import cn.enn.wise.ssop.api.cms.dto.request.ProductCommentParams;
import cn.enn.wise.ssop.api.cms.dto.request.User;
import cn.enn.wise.ssop.api.cms.dto.response.ProductCommentDTO;
import cn.enn.wise.ssop.service.cms.model.ProductComment;
import cn.enn.wise.ssop.service.cms.service.ProductCommentService;
import cn.enn.wise.ssop.service.cms.util.GeneConstant;
import cn.enn.wise.uncs.base.pojo.response.QueryData;

import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@Api(value = "用户评价", tags = {"用户评价"})
@RequestMapping("/user/comment")
public class ProductCommentController{


    @Autowired
    ProductCommentService productCommentService;

    @ApiOperation(value = "评论列表", notes = "评论列表")
    @PostMapping(value="/list")
    @ApiOperationSupport(order = 1)
    public R<QueryData<List<ProductCommentDTO>>> getCommentList(@RequestBody CommentPageParam commentPageParam){
        return  productCommentService.getCommentList(commentPageParam);
    }

    @ApiOperation(value = "所有用户评论",notes = "所有用户评论")
    @PostMapping("/all/list")
    public R<QueryData<List<ProductCommentDTO>>> getCommentAllList(@RequestBody ProductCommentParams prodCommParam){
        return productCommentService.getAllCommentList(prodCommParam);
    }


    @ApiOperation(value = "评论标签",notes = "评论标签")
    @GetMapping(value = "/label")
    public R queryProdCommLabel(){
        List<ProdCommLabel> prodCommLabelList =new ArrayList<>();
        prodCommLabelList.add(new ProdCommLabel(1L,"体验很棒"));
        prodCommLabelList.add(new ProdCommLabel(2L,"安全系数高"));
        prodCommLabelList.add(new ProdCommLabel(3L,"景色很好"));

        return new R(prodCommLabelList);
    }

    @ApiOperation(value = "评价详情",notes = "评价详情")
    @GetMapping(value = "/detail")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true)})
    public R<ProductCommentDTO> findProdCommDetail(@Value("#{request.getAttribute('currentUser')}") User user,String orderCode){


        R<ProductCommentDTO> response = new R<>();
        if (null == user){
            response.setCode(GeneConstant.INT_0);
            response.setMessage("token已过期");
            return response;
        }



      ProductComment prodComment = productCommentService.findProdCommDetail(orderCode);
//        response.setCode(GeneConstant.INT_1);
//        response.setMessage(GeneConstant.SUCCESS);
//        response.setData(prodComment);
        return response;

    }

//    @ApiOperation(value = "添加评论",notes = "添加评论")
//    @PostMapping(value = "/save")
//    @OpenIdAuthRequired
//    public ResponseEntity saveProdComm(@Value("#{request.getAttribute('currentUser')}") User user, @RequestBody ProdCommParam prodCommParam){
//        ResponseEntity<Long> resultVo = new ResponseEntity<>();
//        if (null == user){
//            resultVo.setResult(GeneConstant.INT_0);
//            resultVo.setMessage("token已过期");
//            return resultVo;
//        }
//
//        if (Strings.isEmpty(prodCommParam.getOrderId())){
//            resultVo.setResult(GeneConstant.INT_0);
//            resultVo.setMessage("订单号为空");
//            return resultVo;
//        }
//
//        List<Order> orderList = orderService.findOrderInfo(prodCommParam.getOrderId());
//
//        if (CollectionUtils.isEmpty(orderList)){
//            resultVo.setResult(GeneConstant.INT_0);
//            resultVo.setMessage("暂无该订单,请核实");
//            return resultVo;
//        }
//
//        //todo 1.评价状态待定 2.成功后修改评价状态
//        if (orderList.get(0).getCommSts().equals(2)){
//            resultVo.setResult(GeneConstant.INT_0);
//            resultVo.setMessage("只能评价一次");
//            return resultVo;
//        }
//        prodCommParam.setUserId(String.valueOf(user.getId()));
//        long result = prodCommService.saveProdComm(prodCommParam);
//        resultVo.setResult(result > 0 ? GeneConstant.INT_1
//                :GeneConstant.INT_0);
//        if (result == 0){
//            resultVo.setMessage("服务器出错");
//        }
//        return resultVo;
//
//    }
//
//
//    @PostMapping("/update")
//    @ApiOperation(value = "评论审核", notes = "评论审核")
//    @ApiImplicitParams({
//            @ApiImplicitParam(paramType = "query", dataType = "String", name = "orderCode", value = "订单号", required = true),
//            @ApiImplicitParam(paramType = "query", dataType = "Integer", name = "status", value = "是否显示，1:为显示，2:待审核， -1：不通过审核，不显示。 如果需要审核评论，则是2,，否则1", required = true)})
//    public ResponseEntity updateProdCommSts(String orderCode, Integer status) throws Exception{
//        ResponseEntity<Long> resultVo = new ResponseEntity<>();
//        if (Strings.isEmpty(orderCode)){
//            resultVo.setResult(GeneConstant.INT_0);
//            resultVo.setMessage("缺少订单号");
//            return resultVo;
//        }
//        long result = prodCommService.updateProdCommStatus(orderCode,status);
//        resultVo.setResult(result > 0 ? GeneConstant.INT_1
//                :GeneConstant.INT_0);
//        if (result == 0){
//            resultVo.setMessage("服务器出错");
//        }
//        return resultVo;
//    }






}
