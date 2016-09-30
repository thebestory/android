package com.thebestory.android.util;

public class Theme {
    private static Theme instance = new Theme();

    public static Theme getInstance() {
        return instance;
    }

    private Theme() {
    }
}
