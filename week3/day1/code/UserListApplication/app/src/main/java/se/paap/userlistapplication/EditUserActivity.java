package se.paap.userlistapplication;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import se.paap.userlistapplication.model.User;
import se.paap.userlistapplication.repository.UserRepository;
import se.paap.userlistapplication.repository.inMemory.InMemoryUserRepository;

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

        userRepository = new InMemoryUserRepository();

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
        userRepository.addOrUpdateUser(user);

        setResult(RESULT_OK);
        finish();
    }

    @Override
    public User getUser(long id) {
        return userRepository.getUser(id);
    }
}
