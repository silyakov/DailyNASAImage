package ru.iamageek.nasa;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DownloadManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.*;

import static ru.iamageek.nasa.utils.NetworkUtils.*;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.SQLOutput;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue; //RequestQueue переменная для создания очереди запросов volley
    String url = null;//ссылка на API сайта для получения данных
    String name, date, explanation, urlImage, copyright;
    Bitmap imageSpace;
    TextView titleView, dateView, descriptionView, copyrightView;
    ImageView imageView;

    class NasaQueryTask extends AsyncTask<URL, Void, String> { // с помощью этого вложенного класса реализуется обращение к сети в другом потоке

        @Override
        protected String doInBackground(URL... urls) {
            String response_new = null;
            try {
                response_new = getResponseFromURL(urls[0]);
                System.out.println("String JSON: " + response_new);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response_new;
        }
        @Override
        protected void onPostExecute(String response) {
            System.out.println("Response JSON from NASA: " + response);
//           descriptionView.setText(response);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL urlFromNetUtils = generateURL(); // создаем URL
        url = urlFromNetUtils.toString();

        System.out.println("URL: " + url);

        new NasaQueryTask().execute(urlFromNetUtils);

//пример кода с гитхаба для того, чтобы все работало в Main Thread без использования Async Task это если не используем volley
//*****************************************
//        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
//        StrictMode.setThreadPolicy(policy);
//*****************************************
//без куска кода выше ругается и выдает Network exception. С этим кодом все работает в основной активности, но подвешивает немного при старте

        mRequestQueue = Volley.newRequestQueue(this); //задействуем библиотеку Volley для очереди запросов
        getJsonNasaData(url); //получим данные с сайте в методе
/*
// ниже мы используем старый способ получения данных с использованием массива

        ParseJson jsonString = new ParseJson(this);
        String[] output;
        try {
            output = jsonString.parseJsonString();
            title = output[0];
            date = output[1];
            description = output[2];
            url = output[3];
            imageSpace = getImage();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException jse) {
            jse.printStackTrace();
        }
*/

// вставка в шаблон текста

        titleView = (TextView) findViewById(R.id.title); // название снимка
        dateView = (TextView) findViewById(R.id.dateTaken); // когда снимок сделан
        copyrightView = (TextView) findViewById(R.id.copyright); // авторские права
        descriptionView = (TextView) findViewById(R.id.description); // описание снимка на сайте
        imageView = (ImageView) findViewById(R.id.imageView); // размещение изображения

    }

/*
//получение изображения с сайта по URL ссылке полученной от сообщения JSON это если использовать стандартный метод не задействуя volley

    public Bitmap getImage() throws IOException {

        try {
            URL urlR = new URL(urlImage);
            HttpURLConnection connection = (HttpURLConnection) urlR.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap image = BitmapFactory.decodeStream(input);
            input.close();
            return image;

        } catch(IOException e) {
            System.out.println(e);
            return null;
        }


    }
*/

    private void getJsonNasaData(String urlM) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //GET - API-запрос для получение данных
                urlM, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {
                try {
                    //парсим json объект response и получаем строки (значения) -
                    name = response.getString("title");
                    date = response.getString("date");
                    urlImage = response.getString("url");
                    explanation = response.getString("explanation");
//                    copyright = response.getString("copyright");
//проверочный вывод в консоль
                    System.out.println("Name: "+ name);
                    System.out.println("Date: "+ date);
                    System.out.println("Explanation: " + explanation);

                    System.out.println("URL: " + urlImage);
                    System.out.println("Copyright: " + copyright);//copyright
                    getImageFromUrl(urlImage); // передаем ссылку на изображение в метод получения контента Bitmap с сайта используя volley
                    setValues();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // в случае возникновеня ошибки
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request); // добавляем запрос в очередь
    }

//код ниже используем для получения изображения с помощью библиотеки volley

    private void getImageFromUrl(String url) {


        ImageRequest imageRequest = new ImageRequest(url,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap response) {
                    imageView.setImageBitmap(response);
//                    setValues();
                    }
                }, 0, 0, null, null, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
//        imageRequest.setTag(REQ_TAG);
        mRequestQueue.add(imageRequest);
    }


    private void setValues() {
        titleView.setText(name);
        dateView.setText(date);
        descriptionView.setText(explanation);
        copyrightView.setText(copyright);
    }
}


