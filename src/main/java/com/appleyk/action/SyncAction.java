package com.appleyk.action;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>同步Action（对象）</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:19:36
 */
@Data
@AllArgsConstructor
public class SyncAction implements IReduxAction {
    private String type;
    private Integer payload;

    @Override
    public String toString() {
        return "SyncAction{" +
                "type='" + type + '\'' +
                ", payload=" + payload +
                '}';
    }
}
