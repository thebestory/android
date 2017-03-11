/*
 * The Bestory Project
 */

package com.thebestory.android.util;

import com.thebestory.android.model.Story;

import java.util.Date;
import java.util.Locale;

import static humanize.Humanize.format;
import static humanize.Humanize.naturalTime;

/**
 * Class provides a methods for date/time management.
 */
public class TimeConverter {

    public static String absolute(Story story) {
        if (story == null || story.publishDate == null) {
            return "null";
        }

        return format("{0, joda.time, full.date.time}", story.publishDate, Locale.US);
    }

    public static String relative(Date date) {
        if (date == null) {
            return "";
        }

        return naturalTime(new Date(), date, Locale.US);
    }
}
