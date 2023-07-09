package com.appleyk.store;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.reducer.IReduxReducer;

/**
 * <p></p>
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
    void subscribe(IReduxListener listener);
    void removeListener(IReduxListener listener);
}
