/*
 * The Bestory Project
 */

package com.thebestory.android.api.urlCollection;

public final class UrlBox {
    private UrlBox() {
        throw new AssertionError();
    }

    public static String baseUrl = "https://thebestory.herokuapp.com";
    public static String storyWay = "stories";
    public static String topicWay = "topics";
    public static String commentWay = "comments";

}
