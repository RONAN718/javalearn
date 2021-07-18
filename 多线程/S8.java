import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

//countDownLatch
public class S8 {
    private static volatile int result;

    public static void main(String[] args) throws InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(1);
        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("l am a new asynchronous thread." + Thread.currentThread().getName());
                result = sum();
                countDownLatch.countDown();

            }
        };
        new Thread(runnable).start();
        countDownLatch.await();
        showResult(start);

    }

    private static void showResult(long start) throws InterruptedException {
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}