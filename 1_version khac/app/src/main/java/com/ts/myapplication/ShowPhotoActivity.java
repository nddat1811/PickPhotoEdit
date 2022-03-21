package com.ts.myapplication;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;

public class ShowPhotoActivity extends AppCompatActivity {

    ImageView imageview;
    Button btn_edit;

    String path = "";

    @Override
    protected void onCreate(Bundle saveInstanceState){
        super.onCreate(saveInstanceState);

        setContentView(R.layout.activity_show_photo);

        imageview = (ImageView) findViewById(R.id.imageview);
        btn_edit = (Button) findViewById(R.id.btn_edit);
        path = getIntent().getExtras().getString("path");

        File imgfile = new File(path);

        if(imgfile.exists()){
            Bitmap myBitmap = BitmapFactory.decodeFile(imgfile.getAbsolutePath());
            //new android or after android 10+
            imageview.setImageBitmap(RotateBitmap(myBitmap, 90));
        }

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //open file in another activity in next part

                Intent intent = new Intent(ShowPhotoActivity.this, EditPhotoActivity.class);
                intent.putExtra("path", path);
                startActivity(intent);
            }
        });
    }

    private Bitmap RotateBitmap(Bitmap myBitmap, int i) {
    }
}
