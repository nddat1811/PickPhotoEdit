package com.example.exercise_08;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Channel extends AppCompatActivity {
    String[][] myUrlCaptionMenu_VNExpress = {
            {"https://vnexpress.net/rss/thoi-su.rss","Thời sự"},
            {"https://vnexpress.net/rss/kinh-doanh.rss","Kinh Doanh"},
            {"https://vnexpress.net/rss/giai-tri.rss","Giải trí"},
            {"https://vnexpress.net/rss/the-thao.rss","Thể thao"},
            {"https://vnexpress.net/rss/phap-luat.rss","Pháp luật"},
            {"https://vnexpress.net/rss/giao-duc.rss","Giáo dục"},
            {"https://vnexpress.net/rss/suc-khoe.rss","Sức khỏe"},
            {"https://vnexpress.net/rss/gia-dinh.rss","Đời sống"},
            {"https://vnexpress.net/rss/du-lich.rss","Du lịch"},
            {"https://vnexpress.net/rss/khoa-hoc.rss","Khoa Học"}
    };

    String[][] myUrlCaptionMenu_Thanhnien = {
            {"https://thanhnien.vn/rss/thoi-su-4.rss","Thời sự"},
            {"https://thanhnien.vn/rss/tai-chinh-kinh-doanh-49.rss","Kinh Doanh"},
            {"https://thanhnien.vn/rss/giai-tri-285.rss","Giải trí"},
            {"https://thanhnien.vn/rss/the-thao-318.rss","Thể thao"},
            {"https://thanhnien.vn/rss/van-hoa-93.rss","Văn hóa"},
            {"https://thanhnien.vn/rss/giao-duc-26.rss","Giáo dục"},
            {"https://thanhnien.vn/rss/suc-khoe-65.rss","Sức khỏe"},
            {"https://thanhnien.vn/rss/doi-song-17.rss","Đời sống"},
            {"https://thanhnien.vn/rss/cong-nghe-game-315.rss","Công nghệ - Game"},
            {"https://thanhnien.vn/rss/gioi-tre-69.rss","Giới trẻ"}
    };

    String[][] myUrlCaptionMenu_Tuoitre = {
            {"https://tuoitre.vn/rss/thoi-su.rss","Thời sự"},
            {"https://tuoitre.vn/rss/kinh-doanh.rss","Kinh Doanh"},
            {"https://tuoitre.vn/rss/giai-tri.rss","Giải trí"},
            {"https://tuoitre.vn/rss/the-thao.rss","Thể thao"},
            {"https://tuoitre.vn/rss/phap-luat.rss","Pháp luật"},
            {"https://tuoitre.vn/rss/giao-duc.rss","Giáo dục"},
            {"https://tuoitre.vn/rss/suc-khoe.rss","Sức khỏe"},
            {"https://tuoitre.vn/rss/xe.rss","Xe"},
            {"https://tuoitre.vn/rss/du-lich.rss","Du lịch"},
            {"https://tuoitre.vn/rss/khoa-hoc.rss","Khoa Học"}
    };

    String[][] myUrlCaptionMenu_VietnamNet = {
            {"https://vietnamnet.vn/rss/thoi-su.rss","Thời sự"},
            {"https://vietnamnet.vn/rss/kinh-doanh.rss","Kinh Doanh"},
            {"https://vietnamnet.vn/rss/giai-tri.rss","Giải trí"},
            {"https://vietnamnet.vn/rss/the-thao.rss","Thể thao"},
            {"https://vietnamnet.vn/rss/phap-luat.rss","Pháp luật"},
            {"https://vietnamnet.vn/rss/giao-duc.rss","Giáo dục"},
            {"https://vietnamnet.vn/rss/suc-khoe.rss","Sức khỏe"},
            {"https://vietnamnet.vn/rss/oto-xe-may.rss","Xe"},
            {"https://vietnamnet.vn/rss/thoi-su-chinh-tri.rss","Chính trị"}
    };

    String[][] myUrlCaptionMenu_Tinhte = {
            {"https://tinhte.vn/rss","Tinh tế"}
    };

    String[][] myUrlCaptionMenu_GameK = {
            {"https://gamek.vn/home.rss","Game K"}
    };

    String[] paper = {"thanhnien", "vnexpress", "vietnamnet", "tuoitre", "tinhte", "gamek"};
    String[] title = {"Báo Thanh Niên", "Báo VNExpress", "Báo VietnamNet", "Báo Tuổi trẻ", "Tinh tế", "Game K"};
    int[] icon = {R.drawable.thanhnienn, R.drawable.vnexpress, R.drawable.vietnamnet,
    R.drawable.tuoitre, R.drawable.tinhte, R.drawable.gamek};

    String[][][] UrlsOfPaper = { myUrlCaptionMenu_Thanhnien,myUrlCaptionMenu_VNExpress,
            myUrlCaptionMenu_VietnamNet, myUrlCaptionMenu_Tuoitre,
            myUrlCaptionMenu_Tinhte, myUrlCaptionMenu_GameK };

    String[] myUrlCaption = null;
    String[] myUrlAddress = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channel);
        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        String paperChooser = b.getString("paper");
        int iconValue = -1;

        for(int i=0; i<paper.length; i++){
            if(paperChooser.equals(paper[i])){
                this.setTitle(title[i]);
                iconValue = icon[i];

                myUrlAddress = new String[UrlsOfPaper[i].length];
                myUrlCaption = new String[UrlsOfPaper[i].length];

                for (int j=0; j<UrlsOfPaper[i].length; j++) {
                    myUrlAddress[j] = UrlsOfPaper[i][j][0];
                    myUrlCaption[j] = String.valueOf(UrlsOfPaper[i][j][1]);
                }
            }
        }

        ListView listView = (ListView) findViewById(R.id.listChannel);
        listView.setAdapter(new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                myUrlCaption));
        int finalIconValue = iconValue;
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String urlAddress = myUrlAddress[i],
                        urlCaption = myUrlCaption[i];

                Bundle b = new Bundle();
                b.putString("channel", listView.getItemAtPosition(i).toString());
                b.putString("urlAddress", urlAddress);
                b.putString("urlCaption", urlCaption);
                b.putInt("icon", finalIconValue);

                Intent newIntent = new Intent(Channel.this, ItemInChannel.class);
                newIntent.putExtras(b);
                startActivity(newIntent);
            }
        });
    }
}