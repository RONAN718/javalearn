import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CompletableFuture;

//CompletableFuture
public class S10 {

    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(S10::sum);
        cf.thenAccept((result) -> {
            try {
                showResult(start,result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        cf.exceptionally((e) -> {
            e.printStackTrace();
            return null;
        });
        Thread.sleep(200);
    }

    private static void showResult(long start,Integer result) throws InterruptedException {
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