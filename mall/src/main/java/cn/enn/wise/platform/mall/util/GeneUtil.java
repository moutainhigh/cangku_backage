package cn.enn.wise.platform.mall.util;


import cn.enn.wise.platform.mall.bean.vo.ProdCommLabel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/******************************************
 * @author: haoguodong
 * @createDate: 2019/4/23 14:52
 * @since: JDK 1.8
 * @projectName:IntelliJ IDEA
 * @Description:类功能描述
 ******************************************/
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
     * @param collection
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
     * @param collection
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
     * @param xpath
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
     * @param flag截取标志
     * @param origStr原始字符串
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
     * @param nextPage
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
     * @param xpath
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
     * @param xpath
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
     * MD5加密
     * @Title md5
     * @param s
     * @return
     * @since JDK 1.8
     * @throws
     */
    public static String md5(String s){
        char hexDigist[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("md5");
            //16个字节的长整数;注意编码造成结果不一样;
            byte[] datas = md.digest(s.getBytes("utf-8"));
            char[] str = new char[2 * 16];
            int k = 0;
            for(int i=0;i<16;i++){
                byte b= datas[i];
                //高4位
                str[k++] = hexDigist[b >>> 4 & 0xf];
                //低4位
                str[k++] = hexDigist[b & 0xf];
            }
            s = new String(str);
        } catch (Exception e) {
            log.error("md5 error",e);
        }
        return s;
    }


    public static String GetHttpHeader(HttpServletRequest request, String headerName){
        String header = request.getHeader(headerName);
        return header;
    }

    public static String stripHtml(String content) {
// <p>段落替换为换行
        content = content.replaceAll("<p .*?>", "\r\n");
// <br><br/>替换为换行
        content = content.replaceAll("<br\\s*/?>", "\r\n");
// 去掉其它的<>之间的东西
        content = content.replaceAll("\\<.*?>", "");
// 还原HTML
// content = HTMLDecoder.decode(content);
        return content;
    }

    /**
     *
     * 获取6位验证码
     * @Title createVerificationCode
     * @return
     * @since JDK 1.8
     * @throws
     */
    public static String createVerifyCode() {
        Random random = new Random();
        int mes = random.nextInt(999999);
        String str = String.format("%06d", mes);
        return str;
    }



    /**
     * 截取 富文本json的字符
     * @param content
     * @param size
     * @return
     */
    public static String cutContent(String content,int size){
        if (content.isEmpty()) return content;
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

    /**
     * 生成订单号
     * @return
     */
    public static String getCode() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String str = new SimpleDateFormat(GeneConstant.TIME_DATE_FORMAT).format(timestamp);
        Random random = new Random();
        int mes = random.nextInt(999999);
        str += String.format("%06d", mes);
        str += "r";
        return str;
    }

    /**
     * 生成退款单号
     * @return
     */
    public static String getRefundCode() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String str = new SimpleDateFormat(GeneConstant.TIME_DATE_FORMAT).format(timestamp);
        Random random = new Random();
        int mes = random.nextInt(999999);
        str += String.format("%06d", mes);
        str += "tk";
        return str;
    }

    /**
     * 生成bsc酒店订单号
     * @return
     */
    public static String getJDCode() {
        Timestamp timestamp = Timestamp.valueOf(LocalDateTime.now());
        String str = "jd" + new SimpleDateFormat(GeneConstant.TIME_DATE_FORMAT).format(timestamp);
        Random random = new Random();
        int mes = random.nextInt(99999);
        str += String.format("%05d", mes);
        return str;
    }

    /**
     * 订单成功发送短信模板
     * @param name
     * @param goodName
     * @param orderCode
     * @return
     */
    public static String getSendMessageContent(String name,String goodName,String orderCode){

        return String.format("尊敬的%s您好,您预订的%s,已经支付成功,订单号:%s,祝您游玩愉快!",name,goodName,orderCode);
    }

    /**
     * 格式化bigdecimal
     * @param price
     * @return
     */
    public static BigDecimal formatBigDecimal(BigDecimal price){
        if (price == null){
            return new BigDecimal(0);
        }
        BigDecimal one = new BigDecimal("1");
        if(price.compareTo(one) > 0){
            return price.setScale(0,BigDecimal.ROUND_HALF_DOWN);
        }else{
            return price.setScale(2,BigDecimal.ROUND_HALF_DOWN);
        }

    }

    /**
     * 过滤微信特殊字符
     * @param source
     * @return
     */
    public static String filterEmoji(String source) {

        if(source != null)

        {

            Pattern emoji = Pattern.compile ("[\ud83c\udc00-\ud83c\udfff]|[\ud83d\udc00-\ud83d\udfff]|[\u2600-\u27ff]",Pattern.UNICODE_CASE | Pattern . CASE_INSENSITIVE ) ;

            Matcher emojiMatcher = emoji.matcher(source);

            if ( emojiMatcher.find())

            {

                source = emojiMatcher.replaceAll("*");

                return source ;

            }

            return source;

        }

        return source;

    }


    /**
     * list集合转换成String,中间用逗号隔开
     * @param list
     *         集合
     * @return
     *      逗号分割的字符串
     */
    public static String listToString(Collection list){

        if(CollectionUtils.isEmpty(list)){
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (Object o : list) {
            if(o != null){
                stringBuilder.append(",");
                stringBuilder.append(o.toString());

            }
        }
        return stringBuilder.toString().substring(1);
    }

    /**
     * String转换成List集合
     * @param str
     *          字符串
     * @return
     *       list集合
     *
     */
    public static List<String> stringToList(String str){

        if(StringUtils.isEmpty(str)){
            return new ArrayList<>();
        }

        String[] split = str.split(",");
        List<String> stringList = Arrays.asList(split);
        return stringList;
    }

    /**
     * 求a,b两个集合的交集
     * @param a
     * @param b
     * @return 集合的交集 a中包含b的数据
     */
    public static List intersection(List a,List b){
        if(CollectionUtils.isEmpty(a) || CollectionUtils.isEmpty(b)){
            return new ArrayList();
        }

        List list = new ArrayList();

        for (Object o : a) {
            for (Object o1 : b){
                if(o.equals(o1)){
                    list.add(o1);
                }
            }

        }


        return list;
    }

  public static Map<Long,Map<Integer,String>> labelMap = new HashMap(8);
    public static Map<Long,String> hotelLineMap = new HashMap<>(4);

    static  {
        //涠洲岛景区评论标签
        List<ProdCommLabel> prodCommLabelList =new ArrayList<>();
        prodCommLabelList.add(new ProdCommLabel(1L,"体验很棒"));
        prodCommLabelList.add(new ProdCommLabel(2L,"安全系数高"));
        prodCommLabelList.add(new ProdCommLabel(3L,"景色很好"));

        Map<Integer,String> map = new HashMap<>(8);
        map.put(1,"体验很棒");
        map.put(2,"安全系数高");
        map.put(3,"景色很好");

        labelMap.put(11L,map);
        labelMap.put(13L,map);

        //添加热线电话
        hotelLineMap.put(11L,"0779-6015900");
        hotelLineMap.put(13L,"0577—57686237");
    }

    /**
     * 根据景区获取评价标签
     * @param companyId 景区Id
     *
     * @return Map<Integer,String></>
     *  key 标签id
     *  value 标签名称
     */
    public static Map<Integer,String> getLabelListByCompanyId(Long companyId){

        return labelMap.get(companyId);
    }

    /**
     * 获取热线电话
     * @param companyId 景区Id
     * @return 热线电话
     */
    public static String getHotelLine(Long companyId){

    return hotelLineMap.get(companyId);
    }
}
