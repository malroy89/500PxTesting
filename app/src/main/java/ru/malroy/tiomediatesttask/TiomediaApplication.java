package ru.malroy.tiomediatesttask;

import android.app.Application;
import android.support.annotation.Nullable;

import ru.malroy.tiomediatesttask.presentation.di.AppComponent;
import ru.malroy.tiomediatesttask.presentation.di.AppModule;
import ru.malroy.tiomediatesttask.presentation.di.DaggerAppComponent;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class TiomediaApplication extends Application {
    @Nullable
    private static AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        appComponent = DaggerAppComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }

    @Nullable
    public static AppComponent getAppComponent() {
        return appComponent;
    }
}
