package ru.malroy.tiomediatesttask.presentation.photos.tabs.popular;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.malroy.tiomediatesttask.presentation.photos.tabs.BasePhotosFragment;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class PopularPhotosFragment extends BasePhotosFragment {
    @Nullable
    private PopularPhotosPresenter presenter;

    @NonNull
    public static PopularPhotosFragment newInstance() {
        return new PopularPhotosFragment();
    }

    @NonNull
    @Override
    protected PopularPhotosPresenter getPresenter() {
        if (presenter == null) {
            presenter = getPhotosComponent().popularPhotosPresenter();
        }
        return presenter;
    }
}
