package org.personal.gallery.suggester.infra;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RedisClientTest {

    private static final String PREFIX_INFO = "prefix_info";

    @Mock
    private Jedis jedis;
    private RedisClient redisClient;


    @BeforeEach
    public void setUp() {
        redisClient = new RedisClient();
        ReflectionTestUtils.setField(redisClient, "jedis", jedis);
    }

    @Test
    void testStore() {

        Map<String, String> cacheData = new HashMap<>();
        cacheData.put("key1", "value1");
        cacheData.put("key2", "value2");

        Pipeline pipeline = mock(Pipeline.class);
        when(jedis.pipelined()).thenReturn(pipeline);


        redisClient.store(cacheData);


        verify(pipeline, times(1)).hset(PREFIX_INFO, "key1", "value1");
        verify(pipeline, times(1)).hset(PREFIX_INFO, "key2", "value2");
        verify(pipeline).sync();


        verify(jedis, times(1)).pipelined();
    }

    @Test
    void testRetrieve() {

        String key = "key1";
        String value = "value1";


        when(jedis.hget(PREFIX_INFO, key)).thenReturn(value);


        String result = redisClient.retrieve(key);


        verify(jedis).hget(PREFIX_INFO, key);


        assertEquals(value, result);
    }
}
