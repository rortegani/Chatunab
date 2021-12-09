package com.ingelecron.chatunab.data.db.model;

public class PojoContacto {

    private String id;
    private String foto;
    private String nombre;
    private String especie;
//    private String correo;

    public PojoContacto() {
    }

    public PojoContacto(String foto, String nombre, String especie) {
        this.foto = foto;
        this.nombre = nombre;
        this.especie = especie;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }
}
