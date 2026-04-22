package com.ruoyi.common.core.redis;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.NumberUtil;
import cn.hutool.core.util.StrUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * spring redis 工具类
 * @author ruoyi
 **/
@SuppressWarnings(value = {"unchecked", "rawtypes"})
@Component
public class RedisCache {
	private static final Logger logger = LoggerFactory.getLogger(RedisCache.class);

	@Autowired
	public RedisTemplate redisTemplate;
	@Autowired
	private ObjectMapper objectMapper;
	private final Random random = new Random();

	/**
	 * 判断 key是否存在
	 * @param key 键
	 * @return true 存在 false不存在
	 */
	public Boolean hasKey(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 判断key是否存在
	 * @param key
	 * @return
	 */
	public boolean keyExists(String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 获得缓存的基本对象列表
	 * @param pattern 字符串前缀
	 * @return 对象列表
	 */
	public Collection<String> keys(final String pattern) {
		return redisTemplate.keys(pattern);
	}

	/**
	 * 判断key前一部分是否存在
	 * @param key
	 * @return
	 */
	public boolean keyExistsWithPrefix(String prefix) {
		boolean[] exists = new boolean[]{false};
		try (RedisConnection connection = redisTemplate.getConnectionFactory().getConnection()) {
			Cursor<byte[]> cursor = connection.scan(ScanOptions.scanOptions().match(prefix + "*").build());
			if (cursor.hasNext()) {
				exists[0] = true;
			}
		}
		return exists[0];
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key   缓存的键值
	 * @param value 缓存的值
	 */
	public <T> void setCacheObject(final String key, final T value) {
		redisTemplate.opsForValue().set(key, value);
	}

	/**
	 * 缓存基本的对象，Integer、String、实体类等
	 * @param key      缓存的键值
	 * @param value    缓存的值
	 * @param timeout  时间
	 * @param timeUnit 时间颗粒度
	 */
	public <T> void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
		redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
	}

	/**
	 * 设置有效时间
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout) {
		return expire(key, timeout, TimeUnit.SECONDS);
	}

	/**
	 * 设置有效时间
	 * @param key     Redis键
	 * @param timeout 超时时间
	 * @param unit    时间单位
	 * @return true=设置成功；false=设置失败
	 */
	public boolean expire(final String key, final long timeout, final TimeUnit unit) {
		return redisTemplate.expire(key, timeout, unit);
	}

	/**
	 * 获取有效时间
	 * @param key Redis键
	 * @return 有效时间
	 */
	public long getExpire(final String key) {
		return redisTemplate.getExpire(key);
	}

	/**
	 * 获得缓存的基本对象。
	 * @param key 缓存键值
	 * @return 缓存键值对应的数据
	 */
	public String getCacheString(final String key) {
		ValueOperations<String, String> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	public Integer getCacheInteger(final String key) {
		ValueOperations<String, Integer> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	public <T> T getCacheObject(final String key) {
		ValueOperations<String, T> operation = redisTemplate.opsForValue();
		return operation.get(key);
	}

	/**
	 * 删除单个对象
	 * @param key
	 */
	public boolean deleteCacheByKeys(final String key) {
		return redisTemplate.delete(key);
	}

	/**
	 * 删除集合对象
	 * @param collection 多个对象
	 * @return
	 */
	public boolean deleteCacheByKeys(final Collection collection) {
		return redisTemplate.delete(collection) > 0;
	}

	/**
	 * 用于将多个元素推送到列表的右侧，但它期望的参数类型是一个集合（如 Collection 或 List）。如果你传入单个对象，它将导致类型不匹配，从而抛出错误。
	 * Redis中当前key对应的数据结构需要是一个列表
	 * @param key      缓存的键值
	 * @param dataList 待缓存的List数据
	 * @return 缓存的对象
	 */
	public <T> long setListData(final String key, final List<T> dataList) {
		//rightPushAll 方法用于将多个元素推送到列表的右侧，但它期望的参数类型是一个集合（如 Collection 或 List）。如果你传入单个对象，它将导致类型不匹配，从而抛出错误。
		Long count = redisTemplate.opsForList().rightPushAll(key, dataList);
		return count == null ? 0 : count;
	}

	/**
	 * 用于将一个元素推送到列表的右侧
	 * Redis中当前key对应的数据结构需要是一个列表
	 * @param key 缓存的键值
	 * @param obj 待缓存的数据
	 * @return 缓存的对象
	 */
	public <T> long setListObjData(final String key, final T obj) {
		//rightPush 方法来推送单个对象到列表中
		Long count = redisTemplate.opsForList().rightPush(key, obj);
		return count == null ? 0 : count;
	}

	public long setListRightPushString(final String key, final String value) {
		// count 为当前list中的元素个数
		Long count = redisTemplate.opsForList().rightPush(key, value);
		return count == null ? 0 : count;
	}

	public String getListLeftPopString(String key) {
		try {
			return (String) redisTemplate.opsForList().leftPop(key);
		} catch (Exception e) {
			logger.error("【异常】-getListLeftPopString进行处理时出现异常！", e);
			return null;
		}
	}

	/**
	 * 从列表左侧取出一个数据，并将其写入列表的右侧
	 * @param sourceKey 源列表的键
	 * @param targetKey 目标列表的键
	 * @return 被移动的元素
	 */
	public String getListRightPopAndLeftPush(final String sourceKey) {
		try {
			// 使用 BRPOPLPUSH 操作从源列表左侧取出一个元素，并将其写入目标列表的右侧
			return (String) redisTemplate.opsForList().rightPopAndLeftPush(sourceKey, sourceKey);
		} catch (Exception e) {
			e.printStackTrace();
			return null; // 如果发生异常，返回 null 表示操作失败
		}
	}

	/**
	 * 删除列表中指定值的元素
	 * @param key   Redis 列表的键
	 * @param value 要删除的值
	 * @param count 删除匹配的数量
	 *              - 正数：删除从头到尾的 count 个匹配的值
	 *              - 负数：删除从尾到头的 count 个匹配的值
	 *              - 0：删除所有匹配的值
	 * @return 删除的元素数量
	 */
	public long deleteListValue(final String key, final String value, final long count) {
		try {
			// 使用 LREM 命令删除列表中匹配的元素
			return redisTemplate.opsForList().remove(key, count, value);
		} catch (Exception e) {
			e.printStackTrace();
			return 0; // 如果发生异常，返回 0 表示删除失败
		}
	}

	/**
	 * 获得缓存的list对象
	 * @param key 缓存的键值
	 * @return 缓存键值对应的数据
	 */
	public <T> List<T> getListAllData(final String key) {
		return redisTemplate.opsForList().range(key, 0, -1);
	}

	/**
	 * 从 Redis 列表中根据索引获取元素
	 * @param key   Redis 列表的键
	 * @param index 列表中的索引
	 * @param <T>   返回元素的类型
	 * @return 元素，如果索引超出范围则返回 null
	 */
	public <T> T getListByIndex(String key, long index, Class<T> clazz) {
		try {
			Object element = redisTemplate.opsForList().index(key, index);
			if (element != null && clazz.isInstance(element)) {
				return clazz.cast(element);
			}
		} catch (Exception e) {
			// 处理异常
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 从 Redis 列表中随机索引获取元素
	 * @param key   Redis 列表的键
	 * @param clazz 返回元素的类型
	 * @param <T>   返回元素的类型
	 * @return 元素，如果列表为空或索引超出范围或类型不匹配则返回 null
	 */
	public <T> T getListByRandomIndex(String key, Class<T> clazz) {
		try {
			Long size = redisTemplate.opsForList().size(key);
			logger.info("[获取]-redis中key=" + key + "的List的size=" + size);
			if (size == null || size == 0) {
				return null; // 列表为空
			}
			int randomIndex = random.nextInt(size.intValue());
			logger.info("[获取]-redis中key=" + key + "的List的随机索引=" + randomIndex);
			Object element = redisTemplate.opsForList().index(key, randomIndex);
			if (element != null && clazz.isInstance(element)) {
				return clazz.cast(element);
			}
		} catch (Exception e) {
			// 处理异常
			e.printStackTrace();
		}
		return null;
	}

	public String getListStringByRandomIndex(String key) {
		try {
			Long size = redisTemplate.opsForList().size(key);
			logger.info("[获取]-redis中key=" + key + "的List的size=" + size);
			if (size == null || size == 0) {
				return null; // 列表为空
			}
			int randomIndex = random.nextInt(size.intValue());
			logger.info("[获取]-redis中key=" + key + "的List的随机索引=" + randomIndex);
			return (String) redisTemplate.opsForList().index(key, randomIndex);
		} catch (Exception e) {
			// 处理异常
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 缓存Set
	 * @param key     缓存键值
	 * @param dataSet 缓存的数据
	 * @return 缓存数据的对象
	 */
	public <T> BoundSetOperations<String, T> setSet(final String key, final Set<T> dataSet) {
		BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
		Iterator<T> it = dataSet.iterator();
		while (it.hasNext()) {
			setOperation.add(it.next());
		}
		return setOperation;
	}

	/**
	 * 缓存List数据到Set中
	 * @param key
	 * @param dataList
	 * @param <T>
	 * @return
	 */
	public <T> BoundSetOperations<String, T> setSetWithListData(final String key, final List<T> dataList) {
		BoundSetOperations<String, T> setOperation = redisTemplate.boundSetOps(key);
		for (T data : dataList) {
			setOperation.add(data); // 将每个元素添加到 Redis 集合中
		}
		return setOperation;
	}

	/**
	 * 获得缓存的set
	 * @param key
	 * @return
	 */
	public Set<Object> getSetAllData(final String key) {
		return redisTemplate.opsForSet().members(key);
	}

	/**
	 * 获取Redis的Set中的所有元素并拼接成一个字符串
	 * @param key Redis的键
	 * @return 拼接后的字符串
	 */
	public String getSetAllDataAsString(String key, String delimiter) {
		Set<String> members = redisTemplate.opsForSet().members(key);
		return String.join(delimiter, members); // 使用逗号和空格作为分隔符
	}

	// public <T> T getSetRandomData(final String key, Class<T> clazz) {
	// 	Set<T> set = getSetAllData(key);
	// 	if (set != null && !set.isEmpty()) {
	// 		// 将集合转换为数组
	// 		T[] array = (T[]) set.toArray();
	// 		// 随机选择一个元素
	// 		Random random = new Random();
	// 		logger.info("[获取]-redis中key=" + key + "的Set的length=" + array.length);
	// 		int randomIndex = random.nextInt(array.length);
	// 		logger.info("[获取]-redis中key=" + key + "的Set的随机索引=" + randomIndex);
	// 		return array[randomIndex];
	// 	}
	// 	return null; // 返回 null 如果集合为空
	// }

	/**
	 * 从 Redis 中随机获取 Set 中的一个数据
	 * @param key Set 的键
	 * @return 随机获取的值
	 */
	public Object getSetRandomData(String key) {
		return redisTemplate.opsForSet().randomMember(key);
	}

	/**
	 * 缓存Map
	 * @param key
	 * @param dataMap
	 */
	public <T> void setHashValue(final String key, final Map<String, T> dataMap) {
		if (dataMap != null) {
			redisTemplate.opsForHash().putAll(key, dataMap);
		}
	}

	public void addSetValue(String key, String value) {
		SetOperations<String, String> setOps = redisTemplate.opsForSet();
		setOps.add(key, value);
	}

	/**
	 * 从 Redis SET 中删除指定的元素
	 * @param setKey SET 的键
	 * @param value  要删除的元素
	 */
	public boolean removeSetElement(String setKey, String value) {
		return redisTemplate.opsForSet().remove(setKey, value) > 0;
	}

	/**
	 * 修改 Redis SET 中的一个元素
	 * @param setKey   SET 的键
	 * @param oldValue 要替换的旧元素
	 * @param newValue 新元素
	 */
	public void updateSetElement(String setKey, String oldValue, String newValue) {
		// 删除旧元素
		redisTemplate.opsForSet().remove(setKey, oldValue);

		// 添加新元素
		redisTemplate.opsForSet().add(setKey, newValue);
	}

	/**
	 * 获得缓存的Map
	 * @param key
	 * @return
	 */
	public <T> Map<String, T> getMap(final String key) {
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 往Hash中存入数据
	 * @param key   Redis键
	 * @param hKey  Hash键
	 * @param value 值
	 */
	public <T> void setMapValue(final String key, final String hKey, final T value) {
		redisTemplate.opsForHash().put(key, hKey, value);
	}

	/**
	 * 获取Hash中的数据
	 * @param key  Redis键
	 * @param hKey Hash键
	 * @return Hash中的对象
	 */
	public <T> T getMapValue(final String key, final String hKey) {
		HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
		return opsForHash.get(key, hKey);
	}

	public String getMapValueStr(final String key, final String hKey) {
		HashOperations<String, String, T> opsForHash = redisTemplate.opsForHash();
		return StrUtil.toStringOrNull(opsForHash.get(key, hKey));
	}

	/**
	 * 获取Hash中key下的全部数据
	 * @param key
	 * @return
	 */
	public Map<Object, Object> getMapAllEntries(String key) {
		// 获取指定键下的所有哈希字段和值
		return redisTemplate.opsForHash().entries(key);
	}

	/**
	 * 获取多个Hash中的数据
	 * @param key   Redis键
	 * @param hKeys Hash键集合
	 * @return Hash对象集合
	 */
	public <T> List<T> getMultiCacheMapValue(final String key, final Collection<Object> hKeys) {
		return redisTemplate.opsForHash().multiGet(key, hKeys);
	}

	/**
	 * 删除Hash中的某条数据
	 * @param key  Redis键
	 * @param hKey Hash键
	 * @return 是否成功
	 */
	public boolean deleteMapValue(final String key, final String hKey) {
		return redisTemplate.opsForHash().delete(key, hKey) > 0;
	}

	// 添加元素到Sorted Set
	public boolean addToSortedSet(String key, String value, double score) {
		try {
			Boolean add = redisTemplate.opsForZSet().add(key, value, score);
			logger.debug("[成功]-向Redis有序集合中添加数据-key={}-执行结果={}", key, value, score);
			return add;
		} catch (Exception e) {
			logger.error("【异常 】-向Redis有序集合中添加数据时出现异常！", e);
			return false;
		}
	}

	// 添加元素到Sorted Set
	public boolean addToSortedSetLong(String key, long value, double score) {
		try {
			Boolean add = redisTemplate.opsForZSet().add(key, value, score);
			logger.debug("[成功]-向Redis有序集合中添加数据-key={}-执行结果={}", key, value, score);
			return add;
		} catch (Exception e) {
			logger.error("【异常 】-向Redis有序集合中添加数据时出现异常！", e);
			return false;
		}
	}

	// 获取分数最小的元素
	public String getMinScoreEleFormSortedSet(String key) {
		try {
			// 使用 ZRANGE 获取分数最小的元素
			Set<String> result = redisTemplate.opsForZSet().range(key, 0, 0); // 从索引0到0获取元素
			if (result != null && !result.isEmpty()) {
				String minElement = result.iterator().next();
				logger.info("Min score element: key={}, value={}", key, minElement);
				return minElement;
			} else {
				logger.info("No elements found in sorted set for key={}", key);
				return null; // 处理没有元素的情况
			}
		} catch (Exception e) {
			logger.error("Error getting min score element", e);
			return null; // 处理异常情况
		}
	}

	public Set<ZSetOperations.TypedTuple<String>> getSortedSetMembersWithScores(String key) {
		// 获取有序集合操作对象
		ZSetOperations<String, String> zSetOps = redisTemplate.opsForZSet();
		// 获取所有成员及其分数
		return zSetOps.rangeWithScores(key, 0, -1);
	}

	/**
	 * 通过计算每个元素的权重并根据权重随机选择元素，实现了按分值高低调整获取概率
	 * 根据分值判断获取的比例，分值低的获取到的概率高，分值高的获取到的概率低
	 * @param key
	 * @return
	 */
	public String getSortedSetValueWithProbability(String key) {
		// 校验输入参数
		if (key == null || key.isEmpty()) {
			logger.error("查询redis缓存时-传入的key={}-为空", key);
			return null;
		}

		// 获取所有元素及其分数
		Set<ZSetOperations.TypedTuple<String>> elements = getSortedSetMembersWithScores(key);

		// 如果没有任何元素，返回null
		if (elements == null || elements.isEmpty()) {
			logger.error("查询redis缓存时-通过key={}-没有获取的对应的缓存数据", key);
			return null;
		}

		double totalWeight = 0.0;
		Map<String, Double> weightMap = new TreeMap<>(); // 使用TreeMap保证有序性

		// 计算每个元素的权重并存储
		for (ZSetOperations.TypedTuple<String> element : elements) {
			double score = element.getScore();
			double weight = 1 / score; // 假设分数越高，权重越低
			weightMap.put(element.getValue(), weight);
			totalWeight += weight;
		}

		// 生成随机数并缓存结果
		double randomValue = Math.random() * totalWeight;
		double cumulativeWeight = 0.0;

		// 根据权重选择元素
		for (Map.Entry<String, Double> entry : weightMap.entrySet()) {
			cumulativeWeight += entry.getValue();
			if (cumulativeWeight >= randomValue) {
				String selectedValue = entry.getKey();
				// logger.error("[提示]-按分值概率获取set中的值-totalWeight={}-cumulativeWeight={}-randomValue={}-value={}",
				// 		String.format("%.8f", totalWeight),
				// 		String.format("%.8f", cumulativeWeight),
				// 		String.format("%.8f", randomValue),
				// 		selectedValue); // 返回选中的值
				return selectedValue; // 返回选中的值
			}
		}
		return null; // 如果没有任何元素，返回null
	}

	/**
	 * 从Redis的Sorted Set中获取一个不等于给定值的元素。
	 * @param key           Redis中的键名
	 * @param excludedValue 要排除的值
	 * @return 返回一个不等于excludedValue的随机元素，如果不存在则返回null
	 */
	public String getRandomValueFromSortedSetExcluding(String key, String excludedValue) {
		Set<String> members = redisTemplate.opsForZSet().range(key, 0, -1);
		if (members == null || members.isEmpty()) {
			return null; // 集合为空时返回null
		}

		for (String member : members) {
			if (!member.equals(excludedValue)) {
				return member; // 返回第一个不等于excludedValue的元素
			}
		}

		return null; // 所有元素都等于excludedValue时返回null
	}

	// 获取Sorted Set中的所有元素
	public Set<Object> getSortedSetAllData(String key) {
		try {
			return redisTemplate.opsForZSet().range(key, 0, -1);
		} catch (Exception e) {
			logger.error("Error getting sorted set", e);
			return null;
		}
	}

	// 获取Sorted Set中的某个元素的排名
	public Long getRank(String key, Object value) {
		try {
			return redisTemplate.opsForZSet().rank(key, value);
		} catch (Exception e) {
			logger.error("Error getting rank", e);
			return null;
		}
	}

	// 获取Sorted Set中的某个元素的分数
	public Double getScore(String key, Object value) {
		try {
			return redisTemplate.opsForZSet().score(key, value);
		} catch (Exception e) {
			logger.error("Error getting score", e);
			return null;
		}
	}

	// 删除Sorted Set中的某个元素
	public void removeFromSortedSet(String key, String value) {
		try {
			redisTemplate.opsForZSet().remove(key, value);
			logger.info("Removed from sorted set: key={}, value={}", key, value);
		} catch (Exception e) {
			logger.error("Error removing from sorted set", e);
		}
	}

	// 获取Sorted Set的成员数
	public Long getSortedSetSize(String key) {
		try {
			return redisTemplate.opsForZSet().size(key);
		} catch (Exception e) {
			logger.error("Error getting sorted set size", e);
			return null;
		}
	}

	// 原子性递增操作
	public Long increment(String key) {
		return redisTemplate.opsForValue().increment(key);
	}

	/**
	 * 数据太多，会有性能问题，暂不使用  - 向有序集合时间窗口中添加数据，同时删除窗口中的过期数据
	 * 有序集合的分值使用当前时间戳，通过时间戳来计算时间窗口的大小
	 * 通过使用Lua脚本在Redis服务器端直接处理数据，减少了网络延迟和多次往返，提高了执行效率。同时，确保了操作的原子性，避免了并发问题。
	 * @param key        需要添加的键。
	 * @param value      需要添加的值。
	 * @param windowSize 窗口大小。
	 */
	public boolean addToSortedSetWindowByLua(String key, long value, int windowSize) {
		try {
			long scoreCurrentTimestamp = System.currentTimeMillis();

			boolean result = addToSortedSet(key, String.valueOf(value), scoreCurrentTimestamp);
			removeSortedSetWindowExpireData(key, windowSize, scoreCurrentTimestamp);
			// // Lua脚本
			// // String luaScript =
			// // 		"redis.call('SET', 'mykey', 'myvalue' ); "  ;
			// String luaScript = "local key = KEYS[1]; " +
			// 		//  都传入字符串，使用序列号器StringRedisSerializer，防止类型不匹配问题
			// 		"local value =   ARGV[1]  ; " + "   redis.log(redis.LOG_NOTICE, 'addToSortedSetWindow - value: ' .. value .. ', Length: ' .. string.len(value)) ; " + "local valueNum = tonumber(value)  ; " + "if valueNum == nil then" + " redis.log(redis.LOG_WARNING, 'addToSortedSetWindow - valueNum is nil, check input value: '..value)\n " + " return '传入的value不是数字类型' \n " + "end\n" + "   redis.log(redis.LOG_NOTICE, 'addToSortedSetWindow - valueNum: ' .. valueNum) ; " + "local windowSize = tonumber(ARGV[2]) * 60 * 1000; " + "local currentTimestamp = tonumber(ARGV[3]); " + "local addedCount =  redis.call('ZADD', key, currentTimestamp, valueNum); " + "redis.call('ZREMRANGEBYSCORE', key, 0, currentTimestamp - windowSize);" + "return addedCount .. '';" +// 确保返回ZADD的结果
			// 		"";
			//
			// // 执行Lua脚本
			// DefaultRedisScript<String> script = new DefaultRedisScript<>();
			// script.setScriptText(luaScript);
			// script.setResultType(String.class);
			//
			// // 传入Integer类型，如果传入字符串，lua中取出来的带有双引号，需要local num = tonumber(member:gsub('\"', ''))进行处理
			// String result =
			// 		(String) redisTemplate.execute(script, new StringRedisSerializer(), new StringRedisSerializer(), Arrays.asList(key), value + "", windowSize + "", scoreCurrentTimestamp + "");
			// if (NumberUtil.isInteger(result) && Integer.parseInt(result) == 1) {
			// 	logger.debug("[成功]-向Redis有序集合时间窗口中添加数据-key={}-执行结果={}", key, result);
			// 	return true;
			// } else {
			// 	logger.error("【失败】-向Redis有序集合时间窗口中添加数据失败！key={}-value={}-windowSize={}-score={}-执行结果={}", key, value, windowSize, scoreCurrentTimestamp, result);
			// 	return false;
			// }
			logger.debug("[成功]-向Redis有序集合时间窗口中添加数据-key={}-执行结果={}", key, result);
			return result;
		} catch (Exception e) {
			logger.error("【异常】-向Redis有序集合时间窗口中添加数据时出现异常！", e);
			return false;
		}
	}

	/**
	 * 删除序集合时间窗口过期的数据
	 * @param key                   需要操作的有序集合的键名。
	 * @param windowSize            时间窗口的大小，单位为分钟。
	 * @param scoreCurrentTimestamp 当前的时间戳，单位为毫秒。
	 */
	private long removeSortedSetWindowExpireData(String key, int windowSize, long scoreCurrentTimestamp) {
		// 移除10分钟前的数据
		Long aLong = redisTemplate.opsForZSet().removeRangeByScore(key, 0, scoreCurrentTimestamp - windowSize * 60 * 1000);
		logger.info("[完成]-移除有序集合时间窗口过期的数据-key={}-删除条数={}", key, aLong);
		return aLong;
	}

	/**
	 * 获取有序集合时间窗口内所有元素的个数
	 * @param key 需要查询的有序集合的键名。
	 * @return 如果成功，返回该有序集合的大小；否则记录错误日志并返回null。
	 */
	public Long getSortedSetWindowSize(String key) {
		try {
			return redisTemplate.opsForZSet().size(key);
		} catch (Exception e) {
			logger.error("Error getting sorted set size", e);
			return null;
		}
	}

	/**
	 * 数据太多，会有性能问题，暂不使用  - 计算有序集合时间窗口内所有元素的平均值
	 * @param key       Redis中的键名
	 * @param startTime 起始时间戳
	 * @param endTime   结束时间戳
	 * @return 总响应时间
	 */
	public String calculateSortedSetWindowAvgByLua(String key, String windowSize, int scale) {
		if (StrUtil.isNotBlank(windowSize) && NumberUtil.isInteger(windowSize)) {
			long scoreCurrentTimestamp = System.currentTimeMillis();
			removeSortedSetWindowExpireData(key, Integer.parseInt(windowSize), scoreCurrentTimestamp);
			// Lua脚本内容
			String luaScript =
					"local sum = 0                                                                                        \n" +
							"local count = 0                                                                                      \n" +
							"local key = KEYS[1]                                                                                     \n" +
							"    redis.log(redis.LOG_NOTICE, 'start run calculateSortedSetWindowAvgByLua--key=' .. key  ) \n" +
							"for _, member in ipairs(redis.call('ZRANGE', key, 0, -1)) do                                     \n" +
							// "    redis.log(redis.LOG_NOTICE, 'Current member: ' .. member .. ', Length: ' .. string.len(member))  \n" +
							"        local cleanMember = string.gsub(member, \"[^%d%.%-]\", \"\")                                                              \n" +
							"    if cleanMember ~= nil then                                                                            \n" +
							"        local num = tonumber(cleanMember)                                                                 \n" +
							"        if num ~= nil then                                                                           \n" +
							"            sum = sum + tonumber(num)                                                             \n" +
							"            count = count + 1                                                                        \n" +
							// "            redis.log(redis.LOG_NOTICE, 'Current sum: ' .. sum)                                      \n" +
							// "            redis.log(redis.LOG_NOTICE, 'Current count: ' .. count)                                  \n" +
							"        else                                                                                          \n" +
							"           redis.log(redis.LOG_WARNING, 'num is nil')                                                                                          \n" +
							"        end                                                                                          \n" +
							"    else                                                                                          \n" +
							"        redis.log(redis.LOG_WARNING, 'cleanMember is nil')                                                                                          \n" +
							"    end                                                                                              \n" +
							"end                                                                                                  \n" +
							"if count == 0 then                                                                                   \n" +
							"    redis.log(redis.LOG_NOTICE, 'key=' .. key .. ' - count=' .. count ) \n" +
							"    return ''                                                                                        \n" +
							"else                                                                                                 \n" +
							"    redis.log(redis.LOG_NOTICE, 'key=' .. key .. ' - count=' .. count ) \n" +
							"    return '' .. (sum / count)                                                                       \n" +
							"end                                                                                                  \n";

			try {
				// 执行Lua脚本
				DefaultRedisScript<String> script = new DefaultRedisScript<>();
				script.setScriptText(luaScript);
				script.setResultType(String.class);
				// 有加上key和返回值的序列化器，否则返回值各种类型不匹配错误
				String result = (String) redisTemplate.execute(script, new StringRedisSerializer(), new StringRedisSerializer(), Arrays.asList(key));
				logger.debug("[获取]-计算有序集合时间窗口内所有元素的平均值-key={}-执行结果={}", key, result);
				if (StrUtil.isNotBlank(result)) {
					result = NumberUtil.roundStr(result, scale);
				}
				return StrUtil.nullToEmpty(result);
			} catch (Exception e) {
				logger.error("【异常】-计算有序集合时间窗口内所有元素的平均值处理时出现异常！", e);
				return "";
			}
		} else {
			logger.error("【失败】-计算有序集合时间窗口内所有元素的平均值失败！-key={}-传入的windowSize={}-不是正整数", key, windowSize);
			return "";
		}
	}

	public void incrementWithExpire(String key, int value, int second) {
		String LUA_SCRIPT =
				"local exists = redis.call('EXISTS', KEYS[1])\n" +
						"if exists == 1 then\n" +
						"    redis.call('INCRBY', KEYS[1], ARGV[1])\n" +
						"else\n" +
						"    redis.call('SET', KEYS[1], ARGV[1], 'PX', ARGV[2])\n" +
						"end";
		DefaultRedisScript<Long> script = new DefaultRedisScript<>();
		script.setScriptText(LUA_SCRIPT);
		script.setResultType(Long.class);

		List<String> keys = Collections.singletonList(key);
		List<Object> args = Arrays.asList(value, second * 1000);

		redisTemplate.execute(script, keys, args.toArray());
	}

	public long sumByKeyPrefix(String keyPrefix) {
		String LUA_SCRIPT =
				"local keyPrefix = ARGV[1]                                                              \n" +
						"               redis.log(redis.LOG_NOTICE, 'sumByKeyPrefix -- keyPrefix: ' .. keyPrefix .. ', Length: ' .. string.len(keyPrefix))  \n" +
						"local keys = redis.call('KEYS', keyPrefix)\n" +
						"               redis.log(redis.LOG_NOTICE, 'start run sumByKeyPrefix--keys=' .. cjson.encode(keys))\n" +
						"local totalSum = 0\n" +
						"for i, key in ipairs(keys) do\n" +
						"    local value = redis.call('GET', key)\n" +
						"    if value then\n" +
						"        totalSum = totalSum + tonumber(value)\n" +
						"    else\n" +
						"        redis.log(redis.LOG_WARNING, 'sumByKeyPrefix -- Key not found or invalid value for key=' .. key)\n" +
						"    end\n" +
						"end\n" +
						"    redis.log(redis.LOG_NOTICE, 'sumByKeyPrefix -- totalSum: ' .. totalSum .. ' ----------------- '  )  \n" +
						"return totalSum";

		DefaultRedisScript<Long> script = new DefaultRedisScript<>();
		script.setScriptText(LUA_SCRIPT);
		script.setResultType(Long.class);

		// 使用空列表作为键列表，因为我们在 Lua 脚本中使用了 KEYS 命令来获取所有匹配的键
		List<String> keys = Collections.emptyList();
		List<Object> args = Collections.singletonList(keyPrefix + "*");

		try {
			// 必须指定序列号器，传入的 ARGV[1]: Data_jieKou_requestCount:1*，否则带有双引号，导致查询不到对应的key
			Long result = (Long) redisTemplate.execute(script, new StringRedisSerializer(), new StringRedisSerializer(), keys, args.toArray());
			return result != null ? result : 0;
		} catch (Exception e) {
			logger.error("Error executing Lua script for sumByKeyPrefix with keyPrefix={}", keyPrefix, e);
			return 0;
		}
	}

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			long currentTimeMillis = System.currentTimeMillis();
			String formatDateTime = DateUtil.formatDateTime(new Date(currentTimeMillis));
			System.out.println(formatDateTime);
			System.out.println(currentTimeMillis);
			long minuteTimestamp = (System.currentTimeMillis() / 60000) * 60000; // 获取当前分钟的开始时间戳
			System.out.println(minuteTimestamp);
			System.out.println("--------");
			ThreadUtil.sleep(1000);
		}
	}
}
