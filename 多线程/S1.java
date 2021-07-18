import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class S1 {
    //Callable解决 单线程
    public static void main(String[] args) {

        long start=System.currentTimeMillis();
        Callable<Integer> task=new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                System.out.println( "l am a new asynchronous thread."+Thread.currentThread().getName());
                return sum();
            }
        };
        FutureTask<Integer> futureTask=new FutureTask<>(task);
        new Thread(futureTask).start();
        int result = 0;
        try {
            result=futureTask.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        System.out.println("异步计算结果为："+result);
        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}