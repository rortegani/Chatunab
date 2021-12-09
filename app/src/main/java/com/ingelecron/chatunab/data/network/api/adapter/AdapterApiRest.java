package com.ingelecron.chatunab.data.network.api.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ingelecron.chatunab.data.network.api.ConstApiRest;
import com.ingelecron.chatunab.data.network.api.IEndPointsApiRest;
import com.ingelecron.chatunab.data.network.api.deserializer.DeserializerContactoResponseApiRest;
import com.ingelecron.chatunab.data.network.api.model.ContactoResponseApiRest;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdapterApiRest {

    //genere la llamada a la url y despues ir a la clase IPeticionesResApi y ejecute las acciones

    public IEndPointsApiRest conexionApiRest(){

        Retrofit retrofit= new Retrofit.Builder()
                .baseUrl(ConstApiRest.URL)
                .addConverterFactory(GsonConverterFactory.create(construirGsonDeserializadorMedia())) //deserealizacion todos los datos
                .build();

        return retrofit.create(IEndPointsApiRest.class); //cuando se conecte va a ejecutar las acciones de los endpoints
    }

    public Gson construirGsonDeserializadorMedia(){

        GsonBuilder gsonBuilder= new GsonBuilder();
        gsonBuilder.registerTypeAdapter(ContactoResponseApiRest.class, new DeserializerContactoResponseApiRest());

        return gsonBuilder.create();

    }
}
