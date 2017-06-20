package ru.malroy.tiomediatesttask.domain.use_case;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.malroy.tiomediatesttask.domain.PhotoCloudDataSource;
import ru.malroy.tiomediatesttask.domain.entity.PhotoResponseMeta;
import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class LoadPhotosUseCase {
    //    @NonNull
//    private final Observable.Transformer<PhotoResponseMeta, PhotoResponseMeta> backgroundTransformer = photoResponseMeta ->
//            photoResponseMeta.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    @NonNull
    private CompositeSubscription subscriptions = new CompositeSubscription();
    @NonNull
    private final Observable.Transformer<PhotoResponseMeta, PhotoResponseMeta> backgroundTransformer = new Observable.Transformer<PhotoResponseMeta, PhotoResponseMeta>() {
        @Override
        public Observable<PhotoResponseMeta> call(Observable<PhotoResponseMeta> photoResponseMetaObservable) {
            return photoResponseMetaObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
        }
    };
    @NonNull
    private PhotoCloudDataSource photoCloudDataSource;

    @Inject
    LoadPhotosUseCase(@NonNull PhotoCloudDataSource photoCloudDataSource) {
        this.photoCloudDataSource = photoCloudDataSource;
    }

    public void loadPopular(int pageNumber, @NonNull Subscriber<PhotoResponseMeta> subscriber) {
        Subscription subscription = photoCloudDataSource.fetchPopular(pageNumber)
                .compose(backgroundTransformer)
                .subscribe(subscriber);
        subscriptions.add(subscription);
    }

    public void loadToday(int pageNumber, @NonNull Subscriber<PhotoResponseMeta> subscriber) {
        Subscription subscription = photoCloudDataSource.fetchToday(pageNumber)
                .compose(backgroundTransformer)
                .subscribe(subscriber);
        subscriptions.add(subscription);
    }

    public void unsubscribe() {
        if (!subscriptions.isUnsubscribed()) {
            subscriptions.unsubscribe();
        }

        subscriptions = new CompositeSubscription();
    }

}
