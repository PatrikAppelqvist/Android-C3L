package se.paap.solutionrecyclerviewexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import se.paap.solutionrecyclerviewexercise.model.User;
import se.paap.solutionrecyclerviewexercise.repository.InMemoryUserRepository;
import se.paap.solutionrecyclerviewexercise.repository.UserRepository;
import se.paap.solutionrecyclerviewexercise.view.ContextMenuRecyclerView;

public class UserListActivity extends AppCompatActivity {
    private static final String TAG = "UserListActivity";
    private static final int REQUEST_CODE_EDIT_USER = 1;

    private UserRepository userRepository;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userRepository = new InMemoryUserRepository();

        recyclerView = (ContextMenuRecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        registerForContextMenu(recyclerView);

        updateAdapter();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user_list_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.action_add_user:
                openEditUserActivity(null);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.menu_context_user_list, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        ContextMenuRecyclerView.RecyclerContextMenuInfo menuInfo = (ContextMenuRecyclerView.RecyclerContextMenuInfo) item.getMenuInfo();
        User user = userRepository.getUser(menuInfo.getId());

        switch(item.getItemId()) {
            case R.id.action_edit_user:
                openEditUserActivity(user);

                return true;
            case R.id.action_remove_user:
                userRepository.removeUser(user.getId());
                updateAdapter();

                return true;
        }

        return super.onContextItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_EDIT_USER) {
            if(resultCode == RESULT_OK) {
                User user = data.getParcelableExtra(EditUserActivity.EXTRA_USER_BACK);
                userRepository.addOrUpdateUser(user);

                updateAdapter();
            }
        }
    }

    private void openEditUserActivity(User user) {
        Intent intent = EditUserActivity.createIntent(this, user);
        startActivityForResult(intent, REQUEST_CODE_EDIT_USER);
    }

    private void updateAdapter() {
        recyclerView.setAdapter(new UserListAdapter(userRepository.getUsers()));
    }

    private static class UserListAdapter extends RecyclerView.Adapter<UserViewHolder> {
        private final List<User> users;

        UserListAdapter(List<User> users) {
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
            holder.bindView(user);
        }

        @Override
        public int getItemCount() {
            return this.users.size();
        }

        @Override
        public long getItemId(int position) {
            return users.get(position).getId();
        }
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener{
        private final TextView tvUsername;
        private final TextView tvAge;

        public UserViewHolder(View itemView) {
            super(itemView);

            this.tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            this.tvAge = (TextView) itemView.findViewById(R.id.tv_age);

            itemView.setOnLongClickListener(this);
        }

        public void bindView(User user) {
            tvUsername.setText(user.getUsername());
            tvAge.setText(String.valueOf(user.getAge()));
        }

        @Override
        public boolean onLongClick(View v) {
            v.showContextMenu();
            return true;
        }
    }
}
