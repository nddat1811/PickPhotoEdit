package com.example.btn_08;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ShowChannels extends Activity {
    ArrayAdapter<String> adapterMainSubjects;
    ListView myMainListView;
    Context context;
    SingleItem selectedNewsItem;
    String appName = "";
    String[][] thanhnien = {
            {"https://thanhnien.vn/rss/home.rss", "Trang chủ"},
            {"https://thanhnien.vn/rss/video-316.rss", "Video"},
            {"https://thanhnien.vn/rss/thoi-su-4.rss", "Thời sự"},
            {"https://thanhnien.vn/rss/van-hoa-93.rss", "Văn hóa"},
            {"https://thanhnien.vn/rss/giai-tri-285.rss", "Giải trí"},
            {"https://thanhnien.vn/rss/the-thao-318.rss", "Thể thao"},
            {"https://thanhnien.vn/rss/doi-song-17.rss", "Đời sống"},
            {"https://thanhnien.vn/rss/tai-chinh-kinh-doanh-49.rss", "Tài chính kinh doanh"},
            {"https://thanhnien.vn/rss/gioi-tre-69.rss", "Giới trẻ"},
            {"https://thanhnien.vn/rss/giao-duc-26.rss", "Giáo dục"},
            {"https://thanhnien.vn/rss/cong-nghe-game-315.rss", "Công nghệ game"},
            {"https://thanhnien.vn/rss/suc-khoe-65.rss", "Sức khỏe"},
            {"https://thanhnien.vn/rss/xe-317.rss", "Xe"},
            {"https://thanhnien.vn/rss/thoi-trang-tre-319.rss", "Thời trang trẻ"},
            {"https://thanhnien.vn/rss/ban-doc-190.rss", "Bạn đọc"},
            {"https://thanhnien.vn/rss/ban-can-biet-153.rss", "Bạn cần biết"}
    };
    String[][] vnexpress = {
            {"https://vnexpress.net/rss/tin-moi-nhat.rss", "Trang chủ"},
            {"https://vnexpress.net/rss/suc-khoe.rss", "Sức khỏe"},
            {"https://vnexpress.net/rss/the-gioi.rss", "Thế giới"},
            {"https://vnexpress.net/rss/gia-dinh.rss", "Đời sống"},
            {"https://vnexpress.net/rss/thoi-su.rss", "Thời sự"},
            {"https://vnexpress.net/rss/du-lich.rss", "Du lịch"},
            {"https://vnexpress.net/rss/kinh-doanh.rss", "Kinh doanh"},
            {"https://vnexpress.net/rss/khoa-hoc.rss", "Khoa học"},
            {"https://vnexpress.net/rss/so-hoa.rss", "Số hóa"},
            {"https://vnexpress.net/rss/giai-tri.rss", "Giải trí"},
            {"https://vnexpress.net/rss/oto-xe-may.rss", "Xe"},
            {"https://vnexpress.net/rss/the-thao.rss", "Thể thao"},
            {"https://vnexpress.net/rss/y-kien.rss", "Ý kiến"},
            {"https://vnexpress.net/rss/phap-luat.rss", "Pháp luật"},
            {"https://vnexpress.net/rss/tam-su.rss", "Tâm sự"},
            {"https://vnexpress.net/rss/giao-duc.rss", "Giáo dục"},
            {"https://vnexpress.net/rss/cuoi.rss", "Cười"},
            {"https://vnexpress.net/rss/tin-moi-nhat.rss", "Tin mới nhất"},
            {"https://vnexpress.net/rss/tin-xem-nhieu.rss", "Tin xem nhiều"},
            {"https://vnexpress.net/rss/tin-noi-bat.rss", "Tin nổi bật"},
    };
    String[][] tuoitre = {
            {"https://tuoitre.vn/rss/tin-moi-nhat.rss", "Trang chủ"},
            {"https://tuoitre.vn/rss/thoi-su.rss", "Thời sự"},
            {"https://tuoitre.vn/rss/the-gioi.rss", "Thế giới"},
            {"https://tuoitre.vn/rss/phap-luat.rss", "Pháp luật"},
            {"https://tuoitre.vn/rss/kinh-doanh.rss", "Kinh doanh"},
            {"https://tuoitre.vn/rss/nhip-song-so.rss", "Công nghệ"},
            {"https://tuoitre.vn/rss/xe.rss", "Xe"},
            {"https://tuoitre.vn/rss/nhip-song-tre.rss", "Nhịp sống trẻ"},
            {"https://tuoitre.vn/rss/van-hoa.rss", "Văn hóa"},
            {"https://tuoitre.vn/rss/giai-tri.rss", "Giải trí"},
            {"https://tuoitre.vn/rss/the-thao.rss", "Thể thao"},
            {"https://tuoitre.vn/rss/giao-duc.rss", "Giáo dục"},
            {"https://tuoitre.vn/rss/khoa-hoc.rss", "Khoa học"},
            {"https://tuoitre.vn/rss/suc-khoe.rss", "Sức khỏe"},
            {"https://tuoitre.vn/rss/gia-that.rss", "Giả thật"},
            {"https://tuoitre.vn/rss/thu-gian.rss", "Thư giãn"},
            {"https://tuoitre.vn/rss/ban-doc-lam-bao.rss", "Bạn đọc làm báo"},
            {"https://tuoitre.vn/rss/du-lich.rss", "Du lịch"}
    };
    String[][][] myUrlCaptionMenu = new String[][][]{thanhnien, vnexpress, tuoitre};
    String[] myUrlCaption;
    String[] myUrlAddress;
    int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_channels);

        myMainListView = (ListView) this.findViewById(R.id.myListView);
        // find out which intent is calling us & grab data bundle holding selected url & caption sent to us
        Intent callingIntent = getIntent();
        Bundle myBundle = callingIntent.getExtras();
        appName = myBundle.getString("appName");

        if (appName.equals("Thanh niên")) index = 0;
        if (appName.equals("VnExpress")) index = 1;
        if (appName.equals("Tuổi trẻ")) index = 2;

        myUrlCaption = new String[myUrlCaptionMenu[index].length];
        myUrlAddress = new String[myUrlCaptionMenu[index].length];

        for (int i = 0; i < myUrlAddress.length; i++) {
            myUrlAddress[i] = myUrlCaptionMenu[index][i][0];
            myUrlCaption[i] = myUrlCaptionMenu[index][i][1];
        }
        context = getApplicationContext();

        // update app’s top ‘TitleBar’ (eg. ‘CHANNELS IN VNEXPRESS’)
        this.setTitle("Channels in " + appName);
        // user will tap on a ListView’s row to request category’s headlines
        myMainListView = (ListView) this.findViewById(R.id.myListView);
        myMainListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> _av, View _v, int _index, long _id) {
                String urlAddress = myUrlAddress[_index], urlCaption = myUrlCaption[_index];
                //create an Intent to talk to activity: ShowHeadlines
                Intent callShowHeadlines = new Intent(ShowChannels.this, ShowHeadlines.class);
                //prepare a Bundle and add the input arguments: url & caption
                Bundle myData = new Bundle();
                myData.putString("urlAddress", urlAddress);
                myData.putString("urlCaption", urlCaption);
                myData.putString("appName", appName);
                callShowHeadlines.putExtras(myData);
                startActivity(callShowHeadlines);
            }
        });
        // fill up the Main-GUI’s ListView with main news categories
        adapterMainSubjects = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myUrlCaption);
        myMainListView.setAdapter(adapterMainSubjects);
    }//onCreate
}