package com.megasystem.terminales.entity.app;

import com.megasystem.terminales.entity.Entity;
import com.megasystem.terminales.entity.annotation.Nullable;

import java.util.Date;

/**
 * Created by dpedrazas on 27/07/2017.
 */

public class ViajesFlota extends Entity {

    private  Integer ViajeId;
    private Integer FlotaId;

    public ViajesFlota() {
    }

    public Integer getViajeId() {
        return ViajeId;
    }

    public void setViajeId(Integer viajeId) {
        ViajeId = viajeId;
    }

    public Integer getFlotaId() {
        return FlotaId;
    }

    public void setFlotaId(Integer flotaId) {
        FlotaId = flotaId;
    }
}
