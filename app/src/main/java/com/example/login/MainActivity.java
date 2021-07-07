package com.example.login;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView errorMessage;
    EditText etEmail, etPassword;

    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    SQLiteDatabase db;

    Validation valid = new Validation();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_main);

        if (checkLogin())
        {
            Intent gotoDashboard = new Intent(this, Dashboard.class);
            startActivity(gotoDashboard);
        }

        errorMessage = (TextView)findViewById(R.id.errorMessage);
        etEmail = (EditText)findViewById(R.id.email);
        etPassword = (EditText)findViewById(R.id.password);
    }

    public void login(View view) {

        String email = etEmail.getText().toString();
        String password = etPassword.getText().toString();

        if (valid.isEmpty(email) || valid.isEmpty(password))
        {
            errorMessage.setText("Please provide email and password");
        }
        else
        {
            db = dbHelper.getReadableDatabase();
            Cursor values = db.rawQuery("SELECT * FROM user WHERE email = ? AND password = ?", new String[] {email, password});
            if (values.moveToFirst())
            {
                String userId = values.getString(values.getColumnIndex("user_id"));
                SharedPreferences sharedPref = getSharedPreferences("user-file", Activity.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString("login", userId);
                editor.commit();

                db.close();

                Intent intent = new Intent(this, Dashboard.class);
                startActivity(intent);
            }
            else
            {
                db.close();
                errorMessage.setText("Invalid email or password");
            }
        }
    }

    public void gotoRegister(View view) {
        Intent intent = new Intent(this, Register.class);
        startActivity(intent);
    }




    public boolean checkLogin()
    {
        SharedPreferences sharedPref = getSharedPreferences("user-file", Activity.MODE_PRIVATE);
        String status = sharedPref.getString("login", "noValue");
        if (status.equals("noValue"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
}