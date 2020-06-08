package ru.iamageek.nasa;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
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
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private RequestQueue mRequestQueue; //RequestQueue variable for Volley
    private String url = null;//variable for link to API
    private String name, date, explanation, urlImage, copyright;
    private TextView titleView, dateView, descriptionView, copyrightView;
    private ImageView imageView;
    private static final String LOG_TAG = "Log";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL urlFromNetUtils = generateURL(); // create URL in NetworkUtils class
        url = urlFromNetUtils.toString();// cast URL to string for control

        Log.d(LOG_TAG, "URL: " + url); //Test output for control that URL formed correctly

        mRequestQueue = Volley.newRequestQueue(this); //initialise Volley library for requests queue
        getJsonNasaData(url); // invoke method to receive JSON data from server

// here populate view

        titleView = (TextView) findViewById(R.id.title); // Post title
        dateView = (TextView) findViewById(R.id.dateView); // Date of post
        copyrightView = (TextView) findViewById(R.id.author); // Copyrights
        descriptionView = (TextView) findViewById(R.id.descriptionView); // Description of image from
        imageView = (ImageView) findViewById(R.id.cosmicImage); // Insert image into Image View

    }

// method to receive JSON file from NASA server using API

    private void getJsonNasaData(String urlM) {
        final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, //GET - request to receive data
                urlM, null, new Response.Listener<JSONObject>() {

            @Override
            public void onResponse(JSONObject response) {//parse JSON object and retrieve data
                Log.d(LOG_TAG, "JSON " + response);
                try {
                    name = response.getString("title");
                    date = response.getString("date");
                    urlImage = response.getString("url");
                    explanation = response.getString("explanation");
                    copyright = response.getString("copyright");
//проверочный вывод в консоль
                    Log.d(LOG_TAG,"Name: "+ name);
                    Log.d(LOG_TAG, "Date: "+ date);
                    Log.d(LOG_TAG, "Explanation: " + explanation);
                    Log.d(LOG_TAG, "URL: " + urlImage);//receive URL string where image is located
                    Log.d(LOG_TAG, "Copyright: " + copyright);//copyright
                    getImageFromUrl(urlImage); // call method to download a Bitmap photo from the NASA server
                    setValues();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() { // in case of an error
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        mRequestQueue.add(request); // add request to the Volley queue
    }

//method to download an image in Bitmap format using volley

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
        copyrightView.setText("Copyright: " + copyright);
        dateView.setText(date);
        descriptionView.setText(explanation);

    }
}


