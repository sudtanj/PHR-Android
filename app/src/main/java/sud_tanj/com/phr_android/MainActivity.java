/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;


import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Custom.MyPreferencesActivity;
import sud_tanj.com.phr_android.GPlusLogin.Login;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView fullName;
    private NavigationView navView;
    private ImageView profilePicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Init navigation view
        navView=(NavigationView) findViewById(R.id.nav_view);

        //init Preference
        //Global.setSettings(new SecurePreferences(this, Global.getFireBaseUser().getUid(), getString(R.string.settings_pref_file_name)));
        //Global.setSettings(getSharedPreferences(getString(R.string.settings_pref_file_name), Context.MODE_WORLD_WRITEABLE));
        Global.setSettings(getApplicationContext(),getString(R.string.settings_pref_file_name));
        Global.changePreferences(getString(R.string.application_language),getString(R.string.settings_lang));

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
            ActivityOptions options = ActivityOptions.makeCustomAnimation(getApplicationContext(), android.R.anim.fade_in, android.R.anim.fade_out);
            startActivity(i,options.toBundle());
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
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

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

    }
}
