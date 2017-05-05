package se.paap.photoapp.util;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

public class GridInfiniteScrollListener extends RecyclerView.OnScrollListener {
    private static final String TAG = "GridInfiniteScrollListener";

    private final LoadMoreListener listener;
    private int visibleThreshold = 9;
    private int previousTotal = 0;
    private boolean loading = true;
    private int nextStart = 0;
    private int max = 8;

    public GridInfiniteScrollListener(LoadMoreListener listener) {
        this.listener = listener;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = manager.getItemCount();
        final int firstVisibleItem = manager.findFirstVisibleItemPosition();

        if (loading) {
            if (totalItemCount != previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
            }
        }

        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            nextStart += max;

            listener.loadMore(nextStart);
            loading = true;
        }
    }

    public void setMax(int max) {
        this.max = max;
        visibleThreshold = (int) Math.floor(max / 8);
    }

    public void reset() {
        previousTotal = 0;
        loading = true;
        nextStart = 0;
    }

    public interface LoadMoreListener {
        void loadMore(int start);
    }
}
