package com.example.usuario.xml.model;

import java.io.File;

/**
 * Created by usuario on 11/01/18.
 */

public class FailureEvent {
    public final String message;
    public final int status;

    public FailureEvent(String message, int status) {
        this.message = message;
        this.status = status;
    }
}
