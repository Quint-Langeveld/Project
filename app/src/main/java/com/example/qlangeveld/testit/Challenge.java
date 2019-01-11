package com.example.qlangeveld.testit;

import java.io.Serializable;

public class Challenge implements Serializable {

    private String challege;
    private String amountOfTime;
    private String periodOfTime;
    private String amountOfNotifications;
    private String periodOfNotifications;
    private String state;


    public Challenge(String challenge, String amountOfTime, String periodOfTime, String amountOfNotifications, String periodOfNotifications, String state) {
        this.challege = challenge;
        this.amountOfTime = amountOfTime;
        this.periodOfTime = periodOfTime;
        this.amountOfNotifications = amountOfNotifications;
        this.periodOfNotifications = periodOfNotifications;
        this.state = state;
    }

    public String getChallege() {
        return challege;
    }

    public String getAmountOfTime() {
        return amountOfTime;
    }

    public String getPeriodOfTime() {
        return periodOfTime;
    }

    public String getAmountOfNotifications() {
        return amountOfNotifications;
    }

    public String getPeriodOfNotifications() {
        return periodOfNotifications;
    }

    public String getState() {
        return state;
    }

    public void setChallege(String challege) {
        this.challege = challege;
    }

    public void setAmountOfTime(String amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    public void setPeriodOfTime(String periodOfTime) {
        this.periodOfTime = periodOfTime;
    }

    public void setAmountOfNotifications(String amountOfNotifications) {
        this.amountOfNotifications = amountOfNotifications;
    }

    public void setPeriodOfNotifications(String periodOfNotifications) {
        this.periodOfNotifications = periodOfNotifications;
    }

    public void setState(String state) {
        this.state = state;
    }
}
