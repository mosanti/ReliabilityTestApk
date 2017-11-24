package com.tct.reliability.view;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import com.tct.reliability.CameraCaptureTestActivity;
import com.tct.reliability.CaptureActivity;
import com.tct.reliability.R;

/**
 *
 * Create by shengyang.mo
 * @author user
 *
 */

public class CameraCapturePreference extends Preference {

	private Context mContext;
    private SwitchButton mSwitchButton;
    private boolean mCheck;


    public CameraCapturePreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.camera_capture_preference, parent, false);
        return layout;
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mSwitchButton = (SwitchButton)view.findViewById(R.id.camera_capture_btn);
        mCheck = false;
        mSwitchButton.setChecked(false);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("msy","onCheckedChanged:isChecked="+isChecked);
                if (isChecked) {
                    mCheck = false;
                    //Toast.makeText(mContext,"test",Toast.LENGTH_SHORT).show();
                    Intent captureActivityIntent = new Intent(mContext,CaptureActivity.class);
                    captureActivityIntent.putExtra("pref_key_camera_frequency_period",
                            CameraCaptureTestActivity.intCaptureFrequency);
                    mContext.startActivity(captureActivityIntent);
                    //mHandler.post(mStartTorchTestRunnable);
                } else {
                    mCheck = true;
                    /*mHandler.removeCallbacks(mStartTorchTestRunnable);
                    mHandler.removeMessages(MSG_TORCH_DELAY_FLASH);
                    mHandler.removeMessages(MSG_TORCH_FLASH);
                    mHandler.removeMessages(MSG_TORCH_LIGHT_OFF);
                    mFlashlightController.setFlashlight(false);*/
                }
            }
        });
    }

    public boolean getSwitchButtonCheck() {
        return mCheck;
    }
}
