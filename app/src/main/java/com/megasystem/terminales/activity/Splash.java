package com.megasystem.terminales.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.megasystem.terminales.Application;
import com.megasystem.terminales.R;


public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        PackageInfo info;
        String appVersion = "0.0.0";
        try {
            info = getPackageManager().getPackageInfo(getPackageName(), 0);
            appVersion = info.versionName + " [" + Integer.toString(info.versionCode) + "]";
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        ((TextView) findViewById(R.id.appVersion)).setText(getString(R.string.version) + " " + appVersion);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Validamos google play services
        if (checkPlayServices()) {
            if (hasPermission()) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            //validamos que el usuario haya iniciado sesion

                            Intent intent = new Intent(Splash.this, Main.class);
                            startActivity(intent);
                            finish();
                        } catch (Exception e) {
                            e.printStackTrace();
                            Log.e(Application.tag, e.getMessage());
                        }
                    }

                }).start();
            }
        }
    }

    private boolean hasPermission() {
        if (Build.VERSION.SDK_INT < 23) {
            return true;
        } else {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        // evadimos el backkey para que no salga de la aplicacion
    }

    private boolean checkPlayServices() {

        return true;
    }
}
