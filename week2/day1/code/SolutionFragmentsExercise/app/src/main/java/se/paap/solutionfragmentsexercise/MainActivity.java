package se.paap.solutionfragmentsexercise;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.FrameLayout;

import se.paap.solutionfragmentsexercise.model.Contact;

public class MainActivity extends AppCompatActivity implements ContactListFragment.Callbacks {
    private boolean isInTwoColumnMode = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FrameLayout detailsContainer = (FrameLayout) findViewById(R.id.detail_fragment_container);
        FragmentManager fm = getSupportFragmentManager();

        if(detailsContainer == null) { // We are not in two_column_mode
            Fragment fragment = fm.findFragmentById(R.id.list_fragment_container);

            if(fragment == null) {
                fragment = ContactListFragment.newInstance();
                fm.beginTransaction()
                        .add(R.id.list_fragment_container, fragment)
                        .commit();
            }
        } else { // We are in two_column_mode, add both detail and List
            isInTwoColumnMode = true;

            Fragment listFragment = fm.findFragmentById(R.id.list_fragment_container);
            Fragment detailFragment = fm.findFragmentById(R.id.detail_fragment_container);

            if(listFragment == null && detailFragment == null) {
                listFragment = ContactListFragment.newInstance();
                detailFragment = ContactDetailFragment.newInstance(-1);

                fm.beginTransaction()
                        .add(R.id.list_fragment_container, listFragment)
                        .add(R.id.detail_fragment_container, detailFragment)
                        .commit();
            }
        }
    }

    @Override
    public void onListItemClicked(Contact contact) {
        if(isInTwoColumnMode) {
            FragmentManager fm = getSupportFragmentManager();
            Fragment newFragment = ContactDetailFragment.newInstance(contact.getId());
            fm.beginTransaction().replace(R.id.detail_fragment_container, newFragment).commit();
        } else {
            Intent intent = ContactDetailActivity.createIntent(this, contact);
            startActivity(intent);
        }
    }
}
