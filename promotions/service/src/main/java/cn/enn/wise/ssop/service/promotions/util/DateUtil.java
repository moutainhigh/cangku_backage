package cn.enn.wise.ssop.service.promotions.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

/**
 * 时间工具类
 *
 * @author whz
 */
public class DateUtil {
	/**
	 * 日志
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DateUtil.class);

	/**
	 * 获取当天的开始时间
	 *
	 * @return
	 */
	public static Date getDayBegin() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	/**
	 * 获取当天的结束时间
	 *
	 * @return
	 */
	public static Date getDayEnd() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		return cal.getTime();
	}

	/**
	 * 获取昨天的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfYesterday() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取昨天的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfYesterDay() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, -1);
		return cal.getTime();
	}

	/**
	 * 获取明天的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 1);

		return cal.getTime();
	}

	/**
	 * 获取明天的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfTomorrow() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}

	/**
	 * 获取后天的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfAcquired() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayBegin());
		cal.add(Calendar.DAY_OF_MONTH, 2);

		return cal.getTime();
	}

	/**
	 * 获取后天的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfAcquired() {
		Calendar cal = new GregorianCalendar();
		cal.setTime(getDayEnd());
		cal.add(Calendar.DAY_OF_MONTH, 2);
		return cal.getTime();
	}

	/**
	 * 获取本周的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfWeek() {
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int dayofweek = cal.get(Calendar.DAY_OF_WEEK);
		if (dayofweek == 1) {
			dayofweek += 7;
		}
		cal.add(Calendar.DATE, 2 - dayofweek);
		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本周的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getBeginDayOfWeek());
		cal.add(Calendar.DAY_OF_WEEK, 6);
		Date weekEndSta = cal.getTime();
		return getDayEndTime(weekEndSta);
	}

	/**
	 * 获取本月的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		return getDayStartTime(calendar.getTime());
	}

	/**
	 * 获取本月的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(getNowYear(), getNowMonth() - 1, 1);
		int day = calendar.getActualMaximum(5);
		calendar.set(getNowYear(), getNowMonth() - 1, day);
		return getDayEndTime(calendar.getTime());
	}

	/**
	 * 获取本年的开始时间
	 *
	 * @return
	 */
	public static Date getBeginDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DATE, 1);

		return getDayStartTime(cal.getTime());
	}

	/**
	 * 获取本年的结束时间
	 *
	 * @return
	 */
	public static Date getEndDayOfYear() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.YEAR, getNowYear());
		cal.set(Calendar.MONTH, Calendar.DECEMBER);
		cal.set(Calendar.DATE, 31);
		return getDayEndTime(cal.getTime());
	}

	/**
	 * 获取某个日期的开始时间
	 *
	 * @param d
	 * @return
	 */
	public static Timestamp getDayStartTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
				0, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某个日期的开始时间
	 *
	 * @param value
	 * @return
	 */
	public static Timestamp getDayStartTime(String value)  {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calendar = Calendar.getInstance();
		try{
			Date d = sdf.parse(value.trim());

			if (null != d){
				calendar.setTime(d);
			}

			calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 0,
					0, 0);
			calendar.set(Calendar.MILLISECOND, 0);
			return new Timestamp(calendar.getTimeInMillis());
		}catch(Exception e){
			e.printStackTrace();
		}
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某个日期的结束时间
	 *
	 * @param d
	 * @return
	 */
	public static Timestamp getDayEndTime(Date d) {
		Calendar calendar = Calendar.getInstance();
		if (null != d)
			calendar.setTime(d);
		calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), 23,
				59, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return new Timestamp(calendar.getTimeInMillis());
	}

	/**
	 * 获取某天增加几日后的时间
	 * @param d
	 * @param day
	 * @return
	 */
	public static Timestamp getNowTimeAddSomeDay(Date d,Integer day){
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(Calendar.DATE, day);
		 return new Timestamp(ca.getTimeInMillis());
	}

	/**
	 * 获取某天增加几日后的时间
	 * @param day
	 * @return
	 */
	public static Timestamp getNowTimeAddSomeDay(Integer day){
		Date d = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		ca.add(Calendar.DATE, day);
		return new Timestamp(ca.getTimeInMillis());
	}

	/**
	 * 获取当前时间
	 * @return
	 */
	public static Timestamp getNowDayTime(){
		Date d = new Date();
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		return new Timestamp(ca.getTimeInMillis());
	}
	/**
	 * 获取今年是哪一年
	 *
	 * @return
	 */
	public static Integer getNowYear() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return Integer.valueOf(gc.get(1));
	}

	/**
	 * 获取本月是哪一月
	 *
	 * @return
	 */
	public static int getNowMonth() {
		Date date = new Date();
		GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
		gc.setTime(date);
		return gc.get(2) + 1;
	}

	/**
	 * 两个日期相减得到的天数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int getDiffDays(Date beginDate, Date endDate) {

		if (beginDate == null || endDate == null) {
			throw new IllegalArgumentException("getDiffDays param is null!");
		}

		long diff = (endDate.getTime() - beginDate.getTime()) / (1000 * 60 * 60 * 24);

		int days = new Long(diff).intValue();

		return days;
	}

	/**
	 * 两个日期相减得到的毫秒数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long dateDiff(Date beginDate, Date endDate) {
			long date1ms = beginDate.getTime();
			long date2ms = endDate.getTime();
			return date2ms - date1ms;
	}
	/**
	 * 两个日期相减得到的秒数
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static long dateDiffSce(Date beginDate, Date endDate) {

		return dateDiff(beginDate, endDate)/1000;
	}
	/**
	 * 获取两个日期中的最大日期
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date max(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return beginDate;
		}
		return endDate;
	}

	/**
	 * 获取两个日期中的最小日期
	 *
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static Date min(Date beginDate, Date endDate) {
		if (beginDate == null) {
			return endDate;
		}
		if (endDate == null) {
			return beginDate;
		}
		if (beginDate.after(endDate)) {
			return endDate;
		}
		return beginDate;
	}

	/**
	 * 返回某月该季度的第一个月
	 *
	 * @param date
	 * @return
	 */
	public static Date getFirstSeasonDate(Date date) {
		final int[] SEASON = { 1, 1, 1, 2, 2, 2, 3, 3, 3, 4, 4, 4 };
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int sean = SEASON[cal.get(Calendar.MONTH)];
		cal.set(Calendar.MONTH, sean * 3 - 3);
		return cal.getTime();
	}

	/**
	 * 返回某个日期下几天的日期
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getNextDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) + i);
		return cal.getTime();
	}

	/**
	 * 返回某个日期前几天的日期
	 * @param date
	 * @param i
	 * @return
	 */
	public static Date getFrontDay(Date date, int i) {
		Calendar cal = new GregorianCalendar();
		cal.setTime(date);
		cal.set(Calendar.DATE, cal.get(Calendar.DATE) - i);
		return cal.getTime();
	}

	/**
	 * 获取某年某月到某年某月按天的切片日期集合（间隔天数的日期集合）
	 *
	 * @param beginYear
	 * @param beginMonth
	 * @param k
	 * @return
	 */
	public static List<List<Date>> getTimeList(int beginYear, int beginMonth, int endYear, int endMonth, int k) {
		List<List<Date>> list = new ArrayList<List<Date>>();
		if (beginYear == endYear) {
			for (int j = beginMonth; j <= endMonth; j++) {
				list.add(getTimeList(beginYear, j, k));

			}
		} else {
			{
				for (int j = beginMonth; j < 12; j++) {
					list.add(getTimeList(beginYear, j, k));
				}

				for (int i = beginYear + 1; i < endYear; i++) {
					for (int j = 0; j < 12; j++) {
						list.add(getTimeList(i, j, k));
					}
				}
				for (int j = 0; j <= endMonth; j++) {
					list.add(getTimeList(endYear, j, k));
				}
			}
		}
		return list;
	}

	/**
	 * 获取某年某月按天切片日期集合（某个月间隔多少天的日期集合）
	 *
	 * @param beginYear
	 * @param beginMonth
	 * @param k
	 * @return
	 */
	public static List<Date> getTimeList(int beginYear, int beginMonth, int k) {
		List<Date> list = new ArrayList<Date>();
		Calendar begincal = new GregorianCalendar(beginYear, beginMonth, 1);
		int max = begincal.getActualMaximum(Calendar.DATE);
		for (int i = 1; i < max; i = i + k) {
			list.add(begincal.getTime());
			begincal.add(Calendar.DATE, k);
		}
		begincal = new GregorianCalendar(beginYear, beginMonth, max);
		list.add(begincal.getTime());
		return list;
	}

	/**
	 * 获取当前星期，0代表星期日，1代表星期一，依次类推
	 *
	 * @return
	 */
	public static int getCurrentWeek() {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取指定天星期，0代表星期日，1代表星期一，依次类推
	 *
	 * @return
	 */
	public static int getWeek(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 字符串换日期
	 *
	 * @param value
	 * @return
	 */
	public static Date parse(String value, String format) {
		Date date = null;

		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			date = sdf.parse(value.trim());
		} catch (ParseException e) {
			LOGGER.error("date format error...");
		}
		return date;
	}

	/**
	 * 字符串换日期
	 *
	 * @param value
	 * @return
	 */
	public static Timestamp parse(String value) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date d = sdf.parse(value.trim());
			Calendar ca = Calendar.getInstance();
			ca.setTime(d);
			return new Timestamp(ca.getTimeInMillis());
		} catch (ParseException e) {
			LOGGER.error("date format error...");
		}
		return null;
	}


	/**
	 * date2比date1多的天数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static int differentDays(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		cal1.setTime(date1);

		Calendar cal2 = Calendar.getInstance();
		cal2.setTime(date2);
		int day1 = cal1.get(Calendar.DAY_OF_YEAR);
		int day2 = cal2.get(Calendar.DAY_OF_YEAR);

		int year1 = cal1.get(Calendar.YEAR);
		int year2 = cal2.get(Calendar.YEAR);
		if (year1 != year2) {
			int timeDistance = 0;
			for (int i = year1; i < year2; i++) {
				if (i % 4 == 0 && i % 100 != 0 || i % 400 == 0) // 闰年
				{
					timeDistance += 366;
				} else // 不是闰年
				{
					timeDistance += 365;
				}
			}

			return timeDistance + (day2 - day1);
		} else {
			return day2 - day1 + 1;
		}
	}

	/**
	 * date2与date1相差的分钟数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long differentMunitess(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return Math.abs(Math.round((time2 - time1) / 1000 / 60));
	}

	/**
	 * date2与date1相差的分钟数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long differentMunitessIsNotAbs(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return Math.round((time2 - time1) / 1000 / 60);
	}
	/**
	 * date2比date1多的分钟数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long plusMunitess(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return Math.round((time2 - time1) / 1000 / 60);
	}
	/**
	 * date2比date1多的秒数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long plusScondes(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return Math.round((time2 - time1) / 1000 );
	}

	/**
	 * date比与munitess做差
	 * @param date
	 * @param munitess
	 * @return
	 */
	public static Date plusMunitess(Date date, int munitess) {
		long time = date.getTime();
		return new Date(time-munitess*60*1000);
	}

	/**
	 * date比与sconds做差
	 * @param date
	 * @param sconds
	 * @return
	 */
	public static Date plusSconds(Date date, int sconds) {
		long time = date.getTime();
		return new Date(time-sconds*1000);
	}

	/**
	 * date比与munitess作和
	 * @param date
	 * @param munitess
	 * @return
	 */
	public static Date addMunitess(Date date, int munitess) {
		long time = date.getTime();
		return new Date(time+munitess*60*1000);
	}

	/**
	 * date比与sconds作和
	 * @param date
	 * @param sconds
	 * @return
	 */
	public static Date addSconds(Date date, int sconds) {
		long time = date.getTime();
		return new Date(time+sconds*1000);
	}


	/**
	 * 获取后一天时间
	 *
	 * @param date
	 * @return
	 */
	public static Date getNextDay(Date date) {
		if (null == date)
			return null;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_MONTH, +1); // 今天的时间加一天
		date = calendar.getTime();
		return date;
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String getFormat(Date date) {
		if (null == date)
			return null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}

	/**
	 * 获取当天字符串日期格式为yyyy-MM-dd
	 * @return
	 */
	public static String getFormatDateForNow() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
	/**
	 * 日期格式化yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static Date getFormatToDate(Date date) {
		if(null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			return sdf.parse(sdf.format(date));
		} catch (ParseException e) {
			return null;
		}
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 *
	 * @param date
	 * @return
	 */
	public static String getFormat(Date date, String format) {
		if (null == date) {
			return null;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}

	/**
	 * 获取当天任意时间的时间对象
	 *
	 * @author whz
	 * @param hour
	 * @param minute
	 * @return
	 * @since JDK 1.8
	 */
	public static Date getTodayDate(int hour, int minute) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, hour);
		cal.set(Calendar.MINUTE, minute);
		return cal.getTime();
	}

	/**
	 * 获取指定分钟后的时间
	 *
	 * @author whz
	 * @param minute
	 * @return
	 * @since JDK 1.8
	 */
	public static Date getNextTime(int minute) {
		return new Date(System.currentTimeMillis() + minute * 60 * 1000);
	}

	/**
	 * 判断时间是否在时间段内
	 *
	 * @param nowTime
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public static boolean belongCalendar(Date nowTime, Date beginTime, Date endTime) {
		Calendar date = Calendar.getInstance();
		date.setTime(nowTime);

		Calendar begin = Calendar.getInstance();
		begin.setTime(beginTime);

		Calendar end = Calendar.getInstance();
		end.setTime(endTime);

		if (date.after(begin) && date.before(end)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 判断当前时间是否在当天某个时间段内
	 *
	 * @author whz
	 * @param begin
	 *            时间段上区间，如06:00
	 * @param end
	 *            时间段下区间，如22:00
	 * @since JDK 1.8
	 */
	public static boolean isBelong(String begin, String end) {

		SimpleDateFormat df = new SimpleDateFormat("HH:mm");
		Date now = null;
		Date beginTime = null;
		Date endTime = null;
		try {
			now = df.parse(df.format(new Date()));
			beginTime = df.parse(begin);
			endTime = df.parse(end);
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}

		return belongCalendar(now, beginTime, endTime);
	}

	/**
	 * 判断某个时间是否在当天某个时间段内
	 *
	 * @author whz
	 * @param begin
	 *            时间段上区间，如06:00
	 * @param end
	 *            时间段下区间，如22:00
	 * @since JDK 1.8
	 */
	public static boolean isBelong(String begin, Date date, String end) {

		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date beginTime = null;
		Date endTime = null;
		try {
			beginTime = df.parse(year+"-"+month+"-"+d+" "+begin);
			endTime = df.parse(year+"-"+month+"-"+d+" "+end);
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}
//		cale = null;
		return belongCalendar(date, beginTime, endTime);
	}
	/**
	 * 判断某个时间是否在当天某个时间段内
	 *
	 * @author whz
	 *            时间段上区间，如06:00
	 * @param end
	 *            时间段下区间，如22:00
	 * @since JDK 1.8
	 */
	public static long plusMunitess(Date date, String end) {

		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date endTime = null;
		try {
			endTime = df.parse(year+"-"+month+"-"+d+" "+end);
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}
//		cale = null;
		return plusMunitess(date, endTime);
	}
	/**
	 * 判断某个时间是否在当天某个时间段内
	 *
	 * @author whz
	 *            时间段上区间，如06:00
	 * @param end
	 *            时间段下区间，如22:00
	 * @since JDK 1.8
	 */
	public static Date getDateFormDate(Date date, String end) {

		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);

		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		Date endTime = null;
		try {
			endTime = df.parse(year+"-"+month+"-"+d+" "+end);
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}
//		cale = null;
		return endTime;
	}


	public static String  getStringFormTimestamp(Timestamp date, String end) {

		Calendar cale = Calendar.getInstance();
		cale.setTime(date);
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);

		SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");

		try {
			return df.format(cale);
//			end = year+"/"+month+"/"+d;
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}
//		cale = null;
		return end;
	}


	/**
	 * 判断当天时间
	 *
	 * @author whz
	 * @since JDK 1.8
	 */
	public static Date getDate(String time) {

		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);
		Date date = null;
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			date = df.parse(year+"-"+month+"-"+d+" "+time);
		} catch (Exception e) {
			LOGGER.error("error: isBelong date format error....");
		}
		return date;
	}
	/**
	 * 日期加分钟
	 * @author whz
	 * @param x
	 * @return
	 * @since JDK 1.8
	 */
	public static Date addDateMinut(Date date, int x) {
		if (date == null)
			return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MINUTE, x);
		return cal.getTime();
	}
	/**
	 * 获取当前日期的下一个日期
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static Date nextDate(Date targetTime,String[] strs){
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = null;
			for (String st : strs) {
				date = df.parse(year+"-"+month+"-"+d+" "+st);
				if(date.getTime() - targetTime.getTime()>0){
					return date;
				}
			}
		} catch (ParseException e) {
			LOGGER.error("error: nextDate date format error....");
		}
		return null;
	}
	/**
	 * 获取当前日期的上一个日期
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static Date lastDate(Date targetTime,String[] strs){
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = null;
			for (int i=strs.length-1;i>=0;i--) {
				date = df.parse(year+"-"+month+"-"+d+" "+strs[i]);
				if(date.getTime() - targetTime.getTime()<0){
					return date;
				}
			}
		} catch (ParseException e) {
			LOGGER.error("error: nextDate date format error....");
		}
		return null;
	}

	/**
	 * 获取当前日期的下一个日期
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static Date getFloorTime(Date targetTime,String[] strs){
		Calendar cale = Calendar.getInstance();
		int year = cale.get(Calendar.YEAR);
		int month = cale.get(Calendar.MONTH)+1;
		int d = cale.get(Calendar.DATE);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		try {
			Date date = null;
			for (int i=0;i<strs.length;i++) {
				date = df.parse(year+"-"+month+"-"+d+" "+strs[i]);
				if(date.getTime() - targetTime.getTime()>0){
					return date;
				}
			}
			return date;
		} catch (ParseException e) {
			LOGGER.error("error: nextDate date format error....");
		}
		return null;
	}
	/**
	 * 获取当前时间
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static String getNowTime(){
		Calendar cale = Calendar.getInstance();
		int i = cale.get(Calendar.HOUR_OF_DAY);
		int j = cale.get(Calendar.MINUTE);
		return i+":"+j;
	}
	/**
	 * 获取当前时间
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static String getNowDate(){
		Date now = new Date();
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return smf.format(now);
	}
	/**
	 * 计算两个日期是否是同一天
	 * @author 蜗牛2219
	 * @param endDate
	 * @return
	 * @since JDK 1.8
	 */
	public static int getDifOfTwoDate(Date endDate){
		if(endDate == null){
			return 0;
		}
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		String endStr = smf.format(endDate);
		String startStr = smf.format(new Date());
		try {
			long l = smf.parse(endStr).getTime() - smf.parse(startStr).getTime();
			if(l == 0){
				return 1;
			}else if(l > 0){
				return 3;
			}else{
				return 4;
			}
		} catch (Exception e) {
			LOGGER.error("判断两日期是否同一天异常"+e.getMessage());
		}
		return 2;

	}
	/**
	 * 判断某一个时间是否在这个时间段
	 * @author 蜗牛2219
	 * @param startDate
	 * @param endDate
	 * @return
	 * @since JDK 1.8
	 */
	public static int isBetweenTime(Date startDate,Date endDate){
		if(startDate == null && endDate == null){
			return 0;
		}
		SimpleDateFormat smf = new SimpleDateFormat("yyyy-MM-dd");
		String endStr = smf.format(endDate);
		String startStr = smf.format(startDate);
		String date = smf.format(new Date());

		try {
			if(smf.parse(date).getTime() > smf.parse(endStr).getTime()){//验票日期大于有效期
				return 3;
			}
			if(smf.parse(date).getTime() < smf.parse(startStr).getTime()){//验票日期小于有效期
				return 3;
			}
			//验票日期在有效期内
			if(smf.parse(date).getTime() <= smf.parse(endStr).getTime() && smf.parse(date).getTime() >= smf.parse(startStr).getTime()){
				return 1;
			}
		} catch (ParseException e) {
			LOGGER.error("判断两日期是否同一天异常"+e.getMessage());
		}
		//程序异常
		return 2;
	}

	/**
	 * 获取当前系统时间戳
	 * @return
	 */
	public static Long getCruuentTime(){
		return System.currentTimeMillis()/1000;
	}



	/**
	 * 获取当前时间的字符串格式
	 * @param format
	 * @author whz
	 * @return
	 * @since JDK 1.8
	 */
	public static String getNowStringDate(String format){
		Date now = new Date(Timestamp.valueOf(LocalDateTime.now()).getTime());
		SimpleDateFormat smf = new SimpleDateFormat(format);
		return smf.format(now);
	}

	/**
	 * 获取前N分钟的时间
	 */
	public static String  beforeMinutes(int minute) {

		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE)-minute);
		return 	new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(calendar.getTime());
	}

	/**
	 * mongo 日期查询isodate
	 * @param dateStr
	 * @return
	 */
	public static Date dateToISODate(String dateStr){
		//T代表后面跟着时间，Z代表UTC统一时间
		Date date = formatD(dateStr);
		SimpleDateFormat format =
				new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setCalendar(new GregorianCalendar(new SimpleTimeZone(0, "GMT")));
		String isoDate = format.format(date);
		try {
			return format.parse(isoDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	/** 时间格式(yyyy-MM-dd HH:mm:ss) */
	public final static String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static Date formatD(String dateStr){
		return formatD(dateStr,DATE_TIME_PATTERN);
	}

	public static Date formatD(String dateStr ,String format)  {
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
		Date ret = null ;
		try {
			ret = simpleDateFormat.parse(dateStr) ;
		} catch (ParseException e) {
			//
		}
		return ret;
	}


    /**
     * 获取某个时间的后多少小时
     * @param timeInMillis
     * 			时间基数
     * @param hours
     * 			多少小时
     * @return
     */
    public static Long getAfterHourTimesTamp(Long timeInMillis,int hours){

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        calendar.set(Calendar.HOUR,calendar.get(Calendar.HOUR)+hours);
        return calendar.getTimeInMillis();
    }

	/**
	 * date2与date1相差的秒数
	 *
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long differentSecondIsNotAbs(Date date1, Date date2) {
		long time1 = date1.getTime();
		long time2 = date2.getTime();
		return Math.round((time2 - time1) / 1000 );
	}
}
