/*
 * The Bestory Project
 */

package com.thebestory.android.api.parseResponse;

import android.util.JsonReader;
import android.util.Log;

import com.thebestory.android.api.LoaderStatus;
import com.thebestory.android.fragment.main.DebugFragment;
import com.thebestory.android.model.Story;

import java.io.IOException;
import java.util.ArrayList;

public final class ParseResponseStatus {
    private ParseResponseStatus() {
        throw new AssertionError();
    }

    private static void readError(JsonReader error) throws IOException {
        error.beginObject();
        while (error.hasNext()) {
            switch (error.nextName()) {
                case "code":
                    Log.e("ERROR RESPONSE", "code: " + error.nextInt());
                    break;
                case "message":
                    Log.e("ERROR RESPONSE", "message: " + error.nextString());
                    break;
                default:
                    error.skipValue();
                    break;
            }
        }
        error.endObject();
    }

    private static void readWarning(JsonReader warning) throws IOException {
        warning.beginObject();
        while (warning.hasNext()) {
            switch (warning.nextName()) {
                case "code":
                    Log.e("WARNING RESPONSE", "code: " + warning.nextInt());
                    break;
                case "message":
                    Log.e("WARNING RESPONSE", "message: " + warning.nextString());
                    break;
                default:
                    warning.skipValue();
                    break;
            }
        }
        warning.endObject();
    }

    private static LoaderStatus switchStatus(String status) {
        switch (status) {
            case "ok" :
                return LoaderStatus.OK;
            case "warning" :
                return LoaderStatus.WARNING;
            default:
                return LoaderStatus.ERROR;
        }
    }

    public static LoaderStatus parse(JsonReader response) throws IOException {
        LoaderStatus status = LoaderStatus.OK;
        response.beginObject();
        while (response.hasNext()) {
            String name = response.nextName();

            switch (name) {
                case "status":

                    status = switchStatus(response.nextString());
                    break;
                case "error":
                    readError(response);
                    break;
                case "warning" :
                    readWarning(response);
                    break;
                case "data":
                    if (status != LoaderStatus.ERROR) {
                        return status;
                    } else {
                        response.skipValue();
                    }
                    break;
                default:
                    response.skipValue();
                    break;
            }
        }
        response.endObject();
        return status;
    }
}
