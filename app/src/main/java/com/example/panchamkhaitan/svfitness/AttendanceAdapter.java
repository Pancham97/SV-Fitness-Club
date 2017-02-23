package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by panchamkhaitan on 31/01/17.
 */
public class AttendanceAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public AttendanceAdapter(Context context, int resource) {
        super(context, resource);
    }


    public void add(Object object) {
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.attendance_layout, parent, false);
            contactHolder = new ContactHolder();

            contactHolder.StudentName = (TextView) row.findViewById(R.id.firstName);
            contactHolder.presentButton = (Button) row.findViewById(R.id.presentButton);
            contactHolder.absentButton = (Button)  row.findViewById(R.id.absentButton);

            final View finalRow = row;
            contactHolder.presentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout relativeLayout = (RelativeLayout) finalRow.findViewById(R.id.relativeLayout);
                    relativeLayout.setBackgroundColor(Color.parseColor("#27ae60"));
                    Toast.makeText(getContext(), "Present button was clicked at " + position, Toast.LENGTH_SHORT).show();
                    String attend = "P";
//                    Intent intent = new Intent(getContext(), AddAttendance.class);
//                    intent.putExtra("attendance", attend);

                    Intent myIntent = new Intent(getContext(), AddAttendance.class);
                    myIntent.putExtra("attend", attend);
                }
            });

            contactHolder.absentButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout relativeLayout = (RelativeLayout) finalRow.findViewById(R.id.relativeLayout);
                    relativeLayout.setBackgroundColor(Color.parseColor("#c0392b"));
                    Toast.makeText(getContext(), "Absent button was clicked at " + position, Toast.LENGTH_SHORT).show();
                    String attend = "A";

                    Intent myIntent = new Intent(getContext(), AddAttendance.class);
                    myIntent.putExtra("attend", attend);

//                    Intent intent = new Intent(getContext(), AddAttendance.class);
//                    intent.putExtra("attendance", attend);
                }
            });
            row.setTag(contactHolder);
        } else {
            contactHolder = (ContactHolder) row.getTag();
        }

        AttendanceContacts attendanceContacts = (AttendanceContacts) this.getItem(position);
        contactHolder.StudentName.setText(attendanceContacts.getFirstName());

        return row;
    }

    static class ContactHolder {
        TextView StudentName;
        Button presentButton, absentButton;
    }
}

