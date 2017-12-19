package com.example.usuario.xml;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

/**
 * Created by usuario on 4/12/17.
 */

public class Notas extends AppCompatActivity{

    private TextView txvNotas;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas);
        txvNotas = findViewById(R.id.txvNotas);
        try {
            txvNotas.setText(Analisis.analizarNombres(this));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            txvNotas.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            txvNotas.setText(e.getMessage());
        }
    }
}
