package com.example.panchamkhaitan.svfitness;

/**
 * Created by panchamkhaitan on 31/01/17.
 */
public class AttendanceContacts {
    private String firstName;

    public AttendanceContacts(String firstName) {
        this.setFirstName(firstName);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
