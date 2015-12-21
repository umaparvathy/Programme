package com.uv.programme;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

/**
 * Created by venkatsr on 17/11/15.
 */
public class ContestActivityOld extends SampleActivity {

    Button participate;
    ImageView participateButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.contest);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(R.drawable.arrow);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //participate = (Button)findViewById(R.id.particpateInContest);
        participateButton = (ImageView)findViewById(R.id.messageMe);
        addClickListener();
        //initializeViews();
        //setListeners();
    }

    private void addClickListener() {
        participateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    openWhatsappContact();
                } catch (Exception e) {
                    openEmail();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w("ContestActivity", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.menu_second, menu);  //<-You should remove this
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("ContestActivity", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }

}
