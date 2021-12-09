package com.ingelecron.chatunab.data.db.interactor;

import android.content.ContentValues;

import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public interface IInteractorDBRVContactos {

    ArrayList<PojoContacto> obtenerContacto();

    void agregarContacto(ContentValues contentValues);

    public void borrarContactos();
}
