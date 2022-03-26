package com.example.exercise_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static String niceDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("EE MMM d, yyyy",
                Locale.US);
        return sdf.format(new Date()); //Monday Apr 7, 2014
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("Đọc báo cùng bạn \n" + niceDate() );

        ImageView thanhnien = findViewById(R.id.Logo_ThanhNien);
        ImageView vnExpress = findViewById(R.id.Logo_VNExpress);
        ImageView vietnamNet = findViewById(R.id.Logo_VietnamNet);
        ImageView tuoitre = findViewById(R.id.Logo_TuoiTre);
        ImageView tinhte = findViewById(R.id.Logo_TinhTe);
        ImageView gameK = findViewById(R.id.Logo_GameK);

        thanhnien.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: move to new activity
                Bundle b = new Bundle();
                b.putString("paper", "thanhnien");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        vnExpress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("paper", "vnexpress");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        vietnamNet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("paper", "vietnamnet");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        tuoitre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("paper", "tuoitre");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        tinhte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("paper", "tinhte");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
        gameK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle b = new Bundle();
                b.putString("paper", "gamek");

                Intent newIntent = new Intent(MainActivity.this, Channel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
    }
}