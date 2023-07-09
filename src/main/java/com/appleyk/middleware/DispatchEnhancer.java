package com.appleyk.middleware;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.reducer.IReduxReducer;
import com.appleyk.store.IStore;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:20:57
 */
public interface DispatchEnhancer {
    SyncAction enhancerDispatch(IStore store,IReduxAction action) throws Exception;
    default void next(DispatchEnhancer enhancer){}
}
