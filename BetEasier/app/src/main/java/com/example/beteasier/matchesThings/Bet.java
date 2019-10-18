package com.example.beteasier.matchesThings;

public class Bet {
private String teamName1, teamName2, date, time, rate1, rate2, category, userId;

    public Bet(){}

    public Bet(String teamName1, String teamName2, String date, String time, String rate1, String rate2, String category, String userId) {
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.date = date;
        this.time = time;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.category = category;
        this.userId = userId;
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

    public String getRate1() {
        return rate1;
    }

    public String getRate2() {
        return rate2;
    }

    public String getCategory() {
        return category;
    }

    public String getUserId() {
        return userId;
    }
}
