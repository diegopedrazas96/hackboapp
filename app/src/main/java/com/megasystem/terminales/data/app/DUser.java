package com.megasystem.terminales.data.app;

import android.content.Context;

import com.megasystem.terminales.data.Wrapper;
import com.megasystem.terminales.entity.app.User;

import java.util.List;

/**
 * Created by dpedrazas on 26/07/2017.
 */

public class DUser extends Wrapper {


    public DUser(Context context) {
        super(context, User.class);
    }

    public List<User> list() {
        return super.list("select * from " + tableName);
    }

    public User get() {
        return (User) this.get("select * from " + tableName);
    }


}