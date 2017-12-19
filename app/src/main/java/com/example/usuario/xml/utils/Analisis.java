package com.example.usuario.xml.utils;

import android.content.Context;
import android.content.res.XmlResourceParser;
import android.util.Xml;

import com.example.usuario.xml.R;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by usuario on 19/12/17.
 */

public class Analisis {
    public static final String TEXTO = "<texto><uno>Hello World! </uno><dos>Goodbye</dos></texto>";

    public static String analizar(String texto) throws XmlPullParserException, IOException {
        StringBuilder cadena = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput( new StringReader(texto));
        int eventType = xpp.getEventType();
        cadena.append("Inicio . . . \n");
        while (eventType != XmlPullParser. END_DOCUMENT ) {
            switch (eventType) {
                case XmlPullParser.START_TAG :
                    break;
                case XmlPullParser.TEXT :
                    break;
                case XmlPullParser.END_TAG :
                    break;
            }
            eventType = xpp.next();
        }
        //System.out.println("End document");
        cadena.append("End document" + "\n" + "Fin");
        return cadena.toString();
    }

    public static String analizarXML(XmlPullParser xpp) throws XmlPullParserException, IOException
    {
        int eventType = xpp.getEventType();

        StringBuilder cadena = new StringBuilder();
        int espaciosMRJ = 0; // para ordenar las etiquetas dandoles margen izquierdo
        String espacio = " "; // el espacio que tendran las etiquetas
        cadena.append("Inicio . . . \n");

        while (eventType != XmlPullParser. END_DOCUMENT ) {

            if(eventType == XmlPullParser.START_DOCUMENT){ // empezar el documento
                cadena.append("START_DOCUMENT\n");

            } else if (eventType == XmlPullParser.START_TAG){ // cuando empieza una etiqueta
                cadena.append("\n");
                for (int i = 0; i < espaciosMRJ; i++) {
                    cadena.append(espacio); // cuando empieza la etiqueta añade X espacios
                }
                cadena.append("START_TAG:" + xpp.getName() + "\n");

                espaciosMRJ++;
                // al encontrar una etiqueta sumaremos 1 al espacio
                // lo hacemos despues de START_TAG para que los siguiente ATT o TAG tengas mas espacio

                if(xpp.getAttributeCount() > 0){ // si hay atributos ATT
                    for (int i = 0; i < xpp.getAttributeCount(); i++) { // recorrer atributos
                        for (int m = 0; m < espaciosMRJ; m++) {
                            cadena.append(espacio); // anadir espacios para cada linea de ATT
                        }
                        cadena.append("ATT "+xpp.getAttributeName(i)+": "+xpp.getAttributeValue(i) + "\n");
                    }
                }

            } else if (eventType == XmlPullParser.TEXT){// encontrar el texto de una etiqueta TAG
                for (int i = 0; i < espaciosMRJ; i++) {
                    cadena.append(espacio);
                }
                cadena.append("TEXT:" + xpp.getText()  + "\n");

            } else if (eventType == XmlPullParser.END_TAG){
                espaciosMRJ--;
                // Al encontrar una etiqueta de cierre restamos 1 espacio
                // lo hacemos antes de mostrar el END_TAG para que tengan los mismos espacios
                for (int i = 0; i < espaciosMRJ; i++) {
                    cadena.append(espacio);
                }
                cadena.append("END_TAG:" + xpp.getName() + "\n");

            }
            eventType = xpp.next();
        }
        cadena.append("\nEnd document Fin");
        return cadena.toString();
    }

    public static String analizarNombres(Context c) throws XmlPullParserException, IOException {
        boolean esNombre = false;
        boolean esNota = false;
        StringBuilder cadena = new StringBuilder();
        Double suma = 0.0;
        int contador = 0;
        XmlResourceParser xrp = c.getResources().getXml(R.xml.alumnos);
        int eventType = xrp.getEventType();
        while (eventType != XmlPullParser. END_DOCUMENT ) {
            String etiqueta = new String();
            switch (eventType) {
                case XmlPullParser.START_TAG :
                    if(xrp.getName().equals("nombre"))
                        esNombre = true;
                    else if(xrp.getName().equals("nota")){
                        esNota = true;
                        for (int i = 0; i < xrp.getAttributeCount(); i++) {
                            cadena.append(xrp.getAttributeName(i) + ":"+ xrp.getAttributeValue(i)+"\n");
                        }
                    }
                    break;
                case XmlPullParser.TEXT :
                    if(esNombre){
                        cadena.append("Alumno: "+ xrp.getText()+"\n");
                    }
                    else if(esNota){
                        cadena.append("Nota: "+xrp.getText()+"\n");
                        suma += Double.parseDouble(xrp.getText());
                        contador++;
                    }
                    break;
                case XmlPullParser.END_TAG :
                    if (esNombre){
                        esNombre = false;
                        cadena.append("\n");
                    }
                    else if(esNota){
                        esNota = false;
                    }
                    break;
            }
            eventType = xrp.next();
        }
        cadena.append("Media: "+ String.format("%.2f", suma/contador));
        return cadena.toString();
    }

    public static String analizarRSS(File file) throws NullPointerException, XmlPullParserException, IOException {
        boolean dentroItem = false;
        boolean dentroTitle = false;
        StringBuilder builder = new StringBuilder();
        XmlPullParser xpp = Xml.newPullParser();
        xpp.setInput(new FileReader(file));
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    if(xpp.getName().equals("item")){
                        dentroItem = true;
                    }
                    if(dentroItem && xpp.getName().equalsIgnoreCase("tittle")){
                        dentroTitle = true;
                    }
                    break;
                case XmlPullParser.TEXT:
                    if(dentroTitle){
                        builder.append(xpp.getText() + "\n");
                    }
                    break;
                case XmlPullParser.END_TAG:
                    if(xpp.getName().equals("item")){
                        dentroItem = false;
                    }
                    if(dentroItem && xpp.getName().equalsIgnoreCase("tittle")){
                        dentroTitle = false;
                    }
                    break;
            }
            eventType = xpp.next();
        }
        return builder.toString();
    }
}
