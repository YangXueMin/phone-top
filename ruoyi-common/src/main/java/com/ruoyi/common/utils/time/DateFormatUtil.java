package com.ruoyi.common.utils.time;

import org.apache.commons.lang3.time.DurationFormatUtils;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Date的parse()与format(), 采用Apache Common Lang中线程安全, 性能更佳的FastDateFormat
 *
 * <p>
 * 注意Common Lang版本，3.5版才使用StringBuilder，3.4及以前使用StringBuffer.
 *
 * <p>
 * 1. 常用格式的FastDateFormat定义
 *
 * <p>
 * 2. 日期格式不固定时的String<->Date 转换函数.
 *
 * <p>
 * 3. 打印时间间隔，如"01:10:10"，以及用户友好的版本，比如"刚刚"，"10分钟前"
 *
 * @author calvin
 * @see FastDateFormat#parse(String)
 * @see FastDateFormat#format(Date)
 * @see FastDateFormat#format(long)
 */
@SuppressWarnings("PMD")
public class DateFormatUtil {

	// 以T分隔日期和时间，并带时区信息，符合ISO8601规范
	public static final String PATTERN_ISO = "yyyy-MM-dd'T'HH:mm:ss.SSSZZ";
	public static final String PATTERN_ISO_ON_SECOND = "yyyy-MM-dd'T'HH:mm:ssZZ";
	public static final String PATTERN_ISO_ON_DATE = "yyyy-MM-dd";
	public static final String PATTERN_ISO_ON_MONTH_DATE = "yyyy-MM";
	public static final String PATTERN_ISO_ON_HO_DATE = "yyyyMMdd";
	public static final String PATTERN_ISO_ON_WECHAT_DATE = "yyyyMMddHHmmss";
	public static final String PATTERN_ISO_DATE = "yyyy-MM-dd HH:mm";
	public static final String PATTERN_ISO_DATE_API = "HH:mm yyyy-MM-dd";
	public static final String PATTERN_ISO_CALENDAR_DATE = "yyyy-M-d";

	public static final String PATTERN_ISO_ON_YEAR = "yyyy";
	public static final String PATTERN_ISO_ON_MONTH = "MM";
	public static final String PATTERN_ISO_ON_DAY = "dd";
	public static final String PATTERN_ISO_ON_TIME = "HH:mm";
	public static final String PATTERN_ISO_ON_DAY_TIME = "MM-dd HH:mm";

	// 以空格分隔日期和时间，不带时区信息
	public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss.SSS";
	public static final String PATTERN_DEFAULT_ON_SECOND = "yyyy-MM-dd HH:mm:ss";
	public static final String PATTERN_DEFAULT_SECOND = "yyyyMMddHHmmss";

	// 使用工厂方法FastDateFormat.getInstance(), 从缓存中获取实例

	// 以T分隔日期和时间，并带时区信息，符合ISO8601规范
	public static final FastDateFormat ISO_FORMAT = FastDateFormat.getInstance(PATTERN_ISO);
	public static final FastDateFormat ISO_ON_SECOND_FORMAT = FastDateFormat.getInstance(PATTERN_ISO_ON_SECOND);
	public static final FastDateFormat ISO_ON_DATE_FORMAT = FastDateFormat.getInstance(PATTERN_ISO_ON_DATE);

	// 以空格分隔日期和时间，不带时区信息
	public static final FastDateFormat DEFAULT_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT);
	public static final FastDateFormat DEFAULT_ON_SECOND_FORMAT = FastDateFormat.getInstance(PATTERN_DEFAULT_ON_SECOND);

	/**
	 * 分析日期字符串, 仅用于pattern不固定的情况.
	 *
	 * <p>
	 * 否则直接使用DateFormats中封装好的FastDateFormat.
	 *
	 * <p>
	 * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
	 */
	public static Date pareDate(String pattern, String dateString) throws ParseException {
		return FastDateFormat.getInstance(pattern).parse(dateString);
	}

	/**
	 * 格式化日期, 仅用于pattern不固定的情况.
	 *
	 * <p>
	 * 否则直接使用本类中封装好的FastDateFormat.
	 *
	 * <p>
	 * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
	 */
	public static String formatDate(String pattern, Date date) {
		return FastDateFormat.getInstance(pattern).format(date);
	}

	/**
	 * 格式化日期, 仅用于不固定pattern不固定的情况.
	 *
	 * <p>
	 * 否否则直接使用本类中封装好的FastDateFormat.
	 *
	 * <p>
	 * FastDateFormat.getInstance()已经做了缓存，不会每次创建对象，但直接使用对象仍然能减少在缓存中的查找.
	 */
	public static String formatDate(String pattern, long date) {
		return FastDateFormat.getInstance(pattern).format(date);
	}

	/////// 格式化间隔时间/////////

	/**
	 * 按HH:mm:ss.SSS格式，格式化时间间隔.
	 *
	 * <p>
	 * endDate必须大于startDate，间隔可大于1天，
	 */
	public static String formatDuration(Date startDate, Date endDate) {
		return DurationFormatUtils.formatDurationHMS(endDate.getTime() - startDate.getTime());
	}

	/**
	 * 按HH:mm:ss.SSS格式，格式化时间间隔
	 *
	 * <p>
	 * 单位为毫秒，必须大于0，可大于1天
	 */
	public static String formatDuration(long durationMillis) {
		return DurationFormatUtils.formatDurationHMS(durationMillis);
	}

	/**
	 * 按HH:mm:ss格式，格式化时间间隔
	 *
	 * <p>
	 * endDate必须大于startDate，间隔可大于1天
	 */
	public static String formatDurationOnSecond(Date startDate, Date endDate) {
		return DurationFormatUtils.formatDuration(endDate.getTime() - startDate.getTime(), "HH:mm:ss");
	}

	/**
	 * 按HH:mm:ss格式，格式化时间间隔
	 *
	 * <p>
	 * 单位为毫秒，必须大于0，可大于1天
	 */
	public static String formatDurationOnSecond(long durationMillis) {
		return DurationFormatUtils.formatDuration(durationMillis, "HH:mm:ss");
	}

	//////// 打印用于页面显示的用户友好，与当前时间比的时间差

	/**
	 * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX
	 *
	 * <p>
	 * from AndroidUtilCode
	 */
	public static String formatFriendlyTimeSpanByNow(Date date) {
		return formatFriendlyTimeSpanByNow(date.getTime());
	}

	/**
	 * 打印用户友好的，与当前时间相比的时间差，如刚刚，5分钟前，今天XXX，昨天XXX
	 *
	 * <p>
	 * from AndroidUtilCode
	 */
	public static String formatFriendlyTimeSpanByNow(long timeStampMillis) {
		long now = System.currentTimeMillis();
		long span = now - timeStampMillis;
		if (span < 0) {
			// 'c' 日期和时间，被格式化为 "%ta %tb %td %tT %tZ %tY"，例如 "Sun Jul 20 16:17:00 EDT 1969"。
			return String.format("%tc", timeStampMillis);
		}
		if (span < DateUtil.MILLIS_PER_SECOND) {
			return "刚刚";
		} else if (span < DateUtil.MILLIS_PER_MINUTE) {
			return String.format("%d秒前", span / DateUtil.MILLIS_PER_SECOND);
		} else if (span < DateUtil.MILLIS_PER_HOUR) {
			return String.format("%d分钟前", span / DateUtil.MILLIS_PER_MINUTE);
		}
		// 获取当天00:00
		long wee = DateUtil.beginOfDate(new Date(now)).getTime();
		if (timeStampMillis >= wee) {
			// 'R' 24 小时制的时间，被格式化为 "%tH:%tM"
			return String.format("今天%tR", timeStampMillis);
		} else if (timeStampMillis >= wee - DateUtil.MILLIS_PER_DAY) {
			return String.format("昨天%tR", timeStampMillis);
		} else {
			// 'F' ISO 8601 格式的完整日期，被格式化为 "%tY-%tm-%td"。
			return String.format("%tF", timeStampMillis);
		}
	}

	/**
	 * 获取中文周几
	 *
	 * @param startDay 开始时间，往前数几天
	 * @param endDay   结束时间，往前数几天
	 *                 开始时间要大于结束时间
	 * @return
	 */
	public static Map<String, Object> getWeekChinese(int startDay, int endDay) {
		if (endDay - startDay > 0) {
			return null;
		}
		String[] weekDaysNames = new String[startDay - endDay + 1];
		String startTime = getPastDate(startDay);
		String endTime = getPastDate(endDay);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		List<Date> dateList = null;
		List<String> dayList = new ArrayList<>();
		try {
			dateList = getBetweenDates(sdf.parse(startTime), sdf.parse(endTime));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		for (int i = 0; i < dateList.size(); i++) {
			dayList.add(sdf.format(dateList.get(i)));
			weekDaysNames[i] = dateToWeek(sdf.format(dateList.get(i)));
		}

		Map<String, Object> result = new HashMap<>(16);
		result.put("date", dayList);
		result.put("name", weekDaysNames);
		return result;
	}

	/**
	 * 获取过去第几天的日期
	 *
	 * @param past
	 * @return
	 */
	public static String getPastDate(int past) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
		Date today = calendar.getTime();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(today);
	}

	/**
	 * @param start 开始日期
	 * @param end   结束日期
	 * @return List集合
	 * @doc 获取日期间的日期
	 * @author lzy
	 * @history 2017年10月17日 上午9:55:04 Create by 【lzy】
	 */
	public static List<Date> getBetweenDates(Date start, Date end) {
		List<Date> result = new ArrayList<>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(start);
		//添加或减去指定的时间给定日历领域，基于日历的规则。例如，从日历当前的时间减去5天，您就可以通过
		tempStart.add(Calendar.DAY_OF_YEAR, 0);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(end);
		tempEnd.add(Calendar.DAY_OF_YEAR, 1);
		while (tempStart.before(tempEnd)) {
			result.add(tempStart.getTime());
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * @param start 开始日期  yyyy-MM-dd
	 * @param end   结束日期 yyyy-MM-dd
	 * @return List集合yyyy-MM-dd
	 * @doc 获取日期间的日期
	 * @author lzy
	 * @history 2017年10月17日 上午9:55:04 Create by 【lzy】
	 */
	public static List<String> getBetweenDateStrings(String start, String end) throws ParseException{
		List<String> result = new ArrayList<>();
		Calendar tempStart = Calendar.getInstance();
		tempStart.setTime(DateFormatUtil.pareDate(DateFormatUtil.PATTERN_ISO_ON_DATE,start));
		//添加或减去指定的时间给定日历领域，基于日历的规则。例如，从日历当前的时间减去5天，您就可以通过
		tempStart.add(Calendar.DAY_OF_YEAR, 0);

		Calendar tempEnd = Calendar.getInstance();
		tempEnd.setTime(DateFormatUtil.pareDate(DateFormatUtil.PATTERN_ISO_ON_DATE,end));
		tempEnd.add(Calendar.DAY_OF_YEAR, 1);
		while (tempStart.before(tempEnd)) {
			result.add(DateFormatUtil.formatDate(DateFormatUtil.PATTERN_ISO_ON_DATE,tempStart.getTime()));
			tempStart.add(Calendar.DAY_OF_YEAR, 1);
		}
		return result;
	}

	/**
	 * @param datetime 日期 例:2017-10-17
	 * @return String 例:星期二
	 * @doc 日期转换星期几
	 * @author lzy
	 * @history 2017年10月17日 上午9:55:30 Create by 【lzy】
	 */
	public static String dateToWeek(String datetime) {
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
		String[] weekDays = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
		// 获得一个日历
		Calendar cal = Calendar.getInstance();
		Date date;
		try {
			date = f.parse(datetime);
			cal.setTime(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 指示一个星期中的某天。
		int w = cal.get(Calendar.DAY_OF_WEEK) - 1;
		if (w < 0) {
			w = 0;
		}
		return weekDays[w];
	}
}
