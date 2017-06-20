package ru.malroy.tiomediatesttask.data;

import android.support.annotation.NonNull;

import java.util.List;

import javax.inject.Inject;

import ru.malroy.tiomediatesttask.BuildConfig;
import ru.malroy.tiomediatesttask.data.rest.RestService;
import ru.malroy.tiomediatesttask.data.rest.entity.PhotoResponse;
import ru.malroy.tiomediatesttask.data.rest.entity.RestPhoto;
import ru.malroy.tiomediatesttask.data.rest.entity.RestUser;
import ru.malroy.tiomediatesttask.domain.PhotoCloudDataSource;
import ru.malroy.tiomediatesttask.domain.entity.Photo;
import ru.malroy.tiomediatesttask.domain.entity.PhotoResponseMeta;
import rx.Observable;
import rx.functions.Func1;
import rx.functions.Func2;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public class PhotoCloudDataSourceImpl implements PhotoCloudDataSource {
    @NonNull
    private RestService restService;

    @Inject
    PhotoCloudDataSourceImpl(@NonNull RestService restService) {
        this.restService = restService;
    }

    @NonNull
    @Override
    public Observable<PhotoResponseMeta> fetchPopular(int pageNumber) {
        return fetchPhotos("popular", pageNumber);
    }

    @NonNull
    @Override
    public Observable<PhotoResponseMeta> fetchToday(int pageNumber) {
        return fetchPhotos("fresh_today", pageNumber);
    }

    @NonNull
    private Observable<PhotoResponseMeta> fetchPhotos(String feature, int pageNumber) {
        return restService.getPhotos(BuildConfig.CONSUMER_KEY, feature, pageNumber).flatMap(new Func1<PhotoResponse, Observable<List<Photo>>>() {
            @Override
            public Observable<List<Photo>> call(PhotoResponse photoResponse) {
                return Observable.from(photoResponse.photos)
                        .map(new Func1<RestPhoto, Photo>() {
                            @Override
                            public Photo call(RestPhoto restPhoto) {
                                RestUser restUser = restPhoto.user;
                                return new Photo().setAuthorName(restUser.firstname + " " + restUser.lastname)
                                        .setCountryName(restUser.country)
                                        .setDescription(restPhoto.description)
                                        .setImageUrl(restPhoto.imageUrl)
                                        .setName(restPhoto.name)
                                        .setRemoteId(restPhoto.id);
                            }
                        }).toList();
            }
        }, new Func2<PhotoResponse, List<Photo>, PhotoResponseMeta>() {
            @Override
            public PhotoResponseMeta call(PhotoResponse photoResponse, List<Photo> photos) {
                return new PhotoResponseMeta().setCurrentPage(photoResponse.currentPage)
                        .setTotalPages(photoResponse.totalPages)
                        .setPhotos(photos);
            }
        });

    }

//    Observable.from(photoResponse.photos)
//                .map(restPhoto -> {
//                    RestUser restUser = restPhoto.user;
//                    return new Photo().setAuthorName(restUser.firstname + " " + restUser.lastname)
//                            .setCountryName(restUser.country)
//                            .setDescription(restPhoto.description)
//                            .setImageUrl(restPhoto.imageUrl)
//                            .setName(restPhoto.name)
//                            .setRemoteId(restPhoto.id);
//                }).toList();


//    return restService.getPhotos(BuildConfig.CONSUMER_KEY, feature, pageNumber).flatMap(photoResponse -> {
//        return Observable.from(photoResponse.photos)
//                .map(restPhoto -> {
//                    RestUser restUser = restPhoto.user;
//                    return new Photo().setAuthorName(restUser.firstname + " " + restUser.lastname)
//                            .setCountryName(restUser.country)
//                            .setDescription(restPhoto.description)
//                            .setImageUrl(restPhoto.imageUrl)
//                            .setName(restPhoto.name)
//                            .setRemoteId(restPhoto.id);
//                }).toList();
//    }, (photoResponse, restPhotos) -> {
//        return new PhotoResponseMeta().setCurrentPage(photoResponse.currentPage)
//                .setTotalPages(photoResponse.totalPages)
//                .setPhotos(restPhotos);
//    });
}
