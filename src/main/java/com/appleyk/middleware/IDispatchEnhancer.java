package com.appleyk.middleware;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.store.IStore;

/**
 * <p>dispatch增强器接口，定义两个方法</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:20:57
 */
public interface IDispatchEnhancer {
    /**增强dispatch*/
    SyncAction enhancerDispatch(IStore store,IReduxAction action) throws Exception;
    /**设置中间件（dispatch增强器）的next*/
    default void next(IDispatchEnhancer enhancer){}
}
