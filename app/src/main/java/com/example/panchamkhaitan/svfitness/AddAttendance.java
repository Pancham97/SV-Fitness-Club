package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.panchamkhaitan.svfitness.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddAttendance extends AppCompatActivity {

    // EditText e_firstName;

    String studentPres;
    String firstName, attend = "P";
    //    String curDate = DateFormat.getDateInstance().format(new Date());
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    String currentDateTime = sdf.format(new Date());
    String json_string;
    JSONArray jsonArray;
    JSONObject jsonObject;
    AttendanceAdapter attendanceAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_attendance);

        ListView listView;

        attendanceAdapter = new AttendanceAdapter(this, R.layout.attendance_layout);
        listView = (ListView) findViewById(R.id.list_item);
        listView.setAdapter(attendanceAdapter);

        TextView date = (TextView) findViewById(R.id.dateTextView);
        date.setText(currentDateTime);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        studentPres = bundle.getString("attend");

        json_string = getIntent().getExtras().getString("json_data");
        String firstName;

        try {
            jsonObject = new JSONObject(json_string);
            int count = 0;
            jsonArray = jsonObject.getJSONArray("Student");
            while (count < jsonArray.length()) {
                JSONObject JO = jsonArray.getJSONObject(count);
                firstName = JO.getString("firstName");

                AttendanceContacts attendanceContacts = new AttendanceContacts(firstName);
                attendanceAdapter.add(attendanceContacts);
                count++;
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        }
    }

//    public String studentAbsent(View view) {
//        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
//        relativeLayout.setBackgroundColor(Color.parseColor("#c0392b"));
//
//        TextView name = (TextView)findViewById(R.id.firstName);
//        name.setTextColor(Color.WHITE);
//
//        attend = "A";
//        return attend;
//    }
//
//    public String studentPresent(View view) {
//        RelativeLayout relativeLayout = (RelativeLayout)findViewById(R.id.relativeLayout);
//        relativeLayout.setBackgroundColor(Color.parseColor("#27ae60"));
//
//        TextView name = (TextView)findViewById(R.id.firstName);
//        name.setTextColor(Color.WHITE);
//
//        attend = "P";
//        return attend;
//    }

//    public String attend(String attendance) {
//        TextView reportTextView = (TextView)findViewById(R.id.reportTextView);
//        reportTextView.setText(attendance);
//        return attendance;
//    }

    public void updateAttendance(View view) {
        // firstName = e_firstName.getText().toString();
        attend = studentPres;

        String method = "register";
        BackgroundTask backgroundTask = new BackgroundTask(this);
        backgroundTask.execute(method, currentDateTime, attend);
        finish();
    }

    public class BackgroundTask extends AsyncTask<String, Void, String> {
        Context context;

        BackgroundTask(Context context) {
            this.context = context;
        }

        @Override
        protected String doInBackground(String... params) {
            String reg_url = "http://svfitnessclub.000webhostapp.com/attend.php";
            String method = params[0];

            if (method.equals("register")) {
//                String firstName = params[1];
                String date = params[1];
                String checkAttendance = params[2];

                try {
                    URL url = new URL(reg_url);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream os = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));

                    String data = URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
//                            URLEncoder.encode("date", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8") + "&" +
                            URLEncoder.encode("attend", "UTF-8") + "=" + URLEncoder.encode(checkAttendance, "UTF-8");

                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    os.close();

                    InputStream is = httpURLConnection.getInputStream();
                    is.close();
                    return "Attendance updated!";
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
