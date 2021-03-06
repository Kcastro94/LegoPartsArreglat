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

public class PiecesDownloader extends AsyncTask<String, String, Boolean> {

    private List<LegoPiece> piecesSet = new ArrayList<>();
    private Context context;
    private ListView listView;
    public PiecesDownloader(Context context, ListView listView) {
        this.context = context;
        this.listView = listView;

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
            URL url = new URL("http://stucom.flx.cat/lego/get_set_parts.php?key=Pi2K3OzsDV&set="+params[0]);

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
            Log.d("kev", ""+aux.length);
            for(int i = 1; i < aux.length; i++){
                Log.d("kev", aux[i]);
                String[] auxDetail = aux[i].split("\t");

                long id = i;
                String piece_id = auxDetail[0];
                int quantity = Integer.parseInt(auxDetail[1]);
                String name = auxDetail[4];
                String image = auxDetail[6];
                Bitmap imageBit = null;
                try ( InputStream is = new URL(image).openStream() ) {
                    imageBit = BitmapFactory.decodeStream( is );

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                LegoPiece piece = new LegoPiece(id, piece_id, name, imageBit, quantity);
                piecesSet.add(piece);
                Log.d("kev","Piece"+piece);
            }

            Log.d("kev", "List"+piecesSet);


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

        PiecesAdapter adapter = new PiecesAdapter(context, piecesSet);
        listView.setAdapter(adapter);
    }


}
