package com.uv.programme;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * Created by venkatsr on 18/11/15.
 */
public class SampleActivity extends AppCompatActivity {

    SQLiteHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbHelper = new SQLiteHelper(this);
        //setContentView(R.layout.options_menu_view);
        /*Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);*/

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        Log.w("SampleActivity", "onCreateOptionsMenu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Log.w("SampleActivity", "onOptionsItemSelected");
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            Toast.makeText(getApplicationContext(), "Share option is selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_search) {
            Toast.makeText(getApplicationContext(),"Search option is selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_participate) {
            //Toast.makeText(getApplicationContext(),"Participate option is selected", Toast.LENGTH_SHORT).show();
            participateInContest();
        } else if (id == R.id.action_stories) {
            //Toast.makeText(getApplicationContext(),"Stories option is selected", Toast.LENGTH_SHORT).show();
            listenToStories();
        } else if (id == R.id.action_favorites) {
            Toast.makeText(getApplicationContext(), "Favorites option is selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_facebook) {
            //Toast.makeText(getApplicationContext(),"Facebook option is selected", Toast.LENGTH_SHORT).show();
            openFacebook();
        } else if (id == R.id.action_feedback) {
            //Toast.makeText(getApplicationContext(),"Feedback option is selected", Toast.LENGTH_SHORT).show();
            openEmail();
        } else if (id == R.id.action_settings) {
            openSettings();
            //Toast.makeText(getApplicationContext(),"Settings option is selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_help) {
            Toast.makeText(getApplicationContext(),"Help option is selected", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.action_about) {
            //Toast.makeText(getApplicationContext(),"About option is selected", Toast.LENGTH_SHORT).show();
            aboutUs();
        } else if (id == R.id.action_about_app) {
            Toast.makeText(getApplicationContext(),"AboutApp option is selected", Toast.LENGTH_SHORT).show();
        }


        return super.onOptionsItemSelected(item);
    }

    protected void openFacebook() {
        String facebookUrl = getResources().getString(R.string.facebook_page);
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

        User user = dbHelper.getCurrentUser();
        if(user != null) {
            String helpText = getHelpText(user);
            ClipboardManager clipboard = (ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
            ClipData clip = ClipData.newPlainText("label", helpText);
            clipboard.setPrimaryClip(clip);
            Toast.makeText(getApplicationContext(), "Your name and location are copied to clipboard", Toast.LENGTH_SHORT);
        } else {
            Toast.makeText(getApplicationContext(), "Please set your name and place in settings menu for easy use", Toast.LENGTH_SHORT);
        }


        String number = getResources().getString(R.string.whatsapp_number);
        Uri uri = Uri.parse("smsto:" + number);
        Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        i.putExtra(Intent.EXTRA_TEXT, "The SMS text");
        startActivity(Intent.createChooser(i, "Share with"));


        /*Intent i = new Intent(Intent.ACTION_SEND, Uri.parse("content://com.android.contacts/data/" + number));
        //Uri uri = Uri.parse("smsto:" + number);
        //Intent i = new Intent(Intent.ACTION_SENDTO, uri);
        i.setPackage("com.whatsapp");
        i.setType("text/plain");
        i.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        i.putExtra(Intent.EXTRA_TEXT, "The SMS text");
        startActivity(i);*/
    }



    protected void openEmail() {
        String to = getResources().getString(R.string.email_id);;
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri data = Uri.parse("mailto:?to=" + to);
        intent.setData(data);
        startActivity(intent);
    }

    protected void openSettings() {
        Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
        startActivity(intent);
    }

    protected void aboutUs() {
        Intent intent = new Intent(getApplicationContext(), AboutUsActivity.class);
        startActivity(intent);
    }

    protected void participateInContest() {
        Intent intent = new Intent(getApplicationContext(), ContestActivity.class);
        startActivity(intent);
    }

    protected void listenToStories() {
        Intent intent = new Intent(getApplicationContext(), YoutubeVideosActivity.class);
        startActivity(intent);
    }

    private String getHelpText(User user) {
        String helpText = null;
        String userName = user.getName();
        String userPlace = user.getPlace();
        helpText = "I am " + userName + " from " + userPlace + ". The answer is \n";
        return helpText;
    }
}
