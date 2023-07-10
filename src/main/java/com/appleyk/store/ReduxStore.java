package com.appleyk.store;

import com.appleyk.action.AsyncAction;
import com.appleyk.action.AsyncCallBack;
import com.appleyk.action.IReduxAction;
import com.appleyk.action.SyncAction;
import com.appleyk.listener.IReduxListener;
import com.appleyk.reducer.IReduxReducer;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>状态管理容器</p>
 *
 * @author Appleyk
 * @version v1.0
 * @blob https://blog.csdn.net/appleyk
 * @github https://github.com/kobeyk
 * @date created on 2023/7/9:17:54
 */
@Data
@AllArgsConstructor
public class ReduxStore implements IStore{

    private int currentState;
    private IReduxReducer currentReducer;
    private List<IReduxListener> listeners = new ArrayList<>();

    public ReduxStore(IReduxReducer reducer){
        this.currentReducer = reducer;
        this.currentState = reducer.getState();
    }

    @Override
    public int getState() {
        return currentState;
    }

    @Override
    public SyncAction dispatch(IReduxAction action) throws Exception{
        if (action instanceof AsyncAction){
            throw new RuntimeException("store无法处理异步Action！");
        }
        SyncAction syncAction = (SyncAction) action;
        int state = currentReducer.reduce(currentState, syncAction);
        this.currentState = state;
        notifyListeners();
        return syncAction;
    }
    public void dispatchAsync(AsyncAction asyncAction, AsyncCallBack callBack) throws Exception{
        new Thread(()->{
            int result = asyncAction.execute();
            try {
                SyncAction syncAction = dispatch(new SyncAction("",result));
                callBack.onComplete(syncAction);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    private void notifyListeners(){
        for (IReduxListener listener : listeners) {
            listener.onChangeState(currentState);
        }
    }

    @Override
    public void subscribe(IReduxListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void removeListener(IReduxListener listener) {
        this.listeners.remove(listener);
    }
}
