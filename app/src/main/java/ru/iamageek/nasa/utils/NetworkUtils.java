package ru.iamageek.nasa.utils;

import android.net.Uri;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

// класс для сетевых запросов
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

    public static String getResponseFromURL(URL url) throws IOException {

        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setDoInput(true);
        urlConnection.connect();

        try {


            InputStream input = urlConnection.getInputStream();
//            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
//здесь нужно бы оборачивать в условие, пока есть данные
//            while (reader.readLine() != null) {
//                String text = reader.readLine();
//                return text;
//            }

            Scanner scanner = new Scanner(input);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput)
                return scanner.next();
            else return null;

        } finally {

            urlConnection.disconnect();
 //           return null;
        }

    }


}
