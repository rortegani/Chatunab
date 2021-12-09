package com.ingelecron.chatunab.vistas.tab1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.adaptadores.AdaptadorRVContactos;
import com.ingelecron.chatunab.data.db.model.PojoContacto;
import com.ingelecron.chatunab.presentadores.IPreTab1Fragment;
import com.ingelecron.chatunab.presentadores.PreTab1Fragment;

import java.util.ArrayList;

public class Tab1Fragment extends Fragment implements ITab1Fragment{

    View view;

    private RecyclerView rv_inicio;
    private LinearLayoutManager linearLayoutManager;
    private GridLayoutManager gridLayoutManager;
    private AdaptadorRVContactos adaptadorRVContactos;
    private ArrayList<PojoContacto> pojoContactoArrayList;
    private IPreTab1Fragment iPreTab1Fragment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view= inflater.inflate(R.layout.fragment_tab1, container, false);

        rv_inicio=view.findViewById(R.id.rv_inicio);

        iPreTab1Fragment= new PreTab1Fragment(getActivity(), this);

        return view;
    }

    @Override
    public AdaptadorRVContactos crearAdaptadorRVContactos(ArrayList<PojoContacto> pojoContactoArrayList) {
        adaptadorRVContactos = new AdaptadorRVContactos(getActivity(), pojoContactoArrayList);
        return adaptadorRVContactos;
    }

    @Override
    public void inicializadorAdaptadorRVContactos(AdaptadorRVContactos adaptadorRVContactos) {
        rv_inicio.setAdapter(adaptadorRVContactos);
    }

    @Override
    public void generarLinearLayoutManager() {
        linearLayoutManager= new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_inicio.setLayoutManager(linearLayoutManager);
//        gridLayoutManager= new GridLayoutManager(getActivity(), 2);
//        pojoContactosArrayList= new ArrayList<PojoContactos>();
//        pojoContactosArrayList.add(new PojoContactos(R.drawable.ic_mas, "Jesus", "3121234567", "jesus@gmail.com"));
//        pojoContactosArrayList.add(new PojoContactos(R.drawable.ic_persona, "Maria", "3127894561", "maria@gmail.com"));
//        pojoContactosArrayList.add(new PojoContactos(R.drawable.ic_mas, "Carlos", "3121245789", "carlos@gmail.com"));
//        pojoContactosArrayList.add(new PojoContactos(R.drawable.ic_persona, "Pepe", "3123165478", "pepe@gmail.com"));
//        pojoContactosArrayList.add(new PojoContactos(R.drawable.ic_mas, "Alejandro", "3129632587", "alejandro@gmail.com"));

    }
}
