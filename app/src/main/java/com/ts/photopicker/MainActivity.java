package com.ts.photopicker;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonAdd;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        checkPermission();
    }

    private void checkPermission() {
        //Initialize permission    ở đây khởi tạo biến cho phép để chỉnh sửa ảnh trong thư mục á, gradle có user-permiss rồi, này call ra thôi
        int permission = ActivityCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        //Check condition
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){ //Q laf 29 R laf 28 ???
            // này t chưa hiểu lắm :v cái điều kiện này không hiểu làm gì

            //When device version is greater than equal to version 10
            //Create method
            pickImage();  // chọn ảnh
        } else {
            //When device version is below version 10
            //Check condition
            if (permission != PackageManager.PERMISSION_GRANTED) {
                //When permission is not granted
                //Request permission
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 100);

            } else {
                //When permission is granted
                //Call method
                pickImage();
            }
        }

    }

    private void pickImage() {
        //Initialize Intent
        Intent intent = new Intent(Intent.ACTION_PICK);  // Nó tạo 1 cái Intent mới với action là pick
        //Set type
        intent.setType("image/*");  //Chọn các loại intent là ảnh
        //Start activity for result
        startActivityForResult(intent, 100); //này t ghi theo thôi chứ cũng chưa hiểu :v
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        //Check condition
        if (requestCode == 100 && grantResults.length > 0 && grantResults[0]
             == PackageManager.PERMISSION_GRANTED) {
            //When permission is granted
            //Call method
            pickImage();
        } else {
            //When permission is denied
            //Display toast
            Toast.makeText(getApplicationContext(),
                    "Permission denied", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Check condition
        if (resultCode == RESULT_OK) {
            //When result is ok initialize uri
            Uri uri = data.getData();  //Này là lấy uri của cái ảnh dc pick
            switch (requestCode) {
                case 100:
                    //When request code is equal to 100
                    //Initialize intent
                    Intent intent = new Intent(MainActivity.this, EditPicture.class);   //Tạo intent mới với
                    //Set data
                    Bundle bundle = new Bundle();
                    Log.d("img_data", data.getData().toString());
                    bundle.putString("img_data", uri.toString());    // chuyển uri thành string rồi chuyển qua activity 2 á
                    intent.putExtras(bundle);
                    startActivity(intent);          //Chuyển qua màn hình edit... bên edit chủ yếu mới show ra á, chưa làm gì ehét
                    //Set output directory name

            }
        }
    }
}