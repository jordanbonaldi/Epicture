package com.example.benki.epicture.Utils;

import android.util.Log;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
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

    public interface actionOnPost {
        void action(Request.Builder build, RequestBody body);

        RequestBody getRequestBody();
    }

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

        System.out.println(response.code());

        if (response.isSuccessful())
            return response.body().string();
        return "";
    }

    public static String launchPostRequest(
            String formattedUrl,
            HashMap<String, String> header,
            actionOnPost post
    ) throws Exception
    {
        RequestBody requestBody = post.getRequestBody();

        Request.Builder build = new Request.Builder().url(formattedUrl);

        setHeaders(build, header);

        post.action(build, requestBody);

        OkHttpClient client = new OkHttpClient();
        Response response = client.newCall(build.build()).execute();

        System.out.println(response.code());

        if (response.isSuccessful())
            return response.body().string();

        return new JSONObject().put("success", "false").toString();
    }
}