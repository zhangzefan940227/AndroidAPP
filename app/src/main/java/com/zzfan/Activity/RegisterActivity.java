package com.zzfan.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.zzfan.Class.User;
import com.zzfan.Class.UserService;
import com.zzfan.SQLite.DatabaseHelper;

import java.util.ArrayList;

public class RegisterActivity extends Activity {
    private static final String TAG = "RegisterActivity";
    private Button mRegisterBtn;
    private EditText mUnameEdit, mPwdEdit, mPwd2Edit;
    private TextView mEmptyTv, mPwdDiffTv, mUnameExistTv;
    private String mName = "";
    private String mPwd = "";
    private String mPwd2 = "";
    private UserService uService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initViews();
    }

    private void initViews() {
        mRegisterBtn = findViewById(R.id.register_btn2);
        mUnameEdit = findViewById(R.id.register_uname);
        mPwdEdit = findViewById(R.id.register_pwd);
        mPwd2Edit = findViewById(R.id.register_pwd2);
        mEmptyTv = findViewById(R.id.pwd_check3_tv);
        mPwdDiffTv = findViewById(R.id.pwd_check_tv);
        mUnameExistTv = findViewById(R.id.pwd_check2_tv);

        uService = new UserService(RegisterActivity.this);

        mRegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mName = mUnameEdit.getText().toString().trim();
                mPwd = mPwdEdit.getText().toString().trim();
                mPwd2 = mPwd2Edit.getText().toString().trim();
                if (TextUtils.isEmpty(mName) || TextUtils.isEmpty(mPwd) || TextUtils.isEmpty(mPwd2)) {
                    mEmptyTv.setVisibility(View.VISIBLE);
                    mPwdDiffTv.setVisibility(View.INVISIBLE);
                    mUnameExistTv.setVisibility(View.INVISIBLE);
                } else if (!mPwd.equals(mPwd2)) {
                    mEmptyTv.setVisibility(View.INVISIBLE);
                    mPwdDiffTv.setVisibility(View.VISIBLE);
                    mUnameExistTv.setVisibility(View.INVISIBLE);
                } else if (queryData(mName)) {
                    mEmptyTv.setVisibility(View.INVISIBLE);
                    mPwdDiffTv.setVisibility(View.INVISIBLE);
                    mUnameExistTv.setVisibility(View.VISIBLE);
                } else {
                    UserService userService = new UserService(RegisterActivity.this);
                    User user = new User();
                    user.setUsername(mName);
                    user.setPassword(mPwd);
                    uService.register(user);
                    Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    //注册用
    public boolean queryData(String name) {
        DatabaseHelper dbHelper = new DatabaseHelper(RegisterActivity.this);
        SQLiteDatabase sqLiteDatabase = dbHelper.getReadableDatabase();
        ArrayList<String> unameList = uService.getAll();
        if (unameList.size() == 0) {
            return false;
        }
        for (int i = 0; i < unameList.size(); i++) {
            if (unameList.get(i).equals(name)) {
                return true;
            }
        }
        return false;
    }
}