package com.kmt.massage.di.module;

import android.app.Service;

import dagger.Module;

@SuppressWarnings({"unused", "RedundantSuppression", "FieldCanBeLocal"})
@Module
public class ServiceModule {

    private final Service mService;

    public ServiceModule(Service service) {
        mService = service;
    }
}
