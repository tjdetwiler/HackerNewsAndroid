package com.detwiler.hackernews;

public class HnConnectionManager {
    private static HnConnectionManager sInstance;

    private HnScraper mHnScraper;

    public static HnConnectionManager getInstance() {
        if (sInstance == null) {
            synchronized (HnConnectionManager.class) {
                if (sInstance == null) {
                    sInstance = new HnConnectionManager();
                }
            }
        }
        return sInstance;
    }

    public HnConnectionManager() {
        mHnScraper = new HnScraper();
    }

    public HnScraper getScraper() {
        return mHnScraper;
    }
}
