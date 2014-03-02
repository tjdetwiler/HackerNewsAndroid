package com.detwiler.hackernews;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class HnPostView extends RelativeLayout {
    private TextView mTitle;
    private TextView mPoints;
    private TextView mAuthor;

    @SuppressWarnings("unused")
    public HnPostView(Context context) {
        this(context, null);
    }

    public HnPostView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HnPostView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mTitle = (TextView) findViewById(R.id.title);
        mPoints = (TextView) findViewById(R.id.points);
        mAuthor = (TextView) findViewById(R.id.author);
    }

    public void setPost(final HnSubmission submission) {
        setTitle(submission.getTitle());
        setPoints(submission.getPoints());
        setAuthor(submission.getUsername());
    }

    public void setTitle(final String title) {
        mTitle.setText(title);
    }

    public void setPoints(final int points) {
        final String text = "" + points + " points";
        mPoints.setText(text);
    }

    public void setAuthor(final String username) {
        mAuthor.setText(username);
    }
}
