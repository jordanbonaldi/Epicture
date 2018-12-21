package com.example.benki.epicture.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.R;

public class MainActivity extends AppCompatActivity {

    private final static String IMGUR_LOGIN_URL = "https://api.imgur.com/oauth2/authorize?client_id=f98676cf23e9ab9&response_type=token&state=APPLICATION_STATE";
    private final static String REDIRECT_URL = "https://app.getpostman.com/oauth2";
    private String refreshToken = "";
    private String username = "";
    private String accessToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_NoActionBar);
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        WebView imgurWebView = findViewById(R.id.connection);
        imgurWebView.setBackgroundColor(Color.TRANSPARENT);
        imgurWebView.loadUrl(IMGUR_LOGIN_URL);
        imgurWebView.getSettings().setJavaScriptEnabled(true);

        imgurWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d("WESH",url);
                if (url.contains(REDIRECT_URL)) {
                    splitUrl(url, view);
                    Epicture epicture = (Epicture) getApplicationContext();
                    epicture.setAccessToken(accessToken);
                    epicture.setRefreshToken(refreshToken);
                    epicture.setUsername(username);
                    epicture.createManager();
                    startActivity(new Intent(MainActivity.this, PicturesActivity.class));
                }
                else
                    view.loadUrl(url);

                return true;
            }
        });
    }

        private void splitUrl (String url, WebView view){
            String[] outerSplit = url.split("\\#")[1].split("\\&");

            int index = 0;

            for (String s : outerSplit) {
                String[] innerSplit = s.split("\\=");

                switch (index) {
                    // Access Token
                    case 0:
                        accessToken = innerSplit[1];
                        break;

                    // Refresh Token
                    case 3:
                        refreshToken = innerSplit[1];
                        break;

                    // Username
                    case 4:
                        username = innerSplit[1];
                        break;
                    default:

                }

                index++;
            }
        }
}