package com.appleyk.reducer;

import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;

/**
 * <p></p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:19:48
 */
public interface IReduxReducer {
    int reduce(int state, SyncAction action) throws Exception;
    int getState();
}
