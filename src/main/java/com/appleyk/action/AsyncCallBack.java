package com.appleyk.action;

/**
 * <p>异步Action处理完回调函数</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:20:35
 */
@FunctionalInterface
public interface AsyncCallBack {
    void onComplete(SyncAction action);
}
