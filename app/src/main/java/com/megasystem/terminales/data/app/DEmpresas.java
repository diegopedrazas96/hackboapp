package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.User;

import java.util.List;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DEmpresas extends Wrapper {


    public DEmpresas(Context context) {
        super(context, Empresas.class);
    }

    public List<Empresas> list() {
        return super.list("select * from " + tableName);
    }

    public Empresas get() {
        return (Empresas) this.get("select * from " + tableName);
    }


}