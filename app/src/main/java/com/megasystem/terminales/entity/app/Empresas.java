package com.megasystem.terminales.entity.app;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Nullable;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class Empresas extends Entity {
    @SerializedName("acepcCepc")
    @Nullable
    private Integer baseId;
    @SerializedName("acepcNomb")
    private String nombre;
   @Ignore
    private String icon;
    public Empresas() {
    }

    public Empresas(Integer baseId, String nombre) {
        this.baseId = baseId;
        this.nombre = nombre;
    }

    public Integer getBaseId() {
        return baseId;
    }

    public void setBaseId(Integer baseId) {
        this.baseId = baseId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}
