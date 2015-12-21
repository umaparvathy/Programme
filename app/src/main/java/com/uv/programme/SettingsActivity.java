package com.uv.programme;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SettingsActivity extends SampleActivity {

    SQLiteHelper dbHelper;
    EditText name, place;
    Button save;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
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

        name = (EditText) findViewById(R.id.name);
        place = (EditText) findViewById(R.id.place);
        save = (Button) findViewById(R.id.save);
        dbHelper = new SQLiteHelper(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                save();
            }
        });
        User currentUser = getCurrentUser();
        if(currentUser != null) {
            name.setText(currentUser.getName());
            place.setText(currentUser.getPlace());
        }
    }

    private void save() {
        String userName = name.getText().toString();
        String userPlace = place.getText().toString();
        User user = new User(userName, userPlace);
        User currentUser = getCurrentUser();
        if(currentUser != null) {
            dbHelper.updateUserRecord(currentUser.getUserId(), user);
        } else {
            dbHelper.insertUserRecord(user);
        }
        Toast.makeText(SettingsActivity.this, "Your settings updated", Toast.LENGTH_SHORT).show();
        this.finish();
    }

    private User getCurrentUser() {
        User user = dbHelper.getCurrentUser();
        return user;
    }

}
