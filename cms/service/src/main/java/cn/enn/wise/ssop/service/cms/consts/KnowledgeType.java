package cn.enn.wise.ssop.service.cms.consts;

import java.util.HashMap;

public enum  KnowledgeType {

    HELP(1, "预定帮助（猜您想问）"),
    TICKET(2, "取票登船（猜您想问）"),
    REFUND(3,"关于退改（猜您想问）"),
    SAFE(4,"旅客安全须知"),
    ELSE(5,"其它");


    private int key;
    private String text;

    private KnowledgeType(int key, String text) {
        this.key = key;
        this.text = text;
    }
    public int getKey() {
        return key;
    }
    public String getText() {
        return text;
    }
    private static HashMap<Integer,KnowledgeType> map = new HashMap<Integer,KnowledgeType>();
    static {
        for (KnowledgeType d : KnowledgeType.values()){
            map.put(d.key, d);
        }
    }
    public static KnowledgeType parse(Integer index) {
        if(map.containsKey(index)){
            return map.get(index);
        }
        return null;
    }
}