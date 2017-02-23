package com.example.panchamkhaitan.svfitness;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by panchamkhaitan on 24/01/17.
 */
public class ContactsAdapter extends ArrayAdapter {

    List list = new ArrayList();

    public ContactsAdapter(Context context, int resource) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        View row;
        row = convertView;
        ContactHolder contactHolder;
        if(row == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = layoutInflater.inflate(R.layout.row_layout, parent, false);
            contactHolder = new ContactHolder();

            contactHolder.tx_firstName = (TextView) row.findViewById(R.id.firstName);
            contactHolder.tx_lastName = (TextView) row.findViewById(R.id.lastName);
            contactHolder.tx_parentName = (TextView) row.findViewById(R.id.parentName);
            contactHolder.tx_parentEmail = (TextView) row.findViewById(R.id.parentEmail);
            contactHolder.tx_studentAge = (TextView) row.findViewById(R.id.studentAge);
            contactHolder.tx_flatNo = (TextView) row.findViewById(R.id.flatNo);
            row.setTag(contactHolder);
        } else {
            contactHolder = (ContactHolder) row.getTag();
        }

        Contacts contacts = (Contacts) this.getItem(position);
        contactHolder.tx_firstName.setText(contacts.getFirstName());
        contactHolder.tx_lastName.setText(contacts.getLastName());
        contactHolder.tx_parentName.setText(contacts.getParentName());
        contactHolder.tx_parentEmail.setText(contacts.getParentEmail());
        contactHolder.tx_studentAge.setText(contacts.getStudentAge());
        contactHolder.tx_flatNo.setText(contacts.getFlatNo());
        return row;
    }

    static class ContactHolder {
        TextView tx_firstName, tx_lastName, tx_parentName, tx_parentEmail, tx_studentAge, tx_flatNo;
    }
}
