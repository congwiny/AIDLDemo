package com.congwiny.keepliveprocess;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteService extends Service {

    private static final String TAG = RemoteService.class.getSimpleName();
    private KeepLiveBinder binder;
    private KeepLiveServiceConnection conn;

    @Override
    public void onCreate() {
        super.onCreate();
        binder = new KeepLiveBinder();
        conn = new KeepLiveServiceConnection();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Intent intentNew = new Intent();
        intentNew.setClass(this, LocalService.class);
        bindService(intentNew, conn, BIND_IMPORTANT);

        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }

    private class KeepLiveServiceConnection implements ServiceConnection {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e(TAG, "local service onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "local service onServiceDisconnected");

            Intent intent = new Intent();
            intent.setClass(RemoteService.this, LocalService.class);
            //LocalService已经挂了，先必须先启动，后绑定
            startService(intent);
            bindService(intent, conn, BIND_IMPORTANT);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //解绑，否则会出现 leaked ServiceConnection
        unbindService(conn);
    }

    private class KeepLiveBinder extends IKeepLiveConnection.Stub {

        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }
    }
}
