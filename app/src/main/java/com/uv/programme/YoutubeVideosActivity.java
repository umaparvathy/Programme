package com.uv.programme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by venkatsr on 17/11/15.
 */
public class YoutubeVideosActivity extends SampleActivity {
    private static final String TAG = "YoutubeVideosActivity";
    private ListView videosFound;

    private ImageView firstVideoThumbnail;
    private TextView firstVideoTitle, firstVideoDescription;

    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.stories_list);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.reversehandshootmedium);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        firstVideoThumbnail = (ImageView) findViewById(R.id.first_video_thumbnail);
        firstVideoTitle = (TextView) findViewById(R.id.first_video_title);
        firstVideoDescription = (TextView) findViewById(R.id.first_video_description);
        videosFound = (ListView)findViewById(R.id.suttaKadhais);
        handler = new Handler();
        /*initializeViews();
        setListeners();*/
        addClickListener();
        searchOnYoutube();
    }

    private List<VideoItem> searchResults;

    /*private void searchOnYoutube(final String keywords){
        new Thread(){
            public void run(){
                YoutubeConnector yc = new YoutubeConnector(YoutubeVideosActivity.this);
                searchResults = yc.search(keywords);
                handler.post(new Runnable(){
                    public void run(){
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }*/

    private void addClickListener(){
        firstVideoThumbnail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPlayerActivity(0);
            }
        });
        firstVideoTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPlayerActivity(0);
            }
        });
        firstVideoDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPlayerActivity(0);
            }
        });
        videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> av, View v, int pos,
                                    long id) {
                callPlayerActivity(pos);
            }

        });
    }

    private void callPlayerActivity(int pos) {
        Intent intent = new Intent(getApplicationContext(), PlayerActivity.class);
        intent.putExtra("VIDEO_ID", searchResults.get(pos).getId());
        startActivity(intent);
    }

    private void searchOnYoutube(){
        new Thread(){
            public void run(){
                Log.e(TAG, "Calling YoutubeConnector constructor");
                YoutubeConnector yc = new YoutubeConnector(YoutubeVideosActivity.this);
                //searchResults = yc.search();
                searchResults = yc.searchPlayList();
                handler.post(new Runnable(){
                    public void run(){
                        updateVideosFound();
                    }
                });
            }
        }.start();
    }

    private void updateVideosFound(){
        ArrayAdapter<VideoItem> adapter = new ArrayAdapter<VideoItem>(getApplicationContext(), R.layout.new_video_item, searchResults){
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                if(convertView == null){
                    convertView = getLayoutInflater().inflate(R.layout.new_video_item, parent, false);
                }

                ImageView thumbnail = null;
                TextView title = null;
                TextView description = null;

                if(position == 0) {
                    /*thumbnail = (ImageView)findViewById(R.id.first_video_thumbnail);
                    title = (TextView)findViewById(R.id.first_video_title);
                    description = (TextView)findViewById(R.id.first_video_description);*/
                    thumbnail = firstVideoThumbnail;
                    title = firstVideoTitle;
                    description = firstVideoDescription;
                } else {
                    thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
                    title = (TextView)convertView.findViewById(R.id.video_title);
                    description = (TextView)convertView.findViewById(R.id.video_description);
                }

                title.setTextSize(15);
                description.setTextSize(15);
                VideoItem searchResult = searchResults.get(position);

                Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
                title.setText(searchResult.getTitle());
                description.setText(searchResult.getDescription());
                return convertView;
            }
        };

        videosFound.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w("YoutubeVideosActivity", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.menu_second, menu);  //<-You should remove this
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("YoutubeVideosActivity", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }
}
