package com.phunware.phunwaresampleapp.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.phunware.phunwaresampleapp.R;
import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.ScheduleItem;
import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.Venue;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A fragment representing a single Venue detail screen.
 * This fragment is either contained in a {@link com.phunware.phunwaresampleapp.VenueListActivity}
 * in two-pane mode (on tablets) or a {@link com.phunware.phunwaresampleapp.VenueDetailActivity}
 * on handsets.
 */
public class VenueDetailFragment extends Fragment {
    /**
     * The fragment argument representing the Venue that this fragment
     * represents.
     */

    public static final String ARG_VENUE = "venue";

    /**
     * The dummy content this fragment is presenting.
     */
    private Venue mVenue;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public VenueDetailFragment() {
    }

    static public VenueDetailFragment newInstance(Venue venue)
    {
        VenueDetailFragment fragment = new VenueDetailFragment();
        Bundle bundle = new Bundle();
        if (bundle != null)
        {
            bundle.putParcelable(ARG_VENUE, venue);
            fragment.setArguments(bundle);
        }

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setRetainInstance(true);

        if (getArguments().containsKey(ARG_VENUE)) {
            mVenue = getArguments().getParcelable(ARG_VENUE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_venue_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mVenue != null) {

            final ImageView venueImage = (ImageView)rootView.findViewById(R.id.venue_image);

            if (mVenue.getImageUrl().isEmpty()) {
                venueImage.setVisibility(View.GONE);
            }
            else {
                Picasso.with(getActivity().getApplicationContext())
                        .load(mVenue.getImageUrl())
                        .fit()
                        .centerCrop()
                        .into(venueImage);
            }

            final TextView nameTv = (TextView) rootView.findViewById(R.id.venue_name);
            nameTv.setText(mVenue.getName());

            final TextView addressTv = (TextView) rootView.findViewById(R.id.venue_address);
            if (mVenue.getAddress().isEmpty()) {
                addressTv.setVisibility(View.GONE);
            }
            else {
                addressTv.setText(mVenue.getAddress());
            }

            final TextView address2Tv = (TextView) rootView.findViewById(R.id.venue_city);
            address2Tv.setText(mVenue.getCity() + ", " + mVenue.getState() + " " + mVenue.getZip());

            LinearLayout scheduleList = (LinearLayout)rootView.findViewById(R.id.schedule_container);
            List<ScheduleItem> scheduleItemList = mVenue.getSchedule();
            if (scheduleList != null && scheduleItemList.size() != 0) {
                for (ScheduleItem item : scheduleItemList) {
                    TextView tv = new TextView(getActivity());
                    tv.setText(item.toScheduleDetailString());
                    scheduleList.addView(tv);
                }
            }
            else {
                scheduleList.setVisibility(View.GONE);
            }

        }

        return rootView;
    }
}
