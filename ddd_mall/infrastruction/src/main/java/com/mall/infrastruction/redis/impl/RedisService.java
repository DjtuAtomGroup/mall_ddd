package com.mall.infrastruction.redis.impl;

import com.mall.infrastruction.redis.IRedisService;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;


@Service("redisService")
public class RedisService implements IRedisService {
    /**
     * 设置指定 key 的值
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public <T> void setValue(String key, T value) {

    }

    /**
     * 设置指定 key 的值
     *
     * @param key     键
     * @param value   值
     * @param expired 过期时间
     */
    @Override
    public <T> void setValue(String key, T value, long expired) {

    }

    /**
     * 获取指定 key 的值
     *
     * @param key 键
     * @return 值
     */
    @Override
    public <T> T getValue(String key) {
        return null;
    }

    /**
     * 设置值
     *
     * @param key   key 键
     * @param value 值
     */
    @Override
    public void setAtomicLong(String key, long value) {

    }

    /**
     * 获取值
     *
     * @param key key 键
     */
    @Override
    public Long getAtomicLong(String key) {
        return null;
    }

    /**
     * 自增 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    @Override
    public long incr(String key) {
        return 0;
    }

    /**
     * 指定值，自增 Key 的值；1、2、3、4
     *
     * @param key   键
     * @param delta
     * @return 自增后的值
     */
    @Override
    public long incrBy(String key, long delta) {
        return 0;
    }

    /**
     * 自减 Key 的值；1、2、3、4
     *
     * @param key 键
     * @return 自增后的值
     */
    @Override
    public long decr(String key) {
        return 0;
    }

    /**
     * 指定值，自增 Key 的值；1、2、3、4
     *
     * @param key   键
     * @param delta
     * @return 自增后的值
     */
    @Override
    public long decrBy(String key, long delta) {
        return 0;
    }

    /**
     * 移除指定 key 的值
     *
     * @param key 键
     */
    @Override
    public void remove(String key) {

    }

    /**
     * 判断指定 key 的值是否存在
     *
     * @param key 键
     * @return true/false
     */
    @Override
    public boolean isExists(String key) {
        return false;
    }

    /**
     * 将指定的值添加到集合中
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void addToSet(String key, String value) {

    }

    /**
     * 判断指定的值是否是集合的成员
     *
     * @param key   键
     * @param value 值
     * @return 如果是集合的成员返回 true，否则返回 false
     */
    @Override
    public boolean isSetMember(String key, String value) {
        return false;
    }

    /**
     * 将指定的值添加到列表中
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void addToList(String key, String value) {

    }

    /**
     * 获取列表中指定索引的值
     *
     * @param key   键
     * @param index 索引
     * @return 值
     */
    @Override
    public String getFromList(String key, int index) {
        return null;
    }

    /**
     * 将指定的键值对添加到哈希表中
     *
     * @param key   键
     * @param field 字段
     * @param value 值
     */
    @Override
    public void addToMap(String key, String field, String value) {

    }

    /**
     * 获取哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    @Override
    public String getFromMap(String key, String field) {
        return null;
    }

    /**
     * 获取哈希表中指定字段的值
     *
     * @param key   键
     * @param field 字段
     * @return 值
     */
    @Override
    public <K, V> V getFromMap(String key, K field) {
        return null;
    }

    /**
     * 将指定的值添加到有序集合中
     *
     * @param key   键
     * @param value 值
     */
    @Override
    public void addToSortedSet(String key, String value) {

    }

    /**
     * 布隆过滤器
     *
     * @param key 键
     * @return 返回结果
     */
    @Override
    public Boolean setNx(String key) {
        return null;
    }

    @Override
    public Boolean setNx(String key, long expired, TimeUnit timeUnit) {
        return null;
    }
}
