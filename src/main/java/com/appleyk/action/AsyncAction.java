package com.appleyk.action;

/**
 * <p>异步Action</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:19:38
 */
public class AsyncAction implements IReduxAction {
    public int execute(){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return 100;
    }

}
