/**
 * Copyright (C), 2018-2019
 * FileName: StrategyApi
 * Author:   Administrator
 * Date:     2019-04-20 19:02
 * Description:
 */
package cn.enn.wise.ssop.service.cms.controller;

import cn.enn.wise.ssop.service.cms.util.FastDFSClientUtil;
import cn.enn.wise.uncs.base.pojo.response.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 文件管理 shiz
 */
@RestController
@Api(value = "文件管理", tags = {"文件管理"})
@RequestMapping("file")
public class FileController {

    @Autowired
    FastDFSClientUtil fastDFSClientUtil;

    /**
     * 文件上传　　filename :multipartFile
     * @param multipartFile
     * @return
     */
    @PostMapping("/upload")
    @ApiOperation(value = "上传文件",notes = "上传文件")
    public R<String> uploadFile(MultipartFile multipartFile) throws IOException, MyException {
        if(multipartFile==null&&multipartFile.isEmpty()){
            return R.error("请选择文件");
        }
        try {
            String filepath = fastDFSClientUtil.uploadFiles(multipartFile);
            return R.success(filepath);
        } catch (Exception e) {
            e.printStackTrace();
            String filepath = fastDFSClientUtil.uploadFiles(multipartFile);
            return R.success(filepath);
        }
    }



}