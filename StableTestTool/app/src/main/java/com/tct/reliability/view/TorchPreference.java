package com.tct.reliability.view;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.tct.reliability.R;
import com.tct.reliability.torch.FlashlightController;


/**
 *
 * Create by zhibin.zhong
 * @author user
 *
 */

public class TorchPreference extends Preference implements
        FlashlightController.FlashlightListener {

	private Context mContext;
    private SwitchButton mSwitchButton;
    public FlashlightController mFlashlightController;

    public TorchPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mFlashlightController = new FlashlightController(mContext);
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.torch_preference, parent, false);
        mFlashlightController.setFlashlight(false);
        return layout;
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mSwitchButton = (SwitchButton)view.findViewById(R.id.torch_btn);
        mSwitchButton.setChecked(false);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("zzb","onCheckedChanged: torch = "+isChecked);
                mFlashlightController.setFlashlight(isChecked);
            }
        });

    }

    @Override
    public void onFlashlightChanged(boolean enabled) {
        //refreshState(enabled);
    }

    @Override
    public void onFlashlightError() {
        //refreshState(false);
    }

    @Override
    public void onFlashlightAvailabilityChanged(boolean available) {
        //refreshState();
    }
}
