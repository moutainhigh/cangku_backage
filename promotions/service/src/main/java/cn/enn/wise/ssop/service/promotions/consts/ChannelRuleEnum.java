package cn.enn.wise.ssop.service.promotions.consts;


import cn.enn.wise.uncs.base.constant.BusinessEnum;
import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@BusinessEnum
@Getter
public enum ChannelRuleEnum {

    rebateUnit("rebateUnit", new Byte("1"),"商品个数"),
    rebateFormat1("rebateFormat", new Byte("1"),"百分比"),
    rebateFormat2("rebateFormat", new Byte("2"),"金额"),
    distribuorLevel1("distribuorLevel", new Byte("1"),"初级"),
    distribuorLevel2("distribuorLevel", new Byte("2"),"中级"),
    distribuorLevel3("distribuorLevel", new Byte("3"),"高级"),
    basePrice1("basePrice", new Byte("1"),"成本价"),
    basePrice2("basePrice", new Byte("2"),"销售价"),
    computeMethod1("computeMethod", new Byte("1"),"金额"),
    computeMethod2("computeMethod", new Byte("2"),"折扣/百分比"),
    sign1("sign", new Byte("1"),"加价"),
    sign2("sign", new Byte("2"),"减价"),
    productPerformance1("productPerformance", new Byte("1"),"商品数量(个)"),
    productPerformance2("productPerformance", new Byte("2"),"销售业绩(元)"),
    otherServerRule1("otherServerRule", new Byte("1"),"接送"),
    otherServerRule2("otherServerRule", new Byte("2"),"不接送"),
    otherServerRule3("otherServerRule", new Byte("3"),"散客中心服务"),
    otherServerRule4("otherServerRule", new Byte("4"),"特优服务")
    ;


    // 成员变量
    private String name;
    private Byte value;
    private String type;

    // 构造方法
    ChannelRuleEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
    // 普通方法
    public static String getName(String type,String indexs) {
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (ChannelRuleEnum c : ChannelRuleEnum.values()) {
            String[] index = indexs.split(",");
            for(String str:index ){
                if (c.getValue().equals(new Byte(str))&&c.getType().equals(type)) {
                    Map<String,Object> map = new HashMap<>();
                    map.put("value",c.value);
                    map.put("label",c.getName());
                    listMap.add(map);
                }
            }
        }
        return JSON.toJSONString(listMap);
    }


    public static final List<Map<String,Object>> mapList = new ArrayList<>();

    static{
        String type = "stateLabel";
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(ChannelRuleEnum stateEnum : ChannelRuleEnum.values()){

            if(type.equals(stateEnum.getType())){
                Map<String,Object> map = new HashMap<>();
                map.put("value",stateEnum.value);
                map.put("label",stateEnum.getName());
                listMap.add(map);
            }else{
                Map<String,Object> map1 = new HashMap<>();
                map1.put(type,listMap);
                mapList.add(map1);
                type=stateEnum.getType();
                listMap = new ArrayList<>();
                Map<String,Object> map = new HashMap<>();
                map.put("value",stateEnum.value);
                map.put("label",stateEnum.getName());
                listMap.add(map);
            }
        }
        Map<String,Object> map1 = new HashMap<>();
        map1.put(type,listMap);
        mapList.add(map1);
    }
}
