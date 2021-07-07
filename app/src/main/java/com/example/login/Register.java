package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    TextView errorView;
    EditText etName, etEmail, etAddress, etPassword, etPasswordRepeat;

    Validation valid = new Validation();

    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try
        {
            this.getSupportActionBar().hide();
        }
        catch (NullPointerException e){}

        setContentView(R.layout.activity_register);

        errorView = (TextView)findViewById(R.id.errorMessage);
        etName = (EditText)findViewById(R.id.name);
        etEmail = (EditText)findViewById(R.id.email);
        etAddress = (EditText)findViewById(R.id.address);
        etPassword = (EditText)findViewById(R.id.password);
        etPasswordRepeat = (EditText)findViewById(R.id.passwordRepeat);
    }

    public void gotoLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void register(View view) {

        String name = etName.getText().toString();
        String email = etEmail.getText().toString();
        String address = etAddress.getText().toString();
        String password = etPassword.getText().toString();
        String passwordRepeat = etPasswordRepeat.getText().toString();

        if (
                !valid.isEmpty(name)
                && !valid.isEmpty(email)
                && !valid.isEmpty(address)
                && !valid.isEmpty(password)
                && !valid.isEmpty(passwordRepeat)
        )
        {
            if (!password.equals(passwordRepeat))
            {
                errorView.setText("Password does not match repeat password");
            }
            else
            {
                errorView.setText("");

                db = dbHelper.getWritableDatabase();

                ContentValues data = new ContentValues();
                data.put("name", name);
                data.put("email", email);
                data.put("password", password);
                data.put("address", address);
                Long row = db.insert("user", null, data);
                db.close();
                if (row > 0)
                {
                    Toast.makeText(this, "User Registered successfully!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(this, MainActivity.class);
                    startActivity(intent);
                }
                else
                {
                    errorView.setText("Registration failed!\nEmail might already registered");
                }
            }
        }
        else
        {
            errorView.setText("Please fill out all the fields");
        }
    }
}