package com.example.usuario.xml.model;

import java.io.File;

/**
 * Created by usuario on 11/01/18.
 */

public class MessageEvent {

    public final String message;
    public final File file;

    public MessageEvent(String message, File file) {
        this.message = message;
        this.file = file;
    }
}
