package com.example.usuario.xml.network;

import android.os.Environment;

import com.example.usuario.xml.model.FailureEvent;
import com.example.usuario.xml.model.MessageEvent;
import com.loopj.android.http.FileAsyncHttpResponseHandler;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import cz.msebera.android.httpclient.Header;

/**
 * Created by usuario on 11/01/18.
 */

public class DownloadTask {
    public static void executeDownload(String canal, String temporal){
        File miFichero = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), temporal);
        RestClient.get(canal, new FileAsyncHttpResponseHandler(miFichero) {
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, File file) {
                EventBus.getDefault().post(new FailureEvent("Error en la descarga", statusCode));
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, File file) {
                EventBus.getDefault().post(new MessageEvent("Todo correcto.", file));
            }
        });
    }
}
