package com.example.save;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

public class SharePreferencesTest extends AppCompatActivity {
    private EditText acountText,passwordText;
    private Button loginBtn;
    private CheckBox rememberPassword;
    private static final String TAG = "SharePreferencesTest";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_preferences_test);
        initView();
        SharedPreferences sharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);
        boolean isRemember = sharedPreferences.getBoolean("remember_password",false);
        if (isRemember){
            String acount = sharedPreferences.getString("acount_text","default");
            acountText.setText(acount);
            String password = sharedPreferences.getString("password_text","default");
            passwordText.setText(password);
            rememberPassword.setChecked(true);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = getSharedPreferences("login_data",MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        if (rememberPassword.isChecked()){
            Log.d(TAG, "onStop: work");
            editor.putString("acount_text",acountText.getText().toString());
            editor.putString("password_text",passwordText.getText().toString());
            editor.putBoolean("remember_password",true);
        }else {
            editor.clear();
        }
        editor.apply();
    }

    private void initView(){
        acountText = (EditText) findViewById(R.id.acount_text);
        passwordText = (EditText) findViewById(R.id.password_text);
        loginBtn = (Button) findViewById(R.id.login_btn);
        rememberPassword = (CheckBox) findViewById(R.id.remember_password);
    }
}
