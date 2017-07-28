package com.megasystem.terminales.entity.app;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Nullable;

import java.util.List;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class Flotas extends Entity {
    @SerializedName("TipoIdc")
    private Integer tipoIdc;
    @Nullable
    private Integer BaseId;
    private Integer EmpresaId;
    private String Placa;
    private String Chofer;
    private String Ayudante;
    private Integer NroPisos;
    @Ignore
    private List<DisenoFlotas> lstDisenoFlotas;
    public enum relation {
        DisenoFlotas
    }


    public Flotas() {
    }


    public Integer getTipoIdc() {
        return tipoIdc;
    }

    public void setTipoIdc(Integer tipoIdc) {
        this.tipoIdc = tipoIdc;
    }

    public Integer getBaseId() {
        return BaseId;
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

    public String getPlaca() {
        return Placa;
    }

    public void setPlaca(String placa) {
        Placa = placa;
    }

    public String getChofer() {
        return Chofer;
    }

    public void setChofer(String chofer) {
        Chofer = chofer;
    }

    public String getAyudante() {
        return Ayudante;
    }

    public void setAyudante(String ayudante) {
        Ayudante = ayudante;
    }

    public Integer getNroPisos() {
        return NroPisos;
    }

    public void setNroPisos(Integer nroPisos) {
        NroPisos = nroPisos;
    }

    public List<DisenoFlotas> getLstDisenoFlotas() {
        return lstDisenoFlotas;
    }

    public void setLstDisenoFlotas(List<DisenoFlotas> lstDisenoFlotas) {
        this.lstDisenoFlotas = lstDisenoFlotas;
    }
}
