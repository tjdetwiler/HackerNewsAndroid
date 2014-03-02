package com.detwiler.hackernews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.detwiler.hackernews.model.HnComment;
import com.detwiler.hackernews.server.HnConnection;

import java.util.ArrayList;
import java.util.List;

public class CommentListAdapter extends PostListAdapter<HnComment> {
    private Context mContext;
    private View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(final View view) {
            final HnComment comment = (HnComment) view.getTag();
            Toast.makeText(mContext, "" + comment.getPostId(), Toast.LENGTH_LONG).show();
        }
    };

    public CommentListAdapter(final Context context) {
        mContext = context;
    }

    @Override
    public void setPosts(final List<HnComment> comments) {
        final List<HnComment> flatList = new ArrayList<>();
        for (HnComment comment : comments) {
            flatPack(flatList, comment);
        }
        super.setPosts(flatList);
    }

    private void flatPack(final List<HnComment> flatList, final HnComment comment) {
        flatList.add(comment);
        for (final HnComment reply : comment.getReplies()) {
            flatPack(flatList, reply);
        }
    }

    @Override
    public View getView(final int position, final View convertView, final ViewGroup parent) {
        final HnComment comment = (HnComment) getItem(position);
        final HnCommentView view;
        if (convertView == null) {
            view = (HnCommentView) LayoutInflater.from(mContext).inflate(R.layout.comment_view, parent, false);
            if (view == null) {
                return null;
            }
            view.setOnClickListener(mClickListener);
        } else {
            view = (HnCommentView) convertView;
        }
        view.setTag(comment);
        view.setComment(comment);
        return view;
    }
}
