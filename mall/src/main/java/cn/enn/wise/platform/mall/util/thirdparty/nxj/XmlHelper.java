package cn.enn.wise.platform.mall.util.thirdparty.nxj;

import net.sf.json.*;
import net.sf.json.xml.XMLSerializer;

public class XmlHelper {

    /**
     * JSON(数组)字符串转换成XML字符串
     * （必须引入 xom-1.1.jar）
     *
     * @param jsonString
     * @return
     */
    public static String json2xml(String jsonString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        return xmlSerializer.write(JSONSerializer.toJSON(jsonString));
    }

    /**
     * xml 转 json
     * （必须引入 xom-1.1.jar）
     *
     * @param xmlString xml字符串
     * @return
     */
    public static String xml2json(String xmlString) {
        XMLSerializer xmlSerializer = new XMLSerializer();
        JSON json = xmlSerializer.read(xmlString);
        return json.toString(1);
    }


    public static Map<String, Object> parserJson2Map(String jsonStr) {
        HashMap map = new HashMap();
        JSONObject json = JSONObject.fromObject(jsonStr);
        Iterator i$ = json.keySet().iterator();

        while(i$.hasNext()) {
            Object k = i$.next();
            map.put(k.toString(), parserValue(json.get(k)));
        }

        return map;
    }


    private static Object parserValue(Object v) {
        String vstr = v.toString();
        return v instanceof JSONArray ?parseJson2List(vstr):(v instanceof JSONObject?parserJson2Map(vstr):(v instanceof JSONString ?vstr:v));
    }

    public static List<Object> parseJson2List(String jsonStr) {
        ArrayList list = new ArrayList();
        JSONArray jsonArr = JSONArray.fromObject(jsonStr);
        Iterator iterator = jsonArr.iterator();

        while(iterator.hasNext()) {
            list.add(parserValue(iterator.next()));
        }

        return list;
    }

}
