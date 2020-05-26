package com.example.abcnews;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;


/**
 * @author tusha
 */

public class NewsLoader extends AsyncTaskLoader<List<DataClassNews>> {

    private String guardianUrl;

    public NewsLoader(Context context, String myUrl) {
        super(context);
        guardianUrl = myUrl;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override

    public List<DataClassNews> loadInBackground() {
        if (guardianUrl == null) {
            return null;
        }
        return QueryUtils.getAbcLiveNews(guardianUrl);
    }
}
