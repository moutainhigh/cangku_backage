package cn.enn.wise.platform.mall.util;

import cn.enn.wise.platform.mall.bean.vo.User;
import cn.enn.wise.platform.mall.config.SystemAdapter;
import cn.enn.wise.platform.mall.constants.AppConstants;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author jiaby
 */
public class UserUtil {

    private static final Logger logger = LoggerFactory.getLogger(UserUtil.class);
    /**
     * 通过HTTPClient调用根据用户token获取用户信息的接口
     * @param
     * @return
     */
    public static User getUserByToken(String appId, String companyId, String openId){
        try {

            Map<String, String> urlConfig = SystemAdapter.URL_MAP.get(Long.valueOf(companyId));
            String url = urlConfig.get(AppConstants.OPENID_SERVICE_URL);



            List<NameValuePair> headers = new ArrayList<>();
            headers.add(new BasicNameValuePair("appId",appId));
            headers.add(new BasicNameValuePair("companyId",companyId));
            headers.add(new BasicNameValuePair("openId",openId));

            String post = HttpClientUtil.post(url,headers,new ArrayList<>());

            ResultUtil resultUtil = JSONObject.parseObject(post, ResultUtil.class);
            if(resultUtil!=null){

                int result = resultUtil.getResult();
                if(result == 1){
                    //获取用户信息成功
                    String userStr = JSONObject.toJSONString(resultUtil.getValue());
                    User userInfo = JSONObject.parseObject(userStr, User.class);
                    return userInfo;

                }else {
                    throw new RuntimeException("获取用户信息异常:"+resultUtil);
                }
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
            logger.info("===请求获取用户信息接口异常===");
            return null;
        }
    }
}
