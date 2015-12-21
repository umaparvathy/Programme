package com.uv.programme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

/**
 * Created by venkatsr on 17/11/15.
 */
public class NewHomeActivity extends CommonActivity {


    ImageButton action1, action2, action3, action4, action5, action6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.new_home_screen);
        setContentView(R.layout.modified_home_screen);
        initialize();
        registerListeners();
    }

    /*protected void initialize() {
        action1 = (Button) findViewById(R.id.action1);
        action2 = (Button) findViewById(R.id.action2);
        action3 = (Button) findViewById(R.id.action3);
        action4 = (Button) findViewById(R.id.action4);
        action5 = (Button) findViewById(R.id.action5);
    }*/

    protected void initialize() {
        action1 = (ImageButton) findViewById(R.id.action_participate);
        action2 = (ImageButton) findViewById(R.id.action_stars);
        action3 = (ImageButton) findViewById(R.id.action_stories);
        action4 = (ImageButton) findViewById(R.id.action_facebook);
        action5 = (ImageButton) findViewById(R.id.action_email);
        action6 = (ImageButton) findViewById(R.id.action_about);
    }

    protected void registerListeners() {
        Log.e("NewHomeActivity","Listeners Registered");
        action1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Contest is selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ContestActivity.class);
                startActivity(intent);
            }
        });
        action2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StarsActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Stories is selected", Toast.LENGTH_SHORT).show();
            }
        });
        action3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YoutubeVideosActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Stories is selected", Toast.LENGTH_SHORT).show();
            }
        });
        action4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Facebook is selected", Toast.LENGTH_SHORT).show();
                openFacebook();
            }
        });
        action5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Email is selected", Toast.LENGTH_SHORT).show();
                openEmail();
            }
        });
        action6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Email is selected", Toast.LENGTH_SHORT).show();
                aboutUs();
                /*Intent intent = new Intent(getApplicationContext(), SampleActivity.class);
                startActivity(intent);*/
            }
        });
    }


}
