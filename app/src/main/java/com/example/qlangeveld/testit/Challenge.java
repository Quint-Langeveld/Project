package com.example.qlangeveld.testit;

import java.io.Serializable;

public class Challenge implements Serializable {

    private String challege,  periodOfTime, state, repeat;
    private int amountOfTime, amountOfNotifications, progress;

    public Challenge(String challenge, int amountOfTime, String periodOfTime, int amountOfNotifications, int progress, String state, String repeat) {
        this.challege = challenge;
        this.amountOfTime = amountOfTime;
        this.periodOfTime = periodOfTime;
        this.amountOfNotifications = amountOfNotifications;
        this.progress = progress;
        this.state = state;
        this.repeat = repeat;
    }

    public String getChallege() {
        return challege;
    }

    public int getAmountOfTime() {
        return amountOfTime;
    }

    public String getPeriodOfTime() {
        return periodOfTime;
    }

    public int getAmountOfNotifications() {
        return amountOfNotifications;
    }

    public String getState() {
        return state;
    }

    public String getRepeat() {
        return repeat;
    }

    public int getProgress() {
        return progress;
    }

    public void setChallege(String challege) {
        this.challege = challege;
    }

    public void setAmountOfTime(int amountOfTime) {
        this.amountOfTime = amountOfTime;
    }

    public void setPeriodOfTime(String periodOfTime) {
        this.periodOfTime = periodOfTime;
    }

    public void setAmountOfNotifications(int amountOfNotifications) {
        this.amountOfNotifications = amountOfNotifications;
    }

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setRepeat(String repeat) {
        this.repeat = repeat;
    }
}
