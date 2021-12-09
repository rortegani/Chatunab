package com.ingelecron.chatunab.vistas.tab1;

import com.ingelecron.chatunab.adaptadores.AdaptadorRVContactos;
import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public interface ITab1Fragment {

    AdaptadorRVContactos crearAdaptadorRVContactos(ArrayList<PojoContacto> pojoContactoArrayList);

    void inicializadorAdaptadorRVContactos(AdaptadorRVContactos adaptadorRVContactos);

    void generarLinearLayoutManager();
}


