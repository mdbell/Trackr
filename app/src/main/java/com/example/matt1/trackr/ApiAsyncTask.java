package com.example.matt1.trackr;

import android.os.AsyncTask;

import com.example.matt1.trackr.api.Envelope;
import com.google.gson.Gson;

import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by matt1 on 11/27/2016.
 */

public abstract class ApiAsyncTask<T> extends AsyncTask<String, Void, Envelope<T>> {

    private static final String QUERY_URL = "https://trackr.mdbell.me/query.php?action=%s";

    protected abstract String getAction();

    protected abstract Map<String, String> getParams();

    protected abstract void onPostQuery(Envelope<T> env);

    protected abstract Envelope<T> fromJson(Gson gson, Reader r);

    @Override
    protected final Envelope<T> doInBackground(String... strings) {
        String params = encodeParams();
        String url = String.format(QUERY_URL, getAction());
        if (params.length() > 0) {
            url = url + "&" + params;
        }
        try {
            System.out.println(url);
            URLConnection conn = new URL(url).openConnection();
            conn.connect();

            InputStreamReader reader = new InputStreamReader(conn.getInputStream());
            Gson gson = new Gson();

            return fromJson(gson, reader);
        } catch (Exception e) {
            handleException(e);
            return null;
        }
    }

    protected final void onPostExecute(Envelope<T> env) {
        super.onPostExecute(env);
        onPostQuery(env);
    }

    protected void handleException(Exception e) {
        e.printStackTrace();
    }

    private String encodeParams() {
        StringBuilder builder = new StringBuilder();
        Map<String, String> params = getParams();
        Iterator<String> i = params.keySet().iterator();
        while (i.hasNext()) {
            String key = i.next();
            String value = params.get(key);
            if (value.length() == 0) { // skip empty values
                continue;
            }
            builder.append(encode(key)).append('=').append(encode(value));
            if (i.hasNext()) {
                builder.append('&');
            }
        }
        return builder.toString();
    }

    private String encode(String str) {
        try {
            return URLEncoder.encode(str, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return null;
        }
    }
}
