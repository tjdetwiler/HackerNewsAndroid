package com.detwiler.hackernews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class MainActivity extends Activity implements HnSessionManager.CredentialDelegate {
    private HnConnectionManager mConnectionManager = HnConnectionManager.getInstance();
    private HnSubmissionList mPostList;
    private SubmissionListAdapter mAdapter;
    private AtomicBoolean mLoadInProgress;

    protected View getListFooterView(final ViewGroup list) {
        View v = LayoutInflater.from(this).inflate(R.layout.view_submission_list_footer, list, false);
        if (v == null) {
            return null;
        }
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                loadPostListFromServer();
            }
        });
        return v;
    }

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listView = (ListView) findViewById(android.R.id.list);
        mAdapter = new SubmissionListAdapter(this);
        listView.addFooterView(getListFooterView(listView));
        listView.setAdapter(mAdapter);
        mLoadInProgress = new AtomicBoolean();
        mPostList = mConnectionManager.getSubmissionList(getCategory());
        mAdapter.setPosts(mPostList.getSubmissionList());
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mPostList.getSubmissionList().size() <= 0) {
            loadPostListFromServer();
        }
    }

    @Override
    public String getPasswordForUser(final String username) {
        return "";
    }

    protected HnPostCategory getCategory() {
        return HnPostCategory.TOP;
    }

    private void loadPostListFromServer() {
        if (!mLoadInProgress.compareAndSet(false, true)) {
            return;
        }
        new AsyncTask<Void, Void, Boolean>() {
            private ProgressDialog mDialog;
            @Override
            protected void onPreExecute() {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.show();
            }

            @Override
            protected Boolean doInBackground(final Void... voids) {
                try {
                    mPostList.more();
                    return true;
                } catch (final IOException e) {
                    return false;
                }
            }
            @Override
            protected void onPostExecute(final Boolean success) {
                mDialog.dismiss();
                if (!success) {
                    Toast.makeText(MainActivity.this, "Error loading posts", Toast.LENGTH_LONG).show();
                    return;
                }
                mAdapter.notifyDataSetChanged();
                mLoadInProgress.set(false);
            }
        }.execute();
    }
}
