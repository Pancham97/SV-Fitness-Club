package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView tx = (TextView)findViewById(R.id.head);
        Typeface custom_font = Typeface.createFromAsset(getAssets(), "fonts/abc.ttf");
        tx.setTypeface(custom_font);

        TextView bt = (TextView)findViewById(R.id.button);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/abc.ttf");
        bt.setTypeface(font);

        SharedPreferences sharedpreferences = getApplicationContext().getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        String email = sharedpreferences.getString("emailKey",null);
        String password = sharedpreferences.getString("passwordKey",null);

        if(email != null && password != null) {
            Log.v("MainActivity: ", "Exists");
            if(email.equals("talktopancham@gmail.com")) {
                Intent intent = new Intent(this, AdminActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
//            else {
//                Intent intent = new Intent(this, UserActivity.class);
//                intent.addCategory(Intent.CATEGORY_HOME);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                startActivity(intent);
//                finish();
//            }
        }
    }

    public void login(View view) {
        Intent intent = new Intent(this, Login.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}
