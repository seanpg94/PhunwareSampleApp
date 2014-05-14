package com.phunware.phunwaresampleapp.helpers;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ShareCompat;
import android.support.v7.widget.ShareActionProvider;

import com.phunware.phunwaresampleapp.com.phunware.phunwaresampleapp.model.Venue;

public class ShareHelper {

    public static void updateShareActionProvider(ShareActionProvider provider, Activity activity, Venue venue)
    {
        if (venue != null) {
            Intent shareIntent = ShareCompat.IntentBuilder.from(activity)
                    .setType("text/plain").setText(venue.toShareString()).getIntent();

            if (provider != null) {
                provider.setShareIntent(shareIntent);
            }
        }
    }

    /** Defines a default (dummy) share intent to initialize the action provider.
     * However, as soon as the actual content to be used in the intent
     * is known or changes, you must update the share intent by again calling
     * mShareActionProvider.setShareIntent()
     */
    public static Intent getDefaultIntent() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("image/*");
        return intent;
    }


}
