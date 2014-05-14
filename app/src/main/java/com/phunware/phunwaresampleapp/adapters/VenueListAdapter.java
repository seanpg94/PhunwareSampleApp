package com.phunware.phunwaresampleapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.phunware.phunwaresampleapp.R;
import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.Venue;

import java.util.ArrayList;
import java.util.List;

public class VenueListAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private List<Venue> mVenues = new ArrayList<Venue>();

    public VenueListAdapter(Context context, List<Venue> venues) {
        this.mVenues = venues;
        inflater = LayoutInflater.from(context);
    }

    public void setData(List<Venue> data) {
        if (mVenues != null) {
            mVenues.clear();
        } else {
            mVenues = new ArrayList<Venue>();
        }
        if (data != null) {
            mVenues.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public View getView(int i, View view, ViewGroup parent) {
        Venue venue = (Venue) getItem(i);
        if (view == null) {
            view = inflater.inflate(R.layout.venue_row, null);
        }
        TextView venueName = (TextView) view.findViewById(R.id.name);
        venueName.setText(venue.getName());

        TextView venueAddress = (TextView) view.findViewById(R.id.address);
        venueAddress.setText(venue.toAddressString());

        return view;
    }

    @Override
    public int getCount() {
        return mVenues.size();
    }

    @Override
    public Object getItem(int i) {
        return mVenues.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }
}