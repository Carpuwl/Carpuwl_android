package com.dreamteam.hackwaterloo.utils.server;

import android.annotation.TargetApi;
import android.os.AsyncTask;
import android.os.Build;

public abstract class BaseTask<P, R, E> extends AsyncTask<P, R, E> {
    
    protected OnPostExecuteListener<E> onPostExecuteListener;
    
    public void setOnPostExecuteListener( OnPostExecuteListener<E> listener ){
        onPostExecuteListener = listener;
    }    
    
    public interface OnPostExecuteListener<T> {
        public void onFinish( T returnItem );
    }
    
    @TargetApi(Build.VERSION_CODES.HONEYCOMB) // API 11
    public void executeParallel( P... params ) {
        
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
            executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, params);
        else
            execute(params);
    }
    
}
