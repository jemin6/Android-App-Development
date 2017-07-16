package jemin.uoregon.edu.pigver3_1;

/**
 * Created by Je on 7/12/2017.
 */

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;


public class SettingsFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {

    private SharedPreferences prefs;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Load the preferences from an XML resource
        addPreferencesFromResource(R.xml.preferences);
        prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
    } // End onCreate

    @Override
    public void onResume() {
        super.onResume();
        prefs.registerOnSharedPreferenceChangeListener(this);
    } // End onResume()

    @Override
    public void onPause() {
        prefs.unregisterOnSharedPreferenceChangeListener(this);
        super.onPause();
    } // End onPause()

    @Override
    public void onSharedPreferenceChanged(SharedPreferences prefs, String key) {
        FirstFragment firstFragment = (FirstFragment) getFragmentManager().findFragmentById(R.id.first_fragment);
        if (firstFragment != null) {
            firstFragment.onResume();
        } // End if statement

        SecondFragment secondFragment = (SecondFragment) getFragmentManager().findFragmentById(R.id.second_fragment);
        if (secondFragment != null) {
            secondFragment.onResume();
        } // End if statement

    } // End onSharedPreferenceChanged
} // End SettingsFragment