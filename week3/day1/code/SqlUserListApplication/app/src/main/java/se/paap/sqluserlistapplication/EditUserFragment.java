package se.paap.sqluserlistapplication;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import se.paap.sqluserlistapplication.model.User;

import static android.content.Context.INPUT_METHOD_SERVICE;

public final class EditUserFragment extends Fragment implements TextWatcher {

    private static final String ARGS_USER_ID = "user_id";

    public interface Callbacks {
        void onUserEdited(User user);
        User getUser(long id);
    }

    public static EditUserFragment newInstance(long id) {
        Bundle args = new Bundle();
        args.putLong(ARGS_USER_ID, id);
        EditUserFragment instance = new EditUserFragment();
        instance.setArguments(args);

        return instance;
    }

    private Button button;
    private Callbacks callbacks;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_edit_user, container, false);

        Bundle args = getArguments();
        final long id = args.getLong(ARGS_USER_ID);
        final User user = callbacks.getUser(id);

        final EditText usernameEditText = (EditText) view.findViewById(R.id.edit_text_username);
        final EditText ageEditText = (EditText) view.findViewById(R.id.edit_text_age);
        button = (Button) view.findViewById(R.id.btn_edit_user);

        ageEditText.addTextChangedListener(this);

        ageEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if((event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) || actionId == EditorInfo.IME_ACTION_DONE) {
                    InputMethodManager methodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                    methodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                    return true;
                }

                return false;
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = usernameEditText.getText().toString();
                final int age = Integer.valueOf(ageEditText.getText().toString());

                final User newOrEditedUser = user == null ? new User(username, age) : new User(user.getId(), username, age);

                callbacks.onUserEdited(newOrEditedUser);
            }
        });

        if(user != null) {
            usernameEditText.setText(user.getUsername());
            ageEditText.setText(String.valueOf(user.getAge()));
            button.setText(getString(R.string.btn_edit_user));
        } else {
            button.setText(getString(R.string.action_add_user));
            button.setEnabled(false);
        }

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            callbacks = (Callbacks) context;
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Hosting activity has to implement interface: " + Callbacks.class.getCanonicalName());
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {}

    @Override
    public void afterTextChanged(Editable s) {
        final String ageString = s.toString().trim();

        try {
            Integer.parseInt(ageString);
            button.setEnabled(true);
        } catch (NumberFormatException e) {
            button.setEnabled(false);
        }
    }
}
