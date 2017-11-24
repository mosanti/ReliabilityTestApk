package com.tct.reliability.view;

import android.content.Context;
import android.preference.Preference;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.tct.reliability.R;


public class RingPreference extends Preference {

	private Context mContext;
    private SwitchButton mSwitchButton;

    public RingPreference(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @Override
    protected View onCreateView(ViewGroup parent) {
        LayoutInflater layoutInflater =
                (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.ring_preference, parent, false);
        return layout;
    }
    @Override
    protected void onBindView(View view) {
        super.onBindView(view);
        mSwitchButton = (SwitchButton)view.findViewById(R.id.ring_btn);
        mSwitchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("zzb","onCheckedChanged:isChecked="+isChecked);
            }
        });

    }
}
