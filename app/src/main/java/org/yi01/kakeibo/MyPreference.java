package org.yi01.kakeibo;

import android.content.Context;
import android.content.SharedPreferences;

public class MyPreference {
    private static final String NAME="kakeibo";

    public SharedPreferences get(Context context) {
        return context.getSharedPreferences(NAME, Context.MODE_PRIVATE);
    }
}
