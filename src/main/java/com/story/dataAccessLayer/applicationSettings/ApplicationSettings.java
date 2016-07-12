package com.story.dataAccessLayer.applicationSettings;

/**
 * Created by alex on 12.07.16.
 * Class which stored settings of application
 */
public class ApplicationSettings {
    private int screenWidth;
    private int screenHeight;
    private boolean isFullScreen;

    public ApplicationSettings(){
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

    public boolean isFullScreen() {
        return isFullScreen;
    }

    public void setFullScreen(boolean fullScreen) {
        isFullScreen = fullScreen;
    }
}
