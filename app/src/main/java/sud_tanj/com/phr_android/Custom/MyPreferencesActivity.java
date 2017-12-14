package sud_tanj.com.phr_android.Custom;

import android.os.Build;
import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

import sud_tanj.com.phr_android.R;

/**
 * Created by User on 14/12/2017.
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
    }

    public static class MyPreferenceFragment extends PreferenceFragment
    {
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

            //Language Option
            ListPreference countryList = (ListPreference)findPreference("app_lang");
            List<String> langList = Arrays.asList(Locale.getISOLanguages());
            ArrayList<SuperString> langListName = new ArrayList<SuperString>();
            int defaultValue=0;
            for (int i=0;i<langList.size();i++) {
                Locale loc = new Locale(langList.get(i));
                if (loc.getDisplayLanguage().trim().length()>0 && !langListName.contains(loc.getDisplayLanguage())) {
                    if(loc.getDisplayLanguage().matches(GlobalSettings.langSelected)) {
                        GlobalSettings.langLocale = loc;
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
            countryList.setEntries(entries);
            countryList.setEntryValues(entryValues);
            countryList.setValueIndex(defaultValue);
            countryList.setTitle(new SuperString(getString(R.string.language_settings)).toString());
            countryList.setSummary(new SuperString(getString(R.string.language_description_settings)).toString());
            countryList.setDialogTitle(new SuperString(getString(R.string.language_dialog_title_settings)).toString());

        }
    }

}