package se.paap.fragments2;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

public class MainFragment extends Fragment {
    private static final String BUNDLE_MESSAGE_KEY = "bundle_message";

    public interface Callbacks {
        void onBeenCreated(String message);
    }

    private Callbacks callbacks;

    public static Fragment newInstance(String message) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_MESSAGE_KEY, message);

        Fragment fragment = new MainFragment();
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getArguments();
        String message = bundle.getString(BUNDLE_MESSAGE_KEY);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        callbacks.onBeenCreated("I am fragment, and i am created");
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onAttach(Context context) {
        try {
            callbacks = (Callbacks) context;
        } catch(ClassCastException e) {
            throw new IllegalArgumentException("Activity need to implement Callbacks interface");
        }

        super.onAttach(context);
    }
}
