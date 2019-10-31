package com.example.beteasier.matchesThings;

public class Match {
private String teamName1, teamName2, date, time, category, status;
private double rate1, rate2;

    public Match(){}

    public Match(String teamName1, String teamName2, String date, String time, double rate1, double rate2, String category) {
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.date = date;
        this.time = time;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.category = category;
        this.status = "1";
    }

    public String getTeamName1() {
        return teamName1;
    }

    public String getTeamName2() {
        return teamName2;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public double getRate1() {
        return rate1;
    }

    public double getRate2() {
        return rate2;
    }

    public String getCategory() {
        return category;
    }

    public String getStatus() {
        return status;
    }

    public void setTeamName1(String teamName1) {
        this.teamName1 = teamName1;
    }

    public void setTeamName2(String teamName2) {
        this.teamName2 = teamName2;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRate1(double rate1) {
        this.rate1 = rate1;
    }

    public void setRate2(double rate2) {
        this.rate2 = rate2;
    }
}
