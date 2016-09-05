package com.sebangsa.adnanto.cobarealm.model;

import io.realm.RealmObject;

/**
 * Created by adnanto on 9/5/16.
 */
public class Dog extends RealmObject {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
