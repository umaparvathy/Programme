package com.uv.programme;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

/**
 * Created by venkatsr on 17/11/15.
 */
public class AboutUsActivity extends SampleActivity {

    Button participate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("AboutUsActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu_view);
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.w("AboutUsActivity", "onCreateOptionsMenu");
        super.onCreateOptionsMenu(menu);
        //getMenuInflater().inflate(R.menu.menu_second, menu);  //<-You should remove this
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("AboutUsActivity", "onOptionsItemSelected");
        return super.onOptionsItemSelected(item);
    }
}
