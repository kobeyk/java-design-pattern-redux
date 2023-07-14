package com.appleyk;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.SyncAction;
import com.appleyk.extend.LoggerAMiddleware;
import com.appleyk.middleware.ApplyMiddlewares;
import com.appleyk.middleware.DefaultEnhancer;
import com.appleyk.extend.LoggerBMiddleware;
import com.appleyk.extend.ThunkMiddleware;
import com.appleyk.reducer.CountReducer;
import com.appleyk.store.ReduxStore;

/**
 * <p>Java版redux功能测试</p>
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
//        testSyncAction();
//        testAsyncAction();
//        testSyncActionPlus();
        testAsyncActionPlus();
    }

    /**测试同步Action（不带中间件，v1.0版本测试遗留）*/
    public static void testSyncAction() throws Exception{
        ReduxStore store = new ReduxStore(reducer);
        store.subscribe(state -> {
            System.out.println("dispatch触发了监听器调用，state值发生了变更");
        });
        SyncAction action = store.dispatch(new SyncAction(CountReducer.ADD, 20));
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }

    /**测试异步Action（不带中间件，v1.0版本测试遗留）*/
    public static void testAsyncAction() throws Exception{
        ReduxStore store = new ReduxStore(reducer);
        store.dispatchAsync(new AsyncAction(), action -> {
            System.out.println("异步Action处理完毕，state = "+store.getState());
            System.out.println(action);
        });
    }

    /**测试同步Action（带中间件，v2.0版本测试遗留）*/
    public static void testSyncActionPlus() throws Exception{
        LoggerBMiddleware loggerBMiddleware = new LoggerBMiddleware(new DefaultEnhancer());
        ApplyMiddlewares applyMiddlewares = new ApplyMiddlewares(loggerBMiddleware);
        ReduxStore store = new ReduxStore(reducer,applyMiddlewares);
        SyncAction action = store.dispatch(new SyncAction(CountReducer.ADD, 20));
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }

    /**测试异步Action（带中间件，v2.0版本测试遗留）*/
    public static void testAsyncActionPlus() throws Exception{
        LoggerBMiddleware loggerBMiddleware = new LoggerBMiddleware();
        ThunkMiddleware thunkMiddleware = new ThunkMiddleware();
        LoggerAMiddleware loggerAMiddleware = new LoggerAMiddleware();
        ApplyMiddlewares applyMiddlewares = new ApplyMiddlewares(loggerBMiddleware,thunkMiddleware,loggerAMiddleware);
        ReduxStore store = new ReduxStore(reducer,applyMiddlewares);
        SyncAction action = store.dispatch(new AsyncAction());
        System.out.println("state = "+store.getState());
        System.out.println(action);
    }
}
