package wq.study.demo.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * 自定义的线程池创建工具
 * 使用示例:
 * private static final LongAdder adder = new LongAdder();
 * private static final ExecutorService executor = CustomThreadPool.getThreadPool(8,r->{
 *     adder.increment();
 *     return new Thread(r,"Travel-Cal"+adder.intValue());
 * });
 * executor.execute(()->xxx);
 */
public class CustomThreadPool {
    private static final Logger logger = LoggerFactory.getLogger(CustomThreadPool.class);

    private static ExecutorService getSingleThreadPool(ThreadFactory threadFactory) {
        return new ThreadPoolExecutor(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>(), threadFactory, new customRejectionHandler());
    }

    public static ExecutorService getThreadPool(int threadNum,ThreadFactory threadFactory){
        int maxThreadNum = threadNum*2;
        return new ThreadPoolExecutor(threadNum,maxThreadNum,60,TimeUnit.SECONDS,new LinkedBlockingQueue<>(128),threadFactory,new customRejectionHandler());
    }

    public static class customRejectionHandler extends ThreadPoolExecutor.AbortPolicy {
        @Override
        public void rejectedExecution(Runnable r, ThreadPoolExecutor e) {
            logger.error("thread pool has reached limit num!");
            super.rejectedExecution(r, e);
        }
    }
}
