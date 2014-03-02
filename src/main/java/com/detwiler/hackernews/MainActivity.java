package com.detwiler.hackernews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import com.detwiler.hackernews.server.HnPostListDocument;
import com.detwiler.hackernews.server.HnSessionManager;

import java.io.IOException;

public class MainActivity extends Activity implements HnSessionManager.CredentialDelegate {
    private ListView mListView;
    private SubmissionListAdapter mAdapter;
    private HnScraper mScraper = HnConnectionManager.getInstance().getScraper();
    private HnPostListDocument mPostList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListView = (ListView) findViewById(android.R.id.list);
        mAdapter = new SubmissionListAdapter(this);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        new AsyncTask<Void, Void, HnPostListDocument>() {
            private ProgressDialog mDialog;
            @Override
            protected void onPreExecute() {
                mDialog = new ProgressDialog(MainActivity.this);
                mDialog.show();
            }

            @Override
            protected HnPostListDocument doInBackground(final Void... voids) {
                try {
                    return mScraper.getTopPosts();
                } catch (final IOException e) {
                    return null;
                }
            }
            @Override
            protected void onPostExecute(final HnPostListDocument postList) {
                mDialog.dismiss();
                if (postList == null) {
                    Toast.makeText(MainActivity.this, "Error loading posts", Toast.LENGTH_LONG).show();
                    return;
                }
                mPostList = postList;
                mAdapter.setPosts(postList.getPosts());
            }
        }.execute();
    }

    @Override
    public String getPasswordForUser(final String username) {
        return "";
    }
}
