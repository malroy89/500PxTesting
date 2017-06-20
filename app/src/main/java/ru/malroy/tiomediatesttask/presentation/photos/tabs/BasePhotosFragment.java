package ru.malroy.tiomediatesttask.presentation.photos.tabs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import java.util.List;

import ru.malroy.tiomediatesttask.R;
import ru.malroy.tiomediatesttask.domain.entity.Photo;
import ru.malroy.tiomediatesttask.presentation.di.HasComponent;
import ru.malroy.tiomediatesttask.presentation.mvp.LoadingView;
import ru.malroy.tiomediatesttask.presentation.photos.detail.DetailActivity;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
public abstract class BasePhotosFragment extends Fragment implements LoadingView<List<Photo>> {
    @Nullable
    protected View rootContainer;
    @Nullable
    protected RecyclerView photos;
    @Nullable
    protected ProgressBar progress;
    @Nullable
    private PhotoAdapter photoAdapter;
    @Nullable
    private PhotosViewState photosViewState;
    private boolean isLoading;
    @NonNull
    private final RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
        }

        @Override
        public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
            int visibleItemCount = layoutManager.getChildCount();
            int totalItemCount = layoutManager.getItemCount();
            int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();
            if (dy > 0 && !isLoading) {
                if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount / 3
                        && firstVisibleItemPosition >= 0) {
                    isLoading = true;
                    getPresenter().loadNextPage();
                }
            }
        }
    };

    @NonNull
    protected abstract BasePhotoPresenter getPresenter();

    @NonNull
    @SuppressWarnings("unchecked")
    protected PhotosComponent getPhotosComponent() {
        HasComponent<PhotosComponent> hasComponent = (HasComponent<PhotosComponent>) getActivity();
        return hasComponent.getComponent();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_base_photos, container, false);
        rootContainer = view;
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        photoAdapter = new PhotoAdapter(getActivity(), (clickedPosition, photos) -> DetailActivity.start(getActivity(), photos, clickedPosition));
        photos = (RecyclerView) view.findViewById(R.id.photos);
        photos.setLayoutManager(linearLayoutManager);
        photos.addOnScrollListener(onScrollListener);
        photos.setAdapter(photoAdapter);
        progress = (ProgressBar) view.findViewById(R.id.progress);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getPresenter().attachView(this);
        photosViewState = new PhotosViewState();
        photosViewState.apply(this);
        if (savedInstanceState == null) {
            getPresenter().loadFirstPage();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        getPresenter().detachView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getPresenter().destroy();
    }

    @Override
    public void showLoading() {
        if (photosViewState != null) {
            photosViewState.setStateShowLoading();
        }
        if (photos != null) {
            photos.setVisibility(View.GONE);
        }
        if (progress != null) {
            progress.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void hideLoading() {
        if (progress != null) {
            progress.setVisibility(View.GONE);
        }
    }

    @Override
    public void showError(@Nullable String errorMessage) {
        if (getContext() == null) {
            return;
        }
        if (TextUtils.isEmpty(errorMessage)) {
            errorMessage = getString(R.string.unknown_error);
        }
        isLoading = false;
        if (photosViewState != null) {
            photosViewState.setStateShowError(errorMessage);
        }
        if (rootContainer != null) {
            Snackbar.make(rootContainer, errorMessage, Snackbar.LENGTH_SHORT).show();
        }
    }

    @Override
    public void showContent() {
        if (photoAdapter == null) {
            return;
        }
        if (photosViewState != null) {
            photosViewState.setStateShowContent(photoAdapter.getPhotos());
        }
        if (photos != null) {
            photos.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void setData(@Nullable List<Photo> data) {
        isLoading = false;
        if (photoAdapter != null && data != null) {
            photoAdapter.addPhotos(data);
        }
    }

}
