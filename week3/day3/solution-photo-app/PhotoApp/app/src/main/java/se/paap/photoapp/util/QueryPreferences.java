package se.paap.photoapp.util;

import android.content.Context;
import android.preference.PreferenceManager;

public final class QueryPreferences {
    private static final String PREF_QUERY = "searchQuery";

    public static String getStoredQuery(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(PREF_QUERY, null);
    }

    public static void setStoredQuery(Context context, String query) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(PREF_QUERY, query)
                .apply();
    }
}
