package com.detwiler.hackernews;

import android.widget.BaseAdapter;

import java.util.List;

public abstract class PostListAdapter<T extends HnPost> extends BaseAdapter{
    private List<T> mList;

    public void setPosts(final List<T> posts) {
        mList = posts;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public Object getItem(final int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
