package com.megasystem.terminales.service;

import android.content.Context;

import com.google.gson.reflect.TypeToken;
import com.megasystem.terminales.Application;
import com.megasystem.terminales.entity.app.DisenoFlotas;
import com.megasystem.terminales.entity.app.Empresas;
import com.megasystem.terminales.entity.app.EstadoAsientos;
import com.megasystem.terminales.entity.app.Flotas;
import com.megasystem.terminales.entity.app.User;
import com.megasystem.terminales.entity.app.Viajes;
import com.megasystem.terminales.entity.app.ViajesCliente;
import com.megasystem.terminales.entity.app.ViajesFlota;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class Service extends Web {

    private SimpleDateFormat dateFormat;

    public Service(Context context) {
        super(context);
        dateFormat = new SimpleDateFormat(Application.formatDateShort);
    }

    public Date getServerDay() throws Exception {
        return (Date) super.getObject("/api/default/serverdate", Date.class,"","");
    }

    public Date getNextday(String user, String password) throws Exception {
        return (Date) super.getObject("/api/default/nextday", Date.class, user, password);
    }

    public Date getDay(String user, String password) throws Exception {
        return (Date) super.getObject("/api/default/day", Date.class, user, password);
    }
/*
    public User getUser(String imei) throws Exception {
        return super.get("/api/vendedor/" + imei, User.class,"","");
    }

    public long postSale(Proforma entity, String user, String password) throws Exception {
        return super.post("/api/proformas", entity.getClass(), entity, user, password);
    }
    public long postCustomer(Customer entity, String user, String password) throws Exception {
        return super.post("/api/clientes", entity.getClass(), entity, user, password);
    }*/


/*
    public long postCount(Count entity, String user, String password) throws Exception {
        return super.post("/api/count", entity.getClass(), entity, user, password);
    }

    public long putCount(Count entity, String user, String password) throws Exception {
        return super.put("/api/count/" + entity.getIdServer(), entity.getClass(), entity, user, password);
    }
     public List<Product> getProducts(Long id, String user, String password) throws Exception {
        return super.getList("/api/product/line/" + id, new TypeToken<ArrayList<Product>>() {
        }.getType(), user, password);
    }
*/
public long postViajeCliete(ViajesCliente entity, String user, String password) throws Exception {
    return super.post("/api/viajecliente", entity.getClass(), entity, user, password);
}
    public long postEstadoAsiento(EstadoAsientos entity, String user, String password) throws Exception {
        return super.post("/api/estadoasiento", entity.getClass(), entity, user, password);
    }
    public long postCliente(User entity, String user, String password) throws Exception {
        return super.post("/api/clientes", entity.getClass(), entity, user, password);
    }
    public List<Empresas> getEmpresas( String user, String password) throws Exception {
        return super.getList("/api/empresa" , new TypeToken<ArrayList<Empresas>>() {
        }.getType(), user, password);
    }
    public List<Viajes> getViajes(String user, String password) throws Exception {
        return super.getList("/api/viaje" , new TypeToken<ArrayList<Viajes>>() {
        }.getType(), user, password);
    }
    public List<ViajesFlota> getViajesFlota(String user, String password) throws Exception {
        return super.getList("/api/viajeflota" , new TypeToken<ArrayList<ViajesFlota>>() {
        }.getType(), user, password);
    }
    public List<ViajesCliente> getViajesCliente(String user, String password) throws Exception {
        return super.getList("/api/viajecliente" , new TypeToken<ArrayList<ViajesCliente>>() {
        }.getType(), user, password);
    }
    public List<Flotas> getFlotas(String user, String password) throws Exception {
        return super.getList("/api/flota" , new TypeToken<ArrayList<Flotas>>() {
        }.getType(), user, password);
    }
    public List<DisenoFlotas> getDisenoFlotas(String user, String password) throws Exception {
        return super.getList("/api/disenoflota" , new TypeToken<ArrayList<DisenoFlotas>>() {
        }.getType(), user, password);
    }
    public List<EstadoAsientos> getEstadoAsientos(String user, String password) throws Exception {
        return super.getList("/api/estadoasiento" , new TypeToken<ArrayList<EstadoAsientos>>() {
        }.getType(), user, password);
    }

    public Long getStock(Long idProduct, Long idBranch, String user, String password) throws Exception {
        return (Long) super.getObject("/api/product/stock/" + idProduct + "/" + idBranch, Long.class, user, password);
    }








    public boolean sendReport(Long idSchedule, Long lineId, String user, String password) throws Exception {
        return (Boolean) super.getObject("/api/result/schedule/send/" + idSchedule + "/" + lineId, Boolean.class, user, password);
    }

    public boolean sendToClientSystem(Long idSchedule, Long lineId, String user, String password) throws Exception {
        return (Boolean) super.getObject("/api/result/schedule/close/" + idSchedule + "/" + lineId, Boolean.class, user, password);
    }

    public boolean setClose(Long scheduleId, Date closeDate, String user, String password) throws Exception {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return (Boolean) super.getObject("/api/user/close/" + scheduleId + "/" + formatter.format(closeDate), Boolean.class, user, password);
    }


    public boolean downloadExcel(Long idSchedule, Long lineId, String user, String password) throws Exception {
        return super.downloadExcel("/api/result/schedule/view/" + idSchedule + "/" + lineId, user, password);
    }

    public Double getAvalaibleStock(String imei) throws Exception {
        return (Double) super.getObject("/api/stock/disponible/"+imei, Double.class, "", "");
    }


/*
    public List<Product> getProduct(String idVendedor) throws Exception {
        return super.getList("/api/product/list/"+idVendedor, new TypeToken<ArrayList<Product>>() {
        }.getType(), "", "");
    }

    public List<Customer> getClientes(String idCobrador) throws Exception {
        return super.getList("/api/clientes/list/", new TypeToken<ArrayList<Customer>>() {
        }.getType(), "", "");
    }
    public List<Stock> getStock(String idCobrador, String user, String password) throws Exception {
        return super.getList("/api/stock/list/"+idCobrador, new TypeToken<ArrayList<Stock>>() {
        }.getType(), user, password);
    }*/


}
