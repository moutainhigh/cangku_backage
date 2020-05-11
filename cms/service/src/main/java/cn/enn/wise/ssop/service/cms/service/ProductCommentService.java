package cn.enn.wise.ssop.service.cms.service;

import cn.enn.wise.ssop.api.cms.dto.request.CommentPageParam;
import cn.enn.wise.ssop.api.cms.dto.request.ProductCommentParams;
import cn.enn.wise.ssop.api.cms.dto.request.User;
import cn.enn.wise.ssop.api.cms.dto.response.ProductCommentDTO;
import cn.enn.wise.ssop.api.promotions.dto.response.DistributorListDTO;
import cn.enn.wise.ssop.service.cms.mapper.ProductCommentMapper;
import cn.enn.wise.ssop.service.cms.model.ProductComment;
import cn.enn.wise.uncs.base.pojo.response.QueryData;
import cn.enn.wise.uncs.base.pojo.response.R;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import static cn.enn.wise.uncs.common.http.GeneUtil.getNullPropertyNames;

@Service("productCommentService")
public class ProductCommentService extends ServiceImpl<ProductCommentMapper, ProductComment> {


    private Logger logger = LoggerFactory.getLogger(ProductCommentService.class);

    @Autowired
    private ProductCommentMapper productCommentMapper;

    public R<QueryData<List<ProductCommentDTO>>> getCommentList(CommentPageParam commentPageParam) {

        //分页查询
        Page<ProductCommentDTO> productCommentDTOPageList = new Page<>(commentPageParam.getPageNo(),commentPageParam.getPageSize());
        productCommentDTOPageList.setRecords(productCommentMapper.getProductCommentPageInfo(commentPageParam));
        QueryData<ProductCommentDTO> commentWrapper = new QueryData<>();
        commentWrapper.setPageNo(commentPageParam.getPageNo());
        commentWrapper.setPageSize(commentPageParam.getPageSize());
        //评论总条数
        commentWrapper.setTotalCount(productCommentMapper.getProductCommentCount(commentPageParam));

        List<ProductCommentDTO> productCommentDTOList = new ArrayList<>();
        productCommentDTOPageList.getRecords().forEach(x -> {
            ProductCommentDTO pcDTO = new ProductCommentDTO();
            BeanUtils.copyProperties(x, pcDTO, getNullPropertyNames(x));
            productCommentDTOList.add(pcDTO);
        });

        commentWrapper.setRecords(productCommentDTOList);

     return new R(commentWrapper);
    }


    //获取全部的评论
    public R<QueryData<List<ProductCommentDTO>>> getAllCommentList(ProductCommentParams prodCommParam) {
        Page<ProductCommentDTO> productCommentPageList = new Page<>(prodCommParam.getPageNum(),prodCommParam.getPageSize());
        productCommentPageList.setRecords(productCommentMapper.getProductCommentAll(prodCommParam));
        QueryData<ProductCommentDTO> commentWrapperQuery = new QueryData<>();
        commentWrapperQuery.setPageNo(prodCommParam.getPageNum());
        commentWrapperQuery.setPageSize(prodCommParam.getPageSize());


        List<ProductCommentDTO> productCommentDTOList = new ArrayList<>();
        productCommentPageList.getRecords().forEach(x -> {
            ProductCommentDTO pcDTO = new ProductCommentDTO();
            BeanUtils.copyProperties(x, pcDTO, getNullPropertyNames(x));
            productCommentDTOList.add(pcDTO);
        });

        commentWrapperQuery.setRecords(productCommentDTOList);
        return new R(commentWrapperQuery);
    }


     //评论详情
    public ProductComment findProdCommDetail(String orderCode) {
    }
}