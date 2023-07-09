package com.appleyk.middleware;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.store.IStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:20:59
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoggerMiddleware implements DispatchEnhancer{

    private DispatchEnhancer delegate;

    @Override
    public SyncAction enhancerDispatch(IStore store,IReduxAction action) throws Exception{
        System.out.println("===日志中间件执行前");
        SyncAction syncAction = delegate.enhancerDispatch(store,action);
        System.out.println("===日志中间件执行后");
        return syncAction;
    }

    @Override
    public void next(DispatchEnhancer enhancer) {
        this.delegate = enhancer;
    }
}
