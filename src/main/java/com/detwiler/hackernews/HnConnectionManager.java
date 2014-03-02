package com.detwiler.hackernews;

import java.io.IOException;
import java.util.*;

/**
 * Provides some simple in-memory caching of data.
 */
public class HnConnectionManager {
    private static final int POST_CACHE_SIZE = 20;
    private static HnConnectionManager sInstance;

    private HnScraper mHnScraper;
    private Map<HnPostCategory, HnSubmissionList> mCachedSubmissions;
    private LinkedHashMap<String, HnPostDocument> mCachedPosts = new LinkedHashMap<String, HnPostDocument>() {
        @Override
        protected boolean removeEldestEntry(final LinkedHashMap.Entry<String, HnPostDocument> eldest) {
            return size() > POST_CACHE_SIZE;
        }
    };

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
        mCachedSubmissions = new EnumMap<>(HnPostCategory.class);
    }

    public HnSubmissionList getSubmissionList(final HnPostCategory category) {
        HnSubmissionList submissionList = mCachedSubmissions.get(category);
        if (submissionList == null) {
            submissionList = new HnSubmissionList(mHnScraper, category);
            mCachedSubmissions.put(category, submissionList);
        }
        return submissionList;
    }

    public HnPostDocument getPost(final String id) throws IOException {
        HnPostDocument post = mCachedPosts.get(id);
        if (post == null) {
            post = mHnScraper.getPost(id);
            mCachedPosts.put(id, post);
        }
        return post;
    }
}
