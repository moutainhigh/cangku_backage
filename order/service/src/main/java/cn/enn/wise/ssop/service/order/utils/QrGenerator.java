package cn.enn.wise.ssop.service.order.utils;

import cn.hutool.extra.qrcode.QrCodeUtil;
import lombok.extern.slf4j.Slf4j;
import sun.misc.BASE64Encoder;


/**
 * 二维码生成工具类
 *
 */
@Slf4j
public class QrGenerator {


    public static String getBase64Str(String content){

        try{
            int qrCodeSize = 600;
            byte[] imgData = QrCodeUtil.generatePng(content,qrCodeSize,qrCodeSize);
            BASE64Encoder encoder = new BASE64Encoder();
            String base64Img = encoder.encode(imgData);
            return "data:image/jpeg;base64,"+base64Img.replaceAll("\r|\n","");
        }catch (Exception e){
            log.error("==++! 生成二维码发生错误：{}",e.getMessage());
            e.printStackTrace();
            return null;
        }


    }





}
