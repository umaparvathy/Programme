package com.uv.programme;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

/**
 * Created by venkatsr on 17/11/15.
 */
public class AboutUsActivityOld extends SampleActivity {

    Button participate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.w("AboutUsActivity", "onCreate");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.options_menu_view);

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
