package com.megasystem.terminales.entity.app;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Nullable;

import java.util.Date;
import java.util.List;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class Viajes extends Entity {
    private  Integer EmpresaId;
    @Nullable
    private  Integer BaseId;
    private String Origen;
    private String Destino;
    private Date Horario;
    private Double Precio;
    public enum relation {
        Flotas
    }
    @Ignore
    private List<Flotas> lstFlotas;

    public Viajes() {
    }

    public Double getPrecio() {
        return Precio;
    }

    public void setPrecio(Double precio) {
        Precio = precio;
    }

    public Integer getBaseId() {
        return BaseId;
    }

    public List<Flotas> getLstFlotas() {
        return lstFlotas;
    }

    public void setLstFlotas(List<Flotas> lstFlotas) {
        this.lstFlotas = lstFlotas;
    }

    public void setBaseId(Integer baseId) {
        BaseId = baseId;
    }

    public Integer getEmpresaId() {
        return EmpresaId;
    }

    public void setEmpresaId(Integer empresaId) {
        EmpresaId = empresaId;
    }

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public Date getHorario() {
        return Horario;
    }

    public void setHorario(Date horario) {
        Horario = horario;
    }
}
