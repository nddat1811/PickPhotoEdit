package com.example.exercise_08;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ItemInChannel extends AppCompatActivity {
    ArrayList<SingleItem> listItems = new ArrayList<SingleItem>();
    ListView listViewItems;
    SingleItem selectedNewsItem;
    String urlAddress, urlCaption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_in_channel);
        Intent i = getIntent();
        Bundle b = i.getExtras();
        this.setTitle(b.getString("channel"));

        listViewItems = (ListView)findViewById(R.id.listItems);
        listViewItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                selectedNewsItem = listItems.get(i);
                showNiceDialogBox(selectedNewsItem, getApplicationContext(), b.getInt("icon"));
            }
        });

        urlAddress = b.getString("urlAddress");
        urlCaption = b.getString("urlCaption");

        DownloadRssFeed downloader = new DownloadRssFeed(ItemInChannel.this);
        downloader.execute(urlAddress, urlCaption);
    }

    private void showNiceDialogBox(SingleItem selectedStoryItem, Context applicationContext, int icon) {
        String title = selectedStoryItem.getTitle();
        String description = selectedStoryItem.getDescription();
        if (title.toLowerCase().equals(description.toLowerCase())){ description = ""; }
        try {
            final Uri storyLink = Uri.parse(selectedStoryItem.getLink());
            AlertDialog.Builder myBuilder = new AlertDialog.Builder(this);
            myBuilder.setIcon(icon)
                    .setTitle(Html.fromHtml(urlCaption) )
                    .setMessage(title + "\n\n" + Html.fromHtml(description) + "\n")
                    .setPositiveButton("Close", null)
                    .setNegativeButton("More", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent browser = new Intent(Intent.ACTION_VIEW, storyLink);
                            startActivity(browser);
                        }
                    })
                    .show();
        }
        catch (Exception e) { Log.e("Error DialogBox", e.getMessage() ); }

    }
}