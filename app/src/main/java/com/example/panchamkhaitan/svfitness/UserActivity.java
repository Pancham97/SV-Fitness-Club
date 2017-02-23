package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserActivity extends AppCompatActivity {

    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    ContactsAdapter contactsAdapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        contactsAdapter = new ContactsAdapter(this, R.layout.row_layout);
        listView = (ListView) findViewById(R.id.userListView);
        listView.setAdapter(contactsAdapter);

        json_string = getIntent().getExtras().getString("json_data");
        String firstName, lastName, parentName, parentEmail, studentAge, flatNo;

        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("Student");
            while (count<jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                firstName = JO.getString("firstName");
                lastName = JO.getString("lastName");
                parentName = JO.getString("parentName");
                parentEmail = JO.getString("parentEmail");
                studentAge = JO.getString("studentAge");
                flatNo = JO.getString("flatNo");

                Contacts contacts = new Contacts(firstName, lastName, parentName, parentEmail, studentAge, flatNo);
                contactsAdapter.add(contacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public  void logout(View view){
        SharedPreferences sharedpreferences = getSharedPreferences(Login.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();

        Intent intent = new Intent(this, MainActivity.class);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }
}
