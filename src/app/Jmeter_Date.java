package app;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Jmeter_Date {
	/* version. */
	private static final String VERSION = "1.0.0.0";
	private static final String COMMENT = "2018-02-02 13:39 胡义东 初始化";

	public String getComment() {
		return COMMENT;
	}

	public String getVersion() {
		return VERSION;
	}

	/** 时间戳转日期 */
	public static String ts2date(String timeStamp, String timeUnit,
			String format) {
		String date = null;
		Long ts = null;

		/* 判断时间戳单位,秒,毫秒或空就报错 */
		if (timeUnit.equals("") && timeUnit.equals("s")
				&& timeUnit.equals("ms")) {
			date = "parameter of timeUnit:'" + timeUnit
					+ "' is incorrect,it must be 's' or 'ms' or ''";

		} else {
			/* 判断时间戳单位,秒就补齐为毫秒 */
			if (timeUnit.equals("s")) {
				timeStamp = timeStamp + "000";
			}
			/* 将时间戳从字符串转为long */
			try {
				ts = Long.parseLong(timeStamp);
			} catch (Exception e) {
				date = "parameter of timeStamp:'" + timeStamp
						+ "' is incorrect";
			}
			try {
				/* 时间戳转日期开始 */
				Timestamp t1 = new Timestamp(ts);
				DateFormat sdf = new SimpleDateFormat(format);
				date = sdf.format(t1);

			} catch (Exception e) {
				/* 无法转化就报错 */
				date = "parameter of format:'" + format + "' is incorrect";
			}
		}
		return date;
	}

	/** 日期转时间戳 */
	public static String date2ts(String date, String format, String timeUnit) {
		String timestamp = null;
		/* 判断时间戳类型,不是s ms 空就报错 */
		if (timeUnit.equals("") && timeUnit.equals("s")
				&& timeUnit.equals("ms")) {
			timestamp = "parameter of timeUnit:'" + timeUnit
					+ "' is incorrect,it must be 's' or 'ms' or ''";
		} else {
			try {
				/* 先将类型保存 */
				DateFormat sdf = new SimpleDateFormat(format);
				/* 将日期数据按类型转成date类型 */
				Date date2 = sdf.parse(date);
				/* 将日期输出为long型,即时间戳 */
				long tslong = date2.getTime();
				/* 判断输出时间戳类型并输出 */
				if (timeUnit.equals("s")) {
					timestamp = String.valueOf(tslong / 1000);
				} else {
					timestamp = String.valueOf(tslong);
				}
				/* format类型或date类型错误 */
			} catch (Exception e) {
				timestamp = "parameter of format:'" + format + "' or date:'"
						+ date + "' is incorrect";
			}
		}
		return timestamp;

	}

	/** 日期计算 */
	public static String dateCALC(String date, String format, String type,
			int integer) {
		String newDate = null;
		Date date2 = null;
		DateFormat sdf = null;
		/* 加载历法Calendar的实例 */
		Calendar c = Calendar.getInstance();
		try {
			/* 先将类型保存 */
			sdf = new SimpleDateFormat(format);
			/* 将日期数据按类型转成date类型 */
			date2 = sdf.parse(date);
			/* 将日期转换为历法Calendar格式 */
			c.setTime(date2);
		} catch (Exception e) {
			newDate = "parameter of format:'" + format + "' or date:'" + date
					+ "' is incorrect";
		}
		/* 判断计算类型,不是年月周日的就报错 */
		if (!type.equals("Y") && !type.equals("M") && !type.equals("W")
				&& !type.equals("D")) {
			newDate = "parameter of type:'" + type + "' is incorrect";
			return newDate;
		} else if (type.equals("Y")) {
			c.add(Calendar.YEAR, integer);
		} else if (type.equals("M")) {
			c.add(Calendar.MONTH, integer);
		} else if (type.equals("W")) {
			integer = integer * 7;
			c.add(Calendar.DAY_OF_WEEK, integer);
		} else if (type.equals("D")) {
			c.add(Calendar.DAY_OF_WEEK, integer);
		}
		/* 将历法格式转为字符串类型 */
		try {
			newDate = sdf.format(c.getTime());
		} catch (Exception e) {
			newDate = "parameter of format:'" + format + "' or date:'" + date
					+ "' is incorrect";
		}
		return newDate;
	}

	/** 获取某日所在周的特定日期 */
	public static String dateOfWeekDay(String date, String format,
			String weekDay) {
		String dateOfWeekDay = null;
		int weekDayInt = 0;
		Date date2 = null;
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		try {
			// 将日期字符串转日期格式
			date2 = sdf.parse(date);
		} catch (ParseException e) {
			// 日期字符串与格式不符则报错
			dateOfWeekDay = "parameter of format:'" + format + "' or date:'"
					+ date + "' is incorrect";
			return dateOfWeekDay;
		}
		// 加载日历
		Calendar cal = Calendar.getInstance();
		// 将所需日期加载入日历
		cal.setTime(date2);
		// 设置第一天为周一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 所需周几进行判断
		if (weekDay.equals("Mon")) {
			weekDayInt = 2;
		} else if (weekDay.equals("Tue")) {
			weekDayInt = 3;
		} else if (weekDay.equals("Wed")) {
			weekDayInt = 4;
		} else if (weekDay.equals("Thu")) {
			weekDayInt = 5;
		} else if (weekDay.equals("Fri")) {
			weekDayInt = 6;
		} else if (weekDay.equals("Sat")) {
			weekDayInt = 7;
		} else if (weekDay.equals("Sun")) {
			weekDayInt = 1;
		} else {
			// 不符合以上条件对日期进行报错
			dateOfWeekDay = "parameter of weekDay:'" + weekDay
					+ "' is incorrect";
			return dateOfWeekDay;
		}
		// 进行周几的转换
		cal.set(Calendar.DAY_OF_WEEK, weekDayInt);
		// 将转换后的周几变为日期
		dateOfWeekDay = sdf.format(cal.getTime());
		return dateOfWeekDay;
	}
}
