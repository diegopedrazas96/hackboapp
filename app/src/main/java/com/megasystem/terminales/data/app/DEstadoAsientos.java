package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.EstadoAsientos;

import java.util.List;

import joquery.CQ;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DEstadoAsientos extends Wrapper {


    public DEstadoAsientos(Context context) {
        super(context, EstadoAsientos.class);
    }

    public List<EstadoAsientos> list() {
        return super.list("select * from " + tableName);
    }

    public EstadoAsientos get() {
        return (EstadoAsientos) this.get("select * from " + tableName);
    }

    public List<EstadoAsientos> returnChildByDisenho(String Keys, String[] relations) {
        List<EstadoAsientos> list = super.list("select * from " + tableName + "  where DisenoFlotaId in " + Keys + " ");
        try {
            loadRelations(list, relations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public List<EstadoAsientos> returnChildByDisenho(int valor) {
        List<EstadoAsientos> list = super.list("select * from " + tableName + "  where DisenoFlotaId = " + valor + " ");

        return list;
    }

    protected void loadRelations(List<EstadoAsientos> lstObj, String[] relations) throws Exception {
        if (relations.length == 0 || lstObj == null || lstObj.isEmpty()) {
            return;
        }

    }

}