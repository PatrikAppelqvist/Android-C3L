package se.paap.photoapp;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

import se.paap.photoapp.model.GalleryItem;
import se.paap.photoapp.model.ThumbnailHolder;
import se.paap.photoapp.util.GridInfiniteScrollListener;
import se.paap.photoapp.util.OnLoadFinishedListener;

import static se.paap.photoapp.PhotoGalleryActivity.PAGE_SIZE;

public class PhotoGalleryFragment extends Fragment implements OnLoadFinishedListener {
    interface Callbacks {
        void loadData(int start, OnLoadFinishedListener listener);
        void loadThumbnail(PhotoHolder holder, String url);
    }

    private final List<GalleryItem> photos = new ArrayList<>();

    private ProgressBar progressBar;
    private PhotoAdapter photoAdapter;
    private Callbacks callbacks;
    private GridInfiniteScrollListener infiniteScrollListener;

    public static PhotoGalleryFragment newInstance() {
        return new PhotoGalleryFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        photoAdapter = new PhotoAdapter(photos, getActivity(), new PhotoAdapter.OnQueueThumbnailListener() {
            @Override
            public void queueThumbnail(PhotoHolder holder, String url) {
                callbacks.loadThumbnail(holder, url);
            }
        });

        infiniteScrollListener = new GridInfiniteScrollListener(new GridInfiniteScrollListener.LoadMoreListener() {
            @Override
            public void loadMore(int start) {
                updateItems(start);
            }
        });

        infiniteScrollListener.setMax(PAGE_SIZE);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_photo_gallery, container, false);

        progressBar = (ProgressBar) v.findViewById(R.id.spinner);

        final RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.photo_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        recyclerView.setAdapter(photoAdapter);
        recyclerView.addOnScrollListener(infiniteScrollListener);

        updateItems();

        return v;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity must implement interface: Callbacks");
        }
    }

    @Override
    public void onLoadFinished(List<GalleryItem> result) {
        for (GalleryItem item : result) {
            if (!photos.contains(item)) {
                photos.add(item);
                photoAdapter.notifyItemInserted(photos.size() - 1);
            }
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

    public void clearAndShowSpinner() {
        photos.clear();
        infiniteScrollListener.reset();
        photoAdapter.notifyDataSetChanged();
        progressBar.setVisibility(View.VISIBLE);
    }

    private void updateItems(int start) {
        progressBar.setVisibility(View.VISIBLE);
        callbacks.loadData(start, this);
    }

    private void updateItems() {
        updateItems(0);
    }

    private static class PhotoAdapter extends RecyclerView.Adapter<PhotoHolder> {
        private final List<GalleryItem> photos;
        private final OnQueueThumbnailListener listener;
        private final Context context;

        PhotoAdapter(List<GalleryItem> galleryItems, Context context, OnQueueThumbnailListener listener) {
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

            listener.queueThumbnail(holder, galleryItem.getThumbnailUrl());
        }

        @Override
        public int getItemCount() {
            return photos.size();
        }

        interface OnQueueThumbnailListener {
            void queueThumbnail(PhotoHolder holder, String url);
        }
    }

    static class PhotoHolder extends RecyclerView.ViewHolder implements ThumbnailHolder {
        private final ImageView imageView;

        PhotoHolder(View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.item_image_view);
        }

        @Override
        public void bindThumbnail(Drawable image) {
            imageView.setImageDrawable(image);
        }
    }
}
