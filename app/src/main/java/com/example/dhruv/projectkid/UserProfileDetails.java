package com.example.dhruv.projectkid;

import java.util.GregorianCalendar;

/**
 * Class to store the details of the User and his child
 */
public class UserProfileDetails {
    private static String parentName;
    private static Long parentPhoneNumber;
    private static String parentEmail;
    private static String parentPassword;
    private static String childName;
    private static GregorianCalendar childBirthDate;
    private static String childGender;

    public void setChildBirthDate(GregorianCalendar calendar){
        childBirthDate = calendar;
    }

    public void setParentName(String name){
        parentName = name;
    }

    public void setParentPhoneNumber(String stringNumber){
        parentPhoneNumber = Long.parseLong(stringNumber);
    }

    public void setParentEmail(String email){
        parentEmail = email;
    }

    public void setParentPassword(String password){
        parentPassword = password;
    }

    public void setChildName(String name){
        childName = name;
    }

    public void setChildGender(String gender){
        childGender = gender;
    }

    public String getParentName(){
        return parentName;
    }

    public Long getParentPhoneNumber() {
        return parentPhoneNumber;
    }

    public String getParentEmail() {
        return parentEmail;
    }

    public String getParentPassword() {
        return parentPassword;
    }

    public String getChildName() {
        return childName;
    }

    public GregorianCalendar getChildBirthDate() {
        return childBirthDate;
    }

    public String getChildGender() {
        return childGender;
    }
}
