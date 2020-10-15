package com.kmt.massage.di.component;

import com.kmt.massage.di.module.ApplicationTestModule;

import javax.inject.Singleton;

import dagger.Component;


@SuppressWarnings({"unused", "RedundantSuppression"})
@Singleton
@Component(modules = ApplicationTestModule.class)
public interface TestComponent extends ApplicationComponent {
}
