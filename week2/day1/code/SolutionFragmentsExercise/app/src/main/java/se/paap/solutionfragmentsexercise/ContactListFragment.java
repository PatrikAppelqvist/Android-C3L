package se.paap.solutionfragmentsexercise;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import se.paap.solutionfragmentsexercise.model.Contact;
import se.paap.solutionfragmentsexercise.repository.ContactsRepository;
import se.paap.solutionfragmentsexercise.repository.InMemoryContactRepository;

public class ContactListFragment extends Fragment {
    private static final String BUNDLE_ID_KEY = "bundle_id";
    private ContactsRepository contactsRepository;
    private Callbacks callBacks;

    public interface Callbacks {
        void onListItemClicked(Contact contact);
    }

    public static Fragment newInstance() {
        return new ContactListFragment();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callBacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity must implement callbacks");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        contactsRepository = new InMemoryContactRepository();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts_list, container, false);

        ContactListAdapter adapter = new ContactListAdapter(contactsRepository.getAllContacts(),
                new ContactListAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(Contact contact) {
                callBacks.onListItemClicked(contact);
            }
        });

        final RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.contact_list_recycler_view);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private static final class ContactListAdapter extends RecyclerView.Adapter<ContactListAdapter.ContactViewHolder> {
        private final List<Contact> contacts;
        private final OnItemClickedListener onItemClickedListener;

        private ContactListAdapter(List<Contact> contacts, OnItemClickedListener onItemClickedListener) {
            this.contacts = contacts;
            this.onItemClickedListener = onItemClickedListener;
        }

        @Override
        public ContactViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            View itemView = inflater.inflate(R.layout.list_item_contact, parent, false);
            return new ContactViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(ContactViewHolder holder, int position) {
            Contact contact = contacts.get(position);
            holder.bindView(contact, onItemClickedListener);
        }

        @Override
        public int getItemCount() {
            return contacts.size();
        }

        public interface OnItemClickedListener {
            void onItemClicked(Contact contact);
        }

        static final class ContactViewHolder extends RecyclerView.ViewHolder {
            private final TextView fullNameTextView;
            private final TextView phoneNumberTextView;

            ContactViewHolder(View itemView) {
                super(itemView);
                fullNameTextView = (TextView) itemView.findViewById(R.id.fragment_contact_list_full_name_textview);
                phoneNumberTextView = (TextView) itemView.findViewById(R.id.fragment_contact_list_phone_number_textview);
            }

            void bindView(final Contact contact, final OnItemClickedListener onItemClickedListener) {
                fullNameTextView.setText(contact.getFullName());
                phoneNumberTextView.setText(contact.getPhoneNumber());
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onItemClickedListener.onItemClicked(contact);
                    }
                });
            }
        }
    }


}
