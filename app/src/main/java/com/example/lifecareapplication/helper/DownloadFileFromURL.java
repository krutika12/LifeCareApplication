package com.example.lifecareapplication.helper;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class DownloadFileFromURL extends AsyncTask<String, String, String> {

    ProgressDialog pDialog;
    Context context;
    String pdfName, downloadUrl, pdfThumbnail, pdfUrl;

    public DownloadFileFromURL(Context context, String title, String pdfUrl) {
        this.context = context;
        this.pdfName = title;
        this.pdfUrl = pdfUrl;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        pDialog.show();
    }

    /**
     * Downloading file in background thread
     */
    @Override
    protected String doInBackground(String... f_url) {
        int count;
        try {
            String root = Environment.getExternalStorageDirectory().toString();

            System.out.println("Downloading");
            URL url = new URL(f_url[0]);

            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();

            InputStream input = new BufferedInputStream(url.openStream(), 8192);

            String apks = Environment.getExternalStorageDirectory().toString();

            File folder = new File(apks, "LifeCareApplication");
            folder.mkdir();
            String downloadFileName = pdfName + ".jpg";
            File outputFile = new File(folder, downloadFileName);
            OutputStream output = new FileOutputStream(outputFile);
            byte data[] = new byte[1024];

            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;

                output.write(data, 0, count);

            }

            // flushing output
            output.flush();

            // closing streams
            output.close();
            input.close();

        } catch (Exception e) {
            Log.e("Error: ", e.getMessage());
        }

        return null;
    }


    /**
     * After completing background task
     **/
    @Override
    protected void onPostExecute(String file_url) {
        Toast.makeText(context, "Image Downloaded", Toast.LENGTH_LONG).show();
        pDialog.dismiss();
    }

}
