package com.appleyk.middleware;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.store.IStore;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:21:05
 */
public class DefaultEnhancer implements DispatchEnhancer{

    @Override
    public SyncAction enhancerDispatch(IStore store, IReduxAction action) throws Exception {
        System.out.println("====默认dispatch增强器调用");
        if (action instanceof AsyncAction){
            throw new RuntimeException("不支持异步action！");
        }
        SyncAction syncAction = (SyncAction) action;
        int newState = store.getReducer().reduce(store.getState(), syncAction);
        store.setState(newState);
        return syncAction;
    }

}
