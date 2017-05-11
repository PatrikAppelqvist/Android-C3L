package com.example.patrik.loginmvvmdone;

import android.databinding.BindingAdapter;
import android.view.View;
import android.widget.EditText;

/**
 * Created by patrik on 2017-05-11.
 */

public class BindingHelpers {
    @BindingAdapter("app:error")
    public static void setError(EditText editText, String errorMessage) {
        editText.setError(errorMessage);
    }

    @BindingAdapter("app:visibility")
    public static void setVisibility(View view, boolean visible) {
        view.setVisibility(visible ? View.VISIBLE : View.INVISIBLE);
    }
}
