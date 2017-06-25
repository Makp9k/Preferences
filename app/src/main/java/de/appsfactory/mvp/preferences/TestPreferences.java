package de.appsfactory.mvp.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.LabeledIntent;
import android.widget.Toast;

import de.appsfactory.mvp.preferences.annotations.PreferenceOptions;
import de.appsfactory.mvp.preferences.annotations.Preferences;

/**
 * Created by Collider on 24.06.2017.
 */

@Preferences
public interface TestPreferences {

    @PreferenceOptions(defaultStringValue = "12345", adapter = MyPreferenceAdapter.class)
    Preference<LabeledIntent> appName();

    Preference<Integer> buildVersion();

    //
//    Preference<String> asdq();
//
//    @PreferenceOptions(adapter = MyPreferenceAdapter.class, defaultStringValue = "kvzar")
//    Preference<String> ktuk();
//
    class MyPreferenceAdapter implements PreferenceAdapter<LabeledIntent> {
        private Context context;

        @Override
        public void init(Context context) {
            this.context = context;
        }

        @Override
        public LabeledIntent read(SharedPreferences sharedPreferences, String key) {
//            return sharedPreferences.getString(key, defaultValue);
            return null;
        }

        @Override
        public void write(SharedPreferences sharedPreferences, String key, LabeledIntent value) {
//            Toast.makeText(context, value, Toast.LENGTH_SHORT).show();
//            sharedPreferences.edit().putString(key, value).apply();
        }
    }
}
