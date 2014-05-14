package com.phunware.phunwaresampleapp.loaders;

import android.content.Context;
import android.support.v4.content.AsyncTaskLoader;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.Venue;
import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.VenueList;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class VenueLoader extends AsyncTaskLoader<List<Venue>> {

    RequestQueue mRequestQueue;

    private static final String URL = "https://s3.amazonaws.com/jon-hancock-phunware/nflapi-static.json";

    public VenueLoader(Context context) {
        super(context);
        mRequestQueue = Volley.newRequestQueue(context);
    }

    @Override public List<Venue> loadInBackground() {

        RequestFuture<JSONArray> future = RequestFuture.newFuture();
        JsonArrayRequest request = new JsonArrayRequest(URL, future, future);
        mRequestQueue.add(request);

        try {
            JSONArray response = future.get(); // this will block

            Gson gson = new GsonBuilder()
                    .serializeNulls()
                    .setDateFormat("yyyy-MM-dd HH:mm:ss Z")
                    .create();
//            VenueList venueList = gson.fromJson(response.toString(), VenueList.class);
            Type listType = new TypeToken<List<Venue>>(){}.getType();
            List<Venue> venues = (List<Venue>) gson.fromJson(response.toString(), listType);

            return venues;

        } catch (InterruptedException e) {
            // exception handling
        } catch (ExecutionException e) {
            Log.v("debug", e.toString());
            // exception
        }

        return null;
    }
}