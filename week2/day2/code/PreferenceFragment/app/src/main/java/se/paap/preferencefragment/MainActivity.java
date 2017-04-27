package se.paap.preferencefragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SharedPreferences.OnSharedPreferenceChangeListener {
    private TextView welcomeMessageTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setTitle(getString(R.string.activity_main_title));
        }

        welcomeMessageTextView = (TextView) findViewById(R.id.tv_welcome_message);
        setupFromSharedPreferences();
    }

    private void setupFromSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String welcomeMessage = sharedPreferences.getString(getString(R.string.prefs_welcome_message_key),
                getString(R.string.prefs_welcome_message_default));
        String color = sharedPreferences.getString(getString(R.string.prefs_color_key),
                getString(R.string.prefs_color_default));
        boolean showMessage = sharedPreferences.getBoolean(getString(R.string.prefs_show_message_key), true);

        welcomeMessageTextView.setText(welcomeMessage);
        welcomeMessageTextView.setTextColor(Color.parseColor(color));

        if(!showMessage) {
            welcomeMessageTextView.setVisibility(View.INVISIBLE);
        }

        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.action_settings) {
            Intent intent = SettingsActivity.createIntent(this);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if(key.equals(getString(R.string.prefs_welcome_message_key))) {
            String welcomeMessage = sharedPreferences.getString(getString(R.string.prefs_welcome_message_key),
                    getString(R.string.prefs_welcome_message_default));
            welcomeMessageTextView.setText(welcomeMessage);
        } else if(key.equals(getString(R.string.prefs_color_key))) {
            String color = sharedPreferences.getString(getString(R.string.prefs_color_key),
                    getString(R.string.prefs_color_default));

            welcomeMessageTextView.setTextColor(Color.parseColor(color));
        } else if(key.equals(getString(R.string.prefs_show_message_key))) {
            boolean showMessage = sharedPreferences.getBoolean(getString(R.string.prefs_show_message_key), true);
            welcomeMessageTextView.setVisibility(showMessage ? View.VISIBLE : View.INVISIBLE);
        }
    }
}
