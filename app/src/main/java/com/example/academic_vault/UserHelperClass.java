package com.example.academic_vault;

public class UserHelperClass {
    String firstName, surname, studentNo, courseName, password;

    public UserHelperClass() {
    }

    public UserHelperClass(String firstName, String surname, String studentNo, String courseName, String password) {
        this.firstName = firstName;
        this.surname = surname;
        this.studentNo = studentNo;
        this.courseName = courseName;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getStudentNo() {
        return studentNo;
    }

    public void setStudentNo(String studentNo) {
        this.studentNo = studentNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
