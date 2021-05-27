package wq.study.demo.utils.redis;

import com.google.common.collect.Lists;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.types.RedisClientInfo;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.CollectionUtils;
import redis.clients.jedis.Jedis;

import java.util.*;
import java.util.concurrent.TimeUnit;

public abstract class BaseRedisManager {

    protected RedisTemplate<String, Object> redisTemplate;

    protected BaseRedisManager() {

    }

    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public long getActiveNum() {
        List<RedisClientInfo> list = getRedisClientInfo();
        if (list != null) {
            return redisTemplate.getClientList().size();
        }
        return -1;
    }

    private static final String USER_ARENA_EMPTY_SEQ = "USER_ARENA_SEQ_EMPTY";

    public void arenaRobotOperation(String key, String destinationKey) {
        redisTemplate.opsForZSet().unionAndStore(key,USER_ARENA_EMPTY_SEQ,destinationKey);
    }

    public List<RedisClientInfo> getRedisClientInfo() {
        return redisTemplate.getClientList();
    }

    public Boolean setIfAbsent(String key) {
        return setIfAbsent(key,1500);
    }

    public Boolean setIfAbsent(String key,long timeOut) {
        return redisTemplate.opsForValue().setIfAbsent(key,1,timeOut,TimeUnit.MILLISECONDS);
    }

    public void rename(String oldKey,String newKey) {
        redisTemplate.rename(oldKey, newKey);
    }

    /**
     * 指定缓存失效时间
     *
     * @param key  键
     * @param time 时间(秒)
     */
    public boolean expire(String key, long time) {
        try {
            if (time > 0) {
                redisTemplate.expire(key, time, TimeUnit.SECONDS);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据key 获取过期时间
     *
     * @param key 键 不能为null
     * @return 时间(秒) 返回0代表为永久有效
     */
    public long getExpire(String key) {
        return redisTemplate.getExpire(key, TimeUnit.SECONDS);
    }

    /**
     * 判断key是否存在
     *
     * @param key 键
     * @return true 存在 false不存在
     */
    public boolean hasKey(String key) {
        try {
            return redisTemplate.hasKey(key);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 获取所有的key
     *
     * @return true 存在 false不存在
     */
    public Set<String> keys(String pattern) {
        try {
            return redisTemplate.keys(pattern);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 删除缓存
     *
     * @param key 可以传一个值 或多个
     */
    @SuppressWarnings("unchecked")
    public void delete(String... key) {
        if (key != null && key.length > 0) {
            if (key.length == 1) {
                redisTemplate.delete(key[0]);
            } else {
                redisTemplate.delete((Collection<String>) CollectionUtils.arrayToList(key));
            }
        }
    }


    /**
     * 普通缓存获取
     *
     * @param key 键
     * @return 值
     */
    public <V> V get(String key) {
        return key == null ? null : (V) redisTemplate.opsForValue().get(key);
    }

    public Long getLong(String key) {
        Object v = redisTemplate.opsForValue().get(key);
        if (v == null) return 0L;
        if (v instanceof Long) return (Long) v;
        return NumberUtils.toLong(String.valueOf(v));
    }


    /**
     * 普通缓存放入
     *
     * @param key   键
     * @param value 值
     * @return true成功 false失败
     */
    public boolean set(String key, Object value) {
        try {
            redisTemplate.opsForValue().set(key, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    private static final Long RELEASE_SUCCESS = 1L;
    private static final String LOCK_SUCCESS = "OK";
    private static final String RELEASE_LOCK_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";



    /**
     * 普通缓存放入并设置时间
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒) time要大于0 如果time小于等于0 将设置无限期
     * @return true成功 false 失败
     */
    public boolean set(String key, Object value, long time) {
        try {
            if (time > 0) {
                redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
            } else {
                set(key, value);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 递增
     *
     * @param key 键
     *            要增加几
     */
    public long increment(String key) {
        return increment(key, 1L);
    }

    public long increment(String key, long delta) {
        try {
            Long increment = redisTemplate.opsForValue().increment(key, delta);
            return increment == null ? 0 : increment;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public Long increment(String key, long delta, long time) {
        try {
            Long value = redisTemplate.opsForValue().increment(key, delta);
            if (time > 0) {
                expire(key, time);
            }
            return value;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0L;
    }



    /**
     * HashGet
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return 值
     */
    public<T> T hget(String key, String item) {
        return (T)redisTemplate.opsForHash().get(key, item);
    }

    /**
     * 获取hashKey对应的所有键值
     *
     * @param key 键
     * @return 对应的多个键值
     */
    public Map<Object, Object> hentries(String key) {
        return redisTemplate.opsForHash().entries(key);
    }

    /**
     * HashSet
     *
     * @param key 键
     * @param map 对应多个键值
     * @return true 成功 false 失败
     */
    public boolean hmset(String key, Map<String, Object> map) {
        return hmset(key, map, 0);
    }

    /**
     * HashSet 并设置时间
     *
     * @param key  键
     * @param map  对应多个键值
     * @param time 时间(秒)
     * @return true成功 false失败
     */
    public boolean hmset(String key, Map<String, Object> map, long time) {
        try {
            redisTemplate.opsForHash().putAll(key, map);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value) {
        return hset(key, item, value, 0);
    }

    /**
     * 向一张hash表中放入数据,如果不存在将创建
     *
     * @param key   键
     * @param item  项
     * @param value 值
     * @param time  时间(秒) 注意:如果已存在的hash表有时间,这里将会替换原有的时间
     * @return true 成功 false失败
     */
    public boolean hset(String key, String item, Object value, long time) {
        try {
            redisTemplate.opsForHash().put(key, item, value);
            if (time > 0) {
                expire(key, time);
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 删除hash表中的值
     *
     * @param key  键 不能为null
     * @param item 项 可以使多个 不能为null
     */
    public void hdelete(String key, Object... item) {
        redisTemplate.opsForHash().delete(key, item);
    }

    /**
     * 判断hash表中是否有该项的值
     *
     * @param key  键 不能为null
     * @param item 项 不能为null
     * @return true 存在 false不存在
     */
    public boolean hhaskey(String key, String item) {
        return redisTemplate.opsForHash().hasKey(key, item);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @return
     */
    public double hincrement(String key, String item, double by) {
        return redisTemplate.opsForHash().increment(key, item, by);
    }

    /**
     * hash递增 如果不存在,就会创建一个 并把新增后的值返回
     *
     * @param key  键
     * @param item 项
     * @param by   要增加几(大于0)
     * @param time 过期时间(秒)
     * @return
     */
    public double hincrement(String key, String item, double by, long time) {
        Double increment = redisTemplate.opsForHash().increment(key, item, by);
        if (time > 0) {
            expire(key, time);
        }
        return increment;
    }

    /**
     * 根据key获取Set中的所有值
     *
     * @param key 键
     */
    public <V> Set<V> smembers(String key) {
        try {
            return (Set<V>) redisTemplate.opsForSet().members(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据value从一个set中查询,是否存在
     *
     * @param key   键
     * @param value 值
     * @return true 存在 false不存在
     */
    public boolean sisMember(String key, Object value) {
        try {
            Boolean result = redisTemplate.opsForSet().isMember(key, value);
            return result == null ? false : result;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将数据放入set缓存，覆盖
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sadd(String key, Object values) {
        return sadd(key, 0, values);
    }

    /**
     * 将set数据放入缓存
     *
     * @param key    键
     * @param time   时间(秒)
     * @param values 值 可以是多个
     * @return 成功个数
     */
    public long sadd(String key, long time, Object values) {
        try {
            Long count = redisTemplate.opsForSet().add(key, values);
            if (time > 0)
                expire(key, time);
            return count == null ? 0 : count;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过管道批量插入
     *
     */
    public void sadd(final List<String> keys, final String member) {
        RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
            @Override
            public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                List<byte[]> names = Lists.newArrayList();
                for (String key : keys) {
                    names.add(keySerializer.serialize(key));
                }
                RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                byte[] fields = valueSerializer.serialize(member);
                connection.openPipeline();
                for (byte[] key : names) {
                    connection.sAdd(key, fields);
                }
                return connection.closePipeline();
            }
        };
        List<Object> results = redisTemplate.execute(pipelineCallback);
    }

    /**
     * 获取set缓存的长度
     *
     * @param key 键
     */
    public long ssize(String key) {
        try {
            Long size = redisTemplate.opsForSet().size(key);
            return size == null ? 0 : size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除值为value的
     *
     * @param key    键
     * @param values 值 可以是多个
     * @return 移除的个数
     */
    public long sremove(String key, Object... values) {
        try {
            Long result = redisTemplate.opsForSet().remove(key, values);
            return result == null ? 0 : result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除并返回集合中的一个随机元素
     *
     * @param key 键
     * @return 随机元素
     */
    public Object spop(String key) {
        try {
            return redisTemplate.opsForSet().pop(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Set<String> spop(String key, int count) {
        Set<String> sets = new HashSet<>();
        while (count > 0) {
            Object v = spop(key);
            if (v == null) break;
            sets.add((String) v);
            count--;
        }
        return sets;
    }

    /**
     * 获取list缓存的内容
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public List<Object> lrange(String key, long start, long end) {
        try {
            return redisTemplate.opsForList().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 获取list缓存的长度
     *
     * @param key 键
     */
    public long lsize(String key) {
        try {
            Long size = redisTemplate.opsForList().size(key);
            return size == null ? 0 : size;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 通过索引 获取list中的值
     *
     * @param key   键
     * @param index 索引 index>=0时， 0 表头，1 第二个元素，依次类推；index<0时，-1，表尾，-2倒数第二个元素，依次类推
     */
    public Object lindex(String key, long index) {
        try {
            return redisTemplate.opsForList().index(key, index);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 将list放入头部缓存
     *
     */
    public boolean lleftPush(String key, Object value) {
        return lleftPush(key, value, 0);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lleftPush(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().leftPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lrightPush(String key, Object value) {
        return lrightPush(key, value, 0);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lrightPush(String key, Object value, long time) {
        try {
            redisTemplate.opsForList().rightPush(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     */
    public boolean lrightPushAll(String key, List<Object> value) {
        return lrightPushAll(key, value, 0);
    }

    /**
     * 将list放入缓存
     *
     * @param key   键
     * @param value 值
     * @param time  时间(秒)
     */
    public boolean lrightPushAll(String key, List<Object> value, long time) {
        try {
            redisTemplate.opsForList().rightPushAll(key, value);
            if (time > 0)
                expire(key, time);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 根据索引修改list中的某条数据
     *
     * @param key   键
     * @param index 索引
     * @param value 值
     */
    public boolean lset(String key, long index, Object value) {
        try {
            redisTemplate.opsForList().set(key, index, value);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 移除N个值为value
     *
     * @param key   键
     * @param count 移除多少个 0=移除所有值为value的数据
     * @param value 值
     * @return 移除的个数
     */
    public long lremove(String key, long count, Object value) {
        try {
            Long result = redisTemplate.opsForList().remove(key, count, value);
            return result == null ? 0 : result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }


    /**
     * 有序集合中对指定成员的分数加上增量 increment
     *
     * @param key       键
     * @param member    成员
     * @param increment 值
     */
    public Double zincrementScore(String key, double increment, Object member) {
        return zincrementScore(key, increment, member, 0);
    }

    public Double zincrementScore(String key, double increment, Object member, long time) {
        try {
            Double val = redisTemplate.opsForZSet().incrementScore(key, member, increment);
            if (time > 0)
                expire(key, time);
            return val;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 向有序集合添加一个或多个成员，或者更新已存在成员的分数为value
     *
     * @param key    键
     * @param member 成员
     * @param value  值
     */
    public boolean zadd(String key, double value, Object member) {
        return zadd(key, value, member, 0);
    }

    public boolean zadd(String key, double value, Object member, long time) {
        try {
            Boolean b = redisTemplate.opsForZSet().add(key, member, value);
            if (time > 0)
                expire(key, time);
            return b == null ? false : b;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 通过管道批量插入
     */
    public void zadd(String key, double value,Set<String> member) {
        zadd(key,value,member,0);
    }
    public void zadd(String key, double value,Set<String> members,long time) {
        try {
            RedisCallback<List<Object>> pipelineCallback = new RedisCallback<List<Object>>() {
                @Override
                public List<Object> doInRedis(RedisConnection connection) throws DataAccessException {
                    RedisSerializer keySerializer = redisTemplate.getKeySerializer();
                    byte[] _key = keySerializer.serialize(key);
                    RedisSerializer valueSerializer = redisTemplate.getValueSerializer();
                    List<byte[]> fields = Lists.newArrayList();
                    for(String member : members){
                        fields.add(valueSerializer.serialize(member));
                    }
                    connection.openPipeline();
                    for (byte[] filed : fields) {
                        connection.zAdd(_key,value,filed);
                    }
                    return connection.closePipeline();
                }
            };
            redisTemplate.execute(pipelineCallback);
            if (time > 0)
                expire(key, time);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 返回有序集中，成员的分数值
     *
     * @param key    键
     * @param member 成员
     */
    public Double zscore(String key, Object member) {
        try {
            return redisTemplate.opsForZSet().score(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    /**
     * 获取有序集合的成员数
     *
     * @param key 键
     */
    public Long zsize(String key) {
        try {
            return redisTemplate.opsForZSet().size(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 计算在有序集合中指定区间分数的成员数
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public Long zcount(String key, double start, double end) {
        try {
            return redisTemplate.opsForZSet().count(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public long getSortedSetCount(String key) {
        Long count = redisTemplate.opsForZSet().zCard(key);
        return count == null ? 0 : count;
    }


    /**
     * 移除有序集合中的一个或多个成员
     *
     * @param key    键
     * @param values 值
     */
    public long zremove(String key, Object... values) {
        try {
            Long result = redisTemplate.opsForZSet().remove(key, values);
            return result == null ? 0 : result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集合中给定的排名区间的所有成员
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public long zremoveRange(String key, long start, long end) {
        try {
            Long result = redisTemplate.opsForZSet().removeRange(key, start, end);
            return result == null ? 0 : result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 移除有序集合中给定的分数区间的所有成员
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public long zremoveRangeByScore(String key, double start, double end) {
        try {
            Long result = redisTemplate.opsForZSet().removeRangeByScore(key, start, end);
            return result == null ? 0 : result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }

    /**
     * 返回有序集合中指定成员的排名，有序集成员按分数值递减(从小到大)排序
     *
     * @param key 键
     */
    public Long zrank(String key, Object member) {
        try {
            return redisTemplate.opsForZSet().rank(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序
     *
     * @param key 键
     */
    public Long zreverseRank(String key, Object member) {
        try {
            return redisTemplate.opsForZSet().reverseRank(key, member);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <V> List<V> zRange(String key, long start, long end) {
        Set<V> set = zrange(key, start, end);
        if (set == null) return null;
        return new ArrayList<>(set);
    }

    /**
     * 通过索引区间返回有序集合成指定区间内的成员(从小到大)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> Set<V> zrange(String key, long start, long end) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().range(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过分数返回有序集合指定区间内的成员(从小到大)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> Set<V> zrangeByScore(String key, double start, double end) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().rangeByScore(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 通过分数返回有序集合指定区间内的成员(从小到大)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> Set<V> zrangeByScore(String key, double start, double end, long count) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().rangeByScore(key, start, end, 0, count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定区间内的成员，通过索引，分数(从大到小)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> Set<V> zreverseRange(String key, long start, long end) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().reverseRange(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public <V> List<V> zrevrange(String key, long start, long end) {
        Set<V> set = zreverseRange(key, start, end);
        if (set == null) return null;
        return new ArrayList<>(set);
    }

    /**
     * 返回有序集中指定分数区间内的成员，分数(从大到小)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> Set<V> zreverseRangeByScore(String key, double start, double end) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().reverseRangeByScore(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定分数区间内的成员，分数(从大到小)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeByScoreWithScores(String key, double start, double end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeByScoreWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定分数区间内的成员，分数(从小到大)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public <V> List<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(String key, double start, double end) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeByScoreWithScores(key, start, end);
            List<ZSetOperations.TypedTuple<V>> typedTupleSet = new ArrayList<>();
            if (tupleSet != null) {
                tupleSet.forEach(tuple -> {
                    ZSetOperations.TypedTuple<V> typedTuple = new DefaultTypedTuple<>((V) tuple.getValue(), tuple.getScore());
                    typedTupleSet.add(typedTuple);
                });
            }
            return typedTupleSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集合中指定区间的成员，分数（从大到小）排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     */
    public Set<ZSetOperations.TypedTuple<Object>> reverseRangeWithScores(String key, long start, long end) {
        try {
            return redisTemplate.opsForZSet().reverseRangeWithScores(key, start, end);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集合中指定区间的成员，分数（从小到大）排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @return
     */
    public <V> List<ZSetOperations.TypedTuple<V>> rangeWithScores(String key, long start, long end) {
        try {
            Set<ZSetOperations.TypedTuple<Object>> tupleSet = redisTemplate.opsForZSet().rangeWithScores(key, start, end);
            List<ZSetOperations.TypedTuple<V>> typedTupleSet = new ArrayList<>();
            if (tupleSet != null) {
                tupleSet.forEach(tuple -> {
                    ZSetOperations.TypedTuple<V> typedTuple = new DefaultTypedTuple<>((V) tuple.getValue(), tuple.getScore());
                    typedTupleSet.add(typedTuple);
                });
            }
            return typedTupleSet;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 返回有序集中指定分数区间内的成员，分数(从大到小)排序
     *
     * @param key   键
     * @param start 开始
     * @param end   结束 0 到 -1代表所有值
     * @param count 返回个数
     */
    public <V> Set<V> zreverseRangeByScore(String key, double start, double end, long count) {
        try {
            return (Set<V>) redisTemplate.opsForZSet().reverseRangeByScore(key, start, end, 0, count);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public void zunionstore(String dstkey, String sets) {
        try {
            redisTemplate.opsForZSet().unionAndStore(dstkey, dstkey, sets);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除模糊 key ，线上执行效率低下，谨慎使用
     *
     */
    public int deleteByPrex(String prex) {
        Set<String> keys = redisTemplate.keys(prex);
        if(!CollectionUtils.isEmpty(keys)){
            redisTemplate.delete(keys);
        }else{
            return 0;
        }
        return keys.size();
    }

    /**
     * 该加锁方法仅针对单实例 Redis 可实现分布式加锁
     * 对于 Redis 集群则无法使用
     *
     * 支持重复，线程安全
     *
     * @param lockKey   加锁键
     * @param clientId  加锁客户端唯一标识(采用UUID)
     * @param milliseconds   锁过期时间
     */
    /*public Boolean tryLock(final String lockKey,final String clientId,final long milliseconds) {
        return redisTemplate.execute(new RedisCallback<Boolean>(){
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Jedis jedis = (Jedis) redisConnection.getNativeConnection();
                String result = jedis.set(lockKey, clientId, SetParams.setParams().nx().px(milliseconds));
//                String result = jedis.set(lockKey, clientId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, milliseconds);
                if (LOCK_SUCCESS.equals(result)) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }*/

    /**
     * 与 tryLock 相对应，用作释放锁
     *
     */
    public Boolean releaseLock(final String lockKey,final String clientId) {
        return redisTemplate.execute(new RedisCallback<Boolean>(){
            @Override
            public Boolean doInRedis(RedisConnection redisConnection) throws DataAccessException {
                Jedis jedis = (Jedis) redisConnection.getNativeConnection();
                Object result = jedis.eval(RELEASE_LOCK_SCRIPT, Collections.singletonList(lockKey),
                        Collections.singletonList(clientId));
                if (RELEASE_SUCCESS.equals(result)) {
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
        });
    }

}
