package cn.enn.wise.platform.mall.service.impl;

import cn.enn.wise.platform.mall.bean.bo.Route;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import cn.enn.wise.platform.mall.mapper.RouteMapper;
import cn.enn.wise.platform.mall.service.RouteService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author baijie
 * @since 2019-07-25
 */
@Service
public class RouteServiceImpl extends ServiceImpl<RouteMapper, Route> implements RouteService {
    @Value("${companyId}")
    private String companyId;

    @Autowired
    private RouteMapper routeMapper;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public void syncRoute() {

        //调用接口
        Map<String, String> urlconfig = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));
        String url = urlconfig.get(AppConstants.scenic_service_url);

        Map<String,String> paramMap = new HashMap<>();
        paramMap.put("scenicId",companyId);
        ResponseEntity<String> forEntity = restTemplate.getForEntity(url + "?scenicId={scenicId}", String.class, paramMap);
        String resultStr = forEntity.getBody();

        if(StringUtils.isEmpty(resultStr)){

            return;
        }

        JSONObject jsonObject = JSONObject.parseObject(resultStr);
        Integer result1 = jsonObject.getInteger("result");
        if(result1 != 1){
            throw new RuntimeException("接口调用错误");
        }
        JSONArray value = jsonObject.getJSONArray("value");

        for (Object object : value) {

            JSONObject jo = (JSONObject) object;
            String code = jo.getString("code");
            String name = jo.getString("name");

            //查询code 存在就更新,不存在就添加
            Route getByCode = routeMapper.selectOne(new QueryWrapper<Route>().eq("code", code));
            if(getByCode == null){
                routeMapper.insert(new Route(){{
                    setCode(code);
                    setName(name);
                    setState(1);
                    setScenic(Long.valueOf(companyId));
                }});
            }else {
                getByCode.setName(name);
                routeMapper.updateById(getByCode);
            }



        }

    }
}
