package ru.malroy.tiomediatesttask.presentation.di;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import ru.malroy.tiomediatesttask.data.PhotoCloudDataSourceImpl;
import ru.malroy.tiomediatesttask.domain.PhotoCloudDataSource;
import ru.malroy.tiomediatesttask.TiomediaApplication;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */

@Module
public class AppModule {
    @NonNull
    private Context context;

    public AppModule(@NonNull TiomediaApplication context) {
        this.context = context;
    }

    @AppScope
    @Provides
    @NonNull
    Context getAppContext() {
        return context;
    }

    @AppScope
    @Provides
    @NonNull
    PhotoCloudDataSource providePhotoCloudDataSource(@NonNull PhotoCloudDataSourceImpl placesCloudDataSource) {
        return placesCloudDataSource;
    }
}
