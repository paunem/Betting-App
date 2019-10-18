package com.example.beteasier.matchesThings;

public class Match {
private String teamName1, teamName2, date, time, rate1, rate2, category;

    public Match(){}

    public Match(String teamName1, String teamName2, String date, String time, String rate1, String rate2, String category) {
        this.teamName1 = teamName1;
        this.teamName2 = teamName2;
        this.date = date;
        this.time = time;
        this.rate1 = rate1;
        this.rate2 = rate2;
        this.category = category;
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
}
