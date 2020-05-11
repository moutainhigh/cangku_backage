package cn.enn.wise.ssop.service.promotions.consts;


import cn.enn.wise.uncs.base.constant.BusinessEnum;
import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@BusinessEnum
@Getter
public enum ChannelEnum {

    VALID("stateLabel", new Byte("1"),"启用"),
    INVALID("stateLabel", new Byte("2"),"关闭"),
    CHANNELTYPE1("channelTypeLabel", new Byte("1"),"直营"),
    CHANNELTYPE2("channelTypeLabel", new Byte("2"),"分销"),
    TAG1("tag", new Byte("1"),"XX专用渠道"),
    TAG2("tag", new Byte("2"),"优质分销商居多"),
    TAG3("tag", new Byte("3"),"全部线上流程"),
    REGISTER1("registerResource", new Byte("1"),"APP注册"),
    REGISTER2("registerResource", new Byte("2"),"后台添加"),
    REGISTER3("registerResource", new Byte("3"),"公众号注册"),
    APP1("appRegister", new Byte("1"),"入驻资料免审"),
    APP2("appRegister", new Byte("2"),"补充资料免审"),
    OPERATION1("operation", new Byte("1"),"线上运营"),
    OPERATION2("operation", new Byte("2"),"线下运营"),
    DOCKING1("docking", new Byte("1"),"线上对接"),
    DOCKING2("docking", new Byte("2"),"接口对接"),
    SETTLEMENT1("settlement", new Byte("1"),"底价结算"),
    SETTLEMENT2("settlement", new Byte("2"),"返利结算")
    ;



    // 成员变量
    private String name;
    private Byte value;
    private String type;

    // 构造方法
    ChannelEnum(String type, Byte value, String name) {
        this.name = name;
        this.value = value;
        this.type = type;
    }
    // 普通方法
    public static String getName(String type,String indexs) {
        List<Map<String,Object>> listMap = new ArrayList<>();
        for (ChannelEnum c : ChannelEnum.values()) {
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
//        Map<String,Object> map1 = new HashMap<>();
//        map1.put(type,listMap);

        return JSON.toJSONString(listMap);
    }
    public static final List<Map<String,Object>> mapList = new ArrayList<>();


    static{
        String type = "stateLabel";
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(ChannelEnum stateEnum : ChannelEnum.values()){

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
