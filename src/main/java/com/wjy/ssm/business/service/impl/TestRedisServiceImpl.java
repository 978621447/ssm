package com.wjy.ssm.business.service.impl;

import com.wjy.ssm.business.service.ITestRedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author WangJinYi
 *         2018/8/27
 */
@Service
public class TestRedisServiceImpl implements ITestRedisService{

    private static final Logger LOGGER = LoggerFactory.getLogger(TestRedisServiceImpl.class);

    @Override
    public void testLink() {
        Jedis jedis = new Jedis("192.168.60.136", 6379);
        String name = jedis.get("name");
        LOGGER.info("name: " + name);
        jedis.close();
    }

    @Override
    public void testPool() {
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxIdle(10);
        config.setMaxTotal(30);
        JedisPool jedisPool = new JedisPool(config, "192.168.60.136", 6379);
        Jedis jedis = null;
        try {
            jedis = jedisPool.getResource();
            LOGGER.info(jedis.get("name"));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            jedisPool.close();
        }
    }
}
