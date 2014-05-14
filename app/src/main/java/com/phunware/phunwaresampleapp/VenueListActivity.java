package com.phunware.phunwaresampleapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.ShareActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.Venue;
import com.phunware.phunwaresampleapp.fragments.VenueDetailFragment;
import com.phunware.phunwaresampleapp.fragments.VenueListFragment;
import com.phunware.phunwaresampleapp.helpers.ShareHelper;


/**
 * An activity representing a list of Venues. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link VenueDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link com.phunware.phunwaresampleapp.fragments.VenueListFragment} and the item details
 * (if present) is a {@link com.phunware.phunwaresampleapp.fragments.VenueDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link com.phunware.phunwaresampleapp.fragments.VenueListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class VenueListActivity extends ActionBarActivity
        implements VenueListFragment.Callbacks {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    private ShareActionProvider mShareActionProvider;
//    private Venue mVenue;

    public ShareActionProvider getShareActionProvider() {
        return mShareActionProvider;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.activity_venue_list);

        if (findViewById(R.id.venue_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;

            // In two-pane mode, list items should be given the
            // 'activated' state when touched.
            ((VenueListFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.venue_list))
                    .setActivateOnItemClick(true);
        }

        // TODO: If exposing deep links into your app, handle intents here.
    }

    /**
     * Callback method from {@link VenueListFragment.Callbacks}
     * indicating that the item with the given ID was selected.
     */
    @Override
    public void onItemSelected(Venue venue) {
        if (mTwoPane) {
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putParcelable(VenueDetailFragment.ARG_VENUE, venue);
            VenueDetailFragment fragment = new VenueDetailFragment();
            fragment.setArguments(arguments);

            // update the context for sharing this venue
            ShareHelper.updateShareActionProvider(mShareActionProvider, this, venue);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.venue_detail_container, fragment)
                    .commit();

        } else {

            // make sure we remove any menu items that were added to the action bar
            this.supportInvalidateOptionsMenu();


            // In single-pane mode, simply start the detail activity
            // for the selected item ID.
            Intent detailIntent = new Intent(this, VenueDetailActivity.class);
            detailIntent.putExtra(VenueDetailFragment.ARG_VENUE, venue);
            startActivity(detailIntent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (mTwoPane) {
            getMenuInflater().inflate(R.menu.main, menu);

            // Set up ShareActionProvider's default share intent
            MenuItem shareItem = menu.findItem(R.id.action_share);
            mShareActionProvider = (ShareActionProvider)
                    MenuItemCompat.getActionProvider(shareItem);
            mShareActionProvider.setShareIntent(ShareHelper.getDefaultIntent());
        }
        
        return super.onCreateOptionsMenu(menu);
    }

}
