package com.detwiler.hackernews;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ListView;
import com.detwiler.hackernews.model.HnComment;
import com.detwiler.hackernews.server.HnPostDocument;

import java.io.IOException;
import java.util.Collections;

public class SubmissionDetailActivity extends Activity {
    private CommentListAdapter mAdapter;
    private HnConnectionManager mConnectionManager = HnConnectionManager.getInstance();

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submission_detail);
        ListView listView = (ListView) findViewById(android.R.id.list);
        mAdapter = new CommentListAdapter(this);
        listView.setAdapter(mAdapter);
        initializeFromIntent();
    }

    @Override
    protected void onNewIntent(final Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        mAdapter.setPosts(Collections.<HnComment>emptyList());
        initializeFromIntent();
    }

    private void initializeFromIntent() {
        final Intent intent = getIntent();
        final String postId = intent.getStringExtra("POST_ID");
        new AsyncTask<Void, Void, HnPostDocument>() {
            private ProgressDialog mDialog;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mDialog = new ProgressDialog(SubmissionDetailActivity.this);
                mDialog.show();
            }

            @Override
            protected HnPostDocument doInBackground(final Void... voids) {
                try {
                    return mConnectionManager.getPost(postId);
                } catch (final IOException e) {
                    return null;
                }
            }

            @Override
            protected void onPostExecute(HnPostDocument hnPostDocument) {
                super.onPostExecute(hnPostDocument);
                mDialog.hide();
                mAdapter.setPosts(hnPostDocument.getComments());
            }
        }.execute();
    }
}
