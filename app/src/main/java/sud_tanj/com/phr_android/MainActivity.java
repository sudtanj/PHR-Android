/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android;

import android.app.ActivityOptions;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.app.Fragment;
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

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

import sud_tanj.com.phr_android.CardLayout.CardViewActivity;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Custom.MyPreferencesActivity;
import sud_tanj.com.phr_android.Database.SensorData;
import sud_tanj.com.phr_android.GPlusLogin.Login;
import sud_tanj.com.phr_android.GridLayout.GridViewActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView fullName;
    private TextView age;
    private NavigationView navView;
    private ImageView profilePicture;
    private Fragment currentFragment;

private SensorData arduino;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init navigation view
        navView=(NavigationView) findViewById(R.id.nav_view);

        //Init
        Global.setContext(this);

        //Init firebase database
        Global.getDatabase().setPersistenceEnabled(true);
        arduino=new SensorData("wajekfljwklaf");

        //init Preference
        //Global.setSettings(new SecurePreferences(this, Global.getFireBaseUser().getUid(), getString(R.string.settings_pref_file_name)));
        //Global.setSettings(getSharedPreferences(getString(R.string.settings_pref_file_name), Context.MODE_WORLD_WRITEABLE));
        Global.setSettings(getApplicationContext());
        Global.changePreferences(getString(R.string.application_language),getString(R.string.settings_lang));
        System.out.println(Global.getSettings().getString("age_key","0"));

        Global.getSettings().registerOnSharedPreferenceChangeListener(this);

        //Init navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
        fullName=(TextView) navigationView.getHeaderView(0).findViewById(R.id.fullname);
        fullName.setText(Global.getFireBaseUser().getDisplayName());


        //Init profile image
        profilePicture=(ImageView)navigationView.getHeaderView(0).findViewById(R.id.profile_picture);
        Glide.with(this)
                .load(Global.getFireBaseUser().getPhotoUrl())
                .into(profilePicture);

        //init age
        age=(TextView) navigationView.getHeaderView(0).findViewById(R.id.age);
        age.setText(Global.getSettings().getString("age_key","")+" Years old");


        //first fragment init
        currentFragment = new CardViewActivity();
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, currentFragment);
        transaction.commit();
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
        //System.out.println(arduino.getSensorId());
        //System.out.println(arduino.getSensorName());
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
                startActivity(i,options.toBundle());
            }
            return true;
        } else if (id.equals(R.id.action_logout)){
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
            currentFragment=new CardViewActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_gallery) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            currentFragment=new GridViewActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.addToBackStack(null);
            transaction.commit();

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        age.setText(Global.getSettings().getString("age_key","")+" Years old");
    }
}
