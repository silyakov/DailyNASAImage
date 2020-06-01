package ru.iamageek.nasa;

//этот класс получает JSON объект напрямую из интернета

import android.app.DownloadManager;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

public class GetJsonUrl {


    String url = "https://api.nasa.gov/planetary/apod?api_key=KO3e1OjZ5d1eDIdTmzGdOnfyRIqpW3BEMrgrcckZ";

/*
    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest

            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                @Override
                public void onResponse(JSONObject response) {
                    textView.setText("Response: " + response.toString());
                }
            }, new Response.ErrorListener() {

                @Override
                public void onErrorResponse(VolleyError error) {
                    // TODO: Handle error

                }
            });

// Access the RequestQueue through your singleton class.
//MySingleton.getInstance(this).addToRequestQueue(jsonObjectRequest);
*/
}
