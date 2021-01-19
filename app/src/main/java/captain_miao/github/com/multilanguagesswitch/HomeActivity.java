package captain_miao.github.com.multilanguagesswitch;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import captain_miao.github.com.multilanguagesswitch.utils.AppLanguageUtils;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int CHANGE_LANGUAGE_REQUEST_CODE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home);
        ActionBar actionBar = getSupportActionBar();

        if(actionBar != null) {
            actionBar.setTitle(R.string.pref_setting_customize);
        }

        TextView textView1 = findViewById(R.id.tv_content1);
        textView1.setText(R.string.pref_setting_customize);// 默认语言
        textView1.setOnClickListener(this);

        TextView textView2 = findViewById(R.id.tv_content2);
        textView2.setText(getApplication().getString(R.string.pref_setting_customize)); // 设置的语言
        textView2.setOnClickListener(this);
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(AppLanguageUtils.attachBaseContext(newBase, App.getInstances().getAppLanguage(newBase)));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == CHANGE_LANGUAGE_REQUEST_CODE) {
            recreate();
        }
    }

    @Override
    public void onClick(View v) {
        startActivityForResult(new Intent(HomeActivity.this, LanguagesActivity.class), CHANGE_LANGUAGE_REQUEST_CODE);
    }
}
