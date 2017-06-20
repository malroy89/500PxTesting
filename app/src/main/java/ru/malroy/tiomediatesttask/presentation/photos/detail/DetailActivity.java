package ru.malroy.tiomediatesttask.presentation.photos.detail;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.malroy.tiomediatesttask.R;
import ru.malroy.tiomediatesttask.domain.entity.Photo;

public class DetailActivity extends AppCompatActivity {
    @NonNull
    private static final String EXTRA_URLS = "ru.malroy.tiomediatesttask.extras.EXTRA_URLS";
    @NonNull
    private static final String EXTRA_POSITION = "ru.malroy.tiomediatesttask.extras.EXTRA_POSITION";
    @Nullable
    private ArrayList<String> urls;

    public static void start(@NonNull Context context,  @NonNull List<Photo> photos, int positionToScroll) {
        int size = photos.size();
        ArrayList<String> urls = new ArrayList<>(size);
        for (Photo photo : photos) {
            urls.add(photo.getImageUrl());
        }
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putStringArrayListExtra(EXTRA_URLS, urls);
        intent.putExtra(EXTRA_POSITION, positionToScroll);
        context.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        urls = intent.getStringArrayListExtra(EXTRA_URLS);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.setCurrentItem(intent.getIntExtra(EXTRA_POSITION, 0), true);
    }

    public static class ImageContainerFragment extends Fragment {
        @NonNull
        private static final String ARG_PHOTO_URL = "photo_url";

        public ImageContainerFragment() {
        }

        @NonNull
        public static ImageContainerFragment newInstance(@NonNull String photoUrl) {
            ImageContainerFragment fragment = new ImageContainerFragment();
            Bundle args = new Bundle();
            args.putString(ARG_PHOTO_URL, photoUrl);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            ImageView photo = (ImageView) inflater.inflate(R.layout.fragment_detail, container, false);
            Glide.with(this)
                    .load(getArguments().getString(ARG_PHOTO_URL))
                    .centerCrop()
                    .into(photo);
            return photo;
        }
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (urls == null) {
                return null;
            }
            return ImageContainerFragment.newInstance(urls.get(position));
        }

        @Override
        public int getCount() {
            if (urls == null) {
                return 0;
            }
            return urls.size();
        }

    }
}
