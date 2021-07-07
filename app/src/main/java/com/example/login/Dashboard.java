package com.example.login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dashboard extends AppCompatActivity {

    TextView tvId, tvName, tvEmail, tvAddress;

    MyDatabaseHelper dbHelper = new MyDatabaseHelper(this);
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        SharedPreferences sharedPref = getSharedPreferences("user-file", Activity.MODE_PRIVATE);
        String userId = sharedPref.getString("login", "noValue");

        tvId = (TextView)findViewById(R.id.userId);
        tvName = (TextView)findViewById(R.id.userName);
        tvEmail = (TextView)findViewById(R.id.userEmail);
        tvAddress = (TextView)findViewById(R.id.userAddress);

        db = dbHelper.getReadableDatabase();
        Cursor user = db.rawQuery("SELECT * FROM user WHERE user_id = ?", new String[] {userId});
        if (user.moveToFirst())
        {
            tvId.append(user.getString(user.getColumnIndex("user_id")));
            tvName.append(user.getString(user.getColumnIndex("name")));
            tvEmail.append(user.getString(user.getColumnIndex("email")));
            tvAddress.append(user.getString(user.getColumnIndex("address")));
        }
        db.close();
    }



    public void logout(View view)
    {
        SharedPreferences sharedPref = getSharedPreferences("user-file", Activity.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("login", "noValue");
        if(editor.commit())
        {
            Intent gotoLogin = new Intent(this, MainActivity.class);
            startActivity(gotoLogin);
        }
        else
        {
            Toast.makeText(this, "Please try again", Toast.LENGTH_SHORT).show();
        }
    }
}