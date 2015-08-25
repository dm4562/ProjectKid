package com.example.dhruv.projectkid;

import java.util.ArrayList;
import java.util.GregorianCalendar;

/**
 * Class to store the details of the User and his child
 */
public class UserProfileDetails {
    private static String parentName;
    private static String parentPhoneNumber;
    private static String parentEmail;
    private static String parentPassword;
    private static String childName;
    private static GregorianCalendar childBirthDate;
    private static String childGender;
    private static ArrayList<String> completedActivities = new ArrayList<>();

    public void setChildBirthDate(GregorianCalendar calendar){
        childBirthDate = calendar;
    }

    public void setParentName(String name){
        parentName = name;
    }

    public void setParentPhoneNumber(String stringNumber){
        parentPhoneNumber = stringNumber;
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

    public void setCompletedActivities (ArrayList<String> activities) {
        completedActivities = activities;
    }

    public String getParentName(){
        return parentName;
    }

    public String getParentPhoneNumber() {
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

    public ArrayList<String> getCompletedActivities() {
        return completedActivities;
    }
}
