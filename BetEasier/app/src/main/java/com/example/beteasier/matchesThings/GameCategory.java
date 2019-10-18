package com.example.beteasier.matchesThings;

public class GameCategory {
    private String gameTitle;
    private String imageUrl;
    private int gameCount;

    public GameCategory(){}

    public GameCategory(String title, String imageUrl, int gameCount) {
        this.gameTitle = title;
        this.gameCount = gameCount;
        this.imageUrl = imageUrl;
    }

    public String getGameTitle() {
        return gameTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getGameCount() {
        return gameCount;
    }

}
