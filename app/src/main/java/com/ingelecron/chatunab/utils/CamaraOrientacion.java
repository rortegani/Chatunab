package com.ingelecron.chatunab.utils;

import android.content.Context;
import android.os.Environment;
import android.view.WindowManager;

import java.io.File;
import java.util.UUID;

public class CamaraOrientacion {

    //donde almacenamos la fot
    public static File getArchivo(){

        String rutaGaleria= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();

        File carpeta= new File(rutaGaleria+"/ChatUnab");

        if(!carpeta.exists()){
            carpeta.mkdirs();
        }

        //nombre de la foto ramdon
        return new File(carpeta, UUID.randomUUID().toString()+".jpg");
    }

    public static int getOrientacion(Context contexto){
        return ((WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getRotation();
    }
}
