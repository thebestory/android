/*
 * The Bestory Project
 */

package com.thebestory.android.util;

import android.content.Context;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Class provides a methods for reading and writing files on the File System.
 * TODO: Implement methods for working w/ external Android storage
 */
public final class StorageUtils {
    enum StorageType {
        INTERNAL,
        EXTERNAL
    }

    /**
     * Returns {@link FileInputStream} to read, and, if needed, creates full path to the file.
     *
     * @param filepath full path to the file
     * @param context  application context
     * @return {@link FileInputStream} instance to read
     * @throws FileNotFoundException if path to the file not created, and file cannot be available
     */
    public static FileInputStream getInputFile(String filepath,
                                               Context context) throws FileNotFoundException {
        return new FileInputStream(get(filepath, context));
    }

    /**
     * Returns {@link FileOutputStream} to write, and, if needed, creates full path to the file.
     *
     * @param filepath full path to the file
     * @param context  application context
     * @return {@link FileOutputStream} instance to write
     * @throws FileNotFoundException if path to the file not created, and file cannot be available
     */
    public static FileOutputStream getOutputFile(String filepath,
                                                 Context context) throws FileNotFoundException {
        return new FileOutputStream(get(filepath, context));
    }

    /**
     * Deletes file or folder by abstract path. If this path points to folder, then this folder must
     * be empty.
     *
     * @param path    full path
     * @param context application context
     * @return {@code true}, if file or folder is deleted (or not exists), otherwise {@code false}
     * @throws FileNotFoundException
     */
    public static boolean delete(String path,
                                 Context context) throws FileNotFoundException {
        File file = new File(context.getFilesDir(), path);
        return !file.exists() || file.delete();
    }

    /**
     * Returns {@link File} instance by abstract path, and, if needed, created parents path.
     *
     * @param path    full path
     * @param context application context
     * @return {@link File} instance
     * @throws FileNotFoundException if attempt to create parents path fails
     */
    public static File get(String path,
                           Context context) throws FileNotFoundException {
        File current = new File(context.getFilesDir(), path);
        File parent = current.getParentFile();

        if (!parent.exists()) {
            if (!parent.mkdirs()) {
                throw new FileNotFoundException("Attempt to create parents path fails");
            }
        }

        return current;
    }

    private StorageUtils() {
    }
}
