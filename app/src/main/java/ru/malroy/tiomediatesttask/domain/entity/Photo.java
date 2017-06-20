package ru.malroy.tiomediatesttask.domain.entity;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class Photo {
    @Nullable
    private String name;
    @Nullable
    private String description;
    @Nullable
    private String imageUrl;
    @Nullable
    private String authorName;
    @Nullable
    private String countryName;
    private double remoteId;

    public double getRemoteId() {
        return remoteId;
    }

    @NonNull
    public Photo setRemoteId(double remoteId) {
        this.remoteId = remoteId;
        return this;
    }

    @Nullable
    public String getName() {
        return name;
    }

    @NonNull
    public Photo setName(@Nullable String name) {
        this.name = name;
        return this;
    }

    @Nullable
    public String getDescription() {
        return description;
    }

    @NonNull
    public Photo setDescription(@Nullable String description) {
        this.description = description;
        return this;
    }

    @Nullable
    public String getImageUrl() {
        return imageUrl;
    }

    @NonNull
    public Photo setImageUrl(@Nullable String imageUrl) {
        this.imageUrl = imageUrl;
        return this;
    }

    @Nullable
    public String getAuthorName() {
        return authorName;
    }

    @NonNull
    public Photo setAuthorName(@Nullable String authorName) {
        this.authorName = authorName;
        return this;
    }

    @Nullable
    public String getCountryName() {
        return countryName;
    }

    @NonNull
    public Photo setCountryName(@Nullable String countryName) {
        this.countryName = countryName;
        return this;
    }
}
