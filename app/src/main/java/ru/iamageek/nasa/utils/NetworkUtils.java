package ru.iamageek.nasa.utils;

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

// this class forms GET requests

public class NetworkUtils {

    private static final String NASA_API_BASE_URL = "https://api.nasa.gov/";
    private static final String APOD = "planetary/apod";
    private static final String API_KEY = "api_key";
// method for URL requests forming
    public static URL generateURL() {
        Uri builtUri = Uri.parse( NASA_API_BASE_URL + APOD)//forming URI request
                .buildUpon()
                .appendQueryParameter(API_KEY, "KO3e1OjZ5d1eDIdTmzGdOnfyRIqpW3BEMrgrcckZ")
                .build();
        // convert URI to URL
        URL url = null;
        try {
            url = new URL (builtUri.toString());// this can throw an exceptions, therefore wrap in try/catch
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }

}
