package com.example.benki.epicture.Activities;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.benki.epicture.Application.Epicture;
import com.example.benki.epicture.Application.GlideApp;
import com.example.benki.epicture.ImgurAPI.ImgurAPI;
import com.example.benki.epicture.ImgurAPI.Instances.Managers.PictureManager;
import com.example.benki.epicture.R;

/**
 * DEV_epicture_2018
 * Created by Lucas Benkemoun on 06/11/18.
 * Copyright © 2018 Lucas Benkemoun. All rights reserved.
 */
public class FinishUploadActivity extends AppCompatActivity {

    private EditText title;
    private EditText description;
    private EditText album;
    private ImageView image;
    private String imageContent;

    public final static String PARAM_CONTENT = "content";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish_upload);

        if (savedInstanceState != null) {
            imageContent = savedInstanceState.getString(PARAM_CONTENT);
        }
        else if (getIntent() != null) {
            imageContent = getIntent().getStringExtra(PARAM_CONTENT);
        }

        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        album = findViewById(R.id.album);
        image = findViewById(R.id.image);

        byte[] decodedString = Base64.decode(imageContent, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

        GlideApp.with(this)
                .asBitmap()
                .load(decodedByte)
                .placeholder(R.drawable.ic_menu_camera)
                .into(image);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(PARAM_CONTENT, imageContent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater findMenuItems = getMenuInflater();
        findMenuItems.inflate(R.menu.menu_ok, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_ok:
                if (!title.getText().toString().equals("")) {
                    final String t = title.getText().toString();
                    final String d = description.getText().toString();
                    final String c = imageContent;
                    final String a = album.getText().toString();
                    final Epicture epicture = (Epicture) getApplicationContext();
                    new AsyncTask<Void, Void, Void>() {
                        @Override
                        protected Void doInBackground(Void... voids) {
                            ImgurAPI.sendPicture(epicture.getManager(), new PictureManager.Uploader(
                                    c, t, d, a != "" ? a : null));
                            return null;
                        }
                    }.execute();

                    finish();
                }
                else {
                    Toast.makeText(this, "Title field is empty", Toast.LENGTH_LONG).show();
                }
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
