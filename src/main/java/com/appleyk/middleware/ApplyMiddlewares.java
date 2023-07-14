package com.appleyk.middleware;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.store.IStore;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>store增强器，主要利用责任链插件（中间件）思想来增强dispatch</p>
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

    /**store代理对象*/
    private IStore delegate;

    /**dispatch增强器中间件数组*/
    private IDispatchEnhancer[] middlewares;

    public ApplyMiddlewares(IDispatchEnhancer... middlewares){
        this.middlewares = middlewares;
        int len = middlewares.length;
        for (int i = 0; i < len; i++) {
           if (i == len - 1){
               /**尾链也就是最后一个中间件设置其next为默认的dispatch增强器*/
               middlewares[i].next(new DefaultEnhancer());
           }else{
               /**否则设置当前中间件的next为下一个中间件，用链条串联每一个中间件*/
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
        /**链式调用第一个中间件即可*/
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
