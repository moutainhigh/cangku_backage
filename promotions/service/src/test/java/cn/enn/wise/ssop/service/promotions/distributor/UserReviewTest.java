package cn.enn.wise.ssop.service.promotions.distributor;

import cn.enn.wise.ssop.api.promotions.dto.request.ActivityPriceUtilParam;
import cn.enn.wise.ssop.api.promotions.dto.request.UserReviewParam;
import cn.enn.wise.ssop.api.promotions.dto.response.GroupOrderGoodsDTO;
import cn.enn.wise.uncs.base.config.BaseIntegratedTest;
import cn.enn.wise.uncs.base.constant.enums.CommonResponseEnum;
import com.jayway.jsonpath.JsonPath;
import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.util.LinkedMultiValueMap;
import org.testng.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.sql.Timestamp;
import java.util.*;

/**
 * 渠道销售价格相关接口测试
 * Author: yangshuaiquan
 */
public class UserReviewTest extends BaseIntegratedTest {



    //用户信息回访
    @Test
    public void listUserReview() throws Exception {
        //设置参数
        UserReviewParam userReviewParam = new UserReviewParam();
        userReviewParam.setActivityBaseId(1l);
        userReviewParam.setReviewInfo("测试查询用户回访");
        userReviewParam.setUserId(1l);

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();
       /* map.put("userId", Arrays.asList("1"));
        map.put("activityBaseId", Arrays.asList("1"));*/
        map.put("userReviewParam",Arrays.asList(userReviewParam.toString()));
        MvcResult mvcResult = getToResult("/activity/userreview/list/",userReviewParam,null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }
    @Test
    public void saveUserReview() throws Exception{
        UserReviewParam userReviewParam = new UserReviewParam();
        userReviewParam.setActivityBaseId(1l);
        userReviewParam.setReviewInfo("测试查询用户回访");
        userReviewParam.setUserId(1l);
        MvcResult mvcResult = postToResult("/activity/userreview/save", userReviewParam, null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }
    @Test
    public void activityPriceUtil() throws Exception{
        ActivityPriceUtilParam activityPriceUtilParam = new ActivityPriceUtilParam();
        activityPriceUtilParam.setUserId(1l);
        activityPriceUtilParam.setIsDistribute((byte)1);
        activityPriceUtilParam.setActivityType((byte)1);
        activityPriceUtilParam.setOrderUseTime(new Timestamp(System.currentTimeMillis()));/*
        ActivityPriceParam activityPriceParam = new ActivityPriceParam();
        List<ActivityPriceParam> activityPriceParams = new ArrayList<>();*//*
        activityPriceParam.setAlgorithms((byte)1);
        activityPriceParam.setActivityBaseId(10l);
        activityPriceParam.setActivityName("测试使用活动");
        activityPriceParam.setActivityRuleId(4l);
        activityPriceParam.setSaleType((byte)2);
        activityPriceParams.add(activityPriceParam);
        activityPriceUtilParam.setActivityPriceParams(activityPriceParams);
        List<GoodsPriceParam> goodsPriceParams = new ArrayList<>();
        GoodsPriceParam goodsPriceParam = new GoodsPriceParam();
        goodsPriceParam.setDistributePrice(10);
        goodsPriceParam.setGoodsPrice(8);
        goodsPriceParam.setGoodsName("苹果");
        goodsPriceParam.setGoodsId(1l);
        goodsPriceParam.setGoodsNum(3);
        goodsPriceParams.add(goodsPriceParam);
        activityPriceUtilParam.setGoodsPriceParams(goodsPriceParams);*/

        LinkedMultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        MvcResult mvcResult = getToResult("/activityutil/getprice",activityPriceUtilParam,null);
        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }

   @Test
    public void getGroupGoodsList() throws Exception{
    //拼团活动商品展示测试
       MvcResult mvcResult = getToResult("/applets/grouporder/getgroupgoodslist",null,null);

       String content = mvcResult.getResponse().getContentAsString();
       int code = JsonPath.read(content, "$.code");
       Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
   }

    @Test
    public void getGroupOrderDetail() throws Exception{
        //拼团活动详细信息展示

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("company_id","1");
        MvcResult mvcResult = getToResult("/applets/grouporder/getgrouporderdetail?userId=16",null,httpHeaders);

        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }
    @Test
    public void getGroupOrderApplets() throws Exception{
        //拼团活动详细信息展示


        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("company_id","1");
        MvcResult mvcResult = getToResult("/applets/grouporder/getgrouporderapplets/1",null,httpHeaders);

        String content = mvcResult.getResponse().getContentAsString();
        int code = JsonPath.read(content, "$.code");
        Assert.assertEquals(code, CommonResponseEnum.SUCCESS.getCode());
    }
    @Test
    public void splitTest(){
        List<Long> list = new ArrayList<>();
        list.add(1l);
        list.add(2l);
        StringBuffer stringBuffer = new StringBuffer();
        list.forEach(c->{
            stringBuffer.append(c).append(",");
        });
        String[] split = stringBuffer.toString().split(",");
        int length = split.length;
        for (int i =0 ;i<split.length;i++){
            System.out.println(split[i]);
        }
    }
    public static Object mapToObject(Map<String, Object> map, Class<?> beanClass) throws Exception {
        if (map == null)
            return null;
        Object obj = beanClass.newInstance();
        Field[] fields = obj.getClass().getDeclaredFields();
        for (Field field : fields) {
            int mod = field.getModifiers();
            if (Modifier.isStatic(mod) || Modifier.isFinal(mod)) {
                continue;
            }
            field.setAccessible(true);
            if (map.containsKey(field.getName())) {
                field.set(obj, map.get(field.getName()));
            }
        }
        return obj;
    }
    @Test
    public void  MapToObject() throws Exception{
    Map<String,Object> map = new HashMap<>();
    GroupOrderGoodsDTO groupOrderGoodsDTO = new GroupOrderGoodsDTO();
        String group_price = upperTable("group_price");
        map.put(group_price,10);
        Object o = mapToObject(map,GroupOrderGoodsDTO.class);
        System.out.println(o);
    }
    public static String upperTable(String str)
    {
        // 字符串缓冲区
        StringBuffer sbf = new StringBuffer();
        // 如果字符串包含 下划线
        if (str.contains("_"))
        {
            // 按下划线来切割字符串为数组
            String[] split = str.split("_");
            // 循环数组操作其中的字符串
            for (int i = 0, index = split.length; i < index; i++)
            {
                // 递归调用本方法
                String upperTable = upperTable(split[i]);
                // 添加到字符串缓冲区
                sbf.append(upperTable);
            }
        } else
        {// 字符串不包含下划线
            // 转换成字符数组
            char[] ch = str.toCharArray();
            // 判断首字母是否是字母
            if (ch[0] >= 'a' && ch[0] <= 'z')
            {
                // 利用ASCII码实现大写
                ch[0] = (char) (ch[0] - 32);
            }
            // 添加进字符串缓存区
            sbf.append(ch);
        }
        // 返回
        return sbf.toString();
    }

}
