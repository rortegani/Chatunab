package com.ingelecron.chatunab.data.db.interactor;

import android.content.ContentValues;
import android.content.Context;

import com.ingelecron.chatunab.data.db.DBRVContactos;
import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public class InteractorDBRVContactos implements IInteractorDBRVContactos {

    private Context contexto;

    public InteractorDBRVContactos(Context contexto) {
        this.contexto = contexto;
    }

    @Override
    public ArrayList<PojoContacto> obtenerContacto() {
        DBRVContactos dbrvContactos= new DBRVContactos(contexto);
        return dbrvContactos.leerContacto();
    }

    @Override
    public void agregarContacto(ContentValues contentValues) {
        DBRVContactos dbrvContactos= new DBRVContactos(contexto);
        dbrvContactos.crearContacto(contentValues);
    }

    @Override
    public void borrarContactos(){
        DBRVContactos dbrvContactos =new DBRVContactos(contexto);
        dbrvContactos.eliminarContactos();
    }
}
