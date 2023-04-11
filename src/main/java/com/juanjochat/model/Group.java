package com.juanjochat.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Group implements Serializable {
    private long id;
    private String name;
    private String type;
    private int filter;


    private List<User> members;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getFilter() {
        return filter;
    }

    public void setFilter(int filter) {
        this.filter = filter;
    }

    public Group(long id, String name, String type, int filter) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.filter = filter;
        this.members = new ArrayList<>();
    }

    public Group(){
        this.members = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Group{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", filter=" + filter +
                ", members=" + members +
                '}';
    }
}
