package se.paap.solutionrecyclerviewexercise.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.View;

public final class ContextMenuRecyclerView extends RecyclerView {
    private ContextMenu.ContextMenuInfo menuInfo;

    public ContextMenuRecyclerView(Context context) {
        super(context);
    }

    public ContextMenuRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ContextMenuRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected ContextMenu.ContextMenuInfo getContextMenuInfo() {
        return menuInfo;
    }

    @Override
    public boolean showContextMenuForChild(View originalView) {
        final int position = getChildAdapterPosition(originalView);
        if (position >= 0) {
            final long id = getAdapter().getItemId(position);
            menuInfo = createContextMenuInfo(position, id);

            return super.showContextMenuForChild(originalView);
        }

        return false;
    }

    ContextMenu.ContextMenuInfo createContextMenuInfo(int position, long id) {
        return new RecyclerContextMenuInfo(position, id);
    }

    public static class RecyclerContextMenuInfo implements ContextMenu.ContextMenuInfo {
        private final int position;
        private final long id;

        public RecyclerContextMenuInfo(int position, long id) {
            this.position = position;
            this.id = id;
        }

        public int getPosition() {
            return position;
        }

        public long getId() {
            return id;
        }
    }
}
