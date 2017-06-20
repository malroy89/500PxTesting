package ru.malroy.tiomediatesttask.presentation.photos.tabs;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import ru.malroy.tiomediatesttask.R;
import ru.malroy.tiomediatesttask.domain.entity.Photo;

/**
 * Created by Dzmitry Lamaka on 03.12.2015.
 */
class PhotoAdapter extends RecyclerView.Adapter<PhotoAdapter.PhotoViewHolder> {
    @NonNull
    private LayoutInflater layoutInflater;
    @NonNull
    private List<Photo> photos = new ArrayList<>();
    @NonNull
    private PhotoItemClickListener photoItemClickListener;
    private int itemEdgeSize;

    PhotoAdapter(@NonNull Context context, @NonNull PhotoItemClickListener photoItemClickListener) {
        int[] itemHeightAttr = new int[] { android.R.attr.listPreferredItemHeight };
        int indexOfAttrTextSize = 0;
        TypedArray a = context.obtainStyledAttributes(itemHeightAttr);
        itemEdgeSize = a.getDimensionPixelSize(indexOfAttrTextSize, -1);
        a.recycle();
        layoutInflater = LayoutInflater.from(context);
        this.photoItemClickListener = photoItemClickListener;
    }

    void addPhotos(@NonNull List<Photo> newPhotos) {
        photos.addAll(newPhotos);
        notifyDataSetChanged();
    }

    @NonNull
    public List<Photo> getPhotos() {
        return photos;
    }

    @Override
    public PhotoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = layoutInflater.inflate(R.layout.recycler_item_photo, parent, false);
        return new PhotoViewHolder(item);
    }

    @Override
    public void onBindViewHolder(PhotoViewHolder holder, int position) {
        Photo photo = photos.get(position);
        TextView name = holder.getName();
        if (name != null) {
            name.setText(photo.getName());
        }
        TextView author = holder.getAuthor();
        if (author != null) {
            author.setText(String.format("by %s", photo.getAuthorName()));
        }
        ImageView thumbnail = holder.getThumbnail();
        if (thumbnail != null) {
            Glide.with(holder.itemView.getContext())
                    .load(photo.getImageUrl())
                    .override(itemEdgeSize, itemEdgeSize)
                    .centerCrop()
                    .into(thumbnail);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                photoItemClickListener.click(position, photos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return photos.size();
    }

    static class PhotoViewHolder extends RecyclerView.ViewHolder {
        @Nullable
        private ImageView thumbnail;
        @Nullable
        private TextView name;
        @Nullable
        private TextView author;

        private PhotoViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
            name = (TextView) itemView.findViewById(R.id.name);
            author = (TextView) itemView.findViewById(R.id.author);
        }

        @Nullable
        private ImageView getThumbnail() {
            return thumbnail;
        }

        @Nullable
        private TextView getName() {
            return name;
        }

        @Nullable
        private TextView getAuthor() {
            return author;
        }
    }

}
