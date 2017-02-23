package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.panchamkhaitan.svfitness.R;

import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class AddMember extends AppCompatActivity {

    EditText e_firstName, e_lastName, e_parentName, e_parentEmail, e_age, e_flatNo;
    String firstName, lastName, parentName, parentEmail, age, flatNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_member);

        e_firstName = (EditText) findViewById(R.id.firstName);
        e_lastName = (EditText) findViewById(R.id.lastName);
        e_parentName = (EditText) findViewById(R.id.parentName);
        e_parentEmail = (EditText) findViewById(R.id.parentEmail);
        e_age = (EditText) findViewById(R.id.age);
        e_flatNo = (EditText) findViewById(R.id.flatNo);
    }

    public void registerStudent(View view) {
        firstName = e_firstName.getText().toString();
        lastName = e_lastName.getText().toString();
        parentName = e_parentName.getText().toString();
        parentEmail = e_parentEmail.getText().toString();
        age = e_age.getText().toString();
        flatNo = e_flatNo.getText().toString();

        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, firstName, lastName, parentName, parentEmail, age, flatNo);
        finish();
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://svfitnessclub.000webhostapp.com/insert.php";
            String method = params[0];

            if (method.equals("register")) {
                String firstName = params[1];
                String lastName = params[2];
                String parentName = params[3];
                String parentEmail = params[4];
                String studentAge = params[5];
                String flatNo = params[6];

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String data = URLEncoder.encode("firstName", "UTF-8") + "=" + URLEncoder.encode(firstName, "UTF-8") + "&" +
                            URLEncoder.encode("lastName", "UTF-8") + "=" + URLEncoder.encode(lastName, "UTF-8") + "&" +
                            URLEncoder.encode("parentName", "UTF-8") + "=" + URLEncoder.encode(parentName, "UTF-8") + "&" +
                            URLEncoder.encode("parentEmail", "UTF-8") + "=" + URLEncoder.encode(parentEmail, "UTF-8") + "&" +
                            URLEncoder.encode("studentAge", "UTF-8") + "=" + URLEncoder.encode(studentAge, "UTF-8") + "&" +
                            URLEncoder.encode("flatNo", "UTF-8") + "=" + URLEncoder.encode(flatNo, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    return "Account added!";
                } catch (Exception e) {
                    return new String("Exception: " + e.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            super.onPostExecute(result);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
}
