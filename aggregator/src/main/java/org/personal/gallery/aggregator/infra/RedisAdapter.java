package org.personal.gallery.aggregator.infra;

import org.personal.gallery.aggregator.service.ports.Redis;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.Map;

@Service
public class RedisAdapter implements Redis {
    private final Jedis jedis;
    private static final String PREFIX_INFO = "prefix_info";


    public RedisAdapter() {
        jedis = new Jedis("localhost", 6379);
    }


    @Override
    public void store(Map<String, String> cacheData) {
        Pipeline pipeline = jedis.pipelined();
        for (Map.Entry<String, String> entry : cacheData.entrySet()) {
            String prefix = entry.getKey();
            String value= entry.getValue();
            pipeline.hset(PREFIX_INFO, prefix, value);
        }
        pipeline.sync();
    }

    @Override
    public String retrieve(String key) {
        return jedis.hget(PREFIX_INFO,key);
    }
}
