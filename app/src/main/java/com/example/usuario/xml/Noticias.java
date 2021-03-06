package com.example.usuario.xml;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.usuario.xml.model.Noticia;
import com.example.usuario.xml.network.RestClient;
import com.example.usuario.xml.utils.Analisis;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

/**
 * Created by usuario on 4/12/17.
 */

public class Noticias extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemClickListener {
    public static final String CANAL = "http://www.europapress.es/rss/rss.aspx?ch=279";
    //public static final String CANAL = "http://192.168.1.200/feed/europapress.xml";
    public static final String TEMPORAL = "europapress.xml";
    ListView lista;
    ArrayList<Noticia> noticias;
    ArrayAdapter<Noticia> adapter;
    FloatingActionButton fab_updatenews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.noticias);
        lista = findViewById(R.id.listView);
        lista.setOnItemClickListener(this);
        fab_updatenews = findViewById(R.id.floatingActionButton);
        fab_updatenews.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if (v == fab_updatenews)
            descarga(CANAL, TEMPORAL);
    }

    private void descarga(String canal, String temporal) {
        final ProgressDialog progreso = new ProgressDialog(this);

        progreso.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progreso.setMessage("Descargando");
        progreso.setCancelable(false);
        progreso.show();

        RestClient.get(canal, new FileAsyncHttpResponseHandler(this) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                progreso.dismiss();
                Toast.makeText(Noticias.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                progreso.dismiss();
                Toast.makeText(Noticias.this, "Fichero descargado correctamente", Toast.LENGTH_SHORT).show();
                try {
                    noticias= Analisis.analizarNoticias(file);
                    if(adapter==null)
                    {
                        adapter= new ArrayAdapter<Noticia>(Noticias.this, R.layout.noticias);
                        adapter.addAll(noticias);
                    }else {
                        adapter.clear();
                        adapter.addAll(noticias);
                    }
                    mostrar();
                } catch (XmlPullParserException e) {
                    e.printStackTrace();
                    Toast.makeText(Noticias.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(Noticias.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void mostrar() {
        if (noticias != null) {
            if (adapter != null) {
                lista.setAdapter(adapter);
                Toast.makeText(getApplicationContext(), "Lista creada con exito", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al crear el adapter", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error al crear la lista", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Uri uri = Uri.parse((String) noticias.get(position).getLink());
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        if (intent.resolveActivity(getPackageManager()) != null)
            startActivity(intent);
        else
            Toast.makeText(getApplicationContext(), "No hay un navegador", Toast.LENGTH_SHORT).show();
    }
}
