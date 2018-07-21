/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on 12/14/17 6:51 PM
 */

package sud_tanj.com.phr_android;

import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
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

import com.bumptech.glide.Glide;
import com.iamhabib.easy_preference.EasyPreference;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.osama.firecrasher.FireCrasher;
import com.zendesk.logger.Logger;
import com.zendesk.sdk.model.access.AnonymousIdentity;
import com.zendesk.sdk.network.impl.ZendeskConfig;
import com.zendesk.sdk.support.SupportActivity;

import io.fabric.sdk.android.Fabric;
import sharefirebasepreferences.crysxd.de.lib.SharedFirebasePreferences;
import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Database.Sensor.SensorData;
import sud_tanj.com.phr_android.Database.Sensor.SensorGateway;
import sud_tanj.com.phr_android.Handler.HandlerLoop;
import sud_tanj.com.phr_android.Health_Data.ActiveHealthDataListLayout.ActiveHealthDataListActivity;
import sud_tanj.com.phr_android.Sensor.HardwareDriver.Interface.ArduinoReceiver;
import sud_tanj.com.phr_android.Health_Data.HealthDataListLayout.HealthDataListActivity;
import sud_tanj.com.phr_android.Health_Sensor.GridLayout.GridViewActivity;
import sud_tanj.com.phr_android.Health_Sensor.ModifySensor;
import sud_tanj.com.phr_android.Login.Login;
import sud_tanj.com.phr_android.SensorHandler.Interface.SensorRunnable;
import sud_tanj.com.phr_android.Settings.MyPreferencesActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SharedPreferences.OnSharedPreferenceChangeListener {

    private TextView fullName;
    private TextView age;
    private NavigationView navView;
    private ImageView profilePicture;
    private Fragment currentFragment;
    private SensorData arduino;
    private SensorGateway gate;
    private int delay = 5 * 1000;
    private HandlerLoop sensorBackgroundHandler;
    private SensorRunnable sensorRunnable;
    private IntentFilter intentFilter;
    private NavigationView navigationView;
    private ArduinoReceiver arduinoReceiver;
    private Drawer drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //initialized the activity
        initActivity();

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
        Global.changePreferences("app_lang_settings", getString(R.string.settings_lang));

        Global.getSettings().registerOnSharedPreferenceChangeListener(this);

        //Init navigation drawer
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        navigationView = findViewById(R.id.nav_view);
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

        this.intentFilter = new IntentFilter();
        this.intentFilter.addAction("android.hardware.usb.action.USB_STATE");
        this.arduinoReceiver = new ArduinoReceiver();


        android.support.v7.app.ActionBar bar = getSupportActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#b8e2eb")));

        Logger.setLoggable(BuildConfig.DEBUG);
        initializeZendesk();


    }

    private void initActivity(){
        //Initialize Fabric
        Fabric.with(getApplicationContext());

        //Initialize Crash Handler
        FireCrasher.install(getApplication(), new CrashHandler());

        //Initialize Preferences
        EasyPreference.with(getApplicationContext());

        //Initialize Preferences - Firebase Synchronizer
        SharedFirebasePreferences.getInstance(getApplicationContext(),"app_settings", Context.MODE_PRIVATE);

        //Init the drawer
        drawer = new DrawerBuilder().withActivity(this).build();
    }

    private void setupActivity(){
        //Setup Firebase Preferences

    }

    @Override
    protected void onStart() {
        super.onStart();
        registerReceiver(this.arduinoReceiver, this.intentFilter);
    }

    private void initializeZendesk() {
        // Initialize the Support SDK with your Zendesk Support subdomain, mobile SDK app ID, and client ID.
        // Get these details from your Zendesk Support dashboard: Admin -> Channels -> Mobile SDK
        ZendeskConfig.INSTANCE.init(getApplicationContext(),
                "https://phrandroid.zendesk.com",
                "80db5b254444525b4a13539167547cda78f8fd2a95651a8f",
                "mobile_sdk_client_979f19dfd85d2f193b39");


        // Authenticate anonymously as a Zendesk Support user
        ZendeskConfig.INSTANCE.setIdentity(
                new AnonymousIdentity.Builder()
                        .withNameIdentifier(Global.getFireBaseUser().getDisplayName())
                        .build()
        );
    }


    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(this.arduinoReceiver);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.sensorRunnable=new SensorRunnable();
        this.sensorBackgroundHandler = new HandlerLoop(3, this.sensorRunnable);
        if(currentFragment==null) {
            currentFragment = new HealthDataListActivity();
        }
        getFragmentManager().beginTransaction().replace(R.id.fragment_container, currentFragment).commit();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.sensorBackgroundHandler.removeCallbacks(this.sensorRunnable);
        this.sensorBackgroundHandler.removeCallbacksAndMessages(null);
        this.sensorBackgroundHandler=null;
        this.sensorRunnable=null;
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
      if (id.equals(R.id.action_report)) {
          new SupportActivity.Builder().show(MainActivity.this);
      } else if (id.equals(R.id.action_logout)) {
            Global.getFireBaseAuth().signOut();
            this.onPause();
          if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
              ((ActivityManager)this.getSystemService(ACTIVITY_SERVICE))
                      .clearApplicationUserData(); // note: it has a return value!
          }
          finish();
            //startActivity(new Intent(getApplicationContext(), Login.class));
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
            currentFragment = new HealthDataListActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.commit();

        } else if(id == R.id.nav_active){
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            currentFragment = new ActiveHealthDataListActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.commit();
        }
        else if (id == R.id.nav_gallery) {
            FragmentTransaction transaction = getFragmentManager().beginTransaction();
            currentFragment = new GridViewActivity();
            transaction.replace(R.id.fragment_container, currentFragment);
            transaction.commit();

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
