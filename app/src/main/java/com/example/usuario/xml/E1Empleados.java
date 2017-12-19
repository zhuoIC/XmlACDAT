package com.example.usuario.xml;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class E1Empleados extends AppCompatActivity {

    private TextView txvTexto;
    private XmlPullParser xmlPullParser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empleados);
        txvTexto = findViewById(R.id.txvTexto);
        xmlPullParser = getApplicationContext().getResources().getXml(R.xml.alumnos);

        try {
            txvTexto.setText(Analisis.analizarXML(xmlPullParser));
        } catch (XmlPullParserException e) {
            e.printStackTrace();
            txvTexto.setText(e.getMessage());
        } catch (IOException e) {
            e.printStackTrace();
            txvTexto.setText(e.getMessage());
        }
    }
}