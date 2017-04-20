package se.paap.userlistapplication;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.paap.userlistapplication.model.User;
import se.paap.userlistapplication.repository.InMemoryUserRepository;
import se.paap.userlistapplication.repository.UserRepository;

public class UserListActivity extends AppCompatActivity {

    private UserRepository userRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        userRepository = new InMemoryUserRepository();

        final RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setAdapter(new UserListAdapter(this, userRepository.getUsers()));
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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
    }

    private static class UserViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvUsername;
        private final TextView tvAge;

        public UserViewHolder(View itemView) {
            super(itemView);

            tvUsername = (TextView) itemView.findViewById(R.id.tv_username);
            tvAge = (TextView) itemView.findViewById(R.id.tv_age);
        }

        public void bindView(User user) {
            tvUsername.setText(user.getUsername());
            tvAge.setText(String.valueOf(user.getAge()));
        }
    }
}
