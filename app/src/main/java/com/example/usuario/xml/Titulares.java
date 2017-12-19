package com.example.usuario.xml;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.usuario.xml.network.RestClient;
import com.example.usuario.xml.utils.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;

import cz.msebera.android.httpclient.Header;

/**
 * Created by usuario on 4/12/17.
 */

public class Titulares extends AppCompatActivity implements View.OnClickListener{
    public static final String RSS = "https://www.alejandrosuarez.es/feed/";
    //public static final String RSS = "http://10.0.2.2/feed/alejandro.xml";
    public static final String TEMPORAL = "alejandro.xml";
    private Button btnRSS;
    private TextView txvMostar;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_titulares);
        txvMostar = findViewById(R.id.txvTitular);
        btnRSS = findViewById(R.id.btnTitular);
        btnRSS.setOnClickListener(this);
    }

    private void descarga(String rss, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temporal);
        RestClient.get(rss, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fallo:" + throwable, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(getApplicationContext(), "Fichero descargado correctamente", Toast.LENGTH_SHORT).show();
                try {
                    txvMostar.setText(Analisis.analizarRSS(file));
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onStart() {
                super.onStart();
                progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progreso.setMessage("Conectando . . .");
                progreso.setCancelable(false);
                progreso.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if(v == btnRSS){
            descarga(RSS, TEMPORAL);
        }
    }
}
