package com.appleyk.reducer;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>计数器reducer，state归并操作</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:19:47
 */
@Data
@AllArgsConstructor
public class CountReducer implements IReduxReducer{
    public static final String ADD = "+";
    public static final String SUB = "-";

    public int initState;

    @Override
    public int reduce(int state, SyncAction action) throws Exception{
        String type = action.getType();
        int payload = action.getPayload();
        switch (type) {
            case ADD:
                initState += payload;
                break;
            case SUB:
                initState -= payload;
                break;
            default:
                initState = payload;
        }
        return initState;
    }

    @Override
    public int getState() {
        return initState;
    }
}
