package com.example.computerstarter;

import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.preference.SeekBarPreference;

public class SettingsFragment extends PreferenceFragment {
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences);
    }
}
