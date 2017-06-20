package com.kev.legoparts;


import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by DAM on 26/1/17.
 */

public class SetDownloader extends AsyncTask<String, String, Boolean> {


    private Bitmap imageBit;
    private String descriptionSet;

    private Context context;
    private ImageView imageView;
    private TextView textView;
    public SetDownloader(Context context, ImageView imageView, TextView textView) {
        this.context = context;
        this.imageView = imageView;
        this.textView = textView;
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
            //No funciona per alguna cosa de la api
            URL url = new URL("https://rebrickable.com/api/v3/lego/sets/"+params[0]+"/?key=Pi2K3OzsDV");

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
            String csv = new String(output.toByteArray());
            String[] aux = csv.split(",");

            List<String> list = new ArrayList<>();

            for(int i=0; i<aux.length-3; i++){
                descriptionSet = "\n"+descriptionSet.concat(aux[i]);
            }

            try ( InputStream is = new URL(aux[6]).openStream() ) {
                imageBit = BitmapFactory.decodeStream( is );

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
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

        imageView.setImageBitmap(imageBit);
        textView.setText(descriptionSet);
    }
}
