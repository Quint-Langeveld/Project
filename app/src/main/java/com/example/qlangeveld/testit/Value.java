package com.example.qlangeveld.testit;

public class Value {

    private String challenge;
    private int succeeded;
    private float feeling;

    public Value(String challenge, int succeeded, float feeling) {
        this.challenge = challenge;
        this.succeeded = succeeded;
        this.feeling = feeling;
    }

    public String getChallenge() {
        return challenge;
    }

    public int getSucceeded() {
        return succeeded;
    }

    public float getFeeling() {
        return feeling;
    }

    public void setChallenge(String challenge) {
        this.challenge = challenge;
    }

    public void setSucceeded(int succeeded) {
        this.succeeded = succeeded;
    }

    public void setFeeling(int feeling) {
        this.feeling = feeling;
    }
}
