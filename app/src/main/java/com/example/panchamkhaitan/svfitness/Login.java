package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInstaller;
import android.graphics.Typeface;
import android.media.tv.TvInputService;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.jar.Attributes;


public class Login extends AppCompatActivity {

    String parse_string;
    private EditText u_email, u_password;
    public static final String MyPREFERENCES = "MyPrefs";
    public static final String Email = "emailKey";
    public static final String Password = "passwordKey";

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        TextView tx = (TextView)findViewById(R.id.login_heading);
        Typeface head_font = Typeface.createFromAsset(getAssets(), "fonts/abc.ttf");
        tx.setTypeface(head_font);

        TextView bt = (TextView)findViewById(R.id.login);
        Typeface font = Typeface.createFromAsset(getAssets(), "fonts/abc.ttf");
        bt.setTypeface(font);

        u_email = (EditText) findViewById(R.id.email);
        u_password = (EditText) findViewById(R.id.password);

        sharedPreferences = getApplicationContext().getSharedPreferences(MyPREFERENCES, MODE_PRIVATE);
    }

    public void login(View view) {
        String email = u_email.getText().toString();
        String password = u_password.getText().toString();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(Email, email);
        editor.putString(Password, password);
        editor.commit();

        Log.v("Editor email: ", sharedPreferences.getString(Email, null));
        Log.v("Editor password: ", sharedPreferences.getString(Password, null));

        new SignInActivity(this).execute(email, password);
    }

    class SignInActivity extends AsyncTask<String, Void, String> {
        private Context context;

        public SignInActivity(Context context) {
            this.context = context;
        }
        String link = "http://svfitnessclub.000webhostapp.com/login.php";

        @Override
        protected String doInBackground(String... arg0) {
            try {
                String parentEmail = (String) arg0[0];
                String randPwd = (String) arg0[1];


                String data = URLEncoder.encode("parentEmail", "UTF-8") + "=" +
                        URLEncoder.encode(parentEmail, "UTF-8");
                data += "&" + URLEncoder.encode("randPwd", "UTF-8") + "=" +
                        URLEncoder.encode(randPwd, "UTF-8");

                URL url = new URL(link);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream());

                outputStreamWriter.write(data);
                outputStreamWriter.flush();


//                InputStream inputStream = httpURLConnection.getInputStream();
//                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getErrorStream()));
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));

                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line + "\n");
                }
//                Log.v("Line: ", line);

                bufferedReader.close();
                outputStreamWriter.close();
                httpURLConnection.disconnect();
                Log.v("Returned string: ", stringBuilder.toString().trim());
                return stringBuilder.toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {
            Log.v("First result: ", result);
            Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
            String adminStr = "Hello admin!";
            if (adminStr.equals(result)) {
                Log.v("Result admin: ", result);
                Toast.makeText(context, "Logged in!", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(context, AdminActivity.class);
                intent.addCategory(Intent.CATEGORY_HOME);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            } else {
                parse_string = result;
                Log.v("Result user: ", parse_string);
                Toast.makeText(context,  parse_string, Toast.LENGTH_SHORT).show();

                if(!parse_string.equals("Email or password incorrect!")) {
                    Intent intent = new Intent(context, UserActivity.class);
                    intent.putExtra("json_data", parse_string);
                    Log.v("Parse String: ", parse_string);
                    startActivity(intent);
                    finish();
                }
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
    }
}
