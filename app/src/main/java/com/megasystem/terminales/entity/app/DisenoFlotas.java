package com.megasystem.terminales.entity.app;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Nullable;

import java.util.List;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class DisenoFlotas extends Entity {
    @SerializedName("flotaId")
    private Integer FlotaId;
    @SerializedName("piso")
    private Integer Piso;
    @SerializedName("cantidad")
    private Integer Cantidad;
    @Nullable
    private Integer BaseId;

    public Integer getBaseId() {
        return BaseId;
    }

    public void setBaseId(Integer baseId) {
        BaseId = baseId;
    }

    @Ignore
    private List<EstadoAsientos> lstEstadoAsientos;
    public enum relation {
        EstadoFlotas
    }
    public DisenoFlotas() {
    }

    public List<EstadoAsientos> getLstEstadoAsientos() {
        return lstEstadoAsientos;
    }

    public void setLstEstadoAsientos(List<EstadoAsientos> lstEstadoAsientos) {
        this.lstEstadoAsientos = lstEstadoAsientos;
    }

    public Integer getFlotaId() {
        return FlotaId;
    }

    public void setFlotaId(Integer flotaId) {
        FlotaId = flotaId;
    }

    public Integer getPiso() {
        return Piso;
    }

    public void setPiso(Integer piso) {
        Piso = piso;
    }

    public Integer getCantidad() {
        return Cantidad;
    }

    public void setCantidad(Integer cantidad) {
        Cantidad = cantidad;
    }
}
