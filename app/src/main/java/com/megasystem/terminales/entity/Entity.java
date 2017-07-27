package com.megasystem.terminales.entity;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.megasystem.terminales.Application;
import com.megasystem.terminales.entity.annotation.Ignore;
import com.megasystem.terminales.entity.annotation.Key;

import java.io.Serializable;

@SuppressWarnings("serial")
public class Entity implements Serializable, Cloneable {

    @SerializedName("Id")
    @Key
    protected Long Id;

    @SerializedName("ClientAction")
    @Ignore
    private Action action = Action.None;

    public Long getId() {
        return Id;
    }

    public void setId(Long Id) {
        this.Id = Id;
    }

    public Action getAction() {
        return this.action;
    }

    public void setAction(Action value) {
        this.action = value;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getClone() {

        Entity obj = null;
        try {
            obj = (Entity) super.clone();
        } catch (CloneNotSupportedException e) {
            Log.e(Application.tag, e.getMessage());
        }
        return (T) obj;
    }

    @SuppressWarnings("unchecked")
    public <T extends Entity> T getMe() {
        return (T) this;
    }
}