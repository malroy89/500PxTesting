package ru.malroy.tiomediatesttask.presentation.photos.tabs.today;

import android.support.annotation.NonNull;

import javax.inject.Inject;

import ru.malroy.tiomediatesttask.domain.use_case.LoadPhotosUseCase;
import ru.malroy.tiomediatesttask.presentation.photos.tabs.BasePhotoPresenter;

/**
 * Created by Dzmitry Lamaka on 04.12.2015.
 */
public class TodayPhotosPresenter extends BasePhotoPresenter {

    @Inject
    TodayPhotosPresenter(@NonNull LoadPhotosUseCase loadPopularPhotosUseCase) {
        super(loadPopularPhotosUseCase);
    }

    @Override
    protected void load(int pageNumber) {
        loadPopularPhotosUseCase.loadToday(pageNumber, new PhotoSubscriber());
    }
}
