package com.example.david.myapplication;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

public class App extends Application {
    private static Context instance;

    public static Context getInstance(){
        return instance;
    }

    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
        MultiDex.install(this) ;
    }
}
