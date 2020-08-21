package com.efe.ms.common.util;

import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * 
 * <p>
 * 时区时间工具类
 * </p>
 * 
 * @author Liu TianLong 2018年7月17日 上午11:36:36
 */
public class DateZoneUtil {

	public static final String CHINA_ZONE_ID = "Asia/Shanghai"; // 北京时间（东八区时间）时区ID
	public static final String PACIFIC_OCEAN_ZONE_ID = "America/Los_Angeles"; // 太平洋时间时区ID

	/**
	 * 
	 * <p>
	 * 获取中国时间(东八区)的时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 上午11:42:29
	 * @return Date
	 */
	public static Date nowOfChina() {
		return Date.from(getInstant(CHINA_ZONE_ID));
	}

	/**
	 * 
	 * <p>
	 * 获取太平洋时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 下午6:10:05
	 * @return Date
	 */
	public static Date nowOfPacificOcean() {
		return Date.from(getInstant(PACIFIC_OCEAN_ZONE_ID));
	}

	/**
	 * 
	 * <p>
	 * 获取指定时区的时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 下午1:37:32
	 * @return Date
	 */
	public static Date now(String zoneId) {
		return Date.from(getInstant(zoneId));
	}

	/**
	 * 
	 * <p>
	 * 获取指定时区的时间:
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 下午1:37:52
	 * @return Date
	 */
	public static Date now(ZoneId zoneId) {
		return Date.from(getInstant(zoneId));
	}

	/**
	 * 
	 * <p>
	 * 根据时区ID获取时区时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 上午11:42:43
	 * @return ZonedDateTime
	 */
	public static Instant getInstant(String zoneId) {
		return getInstant(ZoneId.of(zoneId));
	}

	/**
	 * 
	 * <p>
	 * 根据时区ID获取时区时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 下午1:35:07
	 * @return Instant
	 */
	public static Instant getInstant(ZoneId zoneId) {
		return Instant.now().atZone(zoneId).toLocalDateTime()
				.atZone(ZoneId.systemDefault()).toInstant();
	}

	/**
	 * 
	 * <p>
	 * 获取系统默认的时区时间
	 * </p>
	 * 
	 * @param
	 * @author Liu TianLong
	 * @date 2018年7月17日 下午6:12:07
	 * @return Instant
	 */
	public static Instant getSystemDefaultInstant() {
		return getInstant(ZoneId.systemDefault());
	}

	public static XMLGregorianCalendar convertToXMLGregorianCalendar(Date date) {

		GregorianCalendar cal = new GregorianCalendar();
		cal.setTime(date);
		XMLGregorianCalendar gc = null;
		try {
			gc = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return gc;
	}

}
