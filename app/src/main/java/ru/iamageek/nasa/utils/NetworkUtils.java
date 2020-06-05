package ru.iamageek.nasa.utils;

import android.net.Uri;
import java.net.MalformedURLException;
import java.net.URL;

// this class forms GET requests

public class NetworkUtils {

    private static final String NASA_API_BASE_URL = "https://api.nasa.gov/";
    private static final String APOD = "planetary/apod";
    private static final String API_KEY = "api_key";
// метод для формирования строки запроса URL
    public static URL generateURL() {
        Uri builtUri = Uri.parse( NASA_API_BASE_URL + APOD)//вначале формируем запрос uri
                .buildUpon()
                .appendQueryParameter(API_KEY, "KO3e1OjZ5d1eDIdTmzGdOnfyRIqpW3BEMrgrcckZ")
                .build();
        // теперь нужно uri преобразовать в URL
        URL url = null;
        try {
            url = new URL (builtUri.toString());// это рискованная операция и может выбросить исключение
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return url;

    }

}
