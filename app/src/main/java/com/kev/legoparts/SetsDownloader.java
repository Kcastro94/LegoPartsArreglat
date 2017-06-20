package com.kev.legoparts;


import android.app.ProgressDialog;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by DAM on 26/1/17.
 */

public class SetsDownloader extends AsyncTask<String, String, Boolean> {

    private Context context;
    private Spinner listSpinner;
    private List<String> list = new ArrayList<>();;

    public SetsDownloader(Context context, Spinner listSpinner) {
        this.context = context;
        this.listSpinner = listSpinner;

    }

    private ProgressDialog pDialog;

    @Override protected void onPreExecute() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.setTitle(R.string.please_wait);
        String msg = context.getResources().getString(R.string.downloading_set);
        pDialog.setMessage(msg);
        pDialog.setCancelable(true);
        pDialog.show();
    }

    @Override
    protected Boolean doInBackground(String... params) {

        int count;
        try{

            URL url = new URL("http://stucom.flx.cat/lego/search.php?query=&key=Pi2K3OzsDV");

            URLConnection connection = url.openConnection();
            connection.connect();
            int lengthOfFile = connection.getContentLength();
            pDialog.setMax(lengthOfFile);
            InputStream input = new BufferedInputStream(url.openStream(), 8192);
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            byte data[] = new byte[1024];
            long total = 0;
            while ((count = input.read(data)) != -1) {
                total += count;
                publishProgress("" + (int) ((total * 100) / lengthOfFile));
                output.write(data, 0, count);
            }
            input.close();
            output.flush();
            String tsv = new String(output.toByteArray());
            String[] aux = tsv.split("\n");

            for(int i=1; i<aux.length; i++){
                String[] auxDetail = aux[i].split("\t");
                list.add(auxDetail[0]);

                Log.d("kev", "List sets: "+list);
            }



        }catch (Exception e) {
            Log.e("Error: ", e.getMessage());
            return false;

        }

        return true;

    }

    protected void onProgressUpdate(String... progress) {
        pDialog.setProgress(Integer.parseInt(progress[0]));
    }

    @Override public void onPostExecute(Boolean result) {
        pDialog.dismiss();
        SpinnerAdapter adapter = new SpinnerAdapter(context, list);
        listSpinner.setAdapter(adapter);


    }
}
