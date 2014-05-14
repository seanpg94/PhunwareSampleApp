package com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

public class VenueList implements Parcelable {

    private List<Venue> mVenueList;

    public VenueList() {}

    public List<Venue> getVenueList() {
        return mVenueList;
    }

    public void setVenueList(List<Venue> venues) {
        mVenueList = venues;
    }

    protected VenueList(Parcel in) {
        if (in.readByte() == 0x01) {
            mVenueList = new ArrayList<Venue>();
            in.readList(mVenueList, Venue.class.getClassLoader());
        } else {
            mVenueList = null;
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (mVenueList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(mVenueList);
        }
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<VenueList> CREATOR = new Parcelable.Creator<VenueList>() {
        @Override
        public VenueList createFromParcel(Parcel in) {
            return new VenueList(in);
        }

        @Override
        public VenueList[] newArray(int size) {
            return new VenueList[size];
        }
    };
}