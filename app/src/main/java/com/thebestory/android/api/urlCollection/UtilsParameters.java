/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection;

import android.net.Uri;
import android.os.Bundle;

public class UtilsParameters {
    private UtilsParameters() {throw new AssertionError();}

    public static void addParametersToStoryRequest(Uri.Builder url, Bundle args) {
        if (args.containsKey("after")) {
            url.appendQueryParameter("after", args.getString("after", ""));
        } else if (args.containsKey("before")) {
            url.appendQueryParameter("before", args.getString("before", ""));
        }
        url.appendQueryParameter("limit", Integer.toString(args.getInt("limit", 0)));
    }

}
