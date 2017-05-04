package se.paap.networkuserlistapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.repository.UserRepository;
import se.paap.networkuserlistapplication.repository.http.HttpUserRepository;

public class UserListActivity extends AppCompatActivity implements UserListFragment.Callbacks {
    private static final String TAG = "UserListActivity";
    private static final int REQUEST_CODE_EDIT_USER = 1;

    private UserRepository userRepository;
    private UserListFragment userListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_user_list_container);

        //userRepository = SqlUserRepository.getInstance(this);
        userRepository = new HttpUserRepository();
        //userRepository = new InMemoryUserRepository();

        if(fragment == null) {
            fragment = new UserListFragment();

            fm.beginTransaction()
                    .add(R.id.fragment_user_list_container, fragment)
                    .commit();
        }

        userListView = (UserListFragment) fragment;
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_EDIT_USER) {
            if(resultCode == RESULT_OK) {
                updateDataSet(userListView);
            }
        }
    }

    @Override
    public void onItemClicked(User user) {
        openEditUserActivity(user);
    }

    @Override
    public void onItemRemoved(int position, final long id) {
        userRepository.removeUser(id, new UserRepository.OnResultListener<Void>() {
            @Override
            public void onResult(Void result) {
                Log.d(TAG, "onResult: User with id: " + id + " has been removed.");
            }
        });
    }

    @Override
    public void updateDataSet(final OnDataSetChangedListener<List<User>> listener) {
        userRepository.getUsers(new UserRepository.OnResultListener<List<User>>() {
            @Override
            public void onResult(List<User> result) {
                listener.onDataSetChanged(result);
            }
        });
    }

    private void openEditUserActivity(User user) {
        Intent intent = EditUserActivity.createIntent(this, user);
        startActivityForResult(intent, REQUEST_CODE_EDIT_USER);
    }
}
