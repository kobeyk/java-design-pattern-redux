package com.appleyk.middleware;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.AsyncCallBack;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.store.IStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:21:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThunkMiddleware implements DispatchEnhancer{
    ExecutorService executor = Executors.newFixedThreadPool(10);
    private DispatchEnhancer delegate;

    public ThunkMiddleware(DispatchEnhancer enhancer){
        this.delegate = enhancer;
    }

    @Override
    public SyncAction enhancerDispatch(IStore store, IReduxAction action) throws Exception {
        System.out.println("thunk中间件开始执行...");
        long start = System.currentTimeMillis();
        if (action instanceof AsyncAction){
            Future<Integer> future = dispatchAsync((AsyncAction) action);
            Integer newState = future.get();
            long end = System.currentTimeMillis();
            executor.shutdownNow();
            System.out.println("thunk中间件中异步action花费："+(end-start)+"ms");
            return delegate.enhancerDispatch(store,new SyncAction("",newState));
        }else{
            return delegate.enhancerDispatch(store, action);
        }
    }

    public Future<Integer> dispatchAsync(AsyncAction action ){
        return executor.submit(() -> action.execute());
    }

    @Override
    public void next(DispatchEnhancer enhancer) {
        this.delegate = enhancer;
    }
}
