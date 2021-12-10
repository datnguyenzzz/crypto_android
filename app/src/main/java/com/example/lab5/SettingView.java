package com.example.lab5;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.FragmentActivity;
import androidx.preference.CheckBoxPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

public class SettingView extends PreferenceFragmentCompat implements
    SharedPreferences.OnSharedPreferenceChangeListener, Preference.OnPreferenceChangeListener{

    private static FragmentActivity settingActivity;

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String root) {
        settingActivity = getActivity();

        setPreferencesFromResource(R.xml.preference, root);
        SharedPreferences shared = getPreferenceScreen().getSharedPreferences();

        for (int i=0; i<getPreferenceScreen().getPreferenceCount(); i++) {
            Preference p = getPreferenceScreen().getPreference(i);
            if (!(p instanceof CheckBoxPreference)) {
                //System.out.println("LMAO"+p.getKey() + p);
                p.setSummary(shared.getString(p.getKey(), ""));
            }
        }

        shared.registerOnSharedPreferenceChangeListener(this);
        getPreferenceScreen().findPreference(getResources().getString(R.string.pref_days_count)).setOnPreferenceChangeListener(this);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String s) {
        Preference preference = findPreference(s);
        if (preference != null) {
            System.out.println("MAO " + preference.getKey() + sharedPreferences.getString(preference.getKey(), ""));
            preference.setSummary(sharedPreferences.getString(preference.getKey(), ""));
        }
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object value) {
        return true;
    }

}
