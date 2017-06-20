package ru.malroy.tiomediatesttask.presentation.mvp;

import android.support.annotation.NonNull;

/**
 * Created by Dzmitry Lamaka on 02.12.2015.
 */
interface Presenter<V extends MvpView> {
    void attachView(@NonNull V view);
    void detachView();
    void destroy();
}
