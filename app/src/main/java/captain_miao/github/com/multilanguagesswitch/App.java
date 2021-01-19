package captain_miao.github.com.multilanguagesswitch;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.util.Locale;

import captain_miao.github.com.multilanguagesswitch.constants.ConstantLanguages;
import captain_miao.github.com.multilanguagesswitch.utils.AppLanguageUtils;

/**
 * @author YanLu
 * @since 17/5/12
 */

public class App extends Application {

    private static App sInstances;
    private static Context sContext;

    public static App getInstances() {
        return sInstances;
    }

    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sInstances = this;
        sContext = this;
        onLanguageChange();
    }

    @Override
    protected void attachBaseContext(Context base) {
        String language = getAppLanguage(base);
        if(!TextUtils.isEmpty(language)) {
            super.attachBaseContext(AppLanguageUtils.attachBaseContext(base, language));
        } else {
            super.attachBaseContext(base);
        }
    }

    /**
     * Handling Configuration Changes
     * @param newConfig newConfig
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        onLanguageChange();
    }

    private void onLanguageChange() {
        AppLanguageUtils.changeAppLanguage(this, AppLanguageUtils.getSupportLanguage(getAppLanguage(this)));
    }

    public String getAppLanguage(Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String curLanguage = sharedPreferences.getString(context.getString(R.string.app_language_pref_key), "");

        if(TextUtils.isEmpty(curLanguage)) {
            // 读取默认语言
            Locale locale = Locale.getDefault();
            for (String key : AppLanguageUtils.mAllLanguages.keySet()) {
                if (TextUtils.equals(AppLanguageUtils.mAllLanguages.get(key).getLanguage(), locale.getLanguage())) {
                    curLanguage = locale.getLanguage();
                    break;
                }
            }

            if(!TextUtils.isEmpty(curLanguage)) {
                sharedPreferences.edit().putString(context.getString(R.string.app_language_pref_key), curLanguage).apply();
            }
        }


        return TextUtils.isEmpty(curLanguage) ? ConstantLanguages.ENGLISH : curLanguage;
    }
}
