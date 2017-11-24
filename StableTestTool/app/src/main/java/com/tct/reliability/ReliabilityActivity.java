package com.tct.reliability;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import android.os.Bundle;
import android.preference.PreferenceScreen;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.tct.reliability.view.TorchPreference;


public class ReliabilityActivity extends PreferenceActivity implements
        Preference.OnPreferenceChangeListener,Preference.OnPreferenceClickListener {

    private static final String TAG = "ReliabilityActivity";
    private TorchPreference mTorchPreference;
    private static final String CAMERA_CAPTURE_TEST = "key_camera_test";
    private PreferenceScreen mCameraCaptureTestPre;
    private static final String[] REQUEST_PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.reliability_preference);
        if(!hasRequiredPermission(REQUEST_PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, REQUEST_PERMISSIONS, 0);
        }
        mTorchPreference = (TorchPreference)findPreference("key_torch_test");
        mCameraCaptureTestPre = (PreferenceScreen) findPreference(CAMERA_CAPTURE_TEST);
        mCameraCaptureTestPre.setOnPreferenceClickListener(this);
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {
        Log.d(TAG,"onPreferenceChange: preference = "+preference);
        return true;
    }

    @Override
    public boolean onPreferenceClick(Preference preference) {
        Log.d(TAG, "onPreferenceClick: preference = " + preference);
        if(preference.equals(mCameraCaptureTestPre)) {
            Intent cameraCaptureTestIntent = new Intent(this,CameraCaptureTestActivity.class);
            startActivity(cameraCaptureTestIntent);

        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mTorchPreference.mFlashlightController != null){
            mTorchPreference.mFlashlightController.setFlashlight(false);
        }

    }

    /**
     * check requirement permissions
     * @param permissions
     * @return
     */
    protected boolean hasRequiredPermission(String[] permissions) {
        for (String permission : permissions) {
            if (checkSelfPermission(permission)
                    != PackageManager.PERMISSION_GRANTED) {
                Log.d("hasRequiredPermission"," permission ="+permission);
                return false;
            }
        }
        return true;
    }
}
