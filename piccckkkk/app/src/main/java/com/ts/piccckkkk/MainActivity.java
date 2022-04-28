package com.ts.piccckkkk;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    TextView tvEmpty;
    ArrayList<String> arrayList = new ArrayList<>();
    MainAdapter  adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recycler_view);

        tvEmpty = findViewById(R.id.tv_empty);

        //Add values

        arrayList.addAll(Arrays.asList("One", "2", "3", "4"));

        //Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MainAdapter(this, arrayList, tvEmpty);
        recyclerView.setAdapter(adapter);
    }
}