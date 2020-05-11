package cn.enn.wise.platform.mall.constants;

import java.util.HashMap;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/11/28 12:47
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
public enum PeriodEnum {

    MORNING(1, "上午08:00-13:00"),
    AFTERNOON(2, "下午13:00-18:00"),
    ALLDAT(3,"全天08:00-18:00");

    private int key;
    private String text;

    private PeriodEnum(int key, String text) {
        this.key = key;
        this.text = text;
    }
    public int getKey() {
        return key;
    }
    public String getText() {
        return text;
    }
    private static HashMap<Integer,PeriodEnum> map = new HashMap<Integer,PeriodEnum>();
    static {
        for (PeriodEnum d : PeriodEnum.values()){
            map.put(d.key, d);
        }
    }
    public static PeriodEnum parse(Integer index) {
        if(map.containsKey(index)){
            return map.get(index);
        }
        return null;
    }
}
