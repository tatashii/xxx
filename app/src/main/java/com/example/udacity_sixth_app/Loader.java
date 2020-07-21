package com.example.udacity_sixth_app;


import android.content.Context;

import androidx.loader.content.AsyncTaskLoader;

import java.util.List;


public class Loader extends AsyncTaskLoader<List<Class>> {

    private String mUrl;


    public Loader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    /**
     * This is on a background thread.
     */
    @Override
    public List<Class> loadInBackground() {
        if (mUrl == null) {
            return null;
        }

        List<Class> news = QueryUtils.fetchData(mUrl);
        return news;
    }
}