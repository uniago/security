package com.uniago.security.tools;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * redis缓存工具类
 * @author uniago
 * Create by 2024/07/02 15:04
 */
@Component
public class RedisCache<T> {

    @Autowired
    public RedisTemplate<String, T> redisTemplate;

    /**
     * 缓存基本对象 Integer String 实体类等
     * @param key 键
     * @param value 值
     */
    public void setCacheObject(final String key, final T value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存基本对象 Integer String 实体类等
     * @param key 键
     * @param value 值
     * @param timeout 超时时间
     * @param timeUnit 时间颗粒度
     */
    public void setCacheObject(final String key, final T value, final Integer timeout, final TimeUnit timeUnit) {
        redisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间
     * @return 成功/失败
     */
    public boolean expire(final String key, final long timeout) {
        return expire(key, timeout);
    }

    /**
     * 设置有效时间
     * @param key 键
     * @param timeout 超时时间
     * @param timeUnit 时间颗粒度
     * @return 成功/失败
     */
    public boolean expire(final String key, final long timeout, final TimeUnit timeUnit) {
        return redisTemplate.expire(key, timeout, timeUnit);
    }

    /**
     * 从缓存中获取基本对象
     * @param key 键
     * @return 缓存键值对应的数据
     */
    public T getCacheObject(final String key) {
        ValueOperations<String, T> operation = redisTemplate.opsForValue();
        return operation.get(key);
    }

    /**
     * 删除单个对象
     * @param key 键
     * @return 删除成功/失败
     */
    public boolean deleteObject(final String key) {
        return redisTemplate.delete(key);
    }

    /**
     * 删除集合对象
     * @param collection 多个对象
     * @return 删除的个数
     */
    public Long deleteObject(final Collection<String> collection) {
        return redisTemplate.delete(collection);
    }

    /**
     * 缓存List数据
     *
     * @param key 缓存的键值
     * @param dataList 待缓存的List数据
     * @return 缓存的对象
     */
    public Long setCacheList(final String key, final Collection<T> dataList) {
        ListOperations<String, T> operation = redisTemplate.opsForList();
        Long count = operation.rightPushAll(key, dataList);
        return count == null ? 0 : count;
    }

    /**
     * 获得缓存的list对象
     *
     * @param key 缓存的键值
     * @return 缓存键值对应的数据
     */
    public List<T> getCacheList(final String key) {
        ListOperations<String, T> operation = redisTemplate.opsForList();
        return operation.range(key, 0, -1);
    }

    /**
     * 缓存Set
     *
     * @param key 缓存键值
     * @param dataSet 缓存的数据
     * @return 缓存数据的对象
     */
    public BoundSetOperations<String, T> setCacheSet(final String key, final Set<T> dataSet) {
        BoundSetOperations<String, T> operation = redisTemplate.boundSetOps(key);
        Iterator<T> it = dataSet.iterator();
        while (it.hasNext()) {
            operation.add(it.next());
        }
        return operation;
    }

    /**
     * 获得缓存的set
     * @param key 键
     * @return 数据集合
     */
    public Set<T> getCacheSet(final String key) {
        SetOperations<String, T> operation = redisTemplate.opsForSet();
        return operation.members(key);
    }

    /**
     * 缓存map
     * @param key 键
     * @param dataMap map
     */
    public  void setCacheMap(final String key, final Map<String, T> dataMap) {
        HashOperations<String, String, T> operation = redisTemplate.opsForHash();
        operation.putAll(key, dataMap);
    }

    /**
     * 获取缓存中的Map
     * @param key 键
     * @return Map
     */
    public  Map<String, T> getCacheMap(final String key) {
        HashOperations<String, String, T> operation = redisTemplate.opsForHash();
        return operation.entries(key);
    }

    /**
     * 往hash中存入数据
     * @param key 键
     * @param hashKey hash键
     * @param value 数据
     */
    public  void setCacheMapValue(final String key, final String hashKey, final T value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 获取hash中的数据
     * @param key 键
     * @param hashKey hash键
     * @return hash中的对象
     */
    public  T getCacheMapValue(final String key, final String hashKey) {
        HashOperations<String, String, T> operation = redisTemplate.opsForHash();
        return (T) operation.get(key, hashKey);
    }

    /**
     * 获取多个hash中的数据
     * @param key 键
     * @param hashKeys hash键
     * @return hash对象集合
     */
    public List<T> getMultiCacheMapValue(final String key, final Collection<Object> hashKeys) {
        HashOperations<String, Object, T> operation = redisTemplate.opsForHash();
        return operation.multiGet(key, hashKeys);
    }

    /**
     * 删除hash中的数据
     * @param key 键
     * @param hashKey hash键
     * @return 删除的个数
     */
    public Long deleteCacheMapValue(final String key, final String hashKey) {
        HashOperations<String, String, T> operation = redisTemplate.opsForHash();
        return operation.delete(key, hashKey);
    }

    /**
     * 获取缓存的基本对象列表
     * @param pattern key前缀
     * @return 对象列表
     */
    public Collection<String> keys(final String pattern) {
        return redisTemplate.keys(pattern);
    }
}
