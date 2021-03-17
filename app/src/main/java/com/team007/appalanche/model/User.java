package com.team007.appalanche.model;

import java.io.Serializable;

public class User implements Serializable {
    private String id;

    public User(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
