package com.example.udacity_sixth_app;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class QueryUtils {


    public static final String LOG_TAG = QueryUtils.class.getSimpleName();

    private QueryUtils() {
    }




    private static List<Class> extractFeatureFromJson(String JSON) {
        if (TextUtils.isEmpty(JSON)) {
            return null;
        }
        List<Class> news = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(JSON);
            JSONArray Array = baseJsonResponse.getJSONArray("response");
            for (int i = 0; i < Array.length(); i++) {
                JSONObject currentnews = Array.getJSONObject(i);
                JSONObject results = currentnews.getJSONObject("results");

                String title = results.getString("webTitle");
                String section = results.getString("sectionName");

                long time = results.getLong("webPublicationDate");
                String url = results.getString("webUrl");

                JSONArray tagsauthor = results.getJSONArray("tags");
                String author="";
                if (tagsauthor.length()!= 0) {
                    JSONObject currenttagsauthor = tagsauthor.getJSONObject(0);
                    author = currenttagsauthor.getString("webTitle");
                }else{
                    author = "No Author ..";
                }

                Class xclass = new Class(title, section,time, url,author);
                news.add(xclass);
            }
        } catch (JSONException e) {
            Log.e("QueryUtils", "Problem parsing JSON results", e);
        }
        return news;
    }





    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }
        return url;
    }





    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }
        }
        return jsonResponse;
    }






    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }







    public static List<Class> fetchData(String requestUrl) {

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        List<Class> news = extractFeatureFromJson(jsonResponse);

        return news ;
    }


    }
