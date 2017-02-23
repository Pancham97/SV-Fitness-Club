package com.example.panchamkhaitan.svfitness;

/**
 * Created by panchamkhaitan on 24/01/17.
 */
public class Contacts {
    private String firstName, lastName, parentName, parentEmail, studentAge, flatNo;

    public Contacts(String firstName, String lastName, String parentName, String parentEmail, String studentAge, String flatNo) {
        this.setFirstName(firstName);
        this.setLastName(lastName);
        this.setParentName(parentName);
        this.setParentEmail(parentEmail);
        this.setStudentAge(studentAge);
        this.setFlatNo(flatNo);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public void setParentEmail(String parentEmail) {
        this.parentEmail = parentEmail;
    }

    public String getStudentAge() {
        return studentAge;
    }

    public void setStudentAge(String studentAge) {
        this.studentAge = studentAge;
    }

    public String getFlatNo() {
        return flatNo;
    }

    public void setFlatNo(String flatNo) {
        this.flatNo = flatNo;
    }
}
