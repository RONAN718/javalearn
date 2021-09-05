package  tool;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Collections;


@Slf4j
public class RedisPool {

    private static final String LOCK_SUCCESS = "OK";
    private static final String SET_IF_NOT_EXIST = "NX";
    private static final String SET_WITH_EXPIRE_TIME = "PX";

    private static final Long RELEASE_SUCCESS = 1L;

    // 锁的过期时间
    private static int EXPIRE_TIME = 500;

    private static JedisPool pool;//jedis连接池对象

    private static int maxTotal = 20;//最大连接数

    private static int maxIdle = 10;//最大空闲连接数

    private static int minIdle = 5;//最小空闲连接数

    private static boolean testOnBorrow = true;//在取连接时测试连接的可用性

    private static boolean testOnReturn = false;//再还连接时不测试连接的可用性

    static {
        initPool();//初始化连接池
    }
    public static Jedis getJedis(){
        return pool.getResource();
    }

    public static void close(Jedis jedis){
        jedis.close();
    }

    @Autowired
    public static JedisPool getPool(String host,int port) {
        JedisPoolConfig config = new JedisPoolConfig();//连接池配置类
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        return new JedisPool(config, host, port);
    }
    private static void initPool(){
        JedisPoolConfig config = new JedisPoolConfig();//连接池配置类
        config.setMaxTotal(maxTotal);
        config.setMaxIdle(maxIdle);
        config.setMinIdle(minIdle);
        config.setTestOnBorrow(testOnBorrow);
        config.setTestOnReturn(testOnReturn);
        config.setBlockWhenExhausted(true);
        pool = new JedisPool(config, "127.0.0.1", 6379, 5000, null);
    }


    //加锁方法
    //加锁之后返回锁的持有者(锁的value使用唯一时间戳标志每个客户端,保证只有锁的持有者才可以释放锁)
    public static String lock(Jedis jedis, String key,Long waitEnd,String requestId) {
        try {
            // 1秒内数次加锁如果失败,则不断请求重新获取锁,超过1秒还没能加锁,就加锁失败(为了每个线程拥有公平的机会获取锁)
            while (System.currentTimeMillis() < waitEnd) {// 1秒类不断尝试加锁(加锁之后返回锁的持有者)
                String result = jedis.set(key, requestId, SET_IF_NOT_EXIST, SET_WITH_EXPIRE_TIME, EXPIRE_TIME);

                if (LOCK_SUCCESS.equals(result)) {
                    return requestId;
                }
            }
        } catch (Exception ex) {
            log.error("lock error", ex);
        }
        return null;
    }

    public static boolean unLock(Jedis jedis, String lockKey, String requestId) {

        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(requestId));

        if (RELEASE_SUCCESS.equals(result)) {
            return true;
        }
        return false;
    }
}
