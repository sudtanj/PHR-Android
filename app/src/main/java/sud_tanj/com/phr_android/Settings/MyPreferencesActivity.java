/*
 * Create by Sudono Tanjung
 * Copyright (c) 2018. All rights reserved.
 *
 * Last Modified by User on 5/6/18 5:41 PM
 */

package sud_tanj.com.phr_android.Settings;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import sud_tanj.com.phr_android.Custom.Global;
import sud_tanj.com.phr_android.Custom.SuperString;
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

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(this, R.color.appHeader)));


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
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }

    public static class MyPreferenceFragment extends PreferenceFragment {
        private static ListPreference langListPreferences;
        private static EditTextPreference agePreferences;

        @Override
        public void onCreate(final Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            // run the code making use of getActivity() from here
            //initialized
            String appLangPref = Global.getSettings().getString("app_lang_settings", "");
            langListPreferences = (ListPreference) findPreference("app_lang_settings");
            agePreferences = (EditTextPreference) findPreference("age_key");

            //Gson gson = new Gson();
            //String json = settings.getString("MyObject", "");
            //Locale obj = gson.fromJson(json, Locale.class);

            //Language Option
            List<String> langList = Arrays.asList(Locale.getISOLanguages());
            ArrayList<SuperString> langListName = new ArrayList<>();
            int defaultValue = 0;
            for (int i = 0; i < langList.size(); i++) {
                Locale loc = new Locale(langList.get(i));
                if (loc.getDisplayLanguage().trim().length() > 0 && !langListName.contains(loc.getDisplayLanguage())) {
                    if (loc.getDisplayLanguage().matches(appLangPref.toString())) {
                        Global.getSettings().edit().putString(getString(R.string.settings_lang_loc), new Gson().toJson(loc)).commit();
                        defaultValue = i;
                    }
                    langListName.add(new SuperString(loc.getDisplayLanguage()));
                }
            }
            langList = null;
            Collections.sort(langListName);
            CharSequence entries[] = new String[langListName.size()];
            CharSequence entryValues[] = new String[langListName.size()];
            for (int i = 0; i < langListName.size(); i++) {
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