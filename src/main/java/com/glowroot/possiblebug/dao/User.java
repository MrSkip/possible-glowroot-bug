package com.glowroot.possiblebug.dao;

import org.springframework.data.mongodb.core.mapping.Document;

@Document("user")
public class User {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
