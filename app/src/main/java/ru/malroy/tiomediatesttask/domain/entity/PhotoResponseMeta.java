package ru.malroy.tiomediatesttask.domain.entity;

import android.support.annotation.NonNull;

import java.util.Collections;
import java.util.List;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class PhotoResponseMeta {
    @NonNull
    private List<Photo> photos = Collections.emptyList();
    private int currentPage;
    private int totalPages;

    @NonNull
    public List<Photo> getPhotos() {
        return photos;
    }

    @NonNull
    public PhotoResponseMeta setPhotos(@NonNull List<Photo> photos) {
        this.photos = photos;
        return this;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    @NonNull
    public PhotoResponseMeta setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
        return this;
    }

    public int getTotalPages() {
        return totalPages;
    }

    @NonNull
    public PhotoResponseMeta setTotalPages(int totalPages) {
        this.totalPages = totalPages;
        return this;
    }
}
