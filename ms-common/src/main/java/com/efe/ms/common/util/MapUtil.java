package com.efe.ms.common.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Map工具类
 * 
 * @author Tianlong Liu
 * 2020年8月19日 上午10:35:28
 */
public class MapUtil {
	/**
	 * javaBean 转 Map
	 * 
	 * @param object
	 *            需要转换的javabean
	 * @return 转换结果map
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> beanToMap(Object object) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();

		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(object));
		}
		return map;
	}
	
	@SuppressWarnings("rawtypes")
	public static Map<String, String> beanToStringMap(Object object) throws Exception {
		Map<String, String> map = new HashMap<>();
		
		Class cls = object.getClass();
		Field[] fields = cls.getDeclaredFields();
		for (Field field : fields) {
			field.setAccessible(true);
			map.put(field.getName(), field.get(object) == null ? null : field.get(object).toString());
		}
		return map;
	}

	public static Map<String, Object> beansToMap(Object... objects)
			throws Exception {
		final Map<String, Object> map = new HashMap<String, Object>();
		if (objects == null) {
			return map;
		}
		for (Object obj : objects) {
			map.putAll(beanToMap(obj));
		}
		return filterNullValueKeys(map);
	}

	private static Map<String, Object> filterNullValueKeys(Map<String, Object> map) {
		if (map != null) {
			Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
			Entry<String, Object> obj = null;
			while(iterator.hasNext()){
				obj = iterator.next();
				if(obj.getKey() == null || obj.getValue() == null){
					iterator.remove();
				}
			}
		}
		return map;
	}

	/**
	 *
	 * @param map
	 *            需要转换的map
	 * @param cls
	 *            目标javaBean的类对象
	 * @return 目标类object
	 * @throws Exception
	 */
	public static <T> T mapToBean(Map<String, Object> map, Class<T> cls)
			throws Exception {
		T ins = cls.newInstance();
		for (String key : map.keySet()) {
			Field temFiels = cls.getDeclaredField(key);
			temFiels.setAccessible(true);
			temFiels.set(ins, map.get(key));
		}
		return ins;
	}
}