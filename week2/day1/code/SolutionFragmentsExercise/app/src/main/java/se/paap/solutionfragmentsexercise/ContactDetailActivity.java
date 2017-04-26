package se.paap.solutionfragmentsexercise;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import java.util.List;

import se.paap.solutionfragmentsexercise.model.Contact;
import se.paap.solutionfragmentsexercise.repository.ContactsRepository;
import se.paap.solutionfragmentsexercise.repository.InMemoryContactRepository;

public class ContactDetailActivity extends AppCompatActivity {
    private static final String EXTRA_CONTACT_ID = "contact_id";

    public static Intent createIntent(Context context, Contact contact) {
        Intent intent = new Intent(context, ContactDetailActivity.class);
        intent.putExtra(EXTRA_CONTACT_ID, contact.getId());

        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_detail);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        Intent startingIntent = getIntent();
        long id = startingIntent.getLongExtra(EXTRA_CONTACT_ID, 0);

        ContactsRepository repository = new InMemoryContactRepository();
        Contact contact = repository.getContact(id);
        List<Contact> contacts = repository.getAllContacts();

        int positionOfSelectedItem = contacts.indexOf(contact);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.detail_fragment_view_pager);
        viewPager.setAdapter(new ContactPagerAdapter(getSupportFragmentManager(), contacts));
        viewPager.setCurrentItem(positionOfSelectedItem);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private static final class ContactPagerAdapter extends FragmentStatePagerAdapter {
        private final List<Contact> contacts;

        ContactPagerAdapter(FragmentManager fragmentManager, List<Contact> contacts) {
            super(fragmentManager);
            this.contacts = contacts;
        }

        @Override
        public Fragment getItem(int position) {
            long id = contacts.get(position).getId();
            return ContactDetailFragment.newInstance(id);
        }

        @Override
        public int getCount() {
            return contacts.size();
        }
    }
}
