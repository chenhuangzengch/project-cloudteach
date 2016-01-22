package net.xuele.cloudteach.service;

import java.util.Set;

/**
 * 云教学子系统内部使用，非公用
 * Created by sunxh on 2015/7/6 0006.
 */
public interface CloudTeachRedisService {

    /**
     * 通过key删除
     *
     * @param keys
     */
    long del(String... keys);

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void set(byte[] key, byte[] value, long liveTime);

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime 单位秒
     */
    void set(String key, String value, long liveTime);

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    void set(String key, String value);

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    void set(byte[] key, byte[] value);

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    String get(String key);

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    Set Setkeys(String pattern);

    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    boolean exists(String key);

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    String flushDB();

    /**
     * 查看redis里有多少数据
     */
    long dbSize();

    /**
     * 检查是否连接成功
     *
     * @return
     */
    String ping();

    /**
     * 设置存活时间到具体的时间点
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void setByTimePoint(String key, String value, long liveTime);

    /**
     * 设置存活时间到具体的时间点
     *
     * @param key
     * @param value
     * @param liveTime
     */
    void setByTimePoint(byte[] key, byte[] value, long liveTime);
}
