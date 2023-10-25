package org.personal.gallery.suggester.infra;

import org.personal.gallery.suggester.service.ports.Redis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Map;

@Service
public class RedisClient implements Redis {
    private final Jedis jedis;

    public Jedis getJedis() {
        return jedis;
    }

    private static final String PREFIX_INFO = "prefix_info";


    public RedisClient() {
        jedis = new Jedis("localhost", 6379);
    }


    @Override
    public void store(Map<String, String> cacheData) {
        Pipeline pipeline = jedis.pipelined();
        for (Map.Entry<String, String> entry : cacheData.entrySet()) {
            String prefix = entry.getKey();
            String value = entry.getValue();
            pipeline.hset(PREFIX_INFO, prefix, value);
        }
        pipeline.sync();
    }

    @Override
    public String retrieve(String key) {
        return jedis.hget(PREFIX_INFO, key);
    }
}
