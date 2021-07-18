import java.util.concurrent.*;

public class S2 {
    //newSingleThreadExecutor实现+异步
    public static void main(String[] args) {

        long start=System.currentTimeMillis();
        ExecutorService executors=Executors.newSingleThreadExecutor();

        int result=0;
        Future<Integer> futureTask=executors.submit(
                new Callable<Integer>() {
                    @Override
                    public Integer call() throws Exception {
                       return sum();
                    }
                }

        );
        try {
            System.out.println(Thread.currentThread().getName());
            result=futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
        executors.shutdownNow();
    }

    private static int sum() {
        return fibo(45);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}