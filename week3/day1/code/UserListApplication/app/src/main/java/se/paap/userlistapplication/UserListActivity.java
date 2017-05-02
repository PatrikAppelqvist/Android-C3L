package se.paap.userlistapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;

import se.paap.userlistapplication.model.User;
import se.paap.userlistapplication.repository.UserRepository;
import se.paap.userlistapplication.repository.inMemory.InMemoryUserRepository;

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

        userRepository = new InMemoryUserRepository();

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
                userListView.updateDataSet();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        userRepository.close();
    }

    @Override
    public void onItemClicked(User user) {
        openEditUserActivity(user);
    }

    @Override
    public void onItemRemoved(int position, long id) {
        userRepository.removeUser(id);
        userListView.updateDataSet();
    }

    @Override
    public List<User> getDataSet() {
        return userRepository.getUsers();
    }

    private void openEditUserActivity(User user) {
        Intent intent = EditUserActivity.createIntent(this, user);
        startActivityForResult(intent, REQUEST_CODE_EDIT_USER);
    }
}
