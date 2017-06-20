package ru.malroy.tiomediatesttask.presentation.mvp;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.lang.ref.WeakReference;

/**
 * Created by Dzmitry Lamaka on 02.12.2015.
 */
public abstract class BasePresenter<V extends MvpView> implements Presenter<V> {
    @Nullable
    private WeakReference<V> weakView;

    @Override
    public void attachView(@NonNull V view) {
        weakView = new WeakReference<>(view);
    }

    @Nullable
    public V getView() {
        return weakView != null ? weakView.get() : null;
    }

    @Override
    public void detachView() {
        if (weakView != null && weakView.get() != null) {
            weakView.clear();
            weakView = null;
        }
    }
}
