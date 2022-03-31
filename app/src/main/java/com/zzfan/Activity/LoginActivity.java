package com.zzfan.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.zzfan.Class.UserService;
import com.zzfan.SQLite.DatabaseHelper;

import java.util.ArrayList;

public class LoginActivity extends Activity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private ArrayList<String> usernameList;

    private Button mLoginButton, mRegisterButton;
    private EditText mUsernameEdit, mPwdEdit;

    private UserService uService = null;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        dbHelper.getWritableDatabase();
        initViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initViews() {
        mLoginButton = findViewById(R.id.login_btn);
        mRegisterButton = findViewById(R.id.register_btn);
        mUsernameEdit = findViewById(R.id.login_input_username);
        mPwdEdit = findViewById(R.id.login_input_password);
        uService = new UserService(LoginActivity.this);

        mLoginButton.setOnClickListener(this);
        mRegisterButton.setOnClickListener(this);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn:
                boolean flag = false;
                String name = mUsernameEdit.getText().toString();
                String pwd = mPwdEdit.getText().toString();
                if (name.isEmpty() || pwd.isEmpty()) {
                    Toast.makeText(this, "账号或密码为空, 请重新输入", Toast.LENGTH_LONG).show();
                } else {
                    flag = uService.login(name, pwd);
                }
                if (flag) {
                    Log.i(TAG, "登录成功");
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(this, "登录成功", Toast.LENGTH_LONG).show();
                } else {
                    Log.i(TAG, "登录失败");
                    Toast.makeText(this, "登录失败", Toast.LENGTH_LONG).show();
                }
                break;
            case R.id.register_btn:
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                break;
            case R.id.login_input_username:

                break;
            case R.id.login_input_password:
                break;



        }
    }
}