package com.kmt.massage.di.component;

import com.kmt.massage.di.PerService;
import com.kmt.massage.di.module.ServiceModule;
import com.kmt.massage.service.SyncService;

import dagger.Component;

@PerService
@Component(dependencies = ApplicationComponent.class, modules = ServiceModule.class)
public interface ServiceComponent {

    void inject(SyncService service);

}
