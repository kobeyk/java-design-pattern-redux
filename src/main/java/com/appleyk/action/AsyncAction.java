package com.appleyk.action;

/**
 * <p>异步Action，模拟一个IO查询</p>
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
            /**假设查询数据库，耗时1秒*/
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        /**将最终查到的值返出去*/
        return 100;
    }

}
