package se.paap.photoapp;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import java.util.List;

import se.paap.photoapp.model.GalleryItem;
import se.paap.photoapp.network.FetchPhotosTask;
import se.paap.photoapp.network.ThumbnailDownloader;
import se.paap.photoapp.util.OnLoadFinishedListener;
import se.paap.photoapp.util.QueryPreferences;

public class PhotoGalleryActivity extends SingleFragmentActivity implements PhotoGalleryFragment.Callbacks {
    private static final String TAG = PhotoGalleryActivity.class.getSimpleName();

    private PhotoGalleryFragment photoGalleryFragment;
    public static final int PAGE_SIZE = 100;

    @Override
    protected Fragment createFragment() {
        photoGalleryFragment = PhotoGalleryFragment.newInstance();
        return photoGalleryFragment;
    }

    private ThumbnailDownloader<PhotoGalleryFragment.PhotoHolder> thumbnailDownloader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thumbnailDownloader = new ThumbnailDownloader<>(this, new Handler());
        thumbnailDownloader.start();
        thumbnailDownloader.getLooper();

        String query = QueryPreferences.getStoredQuery(this);
        if(query != null) {
            setTitle(query);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.fragment_photo_gallery, menu);

        final MenuItem searchItem = menu.findItem(R.id.menu_item_search);
        final SearchView searchView = (SearchView) searchItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchSubmitted(searchView, query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String query = QueryPreferences.getStoredQuery(PhotoGalleryActivity.this);
                searchView.setQuery(query, false);

                setTitle(query);
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.menu_item_clear) {
            clearSearch();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void clearSearch() {
        QueryPreferences.setStoredQuery(this, null);

        photoGalleryFragment.clearAndShowSpinner();
        setTitle(R.string.app_name);

        loadData(0, photoGalleryFragment);
    }

    private void searchSubmitted(SearchView searchView, String query) {
        QueryPreferences.setStoredQuery(this, query);
        searchView.setQuery("", false);
        searchView.clearFocus();
        searchView.setIconified(true);

        photoGalleryFragment.clearAndShowSpinner();
        setTitle(query);

        loadData(1, photoGalleryFragment);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        thumbnailDownloader.quit();
    }

    @Override
    public void loadData(int start, final OnLoadFinishedListener listener) {
        String query = QueryPreferences.getStoredQuery(this);

        int page = (start / PAGE_SIZE) + 1;

        new FetchPhotosTask(query, new FetchPhotosTask.OnResultListener() {
            @Override
            public void onResult(List<GalleryItem> galleryItems) {
                listener.onLoadFinished(galleryItems);
            }
        }).execute(page);
    }

    @Override
    public void loadThumbnail(PhotoGalleryFragment.PhotoHolder holder, String url) {
        thumbnailDownloader.queueThumbnail(holder, url);
    }
}
