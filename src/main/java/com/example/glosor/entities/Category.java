package com.example.glosor.entities;


import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Category {

    private Integer id;
    private String name;
    private List<Glosa> glosor = new ArrayList<>();

    public Category() {
    }

    public Integer getId() {
        return id;
    }

    public Category(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Glosa> getGlosor() {
        return glosor;
    }

    public void setGlosor(List<Glosa> glosor) {
        this.glosor = glosor;
    }

    public void addGLosa(Glosa glosa) {
        glosor.add(glosa);
    }
}
