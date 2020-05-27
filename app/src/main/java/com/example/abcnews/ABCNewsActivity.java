package com.example.abcnews;

import android.app.LoaderManager;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * @author tusha
 */


public class ABCNewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<DataClassNews>> {

    RecyclerView newRecyclerView;
    TextView somethingWentWrong;
    ArrayList<DataClassNews> newsList = new ArrayList<>();
    String guardianUrl = "https://content.guardianapis.com/search";
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a_b_c_news);

        //defining view..
        newRecyclerView = findViewById(R.id.newRecyclerView);
        somethingWentWrong = findViewById(R.id.somethingWentWrong);
        progressBar = findViewById(R.id.progressBar);


        //checking the connection
        if (isConnected()) {
            //setting up the loader..
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(0, null, this);

        } else {
            progressBar.setVisibility(View.GONE);
            newRecyclerView.setVisibility(View.GONE);
            somethingWentWrong.setVisibility(View.VISIBLE);

        }


    }

    //function to check the internet connection...
    private boolean isConnected() {
        boolean connected = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) getApplicationContext().getSystemService(CONNECTIVITY_SERVICE);
            NetworkInfo nInfo = cm.getActiveNetworkInfo();
            connected = nInfo != null && nInfo.isAvailable() && nInfo.isConnected();
            return connected;
        } catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }

    //setting the adapter....
    private void setAdapter() {
        if (!newsList.isEmpty()) {
            SettingNewsDataAdapter settingNewsDataAdapter = new SettingNewsDataAdapter(this, newsList);
            newRecyclerView.setHasFixedSize(true);
            newRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            newRecyclerView.setAdapter(settingNewsDataAdapter);
        }
        else{
            progressBar.setVisibility(View.GONE);
            newRecyclerView.setVisibility(View.GONE);
            somethingWentWrong.setVisibility(View.VISIBLE);
        }

    }


    //overriding the methods from loader manager.... when loader is created...
    @Override
    public Loader<List<DataClassNews>> onCreateLoader(int id, Bundle args) {
        Uri uriUrlGuardian = Uri.parse(guardianUrl);
        Uri.Builder builder = uriUrlGuardian.buildUpon();

        //appending tags to get other details...
        builder.appendQueryParameter("show-tags", "contributor");
        //appending the api key in our url...
        builder.appendQueryParameter("api-key", getString(R.string.api_key));

        return new NewsLoader(this, builder.toString());
    }


    //overriding the on load finished....
    @Override
    public void onLoadFinished(Loader<List<DataClassNews>> loader, List<DataClassNews> data) {

        //clearing the list so that older data will not add....
        newsList.clear();

        //hiding the progress bar...
        progressBar.setVisibility(View.GONE);
        if (loader != null && !data.isEmpty()) {
            newsList.addAll(data);
        }

        //calling the adapter function...
        setAdapter();

    }

    //overriding the load rest.....
    @Override
    public void onLoaderReset(Loader<List<DataClassNews>> loader) {
        newsList.clear();
    }
}
