package com.zht.util;

import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * redis 工具类
 * </p>
 *
 * @author zht
 * @since 2020-3-8
 */
@Component
public class RedisUtils {

    private static RedisTemplate<String, Object> redisTemplate;

    @Autowired(required = false)
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        RedisUtils.redisTemplate = redisTemplate;
    }

    // =============================Common============================

    /**
     * 指定数据失效时间
     *
     * @param key     键
     * @param timeout 失效时间(秒)
     */
    public static void expire(String key, long timeout) {
        if (timeout > 0) {
            redisTemplate.expire(key, timeout, TimeUnit.SECONDS);
        }
    }

    /**
     * 指定数据失效日期
     *
     * @param key      键
     * @param deadLine 失效日期
     */
    public static void expireAt(String key, Date deadLine) {
        redisTemplate.expireAt(key, deadLine);
    }

    /**
     * 根据key获取数据的失效时间
     *
     * @param key 键
     * @return 失效时间(秒) 返回0代表永久有效
     */
    public static Long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key对应的数据是否存在
     *
     * @param key 键
     * @return true存在 false不存在
     */
    public static Boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 根据key删除缓存
     *
     * @param keys 键，可以传一个或多个
     */
    @SuppressWarnings("unchecked")
    public static void del(String... keys) {
        if (ArrayUtils.isNotEmpty(keys)) {
            redisTemplate.delete(CollectionUtils.arrayToList(keys));
        }
    }

    // ============================String=============================

    /**
     * 根据key获取数据
     *
     * @param key 键
     * @return 值
     */
    public static Object get(String key) {
        return key == null ? null : redisTemplate.opsForValue().get(key);
    }

    /**
     * 存入数据
     *
     * @param key   键
     * @param value 值
     */
    public static void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 存入数据并设置失效时间
     *
     * @param key     键
     * @param value   值
     * @param timeout 失效时间(秒) timeout需要大于0 如果小于等于0 将设置无限期
     */
    public static void set(String key, Object value, long timeout) {
        if (timeout > 0) {
            redisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
        } else {
            set(key, value);
        }
    }

    /**
     * long类型递增
     *
     * @param key   键
     * @param delta 递增因子(大于0)
     * @return 递增后的值
     */
    public static Long increment(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * double类型递增
     *
     * @param key   键
     * @param delta 递增因子(大于0)
     * @return 递增后的值
     */
    public static Double increment(String key, double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, delta);
    }

    /**
     * long类型递减
     *
     * @param key   键
     * @param delta 递减因子(大于0)
     * @return 递减后的值
     */
    public static Long decrement(String key, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    /**
     * double类型递减
     *
     * @param key   键
     * @param delta 递减因子(大于0)
     * @return 递减后的值
     */
    public static Double decrement(String key, double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForValue().increment(key, -delta);
    }

    // ===============================HashMap=================================

    /**
     * 根据key以及hashKey获取map集合的value，没有则返回null
     *
     * @param key     键
     * @param hashKey map集合的key
     * @return map集合的value
     */
    public static Object hget(String key, String hashKey) {
        return redisTemplate.opsForHash().get(key, hashKey);
    }

    /**
     * 根据key获取map集合
     *
     * @param key 键
     * @return map集合
     */
    public static Map<Object, Object> hmget(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * 存入map集合
     *
     * @param key 键
     * @param map map集合
     */
    public static void hmset(String key, Map<String, Object> map) {
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 存入map集合并设置失效时间
     *
     * @param key     键
     * @param map     map集合
     * @param timeout 失效时间(秒) timeout需要大于0 如果小于等于0 将设置无限期
     */
    public static void hmset(String key, Map<String, Object> map, long timeout) {
        redisTemplate.opsForHash().putAll(key, map);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 向map集合中存放数据项，如果map集合不存在则创建
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param value   map集合的value
     */
    public static void hset(String key, String hashKey, Object value) {
        redisTemplate.opsForHash().put(key, hashKey, value);
    }

    /**
     * 向map集合中存放数据项，如果map集合不存在则创建，并设置失效时间
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param value   map集合的value
     * @param timeout 失效时间(秒) timeout需要大于0 如果小于等于0 将设置无限期 注意:如果已存在失效时间将会替换原有的时间
     */
    public static void hset(String key, String hashKey, Object value, long timeout) {
        redisTemplate.opsForHash().put(key, hashKey, value);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 移除map集合中的值
     *
     * @param key     键
     * @param hashKey map集合的key
     */
    public static void hRemove(String key, Object... hashKey) {
        redisTemplate.opsForHash().delete(key, hashKey);
    }

    /**
     * 获取map集合大小
     *
     * @param key 键
     * @return map集合大小
     */
    public static Long hGetHashSize(String key) {
        return redisTemplate.opsForHash().size(key);
    }

    /**
     * 判断map集合中是否有hashKey所对应的值
     *
     * @param key     键
     * @param hashKey map集合的key
     * @return true 存在 false不存
     */
    public static Boolean hHasKey(String key, String hashKey) {
        return redisTemplate.opsForHash().hasKey(key, hashKey);
    }

    /**
     * long类型hash递增
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param delta   递增因子(大于0)
     * @return 递增后的值
     */
    public static Long hincrement(String key, String hashKey, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }


    /**
     * double类型hash递增
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param delta   递增因子(大于0)
     * @return 递增后的值
     */
    public static Double hincrement(String key, String hashKey, double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递增因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hashKey, delta);
    }

    /**
     * long类型hash递减
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param delta   递增因子(大于0)
     * @return 递减后的值
     */
    public static Long hdecrement(String key, String hashKey, long delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    /**
     * double类型hash递减
     *
     * @param key     键
     * @param hashKey map集合的key
     * @param delta   递增因子(大于0)
     * @return 递减后的值
     */
    public static Double hdecrement(String key, String hashKey, double delta) {
        if (delta <= 0) {
            throw new IllegalArgumentException("递减因子必须大于0");
        }
        return redisTemplate.opsForHash().increment(key, hashKey, -delta);
    }

    // ===============================HashSet=================================

    /**
     * 根据key获取set集合
     *
     * @param key 键
     * @return set集合
     */
    public static Set<Object> sget(String key) {
        return redisTemplate.opsForSet().members(key);
    }

    /**
     * 判断set集合中是否有对应的值
     *
     * @param key 键
     * @param obj 值
     * @return true 存在 false不存在
     */
    public static Boolean sHasObj(String key, Object obj) {
        return redisTemplate.opsForSet().isMember(key, obj);
    }

    /**
     * 向set集合中存放数据
     *
     * @param key   键
     * @param value 值
     */
    public static void sSet(String key, Object... value) {
        redisTemplate.opsForSet().add(key, value);
    }

    /**
     * 向set集合中存放数据并设置失效时间
     *
     * @param key     键
     * @param timeout 失效时间(秒) timeout需要大于0 如果小于等于0 将设置无限期 注意:如果已存在失效时间将会替换原有的时间
     * @param value   值
     */
    public static void sSet(String key, long timeout, Object... value) {
        redisTemplate.opsForSet().add(key, value);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 获取set集合大小
     *
     * @param key 键
     * @return set集合大小
     */
    public static Long sGetSetSize(String key) {
        return redisTemplate.opsForSet().size(key);
    }

    /**
     * 移除set集合值
     *
     * @param key   键
     * @param value 要移除的值
     */
    public static void sRemove(String key, Object... value) {
        redisTemplate.opsForSet().remove(key, value);
    }

    // ===============================list=================================

    /**
     * 根据key以及集合索引获取list集合
     *
     * @param key   键
     * @param start 开始索引
     * @param end   结束索引
     * @return list集合
     */
    public static List<Object> lGet(String key, long start, long end) {
        return redisTemplate.opsForList().range(key, start, end);
    }

    /**
     * 根据key获取list集合
     *
     * @param key 键
     * @return list集合
     */
    public static List<Object> lGet(String key) {
        return lGet(key, 0, -1);
    }

    /**
     * 获取list集合大小
     *
     * @param key 键
     * @return list集合大小
     */
    public static Long lGetListSize(String key) {
        return redisTemplate.opsForList().size(key);
    }

    /**
     * 通过索引获取list集合中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 第一个元素，1 第二个元素，依次类推；index<0时，-1 倒数第一个元素，-2 倒数第二个元素，依次类推
     * @return 数据项
     */
    public static Object lGetByIndex(String key, long index) {
        return redisTemplate.opsForList().index(key, index);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 数据项
     */
    public static void lSet(String key, Object value) {
        redisTemplate.opsForList().rightPush(key, value);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 数据项
     * @param time  时间(秒)
     */
    public static void lSet(String key, Object value, long time) {
        redisTemplate.opsForList().rightPush(key, value);
        if (time > 0) {
            expire(key, time);
        }
    }

    /**
     * 存入list集合
     *
     * @param key  键
     * @param list list集合
     */
    public static void lSetAll(String key, List<Object> list) {
        redisTemplate.opsForList().rightPushAll(key, list);
    }

    /**
     * 存入list集合并设置失效时间
     *
     * @param key     键
     * @param list    list集合
     * @param timeout 失效时间(秒) timeout需要大于0 如果小于等于0 将设置无限期 注意:如果已存在失效时间将会替换原有的时间
     */
    public static void lSetAll(String key, List<Object> list, long timeout) {
        redisTemplate.opsForList().rightPushAll(key, list);
        if (timeout > 0) {
            expire(key, timeout);
        }
    }

    /**
     * 根据索引index修改list集合中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 替换的值
     */
    public static void lUpdateByIndex(String key, long index, Object value) {
        redisTemplate.opsForList().set(key, index, value);
    }

    /**
     * 移除count个值为value的数据项
     *
     * @param key   键
     * @param count 移除数量
     * @param value 移除的值
     */
    public static void lRemove(String key, long count, Object value) {
        redisTemplate.opsForList().remove(key, count, value);
    }
}
