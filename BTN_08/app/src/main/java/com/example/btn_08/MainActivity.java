package com.example.btn_08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


// Nguồn RSS:   https://vtc.vn/main-rss.html            ---- VTC News
//              https://baotintuc.vn/rss.htm            ---- Báo tin tức
//              https://vnexpress.net/rss               ---- VNexpress
//              https://thanhnien.vn/rss.html           ---- Báo Thanh niên
//              https://tuoitre.vn/rss.htm              ---- Tuổi trẻ
//              https://www.24h.com.vn/guest/RSS/       ---- Báo 24h
//              https://vietnamnet.vn/vn/rss/           ---- VietNamNet
//              https://ictnews.vietnamnet.vn/rss/      ---- ITC New
public class MainActivity extends Activity {
    MyImageAdapter adapterMainSubjects;
    GridView myMainGridView;
    Context context;
    SingleItem selectedNewsItem;
    String[] apps = {"Thanh Niên", "VnExpress", "Tuổi trẻ", "VTC News" ,"Báo tin tức", "Báo 24h", "VietNamNet", "ITCNew"};
    Integer[] thumbnails = {R.drawable.logo_tn, R.drawable.logo_vnex, R.drawable.logo_tt, R.drawable.logo_vtcnews, R.drawable.logo_tintuc,
            R.drawable.logo_24h, R.drawable.logo_vnnet, R.drawable.logo_itcnew};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        this.setTitle("NEWS APP");
        myMainGridView = (GridView) findViewById(R.id.myGridView);
        myMainGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String appName = apps[_index];
                //create an Intent to talk to activity: ShowChannels
                Intent callShowChannels = new Intent(MainActivity.this, ShowChannels.class);
                //prepare a Bundle and add the input arguments: app name
                Bundle myData = new Bundle();
                myData.putString("appName", appName);
                callShowChannels.putExtras(myData);
                startActivity(callShowChannels);
            }
        });
        // fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new MyImageAdapter(this, thumbnails);
        myMainGridView.setAdapter(adapterMainSubjects);
    }//onCreate
}