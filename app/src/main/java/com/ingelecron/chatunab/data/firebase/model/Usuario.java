package com.ingelecron.chatunab.data.firebase.model;

public class Usuario {

    private String id;
    private String nombre;
    private String correo;
    private String imagen;
    private String estado;
    private long tiempo;

    public Usuario() {
    }

    public Usuario(String id, String nombre, String correo, long tiempo) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.imagen = "";
        this.estado = "";
        this.tiempo = tiempo;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public long getTiempo() {
        return tiempo;
    }

    public void setTiempo(long tiempo) {
        this.tiempo = tiempo;
    }
}
