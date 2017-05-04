package se.paap.networkuserlistapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import se.paap.networkuserlistapplication.model.User;

public class UserListFragment extends Fragment implements OnDataSetChangedListener<List<User>> {

    public interface Callbacks {
        void onItemClicked(User user);
        void onItemRemoved(int position, long id);
        void updateDataSet(OnDataSetChangedListener<List<User>> listener);
    }

    private Callbacks callbacks;
    private UserListAdapter adapter;
    private ProgressBar progressBar;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_list, container, false);

        final ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        final long id = viewHolder.getItemId();
                        final int position = viewHolder.getAdapterPosition();
                        callbacks.onItemRemoved(position, id);
                        callbacks.updateDataSet(UserListFragment.this);
                    }
                });

        adapter = new UserListAdapter(Collections.<User>emptyList());
        adapter.setOnItemClickedListener(new UserListAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(User user) {
                callbacks.onItemClicked(user);
            }
        });

        progressBar = (ProgressBar) view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        itemTouchHelper.attachToRecyclerView(recyclerView);

        callbacks.updateDataSet(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity has to implement interface: " + Callbacks.class.getCanonicalName());
        }
    }

    private void updateAdapter(List<User> users) {
        adapter.setItems(users);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onDataSetChanged(List<User> newDataSet) {
        updateAdapter(newDataSet);
        progressBar.setVisibility(View.INVISIBLE);
    }

    private static class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {
        private List<User> users;
        private OnItemClickedListener listener;

        UserListAdapter(List<User> users) {
            this.users = users;
            setHasStableIds(true);
        }

        void setOnItemClickedListener(OnItemClickedListener listener) {
            this.listener = listener;
        }

        void setItems(List<User> users) {
            this.users = users;
        }

        @Override
        public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View v = inflater.inflate(R.layout.list_item_user, parent, false);

            return new UserViewHolder(v);
        }

        @Override
        public void onBindViewHolder(UserViewHolder holder, int position) {
            User user = users.get(position);
            holder.bindView(user, listener);
        }

        @Override
        public int getItemCount() {
            return this.users.size();
        }

        @Override
        public long getItemId(int position) {
            return users.get(position).getId();
        }

        interface OnItemClickedListener {
            void onItemClicked(User user);
        }
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvAge;

        UserViewHolder(View itemView) {
            super(itemView);

            this.tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            this.tvAge = (TextView) itemView.findViewById(R.id.tv_age);
        }

        void bindView(final User user, final UserListAdapter.OnItemClickedListener listener) {
            tvUsername.setText(user.getUsername());
            tvAge.setText(String.valueOf(user.getAge()));
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener != null) {
                        listener.onItemClicked(user);
                    }
                }
            });
        }
    }
}
