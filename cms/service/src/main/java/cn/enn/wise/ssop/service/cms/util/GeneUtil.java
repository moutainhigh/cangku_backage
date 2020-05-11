package cn.enn.wise.ssop.service.cms.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * 常规工具类
 * 
 * @author whz
 * 
 */
@Slf4j
public class GeneUtil {

	

	/**
	 * 判断集合是否为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <E> boolean isNullOrEmpty(Collection<E> collection) {
		if (null == collection || collection.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * 判断集合大小是否相等
	 * 
	 * @param collection
	 * @return
	 */
	public static <E> boolean isEqualSize(Collection<E> collection,
			Collection<E> other) {
		if (collection == null && other == null)
			return true;
		else if (collection == null)
			return false;
		else if (other == null)
			return false;
		else if (collection.size() != other.size())
			return false;
		else
			return true;
	}

	/**
	 * 判断map是否为空
	 * 
	 * @param map
	 * @return
	 */
	public static <T, V> boolean isNullOrEmpty(Map<T, V> map) {
		if (null == map || map.isEmpty())
			return true;
		else
			return false;
	}

	/**
	 * 判断map是否不为空
	 * 
	 * @param map
	 * @return
	 */
	public static <T, V> boolean isNotNullAndEmpty(Map<T, V> map) {
		return !isNullOrEmpty(map);
	}

	/**
	 * 判断集合是否不为空
	 * 
	 * @param collection
	 * @return
	 */
	public static <E> boolean isNotNullAndEmpty(Collection<E> collection) {
		return !isNullOrEmpty(collection);
	}

	/**
	 * 判断字符串是否为空或为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNullWithTrim(String str) {
		if (null == str || "".equals(str.trim()))
			return true;
		else
			return false;
	}
	/**
	 * 判断字符串是否相同,忽略大小写
	 * @param str
	 * @return
	 */
	public static boolean equalsIgnoreCase(String str,String other) {
		if (str == other)
			return true;
		else if(str == null)
			return false;
		else if(str.equalsIgnoreCase(other))
			return true;
		else 
			return false;
	}
	/**
	 * 判断字符串是否相同
	 * @param str
	 * @return
	 */
	public static boolean equals(String str,String other) {
		if (str == other)
			return true;
		else if(str == null)
			return false;
		else if(str.equals(other))
			return true;
		else 
			return false;
	}

	/**
	 * 判断字符串是否为空或为null
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNotNullWithTrim(String str) {
		return !isNullWithTrim(str);
	}
	/**
	 * 判断两个double是否相等
	 * @author whz  
	 * @param a
	 * @param b
	 * @return  
	 * @since JDK 1.8
	 */
	public static boolean equal(double a, double b) {
		double timesA = a*10000;
		double timesB = b*10000;
        if ((timesA- timesB> -0.000000001) && (timesA- timesB) < 0.000000001)
            return true;
        else
            return false;
    }
	/**
	 * 判断两个double是否相等
	 * @author whz  
	 * @param a
	 * @param b
	 * @return  
	 * @since JDK 1.8
	 */
	public static boolean equal(double a, double b,double e) {
        if ((a- b> 0 - e) && (a- b) < e)
            return true;
        else
            return false;
    }
	/**
	 * 判断两个float是否相等
	 * @author whz  
	 * @param a
	 * @param b
	 * @return  
	 * @since JDK 1.8
	 */
	public static boolean equal(float a, float b) {
        if ((a- b> -0.000001) && (a- b) < 0.000001)
            return true;
        else
            return false;
    }
	/**
	 * 判断两个float是否相等
	 * @author whz  
	 * @param a
	 * @param b
	 * @return  
	 * @since JDK 1.8
	 */
	public static boolean equal(float a, float b,float e) {
        if ((a- b> 0 - e) && (a- b) < e)
            return true;
        else
            return false;
    }
	/**
	 * double类型转换
	 * @param value
	 * @return
	 */
	public static Double getDouble(String value) {
		boolean p = false;
		boolean b = false;
		double d = 0.000000d;
		if (null == value)
			return d;
		if (value.contains("¥"))
			value = value.replaceAll("¥", "");
		if (value.charAt(0) == '-') {
			p = true;
			value = value.substring(1, value.length());
		}
		if (value.charAt(value.length() - 1) == '%') {
			b = true;
			value = value.substring(0, value.length() - 1);
		}
		d = Double
				.valueOf((!Pattern.matches("^\\d+(\\.\\d+)?$", value)) ? "0.00"
						: value);
		if (p)
			d = 0 - d;
		if (b)
			d = d / 100;
		return (double) Math.round(d * 10000000) / 10000000;
	}

	/**
	 * int类型转换
	 * 
	 * @param value
	 * @return
	 */
	public static int getInt(String value) {

		return Integer.valueOf((null == value || !Pattern.matches("^[0-9]+",
				value)) ? "0" : value);
	}

	/**
	 * 字符串截取
	 * 
	 * @param origStr
	 * @param start
	 * @param end
	 * @return
	 */
	public static String split(String origStr, String start, String end) {

		if (origStr == null || start == null)
			return null;
		int sin = origStr.indexOf(start);
		if (-1 == sin)
			return null;
		int ein = origStr.indexOf(end, sin + start.length());
		if (-1 == ein)
			return split(origStr, start);
		else {
			return origStr.substring(sin + start.length(), ein);
		}
	}

	/**
	 * 从右向左截取第一个flag之后的子串,返回整形
	 * 
	 * @param flag 截取标志
	 * @param origStr 原始字符串
	 * @return
	 */
	public static String lastSubString(String origStr, String flag) {
		if (isNullWithTrim(origStr))
			return null;
		else if (isNullWithTrim(flag))
			return origStr;
		int index = origStr.lastIndexOf(flag);
		return origStr.substring(index + 1);
	}

	/**
	 * 字符串截取
	 * 
	 * @param origStr
	 * @param start
	 * @return
	 */
	public static String split(String origStr, String start) {
		if (origStr == null || start == null)
			return null;
		int sin = origStr.indexOf(start);
		if (-1 != sin) {
			return origStr.substring(sin + start.length());
		}
		return null;
	}

	/**
	 * 字符串中提取前面数字，直到第一个不是数字为止
	 * 
	 * @param str
	 * @return
	 */
	public static int parseInt(String str) {
		if (str == null || "".equals(str))
			return 0;
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		return getInt(m.replaceAll("").trim());
	}

	/**
	 * 线程暂停
	 * 
	 * @param sleepTime
	 * @param sleepTime
	 */
	public static void sleep(long sleepTime) {
		try {
			Thread.sleep(sleepTime);
		} catch (InterruptedException e) {
			log.error("thread sleep error");
			throw new RuntimeException("thread sleep error");
		}
	}

	/**
	 * long类型转换
	 * @param value
	 * @return
	 */
	public static long getLong(String value) {
		if (isNotNullWithTrim(value) && value.contains(","))
			value = value.replaceAll(",", "");
		return Long.valueOf((null == value || !Pattern
				.matches("^[0-9]+", value)) ? "0" : value);
	}
	/**
	 * long类型转换
	 * @param value
	 * @return
	 */
	public static short getShort(String value) {
		return Short.valueOf((null == value || !Pattern
				.matches("^[0-9]+", value)) ? "0" : value);
	}

	/**
	 * 字符串中提取数值
	 * 
	 * @param str
	 * @return
	 */
	public static long getNumber(String str) {
		if (isNullWithTrim(str))
			return 0L;
		str = removeZero(str);
		String regEx = "[^0-9]";
		Pattern p = Pattern.compile(regEx);
		Matcher m = p.matcher(str);
		str = m.replaceAll("").trim();
		return getLong(str);
	}

	/**
	 * 去除字符串前面的0
	 * 
	 * @param str
	 * @return
	 */
	public static String removeZero(String str) {
		if (isNullWithTrim(str))
			return str;
		if (!str.startsWith("0"))
			return str;

		str = str.substring(1, str.length());
		if (isNotNullWithTrim(str) && str.startsWith("0"))
			return removeZero(str);
		return str;
	}

	/**
	 * 去除回车换行空格及制表符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		String dest = "";
		if (str != null) {
			Pattern p = Pattern.compile("\\s*|\t|\r|\n");
			Matcher m = p.matcher(str);
			dest = m.replaceAll("");
		}
		return dest;
	}

	/**
	 * 字符串替换
	 * 
	 * @param origStr
	 * @param start
	 * @param end
	 * @param rep
	 * @return
	 */
	public static String repliceWith(String origStr, String start, String end,
			String rep) {
		String regix = split(origStr, start, end);
		if (null != regix && rep != null)
			return origStr.replace(rep, rep);
		else
			return null;
	}

	/**
	 * 字符串替换
	 * 
	 * @param origStr
	 * @param start
	 * @param rep
	 * @return
	 */
	public static String repliceWith(String origStr, String start, String rep) {
		String regix = split(origStr, start);
		if (null != regix && rep != null)
			return origStr.replace(regix, rep);
		else
			return null;
	}
	/**
	 * 获取标准化道路接口
	 * @author whz  
	 * @param roadNo
	 * @return  
	 * @since JDK 1.8
	 */
	public static String getStandardizedRoadNo(String roadNo){
		if(GeneUtil.isNullWithTrim(roadNo)||!roadNo.contains(GeneConstant.UNDERLINE))
			return null;
		String[] split = roadNo.split(GeneConstant.UNDERLINE);
		if(split.length!=2)
			return null;
		int s = GeneUtil.getInt(split[0]);
		if(s==0)
			return null;
		int e = GeneUtil.getInt(split[1]);
		if(e==0)
			return null;
		return s>e?e+GeneConstant.UNDERLINE+s:roadNo;
	}
	/**
	 * 去除url配置中的引号
	 * @author whz
	 * @param towoneStopUrl
	 * @return
	 * @since JDK 1.8
	 */
	public static String getUrl(String url) {
		if (url.startsWith("\"") && url.length() > 1)
			url = url.substring(1);
		if (url.endsWith("\"") && url.length() > 1)
			url = url.substring(0, url.length() - 1);
		return url;
	}
	/**
	 * 生产6位数字验证码
	 * @author whz  
	 * @return  
	 * @since JDK 1.8
	 */
	public static String createVerificationCode() {
		Random random = new Random();
		int mes = random.nextInt(9999);
		String str = String.format("%04d", mes);
		return str;
	}
	

	/**
	 * 截取 富文本json的字符
	 * @param content
	 * @param size
	 * @return
	 */
	public static String cutContent(String content,int size){
		if (GeneUtil.isNullWithTrim(content))
			return content;
		int startIndex = content.indexOf("content")+18;
		content = content.contains("\"},{\"") ?content.substring(startIndex, content.indexOf("\"},{\"",startIndex)):content;
		if(content.length()<size){
			size = content.length();
		}else{
			if(content.substring(size-1,size+1).matches("^[a-zA-Z]*")){    //最后两位位存在英文，就找空格(保留英文单词)
				int spaceIndex = content.indexOf(" ", size - 1);
				if(spaceIndex>0){ //存在空格，截取空格之前
					content = content.substring(0,spaceIndex);
				}
			}
		}

		return content;
	}
	
}
