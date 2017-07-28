package com.megasystem.terminales.entity.app;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Nullable;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class ViajesCliente extends Entity {
    @SerializedName("id")
    @Nullable
    private Integer BaseId;
    @SerializedName("viajeid")
    private  Integer ViajeId;
    @SerializedName("clienteid")
    private Integer ClienteId;
    @SerializedName("asientoid")
    @Nullable
    private Integer AsientoId;
    @SerializedName("ci")
    @Nullable
    private String Ci;
    @Nullable
    private String nombre;
    @SerializedName("edad")
    @Nullable
    private Integer edad;


    public ViajesCliente() {
    }

    public Integer getAsientoId() {
        return AsientoId;
    }

    public void setAsientoId(Integer asientoId) {
        AsientoId = asientoId;
    }

    public String getCi() {
        return Ci;
    }

    public void setCi(String ci) {
        Ci = ci;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public Integer getBaseId() {
        return BaseId;
    }

    public void setBaseId(Integer baseId) {
        BaseId = baseId;
    }

    public Integer getViajeId() {
        return ViajeId;
    }

    public void setViajeId(Integer viajeId) {
        ViajeId = viajeId;
    }

    public Integer getClienteId() {
        return ClienteId;
    }

    public void setClienteId(Integer clienteId) {
        ClienteId = clienteId;
    }
}
