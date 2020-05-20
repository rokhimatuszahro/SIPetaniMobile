package com.mobile.sipetani.Until;

import android.app.Application;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {
    private static final String TAG = AppController.class.getSimpleName();
<<<<<<< HEAD
    private static AppController instance ;
=======
    private static AppController instance  ;
>>>>>>> f004cc61df84e1ed22d0471bb0aaa837f4dba325
    RequestQueue mRequestQueue;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }

<<<<<<< HEAD
    public static synchronized AppController getInstance() {return instance; }

    private RequestQueue getRequestQueue()
    {
        if (mRequestQueue == null)
=======
    public static synchronized AppController getInstance()
    {
        return instance;
    }

    private RequestQueue getRequestQueue()
    {
        if(mRequestQueue == null)
>>>>>>> f004cc61df84e1ed22d0471bb0aaa837f4dba325
        {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue (Request<T> req)
    {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req, String tag)
    {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public void cancelAllRequest(Object req)
    {
        if (mRequestQueue != null)
        {
            mRequestQueue.cancelAll(req);
        }
    }
}
