package se.paap.examplephotoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.List;

import se.paap.examplephotoapp.model.GalleryItem;
import se.paap.examplephotoapp.model.ThumbnailHolder;
import se.paap.examplephotoapp.network.FetchPhotosTask;
import se.paap.examplephotoapp.network.ThumbnailDownloader;

public class PhotoGalleryFragment extends Fragment {
    private final List<GalleryItem> photos = new ArrayList<>();
    private ProgressBar progressBar;
    private PhotoAdapter photoAdapter;
    private ThumbnailDownloader<PhotoHolder> thumbnailDownloader;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        photoAdapter = new PhotoAdapter(photos, getActivity(), new PhotoAdapter.OnQueueThumbnailListener() {
            @Override
            public void onQueueThumbnail(PhotoHolder holder, String url) {
                thumbnailDownloader.queueThumbnail(holder, url);
            }
        });

        updateItems();

        Handler handler = new Handler();
        thumbnailDownloader = new ThumbnailDownloader<>(getActivity(), handler);
        thumbnailDownloader.start();

        thumbnailDownloader.getLooper();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.spinner);

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(photoAdapter);

        progressBar.setVisibility(View.VISIBLE);

        return v;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        thumbnailDownloader.quit();
    }

    private void updateItems() {
        new FetchPhotosTask(new FetchPhotosTask.OnResultListener() {
            @Override
            public void onResult(List<GalleryItem> galleryItems) {
                photos.addAll(galleryItems);
                photoAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.INVISIBLE);
            }
        }).execute();
    }

    private static class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private final List<GalleryItem> photos;
        private final OnQueueThumbnailListener listener;
        private final Context context;

        public PhotoAdapter(List<GalleryItem> galleryItems, Context context, OnQueueThumbnailListener listener) {
            this.photos = galleryItems;
            this.listener = listener;
            this.context = context;
        }

        @Override
        public PhotoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.list_item_gallery, parent, false);

            return new PhotoHolder(itemView);
        }

        @Override
        public void onBindViewHolder(PhotoHolder holder, int position) {
            GalleryItem galleryItem = photos.get(position);

            Drawable placeHolder = context.getResources().getDrawable(R.drawable.photo_gallery_placeholder);
            holder.bindThumbnail(placeHolder);

            listener.onQueueThumbnail(holder, galleryItem.getThumbnailUrl());
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }

        public interface OnQueueThumbnailListener {
            void onQueueThumbnail(PhotoHolder holder, String url);
        }
    }

    private static class PhotoHolder extends RecyclerView.ViewHolder implements ThumbnailHolder {
        private final ImageView imageView;

        public PhotoHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        @Override
        public void bindThumbnail(Drawable image) {
            imageView.setImageDrawable(image);
        }
    }
}
