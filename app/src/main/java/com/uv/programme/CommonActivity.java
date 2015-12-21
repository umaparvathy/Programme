package com.uv.programme;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by venkatsr on 17/11/15.
 */
public class CommonActivity extends AppCompatActivity {

    ImageView contest, stories, facebook, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actions_footer);
        initializeViews();
        setListeners();
    }

    protected void initializeViews() {

        contest = (ImageView) findViewById(R.id.contest);
        stories = (ImageView) findViewById(R.id.stories);
        facebook = (ImageView) findViewById(R.id.facebook);
        email = (ImageView) findViewById(R.id.email);
    }

    protected void setListeners() {
        contest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), "Contest is selected", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), ContestActivity.class);
                startActivity(intent);
            }
        });
        stories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), YoutubeVideosActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Stories is selected", Toast.LENGTH_SHORT).show();
            }
        });
        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Facebook is selected", Toast.LENGTH_SHORT).show();
                openFacebook();
            }
        });
        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(),"Email is selected", Toast.LENGTH_SHORT).show();
                openEmail();
            }
        });
    }

    protected void openFacebook() {
        String facebookUrl = "https://www.facebook.com/SUTTAKADHAI1000";
        try {
            int versionCode = getPackageManager().getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) {
                Uri uri = Uri.parse("fb://facewebmodal/f?href=" + facebookUrl);
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            } else {
                Uri uri = Uri.parse("fb://page/<id_here>");
                startActivity(new Intent(Intent.ACTION_VIEW, uri));
            }
        } catch (PackageManager.NameNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(facebookUrl)));
        }
    }

    protected void openWhatsappContact() {
        String number = "9739140113";
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        startActivity(Intent.createChooser(i, ""));
    }

    protected void openEmail() {
        String to = "senthil@radiomirchi.com";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?to=" + to);
        intent.setData(data);
        startActivity(intent);
    }

    protected void aboutUs() {
        Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
        startActivity(intent);
    }

}
