package com.ingelecron.chatunab.presentadores;

import android.content.ContentValues;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.ingelecron.chatunab.R;
import com.ingelecron.chatunab.data.db.interactor.IInteractorDBRVContactos;
import com.ingelecron.chatunab.data.db.interactor.InteractorDBRVContactos;
import com.ingelecron.chatunab.data.db.model.PojoContacto;
import com.ingelecron.chatunab.data.db.ConstantesDBRVContactos;
import com.ingelecron.chatunab.data.network.api.IEndPointsApiRest;
import com.ingelecron.chatunab.data.network.api.adapter.AdapterApiRest;
import com.ingelecron.chatunab.data.network.api.model.ContactoResponseApiRest;
import com.ingelecron.chatunab.vistas.tab1.ITab1Fragment;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PreTab1Fragment implements IPreTab1Fragment{

    private Context contexto;
    private ITab1Fragment iTab1Fragment;
    private IInteractorDBRVContactos iInteractorDBRVContactos;

    public PreTab1Fragment(Context contexto, ITab1Fragment iTab1Fragment) {
        this.contexto = contexto;
        this.iTab1Fragment = iTab1Fragment;
        this.iInteractorDBRVContactos = new InteractorDBRVContactos(contexto);
        dataApi();
    }


    private void dataApi() {
//        agregarContactoDB(listaContacto());
//        obtenerContactosDB();

        AdapterApiRest adaptadorJsonApiRest = new AdapterApiRest();
        //Gson gsonMedia= apiRestAdaptador.construirGsonDeserializadorMedia();
        //IApiRestServicio iApiRestServicio = apiRestAdaptador.conexionApiRest(gsonMedia);
        IEndPointsApiRest iApiRestServicio = adaptadorJsonApiRest.conexionApiRest();
        Call<ContactoResponseApiRest> callRespuestaPojoContacto= iApiRestServicio.getMedia();
        callRespuestaPojoContacto.enqueue(new Callback<ContactoResponseApiRest>() {
            @Override
            public void onResponse(Call<ContactoResponseApiRest> call, Response<ContactoResponseApiRest> response) {
                //Toast.makeText(contexto, response.body().getArrayListPojoContacto().toString(), Toast.LENGTH_LONG).show();
                ContactoResponseApiRest respuestaApiRestServicio = response.body();
                ArrayList<PojoContacto> pojoContactoArrayList= respuestaApiRestServicio.getPojoContactoArrayList();
                agregarContactoDB(pojoContactoArrayList);
                obtenerContactosDB();
            }

            @Override
            public void onFailure(Call<ContactoResponseApiRest> call, Throwable t) {
                Toast.makeText(contexto, "Error al obtener los datos", Toast.LENGTH_LONG).show();
                Log.e("Fallo al obtener", t.toString());
            }
        });
    }


    @Override
    public void agregarContactoDB(ArrayList<PojoContacto> pojoContactoArrayList) {

        eliminarContactosDB();

        ContentValues contentValues;

        for (int i = 0; i< pojoContactoArrayList.size(); i++) {
            contentValues = new ContentValues();
            contentValues.put(ConstantesDBRVContactos.T_CONTACTO_ID, pojoContactoArrayList.get(i).getId());
            contentValues.put(ConstantesDBRVContactos.T_CONTACTO_FOTO, pojoContactoArrayList.get(i).getFoto());
            contentValues.put(ConstantesDBRVContactos.T_CONTACTO_NOMBRE, pojoContactoArrayList.get(i).getNombre());
//            contentValues.put(ConstantesDBRVContactos.T_CONTACTO_TELEFONO, pojoContactoArrayList.get(i).getTelefono());
//            contentValues.put(ConstantesDBRVContactos.T_CONTACTO_CORREO, pojoContactoArrayList.get(i).getCorreo());
            iInteractorDBRVContactos.agregarContacto(contentValues);
        }
    }


    @Override
    public void obtenerContactosDB() {
        ArrayList<PojoContacto> pojoContactoArrayList= iInteractorDBRVContactos.obtenerContacto();
        mostrarContactosRV(pojoContactoArrayList);
    }


    @Override
    public void mostrarContactosRV(ArrayList<PojoContacto> pojoContactoArrayList) {
        iTab1Fragment.inicializadorAdaptadorRVContactos(iTab1Fragment.crearAdaptadorRVContactos(pojoContactoArrayList));
        iTab1Fragment.generarLinearLayoutManager();
    }

    @Override
    public void eliminarContactosDB() {
        iInteractorDBRVContactos.borrarContactos();
    }


//    public ArrayList<PojoContacto> listaContacto(){
//
//        ArrayList<PojoContacto> pojoContactoArrayList =new ArrayList<>();
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_mas), "Jesus", "3101234567", "jesus@gmail.com"));
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_persona),"maria", "3119876543","devejeface@gmail.com"));
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_mas),"carlos", "3121239876","carlos@gmail.com"));
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_persona),"juan", "3133456778","juan@gmail.com"));
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_mas),"pepe", "3148765435","pepe@gmail.com"));
//        pojoContactoArrayList.add(new PojoContacto(String.valueOf(R.drawable.ic_persona),"alejandro", "3152345768","alejandro@gmail.com"));
//
//        return pojoContactoArrayList;
//    }


}
