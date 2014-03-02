package com.detwiler.hackernews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.detwiler.hackernews.model.HnComment;

public class HnCommentView extends RelativeLayout {
    private TextView mText;
    private View mSpacer;
    private int mReplyIndent;

    @SuppressWarnings("unused")
    public HnCommentView(Context context) {
        this(context, null);
    }

    public HnCommentView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HnCommentView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        mSpacer = findViewById(android.R.id.empty);
        mText = (TextView) findViewById(android.R.id.text1);
        mReplyIndent = getContext().getResources().getDimensionPixelSize(R.dimen.comment_reply_indent);
    }

    public void setComment(final HnComment comment) {
        setupSpacer(comment.getDepth());
        setText(comment.getText());
    }

    public void setupSpacer(final int depth) {
        ViewGroup.LayoutParams lp = mSpacer.getLayoutParams();
        lp.width = depth * mReplyIndent;
        mSpacer.setLayoutParams(lp);
        mSpacer.requestLayout();
    }

    public void setText(final String text) {
        mText.setText(text);
    }
}
