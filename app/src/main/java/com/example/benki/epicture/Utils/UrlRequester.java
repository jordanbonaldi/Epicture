package com.example.benki.epicture.Utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 31/10/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class UrlRequester {

    private static int ERROR_CODE = 404;

    private static int ACCESS = 200;

    private static int READ_SIZE = 1024;

    private static void setHeaders(Request.Builder builder, HashMap<String, String> header) {
        for (Map.Entry<String, String> entries : header.entrySet()) {
            builder.header(entries.getKey(), entries.getValue());
        }
    }

    private static String readOnInputStreamUrl(HttpURLConnection connection) throws IOException {
        InputStream in          = connection.getInputStream();
        final char[] buffer     = new char[READ_SIZE];
        final StringBuilder out = new StringBuilder();
        Reader reader           = new InputStreamReader(in, "UTF-8");
        int res;

        while ((res = reader.read(buffer, 0, buffer.length)) < 0)
            out.append(buffer, 0, res);

        return out.toString();
    }

    public static String launchRequest(String formattedUrl, HashMap<String, String> header) throws IOException {
        OkHttpClient client     = new OkHttpClient.Builder().build();
        Request.Builder builder = new Request.Builder().url(formattedUrl);

        setHeaders(builder, header);

        Response response = client.newCall(builder.build()).execute();

        if (response.isSuccessful())
            return response.body().string();
        return null;
    }
}
