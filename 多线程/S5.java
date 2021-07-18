import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
//condition+lock
public class S5 {
    private static volatile int result;
    private static ReentrantLock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("l am a new asynchronous thread." + Thread.currentThread().getName());
                result = sum();
                lock.lock();
                condition.signal();
                lock.unlock();
            }
        };
        new Thread(runnable).start();
        lock.lock();
        try {
            condition.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.unlock();
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
