package com.example.udacity_sixth_app;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;


import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;

import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Class>> {



    private static final int GUARDIAN_LOADER_ID = 1;
    ListView listView;
    private TextView mEmptyStateTextView;
    ProgressBar progressBar;
    private Adapter mAdapter;
    private static final String GUARDIAN_URL =
   "https://content.guardianapis.com/search?q=debate&tag=politics/politics&from-date=2014-01-01&api-key=test";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Class> list = new ArrayList<Class>();
        progressBar = findViewById(R.id.loading_indicator);
        listView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = findViewById(R.id.empty_view);
        listView.setEmptyView(mEmptyStateTextView);


        mAdapter = new Adapter(this, list);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Class xclass = mAdapter.getItem(position);

                Uri uri = Uri.parse(xclass.getmUrl());

                Intent websiteIntent = new Intent(Intent.ACTION_VIEW, uri);

                startActivity(websiteIntent);
            }
        });


        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            getSupportLoaderManager().initLoader(GUARDIAN_LOADER_ID, null, this);
        } else {
            progressBar = findViewById(R.id.loading_indicator);
            progressBar.setVisibility(View.GONE);
            mEmptyStateTextView.setText(R.string.no_connection);
        }
    }


    @Override
    public Loader<List<Class>> onCreateLoader(int i, Bundle bundle) {

        return new Loader(this, GUARDIAN_URL);
    }

    @Override
    public void onLoadFinished(Loader<List<Class>> loader, List<Class> news) {

        mEmptyStateTextView.setText(R.string.no_connection);
        progressBar=findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);
        mAdapter.clear();


        if (news != null && !news.isEmpty()) {
            mAdapter.addAll(news);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<Class>> loader) {

        mAdapter.clear();
    }

}