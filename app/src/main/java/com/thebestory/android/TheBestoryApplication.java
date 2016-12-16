/*
 * The Bestory Project
 */

package com.thebestory.android;

import android.app.Application;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * The Bestory application entry point.
 */
public class TheBestoryApplication extends Application {
    public String slug = "all";
    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
    }
}
