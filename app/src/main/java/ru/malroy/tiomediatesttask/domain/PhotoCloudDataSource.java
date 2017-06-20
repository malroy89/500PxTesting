package ru.malroy.tiomediatesttask.domain;

import android.support.annotation.NonNull;

import ru.malroy.tiomediatesttask.domain.entity.PhotoResponseMeta;
import rx.Observable;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public interface PhotoCloudDataSource {
    @NonNull
    Observable<PhotoResponseMeta> fetchPopular(int pageNumber);
    @NonNull
    Observable<PhotoResponseMeta> fetchToday(int pageNumber);
}
