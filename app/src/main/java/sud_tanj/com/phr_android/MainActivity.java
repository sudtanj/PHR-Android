/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android;

import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.heinrichreimersoftware.androidissuereporter.IssueReporterLauncher;
import com.physicaloid.lib.Boards;
import com.physicaloid.lib.Physicaloid;
import com.physicaloid.lib.programmer.avr.UploadErrors;

import java.io.IOException;

import sud_tanj.com.phr_android.CardLayout.CardViewActivity;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Custom.MyPreferencesActivity;
import sud_tanj.com.phr_android.Database.SensorData;
import sud_tanj.com.phr_android.Database.SensorGateway;
import sud_tanj.com.phr_android.GPlusLogin.Login;
import sud_tanj.com.phr_android.GridLayout.GridViewActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView fullName;
    private TextView age;
    private NavigationView navView;
    private ImageView profilePicture;
    private Fragment currentFragment;
    private SensorData arduino;
    private SensorGateway gate;
    private Handler sensorHandler=new Handler();
    private int delay = 5*1000;
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init navigation view
        navView = (NavigationView) findViewById(R.id.nav_view);
        //Init
        Global.setContext(this);

        //Init floating button
        Global.setFloatingButton((FloatingActionButton) findViewById(R.id.fab));

        Global.getFloatingButton().hide();

        //init SensorGateway
        Global.setSensorGateway(new SensorGateway());

        //init Preference
        Global.setSettings(getApplicationContext());
        Global.changePreferences(getString(R.string.application_language), getString(R.string.settings_lang));

        Global.getSettings().registerOnSharedPreferenceChangeListener(this);

        //Init navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
               //         .setAction("Action", null).show();
                Intent intent=new Intent(getApplicationContext(),ModifySensors.class);
                intent.putExtra("modifySensor",false);
                startActivity(intent);
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        Global.setNavigationView(navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //init navigation drawer

        //init fullname
        fullName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.fullname);
        fullName.setText(Global.getFireBaseUser().getDisplayName());


        //Init profile image
        profilePicture = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.profile_picture);
        Glide.with(this)
                .load(Global.getFireBaseUser().getPhotoUrl())
                .into(profilePicture);

        //init age
        age = (TextView) navigationView.getHeaderView(0).findViewById(R.id.age);
        age.setText(Global.getSettings().getString("age_key", "") + " Years old");

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        //start handler as activity become visible
        sensorHandler.postDelayed(new Runnable() {
            public void run() {
                //do something
                //first fragment init
                if(Global.getSensorGateway().isReady()) {
                    if (currentFragment == null) {
                        currentFragment = new CardViewActivity();
                        FragmentTransaction transaction = getFragmentManager().beginTransaction();
                        transaction.replace(R.id.fragment_container, currentFragment);
                        transaction.commit();
                    }
                }

                for(SensorData temp : Global.getSensorGateway().getSensorObject()){
                    temp.runScriptListener();
                }
                runnable=this;

                sensorHandler.postDelayed(runnable, delay);
            }
        }, delay);

        super.onResume();
    }

    @Override
    protected void onPause() {
        sensorHandler.removeCallbacks(runnable); //stop handler when activity not visible
        super.onPause();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Integer id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id.equals(R.id.action_settings)) {
            //getSupportFragmentManager().beginTransaction().replace(R.id.content_main,new SettingsFragment()).commit();
            Intent i = new Intent(getApplicationContext(), MyPreferencesActivity.class);
            ActivityOptions options = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                options = ActivityOptions.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i, options.toBundle());
            }
            return true;
        } else if(id.equals(R.id.action_report)){
            IssueReporterLauncher.forTarget("sudtanj", "PHR-Android")
                    // [Recommended] Theme to use for the reporter.
                    // (See #theming for further information.)
                    .theme(R.style.Theme_AppCompat)
                    // [Optional] Auth token to open issues if users don't have a GitHub account
                    // You can register a bot account on GitHub and copy ist OAuth2 token here.
                    // (See #how-to-create-a-bot-key for further information.)
                    .guestToken("7c2d324c17d873083b1a32220d3cb39c1c0f9a9e")
                    // [Optional] Force users to enter an email adress when the report is sent using
                    // the guest token.
                    .guestEmailRequired(true)
                    // [Optional] Set a minimum character limit for the description to filter out
                    // empty reports.
                    .minDescriptionLength(100)
                    // [Optional] Include other relevant info in the bug report (like custom variables)
                    .putExtraInfo("Test 1", "Example string")
                    .putExtraInfo("Test 2", true)
                    // [Optional] Disable back arrow in toolbar
                    .homeAsUpEnabled(true)
                    .launch(this);
        }
        else if (id.equals(R.id.action_logout)) {
            Global.getFireBaseAuth().signOut();
            finish();
            startActivity(new Intent(getApplicationContext(), Login.class));
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            currentFragment = new CardViewActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_gallery) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            currentFragment = new GridViewActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {
            Intent i = new Intent(getApplicationContext(), MyPreferencesActivity.class);
            ActivityOptions options = null;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                options = ActivityOptions.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
                startActivity(i, options.toBundle());
            }
        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        age.setText(Global.getSettings().getString("age_key", "") + " Years old");
    }
}
