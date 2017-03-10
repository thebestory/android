package com.thebestory.android.files;

import android.content.Context;

import com.thebestory.android.util.BankStoriesLocation;
import com.thebestory.android.util.BankTopics;
import com.thebestory.android.util.CacheStories;

import java.util.ArrayList;
/**
 * Created by Alex on 07.03.2017.
 */

public class FilesSystem {

    public interface FileCache {
        void onOpenApp(Context context);
        void onExitApp(Context context);
        void onDeleteCashe(Context context);
        long sizeFile(Context context);
    }

    private final ArrayList<FileCache> files;

    private FilesSystem() {
        files = new ArrayList<>();
        registrDeleteCashe(BankStoriesLocation.getInstance());
        registrDeleteCashe(BankTopics.getInstance());
        registrDeleteCashe(CacheStories.getInstance());
    }

    private static final FilesSystem ourInstance = new FilesSystem();

    public static FilesSystem getInstance() {
        return ourInstance;
    }


    public void registrDeleteCashe(FileCache i) {
        files.add(i);
    }

    public void onDeleteCashe(Context context) {
        for (FileCache i : files) {
            i.onDeleteCashe(context);
        }
    }

    public void onExitApp(Context context) {
        for (FileCache i : files) {
            i.onExitApp(context);
        }
    }

    public void onOpenApp(Context context) {
        for (FileCache i : files) {
            i.onOpenApp(context);
        }
    }

    public double getCasheSize(Context context) {
        long size = 0;
        for (FileCache i : files) {
            size += i.sizeFile(context);
        }


        return size <= 1024 ? 0.0d : ((double)size) / (1048576);
    }
}
