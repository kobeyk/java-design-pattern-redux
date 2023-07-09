package com.appleyk.store;

import com.appleyk.action.IReduxAction;
import com.appleyk.listener.IReduxListener;

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
    IReduxAction dispatch(IReduxAction action) throws Exception;
    void subscribe(IReduxListener listener);
    void removeListener(IReduxListener listener);
}
