package com.congwiny.dongnao;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.dn.remote.IMyAidlInterface;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //显示指定Action和Package（被调用应用的包名）。
        //否则会出现问题：service intent must be explicit
        Intent intent = new Intent("com.dn.remote.service");
        intent.setPackage("com.congwiny.remoteservice");
        bindService(intent, new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                //由远程IBinder创建的Proxy
                IMyAidlInterface asInterface = IMyAidlInterface.Stub.asInterface(service);
                Log.e(TAG,"asInterface.."+asInterface);//IMyAidlInterface$Stub$Proxy@6c459c
                try {
                    asInterface.testBinder();
                    String binderString = asInterface.getBinderString();
                    Log.e(TAG, "binder string = " + binderString);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        }, BIND_AUTO_CREATE);
    }
}
