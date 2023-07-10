package com.appleyk.store;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.AsyncCallBack;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.middleware.ApplyMiddlewares;
import com.appleyk.reducer.IReduxReducer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>基于redux源码思路模拟的一个状态管理容器默认实现</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:17:54
 */
@Data
@AllArgsConstructor
public class ReduxStore implements IStore{

    /**当前管理的state*/
    private int currentState;
    /**当前处理state的reducer，redux源码中是一个map集合，reducer和state是成对的，简单起见这里不搞map那一套*/
    private IReduxReducer currentReducer;
    /**state监听器列表*/
    private List<IReduxListener> listeners = new ArrayList<>();

    /**store增强器（利用中间件实现dispatch的增强）*/
    private ApplyMiddlewares enhancerStore;

    public ReduxStore(IReduxReducer reducer){
        this.currentReducer = reducer;
        this.currentState = reducer.getState();
    }

    public ReduxStore(IReduxReducer reducer,ApplyMiddlewares store){
        this.currentReducer = reducer;
        enhancerStore = store;
        /**这个很关键，要把当前的store对象传给ApplyMiddlewares，以便链式调用中间件的时候可以拿到store的一些成员变量*/
        enhancerStore.setDelegate(this);
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Override
    public SyncAction dispatch(IReduxAction action) throws Exception{
        /**看redux源码知道，如果store增强器不空且不是函数的话，最终store的功能将会被增强store所代替*/
        if (enhancerStore != null){
            return enhancerStore.dispatch(action);
        }
        if (action instanceof AsyncAction){
            throw new RuntimeException("store无法处理异步Action！");
        }
        SyncAction syncAction = (SyncAction) action;
        /**调用合适的reducer操作state并拿到操作后的state*/
        int state = currentReducer.reduce(currentState, syncAction);
        /**更新store中的state*/
        this.currentState = state;
        /**回调所有state监听器，告诉他们store中的state发生了变更*/
        notifyListeners();
        return syncAction;
    }

    /**v1.0版本临时解决store无法处理action的问题而增加的一个方法，主要用来处理AsyncAction，v2.0中标识为已废弃*/
    @Deprecated
    public void dispatchAsync(AsyncAction asyncAction, AsyncCallBack callBack) throws Exception{
        new Thread(()->{
            int result = asyncAction.execute();
            try {
                SyncAction syncAction = dispatch(new SyncAction("",result));
                callBack.onComplete(syncAction);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void notifyListeners(){
        for (IReduxListener listener : listeners) {
            listener.onChangeState(currentState);
        }
    }

    @Override
    public void subscribe(IReduxListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(IReduxListener listener) {
        this.listeners.remove(listener);
    }

    @Override
    public IReduxReducer getReducer() {
        return currentReducer;
    }

    @Override
    public void setState(int state) {
        this.currentState = state;
    }
}
