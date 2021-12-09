package com.ingelecron.chatunab.data.network.api.deserializer;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.ingelecron.chatunab.data.db.model.PojoContacto;
import com.ingelecron.chatunab.data.network.api.model.ContactoResponseApiRest;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class DeserializerContactoResponseApiRest implements JsonDeserializer<ContactoResponseApiRest> {

    @Override
    public ContactoResponseApiRest deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonArray jsonArrayData= json.getAsJsonObject().getAsJsonArray(JsonKeysApiRest.MEDIA_ARRAY); //de los dos objetos obtenga el array data
        Gson gson=new Gson();
        ContactoResponseApiRest respuestaApiRestServicio = gson.fromJson(json, ContactoResponseApiRest.class); //mapea los unicos atributos que necesitamos
        respuestaApiRestServicio.setPojoContactoArrayList(deserializarJsonPojoContacto(jsonArrayData));

        return respuestaApiRestServicio;
    }

    private ArrayList<PojoContacto> deserializarJsonPojoContacto(JsonArray jsonArrayData){

        ArrayList<PojoContacto> arrayListpojoContacto= new ArrayList<>();

        for (int i=0; i<jsonArrayData.size(); i++){

            JsonObject objectosData= jsonArrayData.get(i).getAsJsonObject(); //traer todos los objetos de ese array de data
            //JsonObject objetoJson= objectosData.getAsJsonObject("nombre del campo");
            String id= objectosData.get(JsonKeysApiRest.MEDIA_ARRAY_ID).getAsString();
            String imagen= objectosData.get(JsonKeysApiRest.MEDIA_ARRAY_IMAGEN).getAsString();
            String nombre= objectosData.get(JsonKeysApiRest.MEDIA_ARRAY_NOMBRE).getAsString();
            String especies= objectosData.get(JsonKeysApiRest.MEDIA_ARRAY_ESPECIES).getAsString();

            PojoContacto pojoContacto=new PojoContacto();
            pojoContacto.setId(id);
            pojoContacto.setFoto(imagen);
            pojoContacto.setNombre(nombre);
            pojoContacto.setEspecie(especies);

            arrayListpojoContacto.add(pojoContacto);
        }


        return arrayListpojoContacto;
    }
}
