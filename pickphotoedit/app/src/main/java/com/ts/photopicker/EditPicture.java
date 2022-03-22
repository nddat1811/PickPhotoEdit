package com.ts.photopicker;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class EditPicture extends AppCompatActivity {

    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_editing);

        imageView = (ImageView) findViewById(R.id.imageView);

        Intent callerIntent = getIntent();
        Bundle receive = callerIntent.getExtras();

        String uri = receive.getString("img_data");

        Log.d("uri", uri);

        Uri img = Uri.parse(uri);

        imageView.setImageURI(img);

    }

}
