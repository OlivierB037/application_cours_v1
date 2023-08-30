package fr.cnam.application_cours_v1;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingsActivity extends AppCompatActivity {

    PreferenceListener preferenceListener;
    public boolean prefChanged = false;

    public static interface PreferenceListener{
        public void onPrefChanged();
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.println(Log.WARN,DISPLAY_SERVICE,"oncreate");
        setContentView(R.layout.activity_settings);

        if (savedInstanceState == null){
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.settingsContainer,new SettingsFragment())
                    .commit();
        }

    }

    public static class SettingsFragment extends PreferenceFragmentCompat{
        @Override
        public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
            addPreferencesFromResource(R.xml.preferences);
        }

        @Override
        public boolean onPreferenceTreeClick(Preference preference) {
            preference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
                @Override
                public boolean onPreferenceChange(Preference preference, Object newValue) {
                    Log.i("preference call","preference listener triggered");
                    MainActivity.prefChanged = true;
                    return true;
                }

            });

            Log.i("preference call"," after listener preference is: " + preference.getSummary());
            return super.onPreferenceTreeClick(preference);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        Log.i("SettingsActivity call","onDestroy called");
    }
}