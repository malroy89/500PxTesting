package ru.malroy.tiomediatesttask.presentation.photos.tabs;

import android.support.annotation.NonNull;

import java.util.List;

import ru.malroy.tiomediatesttask.domain.entity.Photo;
import ru.malroy.tiomediatesttask.domain.entity.PhotoResponseMeta;
import ru.malroy.tiomediatesttask.domain.use_case.LoadPhotosUseCase;
import ru.malroy.tiomediatesttask.presentation.mvp.BasePresenter;
import ru.malroy.tiomediatesttask.presentation.mvp.LoadingView;
import rx.Subscriber;

/**
 * Created by Dzmitry Lamaka on 04.12.2015.
 */
public abstract class BasePhotoPresenter extends BasePresenter<LoadingView<List<Photo>>> {
    @NonNull
    protected LoadPhotosUseCase loadPopularPhotosUseCase;
    private int nextPageNumber;

    public BasePhotoPresenter(@NonNull LoadPhotosUseCase loadPopularPhotosUseCase) {
        this.loadPopularPhotosUseCase = loadPopularPhotosUseCase;
    }

    void loadFirstPage() {
        callShowLoading();
        callLoad(1);
    }

    void loadNextPage() {
        callLoad(nextPageNumber);
    }

    private void callLoad(int pageNumber) {
        load(pageNumber);
    }

    protected abstract void load(int pageNumber);

    @Override
    public void destroy() {
        loadPopularPhotosUseCase.unsubscribe();
    }

    private void callShowLoading() {
        LoadingView<List<Photo>> view = getView();
        if (view != null) {
            view.showLoading();
        }
    }

    private void callHideLoading() {
        LoadingView<List<Photo>> view = getView();
        if (view != null) {
            view.hideLoading();
        }
    }

    private void callSetData(@NonNull List<Photo> photos) {
        LoadingView<List<Photo>> view = getView();
        if (view != null) {
            view.setData(photos);
        }
    }

    private void callShowContent() {
        LoadingView<List<Photo>> view = getView();
        if (view != null) {
           view.showContent();
        }
    }

    private void callShowError(@NonNull String errorMessage) {
        LoadingView<List<Photo>> view = getView();
        if (view != null) {
            view.showError(errorMessage);
        }
    }

    public class PhotoSubscriber extends Subscriber<PhotoResponseMeta> {
        @Override
        public void onCompleted() {
            callHideLoading();
            callShowContent();

            if (!isUnsubscribed()) {
                unsubscribe();
            }
        }

        @Override
        public void onError(@NonNull Throwable e) {
            callHideLoading();
            callShowError("Error: " + e.getMessage());
        }

        @Override
        public void onNext(@NonNull PhotoResponseMeta photoResponseMeta) {
            int loadedPage = photoResponseMeta.getCurrentPage();
            if (loadedPage != photoResponseMeta.getTotalPages()) {
                nextPageNumber++;
            }
            callSetData(photoResponseMeta.getPhotos());
        }
    }
}
