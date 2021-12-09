package com.ingelecron.chatunab.data.network.api.model;

import com.ingelecron.chatunab.data.db.model.PojoContacto;

import java.util.ArrayList;

public class ContactoResponseApiRest {


    private ArrayList<PojoContacto> pojoContactoArrayList;

    public ArrayList<PojoContacto> getPojoContactoArrayList() {
        return pojoContactoArrayList;
    }

    public void setPojoContactoArrayList(ArrayList<PojoContacto> pojoContactoArrayList) {
        this.pojoContactoArrayList = pojoContactoArrayList;
    }
}
