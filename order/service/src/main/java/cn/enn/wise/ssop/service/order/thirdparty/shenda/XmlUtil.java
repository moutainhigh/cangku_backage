package cn.enn.wise.ssop.service.order.thirdparty.shenda;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class XmlUtil {
    public XmlUtil() {
    }

    public static Map doXMLParse(String strxml, int type) {
        if (null != strxml && !"".equals(strxml)) {
            Map m = new HashMap();
            InputStream in = new ByteArrayInputStream(strxml.getBytes());
            SAXBuilder builder = new SAXBuilder();

            try {
                Document doc = builder.build(in);
                Element root = doc.getRootElement();
                List list = root.getChildren();

                String k;
                Object v;
                for(Iterator it = list.iterator(); it.hasNext(); m.put(k, v)) {
                    Element e = (Element)it.next();
                    k = e.getName();
                    v = "";
                    List children = e.getChildren();
                    if (children.isEmpty()) {
                        v = e.getTextNormalize();
                    } else if (type == 0) {
                        v = getChildrenText(children);
                    } else if (type == 1) {
                        v = parseChildren(children);
                    }
                }
            } catch (Exception var20) {
                throw new RuntimeException(var20);
            } finally {
                try {
                    in.close();
                } catch (IOException var19) {
                    throw new RuntimeException(var19);
                }
            }

            return m;
        } else {
            return null;
        }
    }

    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();

            while(it.hasNext()) {
                Element e = (Element)it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                sb.append("<" + name + ">");
                if (!list.isEmpty()) {
                    sb.append(getChildrenText(list));
                }

                sb.append(value);
                sb.append("</" + name + ">");
            }
        }

        return sb.toString();
    }

    private static Map parseChildren(List children) {
        Map map = new HashMap();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();

            while(it.hasNext()) {
                Element e = (Element)it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                if (!list.isEmpty()) {
                    map.put(name, parseChildren(list));
                } else {
                    map.put(name, value);
                }
            }
        }

        return map;
    }
}
