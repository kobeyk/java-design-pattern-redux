package com.appleyk.listener;

/**
 * <p>状态变更监听器</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:19:40
 */
public interface IReduxListener {
    void onChangeState(int state);
}
