package com.detwiler.hackernews;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class SubmissionListAdapter extends PostListAdapter<HnSubmission> {
    private Context mContext;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final HnSubmission sub = (HnSubmission) view.getTag();
            Intent intent = new Intent(mContext, SubmissionDetailActivity.class);
            intent.putExtra("POST_ID", sub.getPostId());
            mContext.startActivity(intent);
        }
    };

    public SubmissionListAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final HnPostView view;
        final HnSubmission sub = (HnSubmission) getItem(position);
        if (convertView == null) {
            view = (HnPostView) LayoutInflater.from(mContext).inflate(R.layout.post_view, parent, false);
            if (view == null) {
                return null;
            }
            view.setOnClickListener(mClickListener);
        } else {
            view = (HnPostView) convertView;
        }
        view.setPost(sub);
        view.setTag(sub);
        return view;
    }
}
