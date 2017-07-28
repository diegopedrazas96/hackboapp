package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.EstadoAsientos;
import com.megasystem.terminales.entity.app.Flotas;

import java.util.List;

import joquery.CQ;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DDisenoFlotas extends Wrapper {


    public DDisenoFlotas(Context context) {
        super(context, DisenoFlotas.class);
    }

    public List<DisenoFlotas> list() {
        return super.list("select * from " + tableName);
    }

    public DisenoFlotas get() {
        return (DisenoFlotas) this.get("select * from " + tableName);
    }

    public List<DisenoFlotas> returnChildByFlota(String Keys, String[] relations) {
        List<DisenoFlotas> list = super.list("select * from " + tableName + "  where FlotaId in " + Keys + " ");
        try {
            loadRelations(list, relations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    protected void loadRelations(List<DisenoFlotas> lstObj, String[] relations) throws Exception {
        if (relations.length == 0 || lstObj == null || lstObj.isEmpty()) {
            return;
        }
        DEstadoAsientos dalEstado;
        List<EstadoAsientos> lstEstado = null;
        String keys = "";
        int i = 0;
        for (String clase : relations) {
            if (clase.equals(DisenoFlotas.relation.EstadoFlotas.name())) {
                relations[i] = "";
                dalEstado = new DEstadoAsientos(this.context);
                keys=this.extract(lstObj, "Id");
              //  keys = keys.replaceAll(", ", "','");
                // lstProduct = dalProduct.list();
                lstEstado = dalEstado.returnChildByDisenho(keys, relations);
            }
            i++;
        }
        if (relations.length > 0) {
            for (DisenoFlotas obj : lstObj) {
                if (lstEstado != null && lstEstado.size() > 0) {
                    obj.setLstEstadoAsientos((List<EstadoAsientos>) CQ.filter(lstEstado).where().property("DisenoFlotaId").eq().value(obj.getId().intValue()).list());
                }
            }
        }
    }

}