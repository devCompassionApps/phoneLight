package compassionapps.flashlightapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    private Camera camera;
    private Button power;
    private boolean flashlightOn;
    Camera.Parameters params;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        power = (Button) findViewById(R.id.btn_power);

        boolean cameraFlashOn = getApplicationContext().getPackageManager()
                .hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);

        if(!cameraFlashOn) {
            showCameraAlert();
        }else{
            camera = Camera.open();
            params = camera.getParameters();
        }

        power.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(flashlightOn) {
                    setFlashlightOff();
                } else {
                    setFlashlightOn();
                }
            }
        });
    }

    private void showCameraAlert(){
        new AlertDialog.Builder(this)
        .setTitle("Error: No Flash!")
        .setMessage("Camera Flashlight is not available!")
        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int msg) {
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
        flashlightOn = true;
    }

    private void setFlashlightOff(){
        params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
        camera.setParameters(params);
        camera.stopPreview();
        flashlightOn = false;
    }

    @Override
    protected void onStop() {
        super.onStop();

        if(camera !=null) {
            camera.release();
            camera = null;
        }
    }

}
