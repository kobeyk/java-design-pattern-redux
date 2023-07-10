package com.appleyk.store;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.reducer.IReduxReducer;

/**
 * <p>store（状态管理器）定义</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:17:55
 */
public interface IStore {
    int getState();
    void setState(int state);
    SyncAction dispatch(IReduxAction action) throws Exception;
    default IReduxReducer getReducer(){return null;}
    default void subscribe(IReduxListener listener){}
    default void removeListener(IReduxListener listener){}
}
