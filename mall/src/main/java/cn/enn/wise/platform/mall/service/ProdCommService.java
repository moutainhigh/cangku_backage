package cn.enn.wise.platform.mall.service;

import cn.enn.wise.platform.mall.bean.bo.CurrentWeather;
import cn.enn.wise.platform.mall.bean.bo.HourWeather;
import cn.enn.wise.platform.mall.bean.bo.ProdComm;
import cn.enn.wise.platform.mall.bean.param.ProdCommParam;
import cn.enn.wise.platform.mall.bean.vo.ProdCommVo;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/10/29 14:01
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public interface ProdCommService {


    long saveProdComm(ProdCommParam prodCommParam);

    ProdComm findProdCommDetail(String orderCode);

    List<HourWeather> selectWeatherList(String cityCode);

    PageInfo<ProdCommVo> findCommentList(ProdCommParam prodCommParam);

    long updateProdCommStatus(String orderCode , Integer status);

    CurrentWeather findWeatherList(String cityCode);

    /**
     * 获取评论列表
     * @param projectId 项目Id
     * @param pageNum 页码
     * @param pageSize 每页数据大小
     * @return 项目评价列表
     */
    Map<String,Object> getCommentList(Long projectId, Integer pageNum, Integer pageSize);
}
