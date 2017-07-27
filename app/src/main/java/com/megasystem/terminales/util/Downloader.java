package com.megasystem.terminales.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.widget.ImageButton;

import com.megasystem.terminales.Application;
import com.megasystem.terminales.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class Downloader extends AsyncTask<Integer, Void, Integer> {
    public ProgressDialog progress;
    private Activity activity;
    public ImageButton imageButton;
    private Uri path = null;
    String imei;

    public Downloader(Activity activity) {
        this.activity = activity;
    }

    @Override
    protected void onPreExecute() {
        progress = ProgressDialog.show(activity, null, activity.getString(R.string.downloading));
        TelephonyManager telephonyManager = (TelephonyManager) activity.getSystemService(Context.TELEPHONY_SERVICE);
        imei = telephonyManager.getDeviceId();
    }


    @SuppressWarnings("unused")
    @Override
    protected Integer doInBackground(Integer... arg0) {
        SharedPreferences preferences = activity.getSharedPreferences("Configurations", Context.MODE_PRIVATE);

        File file = null;
        int downloadedSize = 0, totalsize;
        float per = 0;
        try {

            URL url = new URL(preferences.getString("updateApp", Application.downloadUrl) + "/" + Application.apkName);
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            urlConnection.connect();

            if (urlConnection.getResponseCode() != HttpURLConnection.HTTP_OK) {
                return 1;
            }
            File pathDir = new File(Environment.getExternalStorageDirectory(), "/megasystem");
            pathDir.mkdirs();

            File SDCardRoot = new File(Environment.getExternalStorageDirectory() + "/megasystem");
            File fileDel = new File(SDCardRoot, Application.apkName);
            fileDel.deleteOnExit();

            file = new File(SDCardRoot, Application.apkName);
            FileOutputStream fileOutput = new FileOutputStream(file);
            InputStream inputStream = urlConnection.getInputStream();
            totalsize = urlConnection.getContentLength();

            // create a buffer...
            byte[] buffer = new byte[1024 * 1024];
            int bufferLength = 0;

            while ((bufferLength = inputStream.read(buffer)) > 0) {
                fileOutput.write(buffer, 0, bufferLength);
                downloadedSize += bufferLength;
                per = ((float) downloadedSize / totalsize) * 100;
            }
            // close the output stream when complete //
            fileOutput.close();
            path = Uri.fromFile(file);
            return 0;
        } catch (final MalformedURLException e) {
            e.printStackTrace();
        } catch (final IOException e) {
            e.printStackTrace();
        } catch (final Exception e) {
            e.printStackTrace();
        } finally {
            if (path == null) {
                try {
                    File SDCardRoot = new File(Environment.getExternalStorageDirectory() + "/avatech");
                    file = new File(SDCardRoot, Application.apkName);
                    file.delete();
                } catch (Exception e) {

                }
            }

        }
        return 3;
    }

    protected void onPostExecute(Integer result) {
        AlertDialog.Builder dialog;
        switch (result) {
            case 0:
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setDataAndType(path, "application/vnd.android.package-archive");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 1:
                dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(activity.getString(R.string.update));
                dialog.setMessage(activity.getString(R.string.message_no_sync_connect));
                dialog.show();
                break;
            case 2:
                dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(activity.getString(R.string.update));
                dialog.setMessage(activity.getString(R.string.message_day_is_no_closed_update));
                dialog.show();
                break;
            case 3:
                dialog = new AlertDialog.Builder(activity);
                dialog.setTitle(activity.getString(R.string.update));
                dialog.setMessage(activity.getString(R.string.message_no_update_app));
                dialog.show();
                break;

            default:
                break;
        }

        progress.dismiss();
    }

}
