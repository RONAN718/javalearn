package tool;

import java.util.concurrent.*;

public class main {
    public static void main(String[] args) {
        //使用固定线程数为4 的线程池处理并发请求
        ExecutorService pool1 = new ThreadPoolExecutor(4, 10, 1000, TimeUnit.MILLISECONDS, new PriorityBlockingQueue<Runnable>(), Executors.defaultThreadFactory());
        //10个人抢购编码为101的共计5个商品
        for(int i=0;i<50;i++) {//模拟100个人同时抢购5件商品
            String userCode = 10*(i+1)+"";
            //System.out.println("用户"+(i+1)+"的编码:"+userCode);
            pool1.execute(new ThreadTask("101",userCode,1L));
        }

    }
}
