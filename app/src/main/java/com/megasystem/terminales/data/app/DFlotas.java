package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.User;

import java.util.List;

import joquery.CQ;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DFlotas extends Wrapper {


    public DFlotas(Context context) {
        super(context, Flotas.class);
    }

    public List<Flotas> list() {
        return super.list("select * from " + tableName);
    }

    public Flotas get() {
        return (Flotas) this.get("select * from " + tableName);
    }

    public List<Flotas> returnChildByViaje(String Keys, String[] relations) {
        List<Flotas> list = super.list("select m.* from " + tableName + "  m  inner join app_viajesflota v on v.FlotaId = m.Id where v.ViajeId in " + Keys + " ");
        try {
            loadRelations(list, relations);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    protected void loadRelations(List<Flotas> lstObj, String[] relations) throws Exception {
        if (relations.length == 0 || lstObj == null || lstObj.isEmpty()) {
            return;
        }
        DDisenoFlotas dalDiseno;
        List<DisenoFlotas> lstDiseno = null;
        String keys = "";
        int i = 0;
        for (String clase : relations) {
            if (clase.equals(Flotas.relation.DisenoFlotas.name())) {
                relations[i] = "";
                dalDiseno = new DDisenoFlotas(this.context);
                keys=this.extract(lstObj, "Id");
                //keys = keys.replaceAll(", ", "','");
                // lstProduct = dalProduct.list();
                lstDiseno = dalDiseno.returnChildByFlota(keys, relations);
            }
            i++;
        }
        if (relations.length > 0) {
            for (Flotas obj : lstObj) {
                if (lstDiseno != null && lstDiseno.size() > 0) {
                    obj.setLstDisenoFlotas((List<DisenoFlotas>) CQ.filter(lstDiseno).where().property("FlotaId").eq().value(obj.getId().intValue()).list());
                }
            }
        }
    }

}