package se.paap.solutionfragmentsexercise;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import se.paap.solutionfragmentsexercise.model.Contact;
import se.paap.solutionfragmentsexercise.repository.ContactsRepository;
import se.paap.solutionfragmentsexercise.repository.InMemoryContactRepository;

public class ContactDetailFragment extends Fragment {
    private static final String BUNDLE_ARGS_ID = "args_id";
    private Contact currentContact;

    public static Fragment newInstance(long id) {
        Fragment fragment = new ContactDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putLong(BUNDLE_ARGS_ID, id);

        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ContactsRepository repository = new InMemoryContactRepository();
        long id = getArguments().getLong(BUNDLE_ARGS_ID);
        currentContact = repository.getContact(id);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_detail, container, false);

        TextView fullNameTextView = (TextView) view.findViewById(R.id.fragment_contact_detail_full_name);
        TextView phoneNumberTextView = (TextView) view.findViewById(R.id.fragment_contact_detail_phone_number);

        if(currentContact != null) {
            fullNameTextView.setText(currentContact.getFullName());
            phoneNumberTextView.setText(currentContact.getPhoneNumber());
        } else {
            fullNameTextView.setText(getString(R.string.fragment_detail_instruction));
        }

        return view;
    }
}
