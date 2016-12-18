package com.thebestory.android.util;

import com.thebestory.android.model.Story;


import org.joda.time.Period;

import java.util.Date;

import humanize.Humanize;

/**
 * Created by Alex on 18.12.2016.
 */

public class TimeConverter {

    public static String absoluteTime (Story story) {
        if (story == null || story.publishDate == null ) {
            return "null";
        }

        return Humanize.format("{0, joda.time, full.date.time}", story.publishDate);
    }

    public static String relativeTime(Story story, Date nowDate) {
        if (story == null || story.publishDate == null || nowDate == null) {
            return "null";
        }
        Period period = new Period(story.publishDate.getTime(), nowDate.getTime());

        if (period.getYears() < 1) {
            if (period.getMonths() < 1){
                if (period.getWeeks() < 1){
                    if (period.getDays() < 1) {
                        if (period.getHours() < 1) {
                            if (period.getMinutes() < 1) {
                                return "Several seconds ago";
                            }

                            if (period.getMinutes() == 1) {
                                return "Minute ago";
                            }
                            return Integer.toString(period.getMinutes()) + " minutes ago";
                        }

                        if (period.getHours() == 1) {
                            return "Hour ago";
                        }

                        return Integer.toString(period.getHours()) + " hours ago";
                    }

                    if (period.getDays() == 1) {
                        return "Yesterday";
                    }

                    return Integer.toString(period.getDays()) + " days ago";
                }

                if (period.getWeeks() == 1) {
                    return "Weak ago";
                }

                return Integer.toString(period.getWeeks()) + " weaks ago";
            }

            if (period.getMonths() == 1) {
                return "Month ago";
            }

            return Integer.toString(period.getMonths()) + " months ago";
        }

        if (period.getYears() == 1) {
            return "Year ago";
        }

        return Integer.toString(period.getYears()) + " years ago";


    }
}
