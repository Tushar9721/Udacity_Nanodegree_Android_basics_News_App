package com.example.abcnews;

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

/**
 * @author tusha
 */

public class QueryUtils {

    public static List<DataClassNews> getAbcLiveNews(String guardianString) {

        URL url = developURL(guardianString);
        String jsonResponse = null;
        try {
            jsonResponse = httpRequestConnection(url);
        } catch (IOException e) {
            Log.e("jsonResponse err Msg", e.getMessage());
        }

        return getDataFromGuardianLink(jsonResponse);
    }

    //making the connection.....
    private static String httpRequestConnection(URL url) throws IOException {

        String guardianResponse = null;
        if (url == null) {
            return guardianResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                guardianResponse = readData(inputStream);
            }
        } catch (IOException e) {
            Log.e("getting Data Problem.", e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return guardianResponse;

    }

    //reading data line by line....
    private static String readData(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                stringBuilder.append(line);
                line = reader.readLine();
            }
        }
        return stringBuilder.toString();

    }

    //fetching data from the link.....
    private static List<DataClassNews> getDataFromGuardianLink(String jsonResponse) {
        ArrayList<DataClassNews> arrayListAbcNews = new ArrayList<>();
        try {
            JSONObject bResponse = new JSONObject(jsonResponse);
            JSONObject getResult = bResponse.getJSONObject("response");
            JSONArray abcNewsArrayResult = getResult.getJSONArray("results");

            for (int newData = 0; newData < abcNewsArrayResult.length(); newData++) {
                JSONObject jsonObject = abcNewsArrayResult.getJSONObject(newData);

                String abcNewsAuthor = "";
                //to get the author details...
                JSONArray authorArray = jsonObject.getJSONArray("tags");
                if (authorArray.length() == 0) {
                    abcNewsAuthor = "None";
                } else {

                    for (int details = 0; details < authorArray.length(); details++) {
                        JSONObject innerObjectAuthor = authorArray.getJSONObject(details);
                        abcNewsAuthor = innerObjectAuthor.getString("webTitle");
                    }

                }

                String newsTagLine = jsonObject.getString("webTitle");
                String newsTypeName = jsonObject.getString("sectionName");
                String newsDate = jsonObject.optString("webPublicationDate");
                String newsUrl = jsonObject.getString("webUrl");


                //splitting the data and time
                String[] abcNewsTime = newsDate.split("T");
                String dateOnly = abcNewsTime[0];
                String time = abcNewsTime[1];
                String timeUpdated = time.substring(0, 5);

                DataClassNews newsData = new DataClassNews(abcNewsAuthor,newsTagLine, dateOnly, newsTypeName, newsUrl, timeUpdated);
                arrayListAbcNews.add(newsData);
                Log.e("size", "1");

            }


        } catch (JSONException e) {
            Log.i("Getting Exception: ", e.getMessage());
        }
        return arrayListAbcNews;
    }


    public static URL developURL(String guardianString) {
        URL url = null;
        try {
            url = new URL(guardianString);
        } catch (MalformedURLException e) {
            Log.e("Exception making url", e.getMessage());
        }
        return url;
    }

}
