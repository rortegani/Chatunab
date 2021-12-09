package com.ingelecron.chatunab.presentadores;

import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public interface IPreTab1Fragment {

    void agregarContactoDB(ArrayList<PojoContacto> pojoContactoArrayList);

    void obtenerContactosDB();

    void mostrarContactosRV(ArrayList<PojoContacto> pojoContactoArrayList);

    void eliminarContactosDB();
}
