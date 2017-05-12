package captain_miao.github.com.multilanguagesswitch;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.preference.PreferenceManager;

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
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(base, getAppLanguage(base)));
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

    private String getAppLanguage(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context)
                        .getString(context.getString(R.string.app_language_pref_key), ConstantLanguages.ENGLISH);
    }
}
