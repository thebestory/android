package com.thebestory.android.api.urlCollection;

/**
 * Created by Alex on 02.11.2016.
 */

public final class UrlBox {
    private UrlBox() {
        throw new AssertionError();
    }

    public static String baseUrl = "https://thebestory.herokuapp.com";
    public static String storyWay = "stories";
    public static String topicWay = "topics";

}
