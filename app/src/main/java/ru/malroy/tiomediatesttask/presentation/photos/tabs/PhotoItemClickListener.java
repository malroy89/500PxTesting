package ru.malroy.tiomediatesttask.presentation.photos.tabs;

import android.support.annotation.NonNull;

import java.util.List;

import ru.malroy.tiomediatesttask.domain.entity.Photo;

/**
 * Created by Dzmitry Lamaka on 04.12.2015.
 */
interface PhotoItemClickListener {
    void click(int clickedPosition, @NonNull List<Photo> photos);
}
