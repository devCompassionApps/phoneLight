package org.compassionapps.flashlight_v00;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.MotionEvent;
import android.widget.ImageButton;


public class MainActivity extends ActionBarActivity {

    private Camera camera;
    ImageButton powerSwitchImg;
    private boolean isFlashlightOn;
    Camera.Parameters params;
    float x1,x2;
    float y1,y2;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        powerSwitchImg = (ImageButton) findViewById(R.id.powerSwitch);

        boolean isCameraFlash = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if (!isCameraFlash) {
            showCameraAlert();
        } else {
            camera = Camera.open();
            params = camera.getParameters();
        }
    }

//
//        powerSwitchImg.setOnTouchListener(new View.OnSwipeTouchListener(this) {
//            @Override
//            public void onSwipeDown() {
//                    setFlashlightOff();
//            }
//
//            @Override
//            public void onSwipeUp() {
//                    setFlashlightOn();
//            }
//        });

        // onTouchEvent () method gets called when User performs any touch event on screen
        // Method to handle touch event like left to right swap and right to left swap




            public boolean onTouchEvent(MotionEvent touchevent) {
            switch (touchevent.getAction())
            {
                // when user first touches the screen we get x and y coordinate
                case MotionEvent.ACTION_DOWN:
                {
                    x1 = touchevent.getX();
                    y1 = touchevent.getY();
                    break;
                }
                case MotionEvent.ACTION_UP:
                {
                    x2 = touchevent.getX();
                    y2 = touchevent.getY();

                    //if left to right sweep event on screen
//                    if (x1 < x2)
//                    {
//                        Toast.makeText(this, "Left to Right Swap Performed", Toast.LENGTH_LONG).show();
//                    }
//
//                    // if right to left sweep event on screen
//                    if (x1 > x2)
//                    {
//                        Toast.makeText(this, "Right to Left Swap Performed", Toast.LENGTH_LONG).show();
//                    }

                    // if UP to Down sweep event on screen
                    if (y1 < y2)
                    {
                        setFlashlightOff();
                    }

                    //if Down to UP sweep event on screen
                    if (y1 > y2)
                    {
                        setFlashlightOn();
                    }
                    break;
                }
            }
            return false;
        }
//}
//
//            powerSwitchImg.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                if (isFlashlightOn) {
//                    setFlashlightOff();
//                } else {
//                    setFlashlightOn();
//                }
//                return false;
//            }
//        });

//        powerSwitchImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (isFlashlightOn) {
//                    setFlashlightOff();
//                } else {
//                    setFlashlightOn();
//                }
//            }
//        });

  //  }

    private void showCameraAlert(){
        new AlertDialog.Builder(this)
        .setTitle("Error: No Camera Flash!")
        .setMessage("Camera Flashlight is not available on this device.")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener(){

            @Override
            public void onClick(DialogInterface dialog, int which){
                finish();
            }
        })
        .setIcon(android.R.drawable.ic_dialog_alert)
        .show();
    }

    private void setFlashlightOn(){
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
        camera.setParameters(params);
        camera.startPreview();
        isFlashlightOn = true;
        powerSwitchImg.setImageResource(R.drawable.vswitchon);
    }
    private void setFlashlightOff(){
        params = camera.getParameters();
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        isFlashlightOn = false;
        powerSwitchImg.setImageResource(R.drawable.vswitchoff);
    }

    protected void onStop(){
        super.onStop();

        if(camera !=null){
            camera.release();
            camera=null;
        }
    }
}
