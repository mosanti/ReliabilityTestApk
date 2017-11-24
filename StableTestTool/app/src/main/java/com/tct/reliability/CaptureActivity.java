package com.tct.reliability;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Context;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.view.ViewGroup.LayoutParams;
import android.widget.Toast;

/**
 * Created by shengyang.mo on 16-10-14.
 */

public class CaptureActivity extends Activity {
    /** Called when the activity is first created. */

    private Camera camera = null;
    private CameraView cv = null;
    private Context mContext;
    private LinearLayout captureView = null;
    private int intCaptureFrequency;
    private int intCaptureNumber;
    private int intCaptureCount = 0;
    private static final String CAMERA_FREQUENCY_PERIOD = "pref_key_camera_frequency_period";

    private Timer timer = new Timer();

    private Camera.PictureCallback picture = new Camera.PictureCallback() {

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {
            // set the image to the drawable，set the drawable as the backgroud
            ByteArrayInputStream bais = new ByteArrayInputStream(data);
            Drawable d = BitmapDrawable.createFromStream(bais, Environment
                    .getExternalStorageDirectory().getAbsolutePath()
                    + "/img.jpeg");
            captureView.setBackgroundDrawable(d);
            camera.startPreview();
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFormat(PixelFormat.TRANSLUCENT);
        setContentView(R.layout.capture_layout);
        mContext = getApplicationContext();

        initCaptureParams();
        captureView = (LinearLayout) findViewById(R.id.cameraView);

        cv = new CameraView(CaptureActivity.this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT);
        captureView.addView(cv, params);

        timer = new Timer(true);
        timer.schedule(task,10, intCaptureFrequency*1000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        timer.cancel();
        if(camera != null) {
            camera.stopPreview();
            camera.release();
            camera = null;
        }
    }

    class CameraView extends SurfaceView {

        private SurfaceHolder holder = null;

        public CameraView(Context context) {
            super(context);
            holder = this.getHolder();

            holder.addCallback(new SurfaceHolder.Callback() {

                @Override
                public void surfaceChanged(SurfaceHolder holder, int format,
                                           int width, int height) {
                    DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
                    int w_screen = dm.widthPixels;
                    int h_screen = dm.heightPixels;
                    //Camera.Parameters parameters = camera.getParameters();
                    //parameters.setPreviewSize(w_screen, h_screen);
                    //parameters.setFocusMode("auto");
                    //camera.setParameters(parameters);
                    camera.startPreview();
                }

                @Override
                public void surfaceCreated(SurfaceHolder holder) {
                    if (null == camera) {
                        camera = Camera.open(0);
                    }

                    try {
                        //set the camera angle,the default angle is 90 gradient
                        camera.setDisplayOrientation(90);
                        camera.setPreviewDisplay(holder);
                    } catch (IOException e) {
                        camera.release();
                        camera = null;
                        e.printStackTrace();
                    }
                }

                @Override
                public void surfaceDestroyed(SurfaceHolder holder) {
                    if(camera != null) {
                        camera.stopPreview();
                        camera.release();
                        camera = null;
                    }
                }
            });
        }
    }

    public void initCaptureParams() {
        intCaptureFrequency = getIntent().getIntExtra(CAMERA_FREQUENCY_PERIOD,5);
        intCaptureNumber = 5;
        return;
    }

    TimerTask task = new TimerTask() {
        public void run() {
            if (camera == null) {
                camera = Camera.open(0);
            } else {
                if (camera != null && intCaptureCount < intCaptureNumber) {
                    camera.takePicture(null, null, picture);
                    Message message = new Message();
                    message.what = 0x01;
                    handler.sendMessage(message);
                }
            }
        }
    };

    final Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0x01:
                    intCaptureCount ++;
                    if(intCaptureCount > intCaptureNumber) {
                        timer.cancel();
                    } else {
                        Toast.makeText(mContext,String.valueOf(intCaptureCount),Toast.LENGTH_SHORT).show();
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };

}
