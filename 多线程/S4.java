//使用wait+notify
public class S4 {
    private static volatile int result;
    private static Object lock = new Object();

    public static void main(String[] args) throws InterruptedException {

        long start = System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                System.out.println("l am a new asynchronous thread." + Thread.currentThread().getName());
                result = sum();
            }
        };
        new Thread(runnable).start();
        showResult(start);
    }

    private static void showResult(long start) throws InterruptedException {
        synchronized (lock) {
            lock.wait();
        }
        System.out.println("异步计算结果为：" + result);
        System.out.println("使用时间：" + (System.currentTimeMillis() - start) + " ms");
    }

    private static int sum() {
        synchronized (lock) {
            lock.notify();
        }
        return fibo(36);
    }

    private static int fibo(int a) {
        if (a < 2)
            return 1;
        return fibo(a - 1) + fibo(a - 2);
    }
}