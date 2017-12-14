/*
 * Create by Sudono Tanjung
 * Copyright (c) 2017. All rights reserved.
 *
 * Last Modified by User on ${TIME}
 */

package sud_tanj.com.phr_android.Custom;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import sud_tanj.com.phr_android.R;

/**
 * This class is part of PHRAndroid
 * Created by Sudono Tanjung on 12/12/2017.
 */

public class MyPreferencesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        getFragmentManager().beginTransaction().replace(android.R.id.content, new MyPreferenceFragment()).commit();

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed(){
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
        private static SharedPreferences  settings;
        @Override
        public void onCreate(final Bundle savedInstanceState)
        {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            // run the code making use of getActivity() from here
            //initialized
            settings = getActivity().getSharedPreferences(getString(R.string.settings_pref_file_name), Context.MODE_WORLD_WRITEABLE);
            String appLangPref=settings.getString(getString(R.string.application_language),"");

            //Gson gson = new Gson();
            //String json = settings.getString("MyObject", "");
            //Locale obj = gson.fromJson(json, Locale.class);

            //Language Option
            ListPreference langListPreferences = (ListPreference)findPreference(getString(R.string.application_language));
            List<String> langList = Arrays.asList(Locale.getISOLanguages());
            ArrayList<SuperString> langListName = new ArrayList<SuperString>();
            int defaultValue=0;
            for (int i=0;i<langList.size();i++) {
                Locale loc = new Locale(langList.get(i));
                if (loc.getDisplayLanguage().trim().length()>0 && !langListName.contains(loc.getDisplayLanguage())) {
                    if(loc.getDisplayLanguage().matches(appLangPref.toString())) {
                        settings.edit().putString(getString(R.string.settings_lang_loc),new Gson().toJson(loc)).commit();
                        defaultValue=i;
                    }
                    langListName.add(new SuperString(loc.getDisplayLanguage()));
                }
            }
            langList=null;
            Collections.sort(langListName);
            CharSequence entries[] = new String[langListName.size()];
            CharSequence entryValues[] = new String[langListName.size()];
            for(int i=0;i<langListName.size();i++){
                entries[i] = langListName.get(i).toString();
                entryValues[i] = Integer.toString(i);
            }
            langListPreferences.setEntries(entries);
            langListPreferences.setEntryValues(entryValues);
            langListPreferences.setValueIndex(defaultValue);
            langListPreferences.setTitle(new SuperString(getString(R.string.language_settings)).toString());
            langListPreferences.setSummary(new SuperString(getString(R.string.language_description_settings)).toString());
            langListPreferences.setDialogTitle(new SuperString(getString(R.string.language_dialog_title_settings)).toString());

        }
    }

}