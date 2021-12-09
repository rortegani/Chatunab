package com.ingelecron.chatunab.data.network.api;

import com.ingelecron.chatunab.data.network.api.model.ContactoResponseApiRest;

import retrofit2.Call;
import retrofit2.http.GET;

public interface IEndPointsApiRest {

    //EndPonits
    //ejecuta la peticion al servidor de instagram mediante la url, <> coleccion
    @GET(ConstApiRest.GET_MEDIA_ALL)
    Call<ContactoResponseApiRest> getMedia();
}
