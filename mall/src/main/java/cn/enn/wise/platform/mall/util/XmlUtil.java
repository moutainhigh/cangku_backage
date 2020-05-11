/**  
 * @Project tourism-common
 * @Title XmlUtil.java   
 * @Package cn.enn.tourism.common.util   
 * @author whz     
 * @date 2019年4月16日 下午6:03:27   
 * @version V1.0 
 * @Copyright 2019 All rights Reserved, Designed By whz.
 *  
*/  
  
package cn.enn.wise.platform.mall.util;

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


/**  
 * xml工具类
 * @Description 
 * @ClassName  XmlUtil    
 * @date  2019年4月16日 下午6:03:27 
 * @author whz  
 * @since JDK 1.8  
 */
public class XmlUtil {
	 /**
     * 解析xml,返回第一级元素键值对。如果第一级元素有子节点，则此节点的值是子节点的xml数据。
     *
     * @param strxml
     * @return
     * @throws
     * @throws IOException
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
	public static Map doXMLParse(String strxml,int type) {
        if (null == strxml || "".equals(strxml)) {
            return null;
        }

        Map m = new HashMap();
        InputStream in = new ByteArrayInputStream(strxml.getBytes());;
        SAXBuilder builder = new SAXBuilder();
        Document doc;
		try {
			doc = builder.build(in);
			Element root = doc.getRootElement();
	        List list = root.getChildren();
	        Iterator it = list.iterator();
	        while (it.hasNext()) {
	            Element e = (Element) it.next();
	            String k = e.getName();
	            Object v = "";
	            List children = e.getChildren();
	            if (children.isEmpty()) {
	                v = e.getTextNormalize();
	            } else if(type==0) {
	                v = getChildrenText(children);
	            }else if(type==1) {
	                v = parseChildren(children);
	            }
	            
	            m.put(k, v);
	        }
		} catch (Exception e) {
			throw new RuntimeException(e);
		}finally {
			try {
				//关闭流
				in.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
        return m;
    }
    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    @SuppressWarnings("rawtypes")
    private static String getChildrenText(List children) {
        StringBuffer sb = new StringBuffer();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
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
    
    
    /**
     * 获取子结点的xml
     *
     * @param children
     * @return String
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
	private static Map parseChildren(List children) {
        Map map = new HashMap();
        if (!children.isEmpty()) {
            Iterator it = children.iterator();
            while (it.hasNext()) {
                Element e = (Element) it.next();
                String name = e.getName();
                String value = e.getTextNormalize();
                List list = e.getChildren();
                if (!list.isEmpty()) {
                	map.put(name,parseChildren(list));
                }else
                	map.put(name, value);
            }
        }
        return map;
    }
}
 
