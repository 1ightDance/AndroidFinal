package com.example.lightdance.androidfinal.page.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lightdance.androidfinal.R;
import com.example.lightdance.androidfinal.dao.DataBase;
import com.example.lightdance.androidfinal.page.note.MainActivity;

import java.util.Date;

public class LoginActivity extends AppCompatActivity {

    private EditText account;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        new DataBase(this);
        account = findViewById(R.id.account);
        password = findViewById(R.id.password);
        Button login = findViewById(R.id.login);
        loginEvent(login);
    }

    private void loginEvent(Button button) {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if ("".equals(account.getText().toString())) {
                    Toast.makeText(getApplication(), new Date().toString(), Toast.LENGTH_LONG);
                    Toast.makeText(getApplication(), "请填写账户", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("".equals(password.getText().toString())) {
                    Toast.makeText(getApplication(), "请填写密码", Toast.LENGTH_LONG).show();
                    return;
                }
                if ("hdu".equals(account.getText().toString()) && "hdu".equals(password.getText().toString())) {
                    Intent intent = MainActivity.getIntent(LoginActivity.this);
                    intent.putExtra("load", true);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(getApplication(), "账户密码错误", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
