package com.example.harshjain.videoplayer;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
private ListView listView;
private List<VideoModel> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView)findViewById(R.id.list_item);
        if(ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},0);
        }

        list = getAllAudioFromDevice(MainActivity.this);
        
    }


    public List<VideoModel> getAllAudioFromDevice(final Context context) {

        final List<VideoModel> tempAudioList = new ArrayList<>();
        Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String[] projection = {MediaStore.Video.VideoColumns.DATA};
        Cursor c = context.getContentResolver().query(uri, projection, null,null, null);
        if (c != null) {
            while (c.moveToNext()) {
                VideoModel videoModel = new VideoModel();
                String path = c.getString(0);
//                String album = c.getString(1);
//                String artist = c.getString(2);
                String name = path.substring(path.lastIndexOf("/") + 1);
                videoModel.setName(name);
                videoModel.setPath(path);
                tempAudioList.add(videoModel);
            }c.close();
        }
        return tempAudioList;
    }
}
