package com.appleyk;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.AsyncCallBack;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.middleware.ApplyMiddlewares;
import com.appleyk.middleware.DefaultEnhancer;
import com.appleyk.middleware.LoggerMiddleware;
import com.appleyk.middleware.ThunkMiddleware;
import com.appleyk.reducer.CountReducer;
import com.appleyk.store.ReduxStore;

import javax.swing.*;

/**
 * <p>测试</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:20:05
 */
public class PageClient {

    private static CountReducer reducer = new CountReducer(1);

    public static void main(String[] args) throws Exception{
        testAsyncActionPlus();
    }

    public static void testSyncAction() throws Exception{
        ReduxStore store = new ReduxStore(reducer);
        store.subscribe(state -> {
            System.out.println("dispatch触发了监听器调用，state值发生了变更");
        });
        SyncAction action = store.dispatch(new SyncAction(CountReducer.ADD, 20));
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }

    public static void testAsyncAction() throws Exception{
        ReduxStore store = new ReduxStore(reducer);
        store.dispatchAsync(new AsyncAction(), action -> {
            System.out.println("异步Action处理完毕，state = "+store.getState());
            System.out.println(action);
        });
    }

    public static void testSyncActionPlus() throws Exception{
        LoggerMiddleware loggerMiddleware = new LoggerMiddleware(new DefaultEnhancer());
        ApplyMiddlewares applyMiddlewares = new ApplyMiddlewares(loggerMiddleware);
        ReduxStore store = new ReduxStore(reducer,applyMiddlewares);
        SyncAction action = store.dispatch(new SyncAction(CountReducer.ADD, 20));
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }

    public static void testAsyncActionPlus() throws Exception{
        LoggerMiddleware loggerMiddleware = new LoggerMiddleware();
        ThunkMiddleware thunkMiddleware = new ThunkMiddleware();
        ApplyMiddlewares applyMiddlewares = new ApplyMiddlewares(loggerMiddleware,thunkMiddleware);
        ReduxStore store = new ReduxStore(reducer,applyMiddlewares);
        SyncAction action = store.dispatch(new AsyncAction());
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }
}
