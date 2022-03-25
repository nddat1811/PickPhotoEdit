package com.example.btn_08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends Activity {
    MyImageAdapter adapterMainSubjects;
    GridView myMainGridView;
    Context context;
    SingleItem selectedNewsItem;
    String[] apps = {"Thanh Niên", "VnExpress", "Tuổi trẻ"};
    Integer[] thumbnails = {R.drawable.logo_tn, R.drawable.logo_vnex, R.drawable.logo_tt};

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