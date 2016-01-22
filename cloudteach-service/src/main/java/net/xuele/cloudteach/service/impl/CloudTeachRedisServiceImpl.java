package net.xuele.cloudteach.service.impl;


import com.alibaba.dubbo.config.annotation.Service;
import net.xuele.cloudteach.service.CloudTeachRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;
import java.util.Set;

/**
 * CloudTeachRedisService
 *
 * @author sunxh
 * @date 2015/7/6 0006
 */
@Service
public class CloudTeachRedisServiceImpl implements CloudTeachRedisService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static String redisCode = "utf-8";

    private static Logger logger = LoggerFactory.getLogger(CloudTeachRedisServiceImpl.class);

    private CloudTeachRedisServiceImpl() {

    }

    /**
     * 通过key删除
     *
     * @param keys
     */
    @Override
    public long del(final String... keys) {
        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                long result = 0;
                for (int i = 0; i < keys.length; i++) {
                    result = connection.del(keys[i].getBytes());
                }
                return result;
            }
        });
    }

    /**
     * 添加key value 并且设置存活时间(byte)
     *
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.expire(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 添加key value 并且设置存活时间
     *
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void set(String key, String value, long liveTime) {
        this.set(key.getBytes(), value.getBytes(), liveTime);
    }

    /**
     * 添加key value
     *
     * @param key
     * @param value
     */
    @Override
    public void set(String key, String value) {
        this.set(key, value, 0L);
    }

    /**
     * 添加key value (字节)(序列化)
     *
     * @param key
     * @param value
     */
    @Override
    public void set(byte[] key, byte[] value) {
        this.set(key, value, 0L);
    }

    /**
     * 获取redis value (String)
     *
     * @param key
     * @return
     */
    @Override
    public String get(final String key) {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                try {
                    if (connection.get(key.getBytes()) != null) {
                        return new String(connection.get(key.getBytes()), redisCode);
                    }
                } catch (UnsupportedEncodingException e) {
                    logger.error("redis连接时发生字符集错误：", e);
                }
                return "";
            }
        });
    }

    /**
     * 通过正则匹配keys
     *
     * @param pattern
     * @return
     */
    @Override
    public Set Setkeys(String pattern) {
        return redisTemplate.keys(pattern);
    }


    /**
     * 检查key是否已经存在
     *
     * @param key
     * @return
     */
    @Override
    public boolean exists(final String key) {
        return (boolean) redisTemplate.execute(new RedisCallback() {
            public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.exists(key.getBytes());
            }
        });
    }

    /**
     * 清空redis 所有数据
     *
     * @return
     */
    @Override
    public String flushDB() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                connection.flushDb();
                return "ok";
            }
        });
    }

    /**
     * 查看redis里有多少数据
     */
    @Override
    public long dbSize() {
        return (long) redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.dbSize();
            }
        });
    }

    /**
     * 检查是否连接成功
     *
     * @return
     */
    @Override
    public String ping() {
        return (String) redisTemplate.execute(new RedisCallback() {
            public String doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.ping();
            }
        });
    }

    /**
     * 设置存活时间到具体的时间点
     *
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void setByTimePoint(final byte[] key, final byte[] value, final long liveTime) {
        redisTemplate.execute(new RedisCallback() {
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                connection.set(key, value);
                if (liveTime > 0) {
                    connection.pExpireAt(key, liveTime);
                }
                return 1L;
            }
        });
    }

    /**
     * 设置存活时间到具体的时间点
     *
     * @param key
     * @param value
     * @param liveTime
     */
    @Override
    public void setByTimePoint(String key, String value, long liveTime) {
        this.setByTimePoint(key.getBytes(), value.getBytes(), liveTime);
    }
}
