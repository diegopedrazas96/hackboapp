package com.megasystem.terminales.entity.app;

import com.gc.materialdesign.widgets.Dialog;
import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Nullable;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class EstadoAsientos extends Entity {

    private Integer DisenoFlotaId;
    private String CodAsiento;
    private Integer Estado;


    public EstadoAsientos() {
    }


    public String getCodAsiento() {
        return CodAsiento;
    }

    public void setCodAsiento(String codAsiento) {
        CodAsiento = codAsiento;
    }

    public Integer getDisenoFlotaId() {
        return DisenoFlotaId;
    }

    public void setDisenoFlotaId(Integer disenoFlotaId) {
        DisenoFlotaId = disenoFlotaId;
    }

    public Integer getEstado() {
        return Estado;
    }

    public void setEstado(Integer estado) {
        Estado = estado;
    }
}
