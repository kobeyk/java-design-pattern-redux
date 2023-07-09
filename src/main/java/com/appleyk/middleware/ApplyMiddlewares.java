package com.appleyk.middleware;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.reducer.IReduxReducer;
import com.appleyk.store.IStore;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>store增强器</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:21:10
 */
@Data
@AllArgsConstructor
public class ApplyMiddlewares implements IStore {

    private IStore delegate;

    private DispatchEnhancer[] middlewares;

    public ApplyMiddlewares(DispatchEnhancer... middlewares){
        this.middlewares = middlewares;
        int len = middlewares.length;
        for (int i = 0; i < len; i++) {
           if (i == len - 1){
               middlewares[i].next(new DefaultEnhancer());
           }else{
               middlewares[i].next(middlewares[i+1]);
           }
        }
    }

    @Override
    public int getState() {
        return this.delegate.getState();
    }

    @Override
    public void setState(int state) {
        this.delegate.setState(state);
    }

    @Override
    public SyncAction dispatch(IReduxAction action) throws Exception {
        if (this.middlewares.length == 0){
            throw  new RuntimeException("中间件不能为空！");
        }
        SyncAction syncAction = middlewares[0].enhancerDispatch(delegate, action);
        return syncAction;
    }


    @Override
    public void subscribe(IReduxListener listener) {

    }

    @Override
    public void removeListener(IReduxListener listener) {

    }
}
