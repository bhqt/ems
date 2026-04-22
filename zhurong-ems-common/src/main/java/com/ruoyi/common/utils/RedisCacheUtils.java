package com.ruoyi.common.utils;

import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson2.JSONArray;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.spring.SpringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 字典工具类
 * @author ruoyi
 */
public class RedisCacheUtils {
	private static final Logger logger = LoggerFactory.getLogger(RedisCacheUtils.class);

	// 添加的缓存key，需要再CacheController中一同添加，才能再系统缓存列表中进行查询和操作
	public static final String Config_jieKouPeiZhi = "Config_jieKouPeiZhi:";
	public static final String Config_zhiJianSheZhi = "Config_zhiJianSheZhi";
	public static final String Config_guanJianCi_Prefix = "Config_guanJianCi:";
	public static final String Data_receivedVoiceData = "Data_receivedVoiceData";
	public static final String Data_receivedVoiceQcCallBackData = "Data_receivedVoiceQcCallBackData";
	public static final String Data_PushCallBillStateCount = "Data_PushCallBillStateCount:";

	public static final String Config_jieKouPeiZhi_idUrlRela = "Config_jieKouPeiZhi_idUrlRela:";
	public static final String Data_jieKou_requestCount = "Data_jieKou_requestCount:";
	public static final String Data_jieKou_requestTime = "Data_jieKou_requestTime:";
	public static final String Data_jieKou_responseCount = "Data_jieKou_responseCount:";
	public static final String Data_jieKou_responseUseTime = "Data_jieKou_responseUseTime:";
	public static final String Data_jieKou_queueSize = "Data_jieKou_queueSize:";

	/**
	 * 分隔符
	 */
	public static final String SEPARATOR = ",";

	private static final ObjectMapper objectMapper = new ObjectMapper();

	/**
	 * 设置缓存
	 * @param key 参数键
	 * @param
	 */
	public static void setCacheString(String key, String value) {
		SpringUtils.getBean(RedisCache.class).setCacheObject(key, value);
	}

	public static void setCacheWithExpire(String key, String value, Integer timeout, TimeUnit timeUnit) {
		SpringUtils.getBean(RedisCache.class).setCacheObject(key, value, timeout, timeUnit);
	}

	/**
	 * 设置缓存
	 * @param key 参数键
	 * @param
	 */
	public static void setCacheList(String key, List datas) {
		SpringUtils.getBean(RedisCache.class).setCacheObject(key, datas);
	}

	/**
	 * 获取缓存
	 * @param key 参数键
	 * @return
	 */
	public static String getCacheString(String key) {
		return SpringUtils.getBean(RedisCache.class).getCacheString(key);
	}

	public static <T> T getCache(String key, Class<T> clazz) {
		// 从 Redis 缓存中获取对象
		Object cachedObject = SpringUtils.getBean(RedisCache.class).getCacheObject(key);
		if (cachedObject != null) {
			try {
				// 将缓存对象转换为 JSON 字符串
				// String jsonString = objectMapper.writeValueAsString(cachedObject);
				T bean = JSONUtil.toBean(JSONUtil.toJsonStr(cachedObject), clazz);
				// 将 JSON 字符串转换为指定类型的对象
				return bean;
			} catch (Exception e) {
				String msg = "获取Redis中key为[" + key + "]的缓存数据时出现异常！" + e.getMessage();
				logger.error(msg, e);
				throw new ServiceException(msg);
			}
		}
		return null;
	}

	public static <T> List<T> getCacheList(String key, Class<T> clazz) {
		// 获取缓存对象（假设 RedisCache.getCacheObject 返回 JSONArray）
		JSONArray arrayCache = SpringUtils.getBean(RedisCache.class).getCacheObject(key);

		if (arrayCache != null && !arrayCache.isEmpty()) {
			try {
				// 转换 JSONArray 到 List<T>
				return objectMapper.readValue(arrayCache.toJSONString(),
						objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
			} catch (Exception e) {
				String msg = "获取Redis中key为[" + key + "]的缓存数据时出现异常！" + e.getMessage();
				logger.error(msg, e);
				throw new ServiceException(msg);
			}
		}
		return null;
	}

	/**
	 * 删除指定key缓存
	 * @param key 字典键
	 */
	public static void deleteCacheByKey(String key) {
		SpringUtils.getBean(RedisCache.class).deleteCacheByKeys(key);
	}

	/**
	 * 删除符合key前缀的所有缓存
	 */
	public static void deleteCacheByKeyPrefix(String keyPrefix) {
		Collection<String> keys = SpringUtils.getBean(RedisCache.class).keys(keyPrefix + "*");
		SpringUtils.getBean(RedisCache.class).deleteCacheByKeys(keys);
	}

	/**
	 * 设置 hash 类型的缓存
	 * @param key     参数键
	 * @param hashKey hash 中的键
	 * @param value   hash 中的值
	 */
	public static void setMapValue(String key, String hashKey, Object value) {
		SpringUtils.getBean(RedisCache.class).setMapValue(key, hashKey, value);
	}

	/**
	 * 获取 hash 类型的缓存
	 * @param key     参数键
	 * @param hashKey hash 中的键
	 * @param clazz   返回值的类型
	 * @return hash 中的值
	 */
	public static <T> T getMapValue(String key, String hashKey, Class<T> clazz) {
		Object cachedValue = SpringUtils.getBean(RedisCache.class).getMapValue(key, hashKey);
		if (cachedValue != null) {
			try {
				return objectMapper.readValue(objectMapper.writeValueAsString(cachedValue), clazz);
			} catch (Exception e) {
				String msg = "获取Redis中key为[" + key + "]，hashKey为[" + hashKey + "]的缓存数据时出现异常！" + e.getMessage();
				logger.error(msg, e);
				throw new ServiceException(msg);
			}
		}
		return null;
	}

	public static String getMapValue(String key, String hashKey) {
		return getMapValue(key, hashKey, String.class);
	}

	/**
	 * 删除Hash中的某条数据
	 * @param key  Redis键
	 * @param hKey Hash键
	 * @return 是否成功
	 */
	public static boolean deleteMapValue(final String key, final String hKey) {
		return SpringUtils.getBean(RedisCache.class).deleteMapValue(key, hKey);
	}

	/**
	 * 添加 Set 类型缓存
	 * @param key   参数键
	 * @param value 要添加的值
	 */
	public static void addSetValue(String key, String value) {
		SpringUtils.getBean(RedisCache.class).addSetValue(key, value);
	}

	/**
	 * 获取 Set 类型缓存
	 * @param key   参数键
	 * @param clazz 返回值的类型
	 * @return Set 中的值
	 */
	public static <T> Set<T> getSetAllData(String key, Class<T> clazz) {
		Set<Object> cachedSet = SpringUtils.getBean(RedisCache.class).getSetAllData(key);
		if (cachedSet != null) {
			try {
				Set<T> resultSet = new HashSet<>();
				for (Object item : cachedSet) {
					resultSet.add(objectMapper.readValue(objectMapper.writeValueAsString(item), clazz));
				}
				return resultSet;
			} catch (Exception e) {
				String msg = "获取Redis中key为[" + key + "]的缓存数据时出现异常！" + e.getMessage();
				logger.error(msg, e);
				throw new ServiceException(msg);
			}
		}
		return null;
	}

	/**
	 * 随机获取 Set 中的一个数据并转换为指定类型
	 * @param key   Set 的键
	 * @param clazz 返回值的类型
	 * @param <T>   返回值的泛型类型
	 * @return Set 中的一个随机值
	 */
	public static <T> T getSetRandomData(final String key, Class<T> clazz) {
		Object cachedValue = SpringUtils.getBean(RedisCache.class).getSetRandomData(key);
		if (cachedValue != null) {
			try {
				return objectMapper.readValue(objectMapper.writeValueAsString(cachedValue), clazz);
			} catch (Exception e) {
				String msg = "获取Redis中key为[" + key + "]的缓存数据时出现异常！" + e.getMessage();
				logger.error(msg, e);
				throw new ServiceException(msg);
			}
		}
		return null;
	}

	public static String getSetRandomStr(final String key) {
		return getSetRandomData(key, String.class);
	}

	/**
	 * 从 Redis SET 中删除指定的元素
	 * @param setKey SET 的键
	 * @param value  要删除的元素
	 */
	public static boolean removeSetElement(String key, String value) {
		return SpringUtils.getBean(RedisCache.class).removeSetElement(key, value);
	}

	/**
	 * 修改 Redis SET 中的一个元素
	 * @param setKey   SET 的键
	 * @param oldValue 要替换的旧元素
	 * @param newValue 新元素
	 */
	public static void updateSetElement(String key, String oldValue, String newValue) {
		SpringUtils.getBean(RedisCache.class).updateSetElement(key, oldValue, newValue);
	}

	/**
	 * 向列表右侧添加数据
	 * @param key
	 * @param value
	 * @return
	 */
	public static long setListRightPushString(final String key, final String value) {
		return SpringUtils.getBean(RedisCache.class).setListRightPushString(key, value);
	}

	/**
	 * 从列表左侧取出数据
	 * @param key
	 * @return
	 */
	public static String getListLeftPopString(final String key) {
		return SpringUtils.getBean(RedisCache.class).getListLeftPopString(key);
	}

	/**
	 * 从列表左侧取出一个数据，并将其写入列表的右侧
	 * @param sourceKey 列表的键
	 * @return 被移动的元素
	 */
	public static String getListRightPopAndLeftPush(final String sourceKey) {
		return SpringUtils.getBean(RedisCache.class).getListRightPopAndLeftPush(sourceKey);
	}

	/**
	 * 从列表中删除所有传入的值
	 * @param key
	 * @param value
	 * @return
	 */
	public static long deleteListValue(final String key, final String value) {
		return SpringUtils.getBean(RedisCache.class).deleteListValue(key, value, 0);
	}

	/**
	 * 添加元素到Sorted Set
	 */
	public static void addToSortedSet(final String key, final String value, double score) {
		SpringUtils.getBean(RedisCache.class).addToSortedSet(key, value, score);
	}

	/**
	 * 获取Sorted Set中分数最小的值
	 */
	public static void getMinScoreEleFormSortedSet(final String key) {
		SpringUtils.getBean(RedisCache.class).getMinScoreEleFormSortedSet(key);
	}

	/**
	 * 获取Sorted Set中的一个值
	 */
	public static void removeFromSortedSet(final String key, final String value) {
		SpringUtils.getBean(RedisCache.class).removeFromSortedSet(key, value);
	}
}
