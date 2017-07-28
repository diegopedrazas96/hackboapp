package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesCliente;

import java.util.List;

import joquery.CQ;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DViajesCliente extends Wrapper {


    public DViajesCliente(Context context) {
        super(context, ViajesCliente.class);
    }

    public List<ViajesCliente> list() {
        return super.list("select * from " + tableName);
    }

    public ViajesCliente get() {
        return (ViajesCliente) this.get("select * from " + tableName);
    }

    public List<Viajes> returnChildByEmpresa(int Keys, String[] relations) {
        List<Viajes> list = super.list("select * from " + tableName + " where EmpresaId = " + Keys + " ");
        try {
            loadRelations(list, relations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    protected void loadRelations(List<Viajes> lstObj, String[] relations) throws Exception {
        if (relations.length == 0 || lstObj == null || lstObj.isEmpty()) {
            return;
        }
        DFlotas dalFlotas;
        List<Flotas> lstFlotas = null;
        String keys = "";
        int i = 0;
        for (String clase : relations) {

            if (clase.equals(Viajes.relation.Flotas.name())) {
                relations[i] = "";
                keys = this.extract(lstObj, "Id");
                dalFlotas = new DFlotas(this.context);
                lstFlotas = dalFlotas.returnChildByViaje(keys, relations);
            }


            i++;
        }
        if (relations.length > 0) {
            for (Viajes obj : lstObj) {

                if (lstFlotas != null && lstFlotas.size() > 0) {
                    obj.setLstFlotas((List<Flotas>) CQ.filter(lstFlotas).where().property("EmpresaId").eq().value(obj.getEmpresaId()).list());
                }

            }
        }
    }
}