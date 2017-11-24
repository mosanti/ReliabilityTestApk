package com.tct.reliability;

import android.os.Bundle;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.SwitchPreference;
import android.util.Log;
import android.widget.Toast;

import com.tct.reliability.view.CameraCapturePreference;

/**
 * Created by shengyang.mo on 16-10-14.
 */
public class CameraCaptureTestActivity extends PreferenceActivity implements Preference.OnPreferenceChangeListener {
    private static final String TAG = "CameraCaptureTestActivity";
    private static final String CAMERA_CAPTURE_PREFERENCE = "pre_key_camera_capture_test";
    private static final String CAMERA_FREQUENCY_PERIOD = "pref_key_camera_frequency_period";
    private CameraCapturePreference mCameraCapturePreference;
    private ListPreference mCameraFrequencyPeriod;
    //private SwitchPreference mCameraCaptureSwitchPreference;

    private boolean mCheck;
    public static int intCaptureFrequency;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.camera_captuer_test_preference);
        mCameraCapturePreference = (CameraCapturePreference) findPreference(CAMERA_CAPTURE_PREFERENCE);
        mCameraCapturePreference.setOnPreferenceChangeListener(this);
        //mCameraCaptureSwitchPreference = (SwitchPreference) findPreference(CAMERA_CAPTURE_PREFERENCE);
        mCameraFrequencyPeriod = (ListPreference) findPreference(CAMERA_FREQUENCY_PERIOD);
        mCameraFrequencyPeriod.setSummary(mCameraFrequencyPeriod.getEntry());
        mCameraFrequencyPeriod.setOnPreferenceChangeListener(this);

        String index = mCameraFrequencyPeriod.getValue();
        intCaptureFrequency = Integer.parseInt(index);

    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(TAG, "onPreferenceChange: preference = " + preference);
        if(preference.equals(mCameraFrequencyPeriod)) {
            ListPreference listPreference=(ListPreference)preference;
            CharSequence[] entries = listPreference.getEntries();
            int index=listPreference.findIndexOfValue((String)newValue);
            listPreference.setSummary(entries[index]);
            CharSequence[] entriesValues = listPreference.getEntryValues();
            intCaptureFrequency = Integer.parseInt((String) entriesValues[index]);
        }

        return true;
    }

//    @Override
//    public boolean onPreferenceClick(Preference preference) {
//        if(preference.equals(CAMERA_CAPTURE_PREFERENCE)) {
//            if(mCameraCapturePreference.getSwitchButtonCheck()) {   //mSwitchButton is checked
//            } else {
//            }
//        }
//        return false;
//    }
//
//    @Override
//    public boolean onPreferenceTreeClick(PreferenceScreen preferenceScreen, Preference preference) {
//        return super.onPreferenceTreeClick(preferenceScreen, preference);
//    }

}
