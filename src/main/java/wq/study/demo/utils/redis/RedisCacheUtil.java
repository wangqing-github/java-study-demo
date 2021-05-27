package wq.study.demo.utils.redis;

import org.springframework.data.redis.core.RedisTemplate;
import wq.study.demo.utils.context.SpringContext;

public class RedisCacheUtil extends BaseRedisManager {
    // 使用了一个私有静态变量syncObj来保证线程同步
    private static final Object syncObj = new Object();
    private static volatile RedisCacheUtil _inst;

    private RedisCacheUtil() {
        super.redisTemplate = (RedisTemplate) SpringContext.inst().getBean("localServerRedisTemplate");
    }

    public static RedisCacheUtil inst() {
        if (_inst == null) {
            synchronized (syncObj) {
                if (_inst == null)
                    _inst = new RedisCacheUtil();
            }
        }
        return _inst;
    }

}
