package ru.malroy.tiomediatesttask.presentation.mvp;

import android.support.annotation.Nullable;

/**
 * Created by Dzmitry Lamaka on 02.12.2015.
 */
public interface LoadingView<E> extends MvpView {
    void showLoading();
    void hideLoading();
    void showError(@Nullable String errorMessage);
    void showContent();
    void setData(@Nullable E data);
}
