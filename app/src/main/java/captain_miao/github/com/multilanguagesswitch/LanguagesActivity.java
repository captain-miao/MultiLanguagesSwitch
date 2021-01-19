package captain_miao.github.com.multilanguagesswitch;


import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.text.TextUtils;
import android.view.MenuItem;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import captain_miao.github.com.multilanguagesswitch.utils.AppLanguageUtils;

public class LanguagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_languages);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setTitle(R.string.pref_setting_customize);
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

        if(savedInstanceState == null) {
            FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.fragment_container, new LanguagesPreferenceFragment()).commitAllowingStateLoss();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, App.getInstances().getAppLanguage(newBase)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class LanguagesPreferenceFragment extends PreferenceFragment {
        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.languages_preference);

            ListPreference preference = (ListPreference) findPreference(getString(R.string.app_language_pref_key));
            preference.setSummary(preference.getEntry());

            SharedPreferences prefs = getPreferenceManager().getSharedPreferences();
            prefs.registerOnSharedPreferenceChangeListener(mPreferenceChangeListener);
        }




        @Override
        public void onDestroy() {
            super.onDestroy();

            getPreferenceManager().getSharedPreferences()
                    .unregisterOnSharedPreferenceChangeListener(mPreferenceChangeListener);
        }


        private final SharedPreferences.OnSharedPreferenceChangeListener mPreferenceChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if (getString(R.string.app_language_pref_key).equals(key)) {
                    ListPreference preference = (ListPreference) findPreference(key);
                    preference.setSummary(preference.getEntry());
                    CharSequence language = preference.getValue();
                    if (!TextUtils.isEmpty(language)) {
                        onChangeAppLanguage(language.toString());

                        sharedPreferences.edit().putString(getString(R.string.app_language_pref_key), language.toString()).commit();
                    }
                }
            }
        };

        private void onChangeAppLanguage(String newLanguage) {
            AppLanguageUtils.changeAppLanguage(getActivity(), newLanguage);
            AppLanguageUtils.changeAppLanguage(App.getContext(), newLanguage);
            getActivity().recreate();
        }
    }
}
