package tool;

import redis.clients.jedis.Jedis;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

public class ThreadTask implements Runnable,Comparable<ThreadTask> {

    private String userCode;

    private Long num;

    private String key;

    private static int WAIT_TIME = 2 * 1000;

    private int priority;

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public ThreadTask() {

    }

    public ThreadTask(int priority) {
        this.priority = priority;
    }

    //当前对象和其他对象做比较，当前优先级大就返回-1，优先级小就返回1,值越小优先级越高
    public int compareTo(ThreadTask o) {
        return this.priority > o.priority ? -1 : 1;
    }

    public ThreadTask(String key, String userCode, Long num) {
        this.userCode = userCode;
        this.num = num;
        this.key = key;
    }

    private static String STOCK_KEY_PREFIX = "sku-";

    @Override
    public void run() {

        //让线程阻塞，使后续任务进入缓存队列
        System.out.println("当前系统线程:" + Thread.currentThread().getName());
        Jedis jedis = RedisPool.getJedis();
        Long waitEnd = System.currentTimeMillis() + WAIT_TIME;
        String bb = jedis.get("sku-101");
        String value = RedisPool.lock(jedis, key, waitEnd, userCode);//加锁成功,获取锁的持有者
        String lockKey = null;
        if (!StringUtils.isBlank(value) && value.equals(userCode)) {//加锁成功后查询库存
            try {
                //synchronized (key){
                lockKey = key;
                Long stock = Long.valueOf(jedis.get(STOCK_KEY_PREFIX + lockKey));
                if (stock >= num) {
                    String script = "return redis.call('incrby', KEYS[1], ARGV[1])";
                    Object eval = jedis.eval(script, new ArrayList(Collections.singleton(STOCK_KEY_PREFIX + lockKey)), new ArrayList(Collections.singleton(String.valueOf(0 - num))));
                    if (null != eval && Integer.valueOf(String.valueOf(eval)) >= 0) {
                        System.out.println("用户【" + userCode + "】秒杀商品【" + lockKey + "】成功,库存剩余:" + String.valueOf(eval));
                    }

                } else {
                    System.out.println("用户【" + userCode + "】秒杀商品【" + lockKey + "】失败,库存剩余:" + stock.toString());
                }
                Thread.sleep(100);//这里为了更真实的模拟多线程并发,这里线程获取到锁后,线程休眠100ms
                //}
            } catch (Exception e) {
                // TODO: handle exception
            } finally {
                System.out.println("用户【" + userCode + "】秒杀商品【" + lockKey + "】的线程释放锁");
                RedisPool.unLock(jedis, lockKey, userCode);//处理完扣减库存业务释放锁,把抢购这件商品的机会留给其余用户
            }

        } else {
            System.out.println("当前用户【" + userCode + "】抢购商品【" + key + "】的线程加锁失败,未抢购到,请再试");
        }
    }
}
