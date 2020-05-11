package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.vo.DistributeOrderVo;
import cn.enn.wise.platform.mall.bean.vo.ProjectInfoVo;
import cn.enn.wise.platform.mall.bean.vo.SysStaff;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author bj
 * @Description 统一调用远程接口类
 * @Date19-5-30 下午5:26效
 * @Version V1.0
 **/
@Component
public class RemoteServiceUtil {

    public static final Logger logger = LoggerFactory.getLogger(RemoteServiceUtil.class);

    @Value("${companyId}")
    private String companyId;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    public static String TAG_REDIS_KEY = "tag_goods_list:*";

    public static String PROJECT_REDIS_KEY = "project_info:*";

    public static String EXPERIENCE_KEY = "experience_time:*";

    /**
     * 检查分销商身份是否有效
     *
     * @param phone
     * @param companyId
     * @return
     */
    public ResponseEntity getCheckUserResult(String phone, Long companyId) {

        //检查该用户是否拥有分销身份
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(companyId);
        String url = urlConfig.get(AppConstants.CHECKUSER_SERVICE_URL);
        String post = HttpClientUtil.post(url, map);

        if (StringUtils.isNotEmpty(post)) {

            try {

                ResponseEntity responseEntity = JSONObject.parseObject(post, ResponseEntity.class);
                return responseEntity;

            } catch (Exception e) {

                e.printStackTrace();
                logger.info("===getCheckUserResult===json解析异常");
                return null;

            }
        }

        return null;
    }

    public ResponseEntity getCheckStrategyItemsResult(String goodsId, String roleId, Long companyId) {

        ArrayList<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
        basicNameValuePairs.add(new BasicNameValuePair("goodsId", goodsId));
        basicNameValuePairs.add(new BasicNameValuePair("roleId", roleId));
        Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(companyId);
        String url = urlConfig.get(AppConstants.CHECK_STRATEGY_URL);

        String post = HttpClientUtil.get(url, basicNameValuePairs);

        if (StringUtils.isNotEmpty(post)) {
            try {
                ResponseEntity responseEntity = JSONObject.parseObject(post, ResponseEntity.class);
                return responseEntity;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("===getCheckStrategyItemsResult===json解析异常");
                return null;
            }
        }

        return null;
    }


    /**
     * 调用产生分销订单的接口
     *
     * @return
     */
    public ResponseEntity getSaveDistributeOrderResult(DistributeOrderVo distributeOrderVo) {
        //产生一笔分销订单

        String snapshot = distributeOrderVo.getSnapshot();
        if (StringUtils.isEmpty(snapshot)) {
            return null;
        }

        JSONObject jsonObject = JSONObject.parseObject(snapshot);
        Long scenicId = jsonObject.getLong("scenicId");

        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(scenicId);
        String url = urlMap.get(AppConstants.SAVEDISTRIBUTEORDER_SERVICE_URL);

        logger.info("===生成分销订单===");
        Map<String, String> map = new HashMap<>();
        map.put("distributorId", distributeOrderVo.getDistributorId());
        map.put("orderId", distributeOrderVo.getOrderId());
        map.put("goodsId", distributeOrderVo.getGoodsId());
        map.put("goodsName", distributeOrderVo.getGoodsName());
        map.put("goodsCount", distributeOrderVo.getGoodsCount());
        map.put("goodsPrice", distributeOrderVo.getGoodsPrice());
        map.put("orderPrice", distributeOrderVo.getOrderPrice());
        map.put("contacts", distributeOrderVo.getContacts());
        map.put("snapshot", snapshot);
        map.put("roleId", distributeOrderVo.getRoleId());
        String post = HttpClientUtil.post(url, map);
        logger.info("===SAVEDISTRIBUTEORDER_SERVICE_URL接口调用结果===" + post);
        if (StringUtils.isNotEmpty(post)) {

            try {
                ResponseEntity responseEntity = JSONObject.parseObject(post, ResponseEntity.class);
                return responseEntity;
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("===getSaveDistributeOrderResult===json解析异常");
                return null;
            }
        }

        return null;
    }

    /**
     * 根据用户手机号查询用户可以分销的商品
     *
     * @return
     */
    public List<String> getGoodsIdByPhone(String phone, Long companyId) {

        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(companyId);

        String url = urlMap.get(AppConstants.GET_GOODSID_BYROLEID);
        List<BasicNameValuePair> list = new ArrayList<>();
        list.add(new BasicNameValuePair("phone", phone));

        String get = HttpClientUtil.get(url, list);
        logger.info("===GET_GOODSID_BYROLEID接口调用结果===" + get);
        if (StringUtils.isNotEmpty(get)) {
            try {

                JSONObject result = JSONObject.parseObject(get);
                if (result.getInteger("result") != 1) {
                    logger.info("===调用接口错误===" + result.getString("message"));
                    return null;
                }
                JSONArray value = result.getJSONArray("value");
                List<String> stringList = new ArrayList<>();
                if (value != null) {

                    for (Object o : value) {
                        stringList.add(o.toString());
                    }

                }
                return stringList;

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("==getGoodsIdByPhone====json解析异常");

            }
        }

        return null;
    }

    /**
     * 根据用户id集合获取用户信息
     *
     * @param ids
     * @return
     */
    public List<Map<String, Object>> getMemberInfo(String ids, Long companyId) {

        List<Map<String, Object>> map = new ArrayList<>();

        String url = getUrl(companyId, AppConstants.GET_MEMBER_INFO_URL);
        if (StringUtils.isEmpty(url)) {
            return map;
        }

        logger.debug("调用接口地址为{}", url);
        ArrayList<BasicNameValuePair> basicNameValuePairs = new ArrayList<>();
        basicNameValuePairs.add(new BasicNameValuePair("memberIds", ids));
        String getString = HttpClientUtil.get(url, basicNameValuePairs);
        logger.debug("调用接口结果为{}", getString);
        if (StringUtils.isEmpty(getString)) {
            return map;
        }

        try {
            JSONObject object = JSONObject.parseObject(getString);
            Integer result = object.getInteger("result");
            if (result != 1) {
                return map;
            }

            List<Map<String, Object>> maps = (List<Map<String, Object>>) object.get("value");

            return maps;
        } catch (Exception e) {
            logger.error("转换接口调用结果错误", e);
            return map;
        }
    }


    /**
     * 获取url
     *
     * @param companyId  公司id
     * @param methodName 调用接口名称
     * @return 接口地址
     */
    private static String getUrl(Long companyId, String methodName) {
        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(companyId);
        if (MapUtils.isEmpty(urlMap)) {

            return "";
        }
        String url = urlMap.get(methodName);
        return url;
    }

    /**
     * 修改分销订单的策略Id
     * 根据是否是景区接送服务
     *
     * @return
     */
    public ResponseEntity updateStrategyItemByIsScenicService(Long orderId, Integer isScenicService) {

        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));

        String url = urlMap.get(AppConstants.UPDATE_STRATEGY_ITEM_BY_IS_SERVICE);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId", orderId.toString());
        paramMap.put("isService", isScenicService.toString());
        String str = HttpClientUtil.post(url, paramMap);
        if (StringUtils.isNotEmpty(str)) {
            try {
                JSONObject result = JSONObject.parseObject(str);
                if (result.getInteger(AppConstants.RESULT) != 1) {
                    logger.info("===调用updateStrategyItemByIsScenicService接口错误===" + result.getString("message"));
                    return null;
                }else {
                    return new ResponseEntity();
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("==getGoodsIdByPhone====json解析异常");

            }
        }
        return null;
    }


    /**
     * 修改分销订单的策略Id
     *
     * @return
     */
    public ResponseEntity updateDistributeOrderStrategyItemId(Long orderId, Long strategyItemId) {

        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));

        String url = urlMap.get(AppConstants.UPDATE_STRATEGY_ITEM);

        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("orderId", orderId.toString());
        paramMap.put("strategyItemId", strategyItemId.toString());
        String str = HttpClientUtil.post(url, paramMap);

        logger.info("===调用updateDistributeOrderStrategyItemId结果===" + str);

        if (StringUtils.isNotEmpty(str)) {
            try {
                JSONObject result = JSONObject.parseObject(str);
                if (result.getInteger(AppConstants.RESULT) != 1) {
                    logger.info("===调用接口错误===" + result.getString("message"));
                    return null;
                } else {
                    return new ResponseEntity();
                }

            } catch (Exception e) {
                e.printStackTrace();
                logger.info("==getGoodsIdByPhone====json解析异常");

            }
        }
        return null;
    }


    /**
     * 根据项目Id获取项目综合信息
     *
     * @param projectId
     * @return
     */
    public ProjectInfoVo getProjectInfoById(Long projectId) {

        if(projectId == null){
            return null;
        }
        try {
            String url = SystemAdapter.URL_MAP.get(Long.valueOf(companyId)).get(AppConstants.GET_PROJECT_INFO);
            Map<String, String> paramMap = new HashMap<>(8);
            paramMap.put("projectId", projectId.toString());
            String projectInfoString = HttpClientUtil.post(url, paramMap);
            logger.debug("调用GET_PROJECT_INFO接口：{}", url);

            if (StringUtils.isNotEmpty(projectInfoString)) {
                JSONObject object = JSONObject.parseObject(projectInfoString);
                if (object.getInteger(AppConstants.RESULT) == 1) {
                    if (StringUtils.isNotEmpty(object.getString(AppConstants.VALUE))) {
                        ProjectInfoVo value = JSONObject.parseObject(object.getString(AppConstants.VALUE), ProjectInfoVo.class);
                        return value;
                    }

                }
            }

        } catch (Exception e) {
            logger.error("调用接口错误", e);
        }

        return null;
    }

    /**
     * 根据项目集合获取项目信息
     *
     * @param projectIds
     *          项目Id集合逗号分割
     *
     * @return 项目基础信息集合
     */
    public List<ProjectInfoVo> getProjectInfoVoByIds(String projectIds) {
        Map<String, String> urlMap = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));

        String url = urlMap.get(AppConstants.GET_PROJECT_INFO_LIST);
        Map<String, String> paramMap = new HashMap<>(8);
        paramMap.put("projectIds", projectIds);
        String post = HttpClientUtil.post(url, paramMap);
        logger.debug("调用接口：{}", url);
        try {

            if (StringUtils.isNotEmpty(post)) {
                JSONObject object = JSONObject.parseObject(post);
                if (object.getInteger(AppConstants.RESULT) == 1) {
                    String jsonArray = object.getString(AppConstants.VALUE);
                    if (StringUtils.isNotEmpty(jsonArray)) {
                        List<ProjectInfoVo> projectInfoVos = JSONObject.parseArray(jsonArray, ProjectInfoVo.class);
                        return projectInfoVos;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("调用接口错误", e);
        }

        return null;

    }

    public static String getListBusiness(){
        String url = "https://tx.enn.cn/member-v1/wisePlatform/business/list";
        net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
        jsonObject.put("pageNum","1");
        jsonObject.put("pageSize","10000");
        jsonObject.put("roleId","38");
        jsonObject.put("businessName","");
        jsonObject.put("phone","");
        //String post = HttpClientUtil.posts( jsonObject,url);
        return null;
    }
    //tx.enn.cn
    public List<SysStaff> getListBusinesss(){
        String url = "http://genius.enn.cn//member-v1/wisePlatform/business/list";
        //String url = "http://10.38.128.231:8051/member-v2/wisePlatform/business/list";
        JSONObject jsonObject = new  JSONObject();
        jsonObject.put("pageNum","1");
        jsonObject.put("pageSize","10000");
        jsonObject.put("roleId","38");
        jsonObject.put("businessName","");
        jsonObject.put("phone","");
        try {
            String post = HttpClientUtil.httpPostWithJSON(url, jsonObject);
            if (StringUtils.isNotEmpty(post)) {
                JSONObject object = JSONObject.parseObject(post);
                if (object.getInteger(AppConstants.RESULT) == 1) {
                    String jsonArray = object.getString(AppConstants.VALUE);
                    JSONObject objecst = JSONObject.parseObject(jsonArray);
                    String string = objecst.getString(AppConstants.LIST);
                    if (StringUtils.isNotEmpty(string)) {
                        List<SysStaff> pageInfo =  JSONObject.parseArray(string, SysStaff.class);
                        return pageInfo;
                    }
                }
            }

        } catch (Exception e) {
            logger.error("调用接口错误", e);
        }
        return null;
    }


    public void deleteKey(String key){
        if(StringUtils.isNotEmpty(key)){
            redisTemplate.delete(key);
        }

    }


}
