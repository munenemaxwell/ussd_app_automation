package com.safaricom.mnjuguna3.ussd_app_automation;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import static android.content.ContentValues.TAG;

class RetrieveData extends AsyncTask<String, Void,  ArrayList<String>> {

    @Override
    protected  ArrayList<String> doInBackground(String... urls) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        URL url;
        ArrayList<String> stringArray = new ArrayList<String>();
        JSONArray jsonArray;

        try {
            url = new URL(urls.toString());
            urlConnection.setRequestMethod("GET"); //Your method here
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null)
                buffer.append(line + "\n");

            if (buffer.length() == 0)
                return null;

            try {

                jsonArray=new JSONArray(buffer.toString());

                for(int i = 0, count = jsonArray.length(); i< count; i++)
                {
                    try {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        stringArray.add(jsonObject.toString());
                    }
                    catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return stringArray;
        } catch (IOException e) {

            Log.e(TAG, "Error closing stream", e);
            return null;
        }finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(TAG, "Error closing stream", e);
                }
            }
        }


    }




    }




