package com.efe.ms.crawlerservice.util;

import java.util.Properties;

/**
 * 
 * <p>
 * 操作系统工具类:
 * </p>
 * 
 * @author Liu TianLong 2019年7月26日 下午3:56:40
 */
public final class OSUtils {
	
	/**
	 * 
	 * <p>判断当前操作系统是否之linux: </p>
	 * @param
	 * @author Liu TianLong
	 * @date 2019年7月26日 下午3:57:03
	 * @return boolean
	 */
	public static boolean isLinuxOS() {
		Properties prop = System.getProperties();

		String os = prop.getProperty("os.name");
		if (os != null && os.toLowerCase().indexOf("linux") > -1) {
			return true;
		} else {
			return false;
		}
	}
}
