package com.emc.recipester.core;

import android.os.AsyncTask;
import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Munsif on 3/21/2018.
 */

public class WebServiceExecutor extends AsyncTask<Integer, Void, String> {

    private String URL;
    private Callback callback;

    public WebServiceExecutor(String url, Callback cb) {
        this.URL = url;
        this.callback = cb;
    }

    private String getUrlConnectionResult(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.connect();

        InputStream stream = connection.getInputStream();
        InputStreamReader streamReader = new InputStreamReader(stream);

        int MAX_READ_SIZE = 1000000;
        char[] buffer = new char[MAX_READ_SIZE];
        int readSize;
        StringBuilder stringBuffer = new StringBuilder();

        while (((readSize = streamReader.read(buffer)) != -1) && MAX_READ_SIZE > 0) {
            if (readSize > MAX_READ_SIZE) {
                readSize = MAX_READ_SIZE;
            }
            stringBuffer.append(buffer, 0, readSize);
            MAX_READ_SIZE -= readSize;
        }
        return stringBuffer.toString();
    }

    @Override
    protected String doInBackground(Integer... integers) {
        String result = null;
        try {
            URL url = new URL(URL);
            result = getUrlConnectionResult(url);
            Log.d("datayo", result);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(String s) {
        this.callback.onCallbackCompleted(s);
    }
}
