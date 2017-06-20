package com.kev.legoparts;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ListView listPieces = (ListView) findViewById(R.id.list);
        final Spinner listSpinner = (Spinner) findViewById(R.id.listSpinner);
        final EditText textPieces = (EditText) findViewById(R.id.text);

        final ImageView imageSet = (ImageView) findViewById(R.id.imageSet);
        final TextView description = (TextView) findViewById(R.id.description);

        SetsDownloader ssd = new SetsDownloader(MainActivity.this, listSpinner);
        ssd.execute();

        listSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                PiecesDownloader pd = new PiecesDownloader(MainActivity.this, listPieces);
                pd.execute(listSpinner.getSelectedItem().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button btnSearch = (Button) findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SetDownloader sd = new SetDownloader(MainActivity.this, imageSet, description);
                PiecesDownloader pd = new PiecesDownloader(MainActivity.this, listPieces);
                String setId = textPieces.getText().toString();
                sd.execute(setId);
                pd.execute(setId);

            }
        });
    }
}
