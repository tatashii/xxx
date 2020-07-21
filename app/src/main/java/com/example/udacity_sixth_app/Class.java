package com.example.udacity_sixth_app;

public class Class {

    private String mtitle;
    private String msection;
    private long   mTimeInMilliseconds;
    private String mUrl;
    private String mauthor;

    public Class(String title,String section,
                 long timeInMilliseconds,String url,String author){

        mtitle=title;
        msection=section;
        mauthor=author;
        mTimeInMilliseconds=timeInMilliseconds;
        mUrl=url;

    }


    public String getMtitle() {
        return mtitle;
    }

    public String getMsection() {
        return msection;
    }

    public long getmTimeInMilliseconds() {
        return mTimeInMilliseconds;
    }

    public String getmUrl() {
        return mUrl;
    }

    public String getMauthor() {
        return mauthor;
    }
}
