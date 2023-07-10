package com.appleyk.extend;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.middleware.IDispatchEnhancer;
import com.appleyk.store.IStore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * <p>日志中间件A</p>
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
public class LoggerAMiddleware implements IDispatchEnhancer {

    /**持有一个代理对象，间接的增强了DispatchEnhancer的能力（代理+装饰器）*/
    private IDispatchEnhancer delegate;

    @Override
    public SyncAction enhancerDispatch(IStore store,IReduxAction action) throws Exception{
        System.out.println("===>日志中间件A执行前");
        /**委托给delegate去执行（链式调用）*/
        SyncAction syncAction = delegate.enhancerDispatch(store,action);
        System.out.println("===>日志中间件A执行后");
        return syncAction;
    }

    @Override
    public void next(IDispatchEnhancer enhancer) {
        this.delegate = enhancer;
    }
}
