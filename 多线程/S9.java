import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;
//cyclicBarrier
public class S9 {
    private static volatile int result;

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CyclicBarrier cyclicBarrier = new CyclicBarrier(2, new Runnable() {
            @Override
            public void run() {
                System.out.println("sub thread group finished!");
            }
        });
        new Thread(new readNum(1, cyclicBarrier)).start();

        try {
            cyclicBarrier.await();
            showResult(start);
        } catch (BrokenBarrierException e) {
            e.printStackTrace();
        }


    }

    static class readNum implements Runnable {
        private int id;
        private CyclicBarrier cyc;

        public readNum(int id, CyclicBarrier cyc) {
            this.id = id;
            this.cyc = cyc;
        }

        @Override
        public void run() {
            synchronized (this) {
                System.out.println("id:" + id + "," + Thread.currentThread().getName());
                try {
                    result = sum();
                    cyc.await();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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