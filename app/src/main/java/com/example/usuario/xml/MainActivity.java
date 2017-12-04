package com.example.usuario.xml;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button btnNotas,
            btnPartes,
            btnTitulares,
            btnNoticias,
            btnCreacionXML;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnNotas = findViewById(R.id.btnNotas);
        btnPartes = findViewById(R.id.btnPartes);
        btnNoticias = findViewById(R.id.btnNoticias);
        btnTitulares = findViewById(R.id.btnTitulares);
        btnCreacionXML = findViewById(R.id.btnCreacionXML);
        btnPartes.setOnClickListener(this);
        btnNotas.setOnClickListener(this);
        btnTitulares.setOnClickListener(this);
        btnNoticias.setOnClickListener(this);
        btnCreacionXML.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        if (view == btnPartes){
            intent = new Intent(this, Partes.class);
            startActivity(intent);
        }
        if (view == btnNotas){
            intent = new Intent(this, Notas.class);
            startActivity(intent);
        }
        if (view == btnTitulares){
            intent = new Intent(this, Titulares.class);
            startActivity(intent);
        }
        if (view == btnNoticias){
            intent = new Intent(this, Noticias.class);
            startActivity(intent);
        }
        if (view == btnCreacionXML){
            intent = new Intent(this, CreacionXML.class);
            startActivity(intent);
        }
    }
}