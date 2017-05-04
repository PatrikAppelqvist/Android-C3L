package se.paap.networkuserlistapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import se.paap.networkuserlistapplication.model.User;
import se.paap.networkuserlistapplication.repository.UserRepository;
import se.paap.networkuserlistapplication.repository.http.HttpUserRepository;

public class EditUserActivity extends AppCompatActivity implements EditUserFragment.Callbacks {
    private static final String EXTRA_USER_ID = "user_id";

    private UserRepository userRepository;

    public static Intent createIntent(Context context, User user) {
        Intent intent = new Intent(context, EditUserActivity.class);
        intent.putExtra(EXTRA_USER_ID, user == null ? -1 : user.getId());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //userRepository = SqlUserRepository.getInstance(this);
        userRepository = new HttpUserRepository();
        //userRepository = new InMemoryUserRepository();

        Intent intent = getIntent();
        final long id = intent.getLongExtra(EXTRA_USER_ID, -1);

        FragmentManager fm = getSupportFragmentManager();
        Fragment fragment = fm.findFragmentById(R.id.fragment_edit_user_container);

        if(fragment == null) {
            fragment = EditUserFragment.newInstance(id);

            fm.beginTransaction()
                    .add(R.id.fragment_edit_user_container, fragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onUserEdited(User user) {
        userRepository.addOrUpdateUser(user, new UserRepository.OnResultListener<Long>() {
            @Override
            public void onResult(Long result) {
                setResult(RESULT_OK);
                finish();
            }
        });
    }

    @Override
    public void getUser(long id, final OnDataSetChangedListener<User> listener) {
        userRepository.getUser(id, new UserRepository.OnResultListener<User>() {
            @Override
            public void onResult(User result) {
                listener.onDataSetChanged(result);
            }
        });
    }
}
