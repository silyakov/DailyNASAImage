package ru.iamageek.nasa;

//этот класс открывает файл из директории res/raw и передает данные в виде строки в MainActivity

import android.content.Context;
import android.content.res.Resources;

import org.json.JSONObject;
import org.json.JSONException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ParseJson {

    String jsonString;
    Context context;
    Resources resources;
    String file;
    String name, url, date, description;

    public ParseJson(Context context1) {
        context = context1;
    }

//метод для парсинга json
     public String[] parseJsonString() throws IOException, JSONException {

            JSONObject jsonObject;

            String[] jsonArray = new String[4];

            jsonString = getJsonString();
            jsonObject = new JSONObject(jsonString);
//парсим json и получаем маппинги (значения) - кастрируем в строку
            name = (String)jsonObject.get("title");
            date = (String)jsonObject.get("date");
            url = (String)jsonObject.get("url");
            description = (String)jsonObject.get("explanation");
//проверочный вывод в консоль
            System.out.println("Name: "+ name);
            System.out.println("Date: "+ date);
            System.out.println("Explanation: " + description);
            System.out.println("URL: " + url);

            jsonArray[0] = name;
            jsonArray[1] = date;
            jsonArray[2] = description;
            jsonArray[3] = url;

            return jsonArray;
        }

// метод для чтения файла .json из директория ресурсов raw

    public String getJsonString() throws IOException {

        file = "response_12_05_20"; //имя файла
        resources = context.getResources();
        int id = resources.getIdentifier(file,"raw", context.getPackageName());//для обращения к ресурсу нужно получить его ID

        System.out.println("package name: " + context.getPackageName());//control check
        InputStream raw = resources.openRawResource(id);//передаем во входной поток значение ID

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(raw));//читаем входной поток байт в буфер
        String jsonStringReturn = null;// так будет выглядеть строка json
        try
        {
            jsonStringReturn = bufferedReader.readLine();
            bufferedReader.close();
            // поимка исключений
        } catch (FileNotFoundException e)
        {
            System.out.println("File not found");
            e.printStackTrace();
        } catch (IOException e)
        {
            System.out.println("IOException");
            e.printStackTrace();
        }

        return jsonStringReturn;
    }
}
