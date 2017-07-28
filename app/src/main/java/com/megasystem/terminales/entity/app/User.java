package com.megasystem.terminales.entity.app;

import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Nullable;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class User extends Entity {
    private  String nombre;
    private  String email;
    @Nullable
    private  String foto;
    private  String imei;
    private  Long estado;
    public User() {
    }


    public Long getEstado() {
        return estado;
    }

    public void setEstado(Long estado) {
        this.estado = estado;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


}
