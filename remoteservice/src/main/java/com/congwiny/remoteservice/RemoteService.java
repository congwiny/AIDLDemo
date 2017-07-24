package com.congwiny.remoteservice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import com.dn.remote.IMyAidlInterface;

/**
 * Created by congwiny on 2017/7/22.
 */

public class RemoteService extends Service{

    private String TAG = RemoteService.class.getSimpleName();

    private MyBinderTestStub binder;
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new MyBinderTestStub();
    }

    private class MyBinderTestStub extends IMyAidlInterface.Stub{

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public void testBinder() throws RemoteException {
            Log.e(TAG,"test binder execute...");

        }

        @Override
        public String getBinderString() throws RemoteException {
            return "remote binder String text";
        }
    }
}
