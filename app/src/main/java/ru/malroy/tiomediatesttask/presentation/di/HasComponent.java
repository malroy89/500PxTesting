package ru.malroy.tiomediatesttask.presentation.di;

import android.support.annotation.NonNull;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public interface HasComponent<C> {
    @NonNull
    C getComponent();
}
