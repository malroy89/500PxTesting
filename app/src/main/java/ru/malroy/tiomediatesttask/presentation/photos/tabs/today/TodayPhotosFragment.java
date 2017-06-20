package ru.malroy.tiomediatesttask.presentation.photos.tabs.today;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import ru.malroy.tiomediatesttask.presentation.photos.tabs.BasePhotosFragment;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class TodayPhotosFragment extends BasePhotosFragment {
    @Nullable
    private TodayPhotosPresenter presenter;

    @NonNull
    public static TodayPhotosFragment newInstance() {
        return new TodayPhotosFragment();
    }

    @NonNull
    @Override
    protected TodayPhotosPresenter getPresenter() {
        if (presenter == null) {
            presenter = getPhotosComponent().todayPhotosPresenter();
        }
        return presenter;
    }
}
